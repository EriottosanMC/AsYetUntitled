package asyetuntitled.common.entity.livingchest;

import java.util.function.Consumer;

import asyetuntitled.AsYetUntitled;
import asyetuntitled.common.container.LivingChestContainer;
import asyetuntitled.common.entity.EntityRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.JumpControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;

public class LivingChest extends TamableAnimal 
{
	private static final EntityDataAccessor<Byte> INVENTORY_SIZE = SynchedEntityData.defineId(LivingChest.class, EntityDataSerializers.BYTE);
	
	private int jumpDuration;
	private int jumpTicks;
	private int jumpDelayTicks;
	private boolean wasOnGround;
	private ItemStackHandler inventory;
	
	public LivingChest(EntityType<? extends LivingChest> chest, Level level) 
	{
		super(chest, level);
		this.jumpControl = new LivingChest.ChestJumpControl(this);
		this.moveControl = new LivingChest.ChestMoveControl(this);
		this.setSpeedModifier(0.0D);
		this.inventory = this.createInventory();
	}
	
	public void setRandomName()
	{
		String name = EntityRegistry.NAMES[this.random.nextInt(EntityRegistry.NAMES.length)];
		this.setName(name);
	}
	
	public void setName(String name)
	{
		this.setCustomName(new TextComponent(name));
	}
	
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(INVENTORY_SIZE, (byte) 9);
	}
	
	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);

		tag.putByte("size", this.entityData.get(INVENTORY_SIZE));
		tag.put("inventory", this.inventory.serializeNBT());
	}
	
	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);

		this.entityData.set(INVENTORY_SIZE, tag.getByte("size"));
		this.inventory.deserializeNBT(tag.getCompound("inventory"));
	}
	
	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> p_146754_) {
		if (INVENTORY_SIZE.equals(p_146754_)) {
			this.updateInventory();
	      }
		super.onSyncedDataUpdated(p_146754_);
	}
	
	@Override
	public void tick()
	{
		super.tick();
	}
	
	/**
	 * Inventory ----------------------------
	 */
	
	protected ItemStackHandler createInventory() {
		if(this.getInventorySize() > 0)
		{
			return new ItemStackHandler(this.getInventorySize());
		}
		else
		{
			return new ItemStackHandler(9);

		}
	}
	
	public ItemStackHandler getInventory() {
		return this.inventory;
	}
	
	protected int getInventorySize() {
		return this.getEntityData().get(INVENTORY_SIZE);
	}

	
	/**
	 * General ----------------------------
	 */
	
	@Override
	public boolean canBeLeashed(Player p_21813_) {
		  return false;
	}
	
	@Override
	protected void registerGoals()
	{
	      this.goalSelector.addGoal(0, new FloatGoal(this));
	      this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
	      this.goalSelector.addGoal(1, new ChestNewOwnerGoal(this, Player.class, 12.0F, 1.0F, false));
	      this.goalSelector.addGoal(2, new PanicGoal(this, 1.25D));
	      this.goalSelector.addGoal(3, new ChestFollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
	      this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 0.6D));
	      this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0F));
	      this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
//	      this.goalSelector.addGoal(1, new Rabbit.RabbitPanicGoal(this, 2.2D));
//	      this.goalSelector.addGoal(2, new BreedGoal(this, 0.8D));
//	      this.goalSelector.addGoal(3, new TemptGoal(this, 1.0D, Ingredient.of(Items.CARROT, Items.GOLDEN_CARROT, Blocks.DANDELION), false));
//	      this.goalSelector.addGoal(4, new Rabbit.RabbitAvoidEntityGoal<>(this, Player.class, 8.0F, 2.2D, 2.2D));
//	      this.goalSelector.addGoal(4, new Rabbit.RabbitAvoidEntityGoal<>(this, Wolf.class, 10.0F, 2.2D, 2.2D));
//	      this.goalSelector.addGoal(4, new Rabbit.RabbitAvoidEntityGoal<>(this, Monster.class, 4.0F, 2.2D, 2.2D));
//	      this.goalSelector.addGoal(5, new Rabbit.RaidGardenGoal(this));
//	      this.goalSelector.addGoal(11, new LookAtPlayerGoal(this, Player.class, 10.0F));
	}

	@Override
	public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob mob)
	{
		return EntityRegistry.LIVING_CHEST.get().create(level);
	}
	
	public static AttributeSupplier.Builder createAttributes()
	{
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 3.0D).add(Attributes.MOVEMENT_SPEED, (double)0.8F);
	}
	
	public void startJumping()
	{
		this.setJumping(true);
		this.jumpDuration = 10;
		this.jumpTicks = 0;
	}
	
	@Override
	protected float getJumpPower() 
	{
		if (!this.horizontalCollision && (!this.moveControl.hasWanted() || !(this.moveControl.getWantedY() > this.getY() + 0.5D))) 
		{
			Path path = this.navigation.getPath();
	         if (path != null && !path.isDone()) 
	         {
	        	 Vec3 vec3 = path.getNextEntityPos(this);
	        	 if (vec3.y > this.getY() + 0.5D) 
	        	 {
	               return 0.5F;
	        	 }
	         }

	         return this.moveControl.getSpeedModifier() <= 0.6D ? 0.2F : 0.3F;
		} 
		else 
		{
			return 0.5F;
		}
	}
	
	@Override
	protected void jumpFromGround() 
	{
	      super.jumpFromGround();
	      double d0 = this.moveControl.getSpeedModifier();
	      if (d0 > 0.0D)
	      {
	         double d1 = this.getDeltaMovement().horizontalDistanceSqr();
	         if (d1 < 0.01D) 
	         {
	            this.moveRelative(0.1F, new Vec3(0.0D, 0.0D, 1.0D));
	         }
	      }

	      if (!this.level.isClientSide) 
	      {
	         this.level.broadcastEntityEvent(this, (byte)1);
	      }

	}
	
	public float getJumpCompletion(float p_29736_) 
	{
		return this.jumpDuration == 0 ? 0.0F : ((float)this.jumpTicks + p_29736_) / (float)this.jumpDuration;
	}
		
	@Override
	public void setJumping(boolean p_29732_) 
	{
		super.setJumping(p_29732_);
		if (p_29732_) 
		{
			this.playSound(this.getJumpSound(), this.getSoundVolume(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) * 0.8F);
		}
	}
	
	@Override
	public void aiStep() 
	{
		super.aiStep();
		if (this.jumpTicks != this.jumpDuration) 
		{
			++this.jumpTicks;
		} 
		else if (this.jumpDuration != 0)
		{
		         this.jumpTicks = 0;
		         this.jumpDuration = 0;
		         this.setJumping(false);
		}

	}
	
	@Override
	public void customServerAiStep() 
	{
	      if (this.jumpDelayTicks > 0) 
	      {
	         --this.jumpDelayTicks;
	      }

	      if (this.onGround) {
	         if (!this.wasOnGround) {
	            this.setJumping(false);
	            this.checkLandingDelay();
	         }

	         LivingChest.ChestJumpControl chestjumpcontrol = (LivingChest.ChestJumpControl)this.jumpControl;
	         if (!chestjumpcontrol.wantJump()) {
	            if (this.moveControl.hasWanted() && this.jumpDelayTicks == 0) {
	               Path path = this.navigation.getPath();
	               Vec3 vec3 = new Vec3(this.moveControl.getWantedX(), this.moveControl.getWantedY(), this.moveControl.getWantedZ());
	               if (path != null && !path.isDone()) {
	                  vec3 = path.getNextEntityPos(this);
	               }

	               this.facePoint(vec3.x, vec3.z);
	               this.startJumping();
	            }
	         } else if (!chestjumpcontrol.canJump()) {
	            this.enableJumpControl();
	         }
	      }

	      this.wasOnGround = this.onGround;
	   }
	

	public void setSpeedModifier(double p_29726_) 
	{
		this.getNavigation().setSpeedModifier(p_29726_);
		this.moveControl.setWantedPosition(this.moveControl.getWantedX(), this.moveControl.getWantedY(), this.moveControl.getWantedZ(), p_29726_);
	}
	
   private void facePoint(double p_29687_, double p_29688_) {
	   this.setYRot((float)(Mth.atan2(p_29688_ - this.getZ(), p_29687_ - this.getX()) * (double)(180F / (float)Math.PI)) - 90.0F);
   }	
	
   private void enableJumpControl() {
      ((LivingChest.ChestJumpControl)this.jumpControl).setCanJump(true);
   }

   private void disableJumpControl() {
      ((LivingChest.ChestJumpControl)this.jumpControl).setCanJump(false);
   }

   private void setLandingDelay() {
      if (this.moveControl.getSpeedModifier() < 2.2D) {
         this.jumpDelayTicks = 10;
      } else {
         this.jumpDelayTicks = 1;
      }

   }

   private void checkLandingDelay() {
      this.setLandingDelay();
      this.disableJumpControl();
   }
	
   protected SoundEvent getJumpSound() {
      return SoundEvents.WOODEN_DOOR_OPEN;
   }

   protected SoundEvent getAmbientSound() {
      return SoundEvents.HORSE_STEP_WOOD;
   }

   protected SoundEvent getHurtSound(DamageSource p_29715_) {
      return SoundEvents.ZOMBIE_ATTACK_WOODEN_DOOR;
   }

   protected SoundEvent getDeathSound() {
      return SoundEvents.WOODEN_TRAPDOOR_CLOSE;
   }

   public boolean doHurtTarget(Entity p_29659_) {
	   return p_29659_.hurt(DamageSource.mobAttack(this), 3.0F);
   }

   public SoundSource getSoundSource() {
      return SoundSource.NEUTRAL;
   }

	@Override
	public void handleEntityEvent(byte p_29663_) {
      if (p_29663_ == 1) {
         this.spawnSprintParticle();
         this.jumpDuration = 10;
         this.jumpTicks = 0;
      } else {
         super.handleEntityEvent(p_29663_);
      }
   }
   
	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		Item item = itemstack.getItem();
		if (this.level.isClientSide) {
         boolean flag = this.isOwnedBy(player) || this.isTame() || itemstack.is(Items.OAK_TRAPDOOR) && !this.isTame();
         return flag ? InteractionResult.CONSUME : InteractionResult.PASS;
      } else {
         if (this.isTame()) {
            if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
               if (!player.getAbilities().instabuild) {
                  itemstack.shrink(1);
               }

               this.heal((float)item.getFoodProperties().getNutrition());
               this.gameEvent(GameEvent.MOB_INTERACT, this.eyeBlockPosition());
               return InteractionResult.SUCCESS;
            }
            
            if(item == Items.ACACIA_BOAT)
            {
//            	this.entityData.set(INVENTORY_SIZE, (byte)12);
            	return InteractionResult.SUCCESS;
            }
           LivingChest chest = this;
            MenuProvider containerProvider = new MenuProvider() {
            	@Override
				public AbstractContainerMenu createMenu(int window, Inventory inv, Player player) {
					return new LivingChestContainer(window, inv, chest);
				}
				
				@Override
				public Component getDisplayName() {
					return new TranslatableComponent("screen." + AsYetUntitled.MODID + ".chester");
				}
            };
            this.setOrderedToSit(true);
            this.jumping = false;
            this.navigation.stop();
            this.setTarget((LivingEntity)null);
            NetworkHooks.openGui((ServerPlayer) player, 
            		containerProvider, 
            		new Consumer<FriendlyByteBuf>() {
            	@Override
				public void accept(FriendlyByteBuf buff) {
					buff.writeInt(LivingChest.this.getId());
				}
            });
            
			return InteractionResult.PASS;


         } else if (itemstack.is(Items.OAK_TRAPDOOR) ) {
            if (!player.getAbilities().instabuild) {
               itemstack.shrink(1);
            }

            if (this.random.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
               this.tame(player);
               this.navigation.stop();
               this.setTarget((LivingEntity)null);
               this.level.broadcastEntityEvent(this, (byte)7);
            } else {
               this.level.broadcastEntityEvent(this, (byte)6);
            }

            return InteractionResult.SUCCESS;
         }

         return super.mobInteract(player, hand);
      }
   }
	
	@Override
	protected void dropEquipment() 
	{
		super.dropEquipment();
		if (this.inventory != null) {
			for(int i = 0; i < this.inventory.getSlots(); ++i) {
				ItemStack itemstack = this.inventory.getStackInSlot(i);
				if (!itemstack.isEmpty() && !EnchantmentHelper.hasVanishingCurse(itemstack)) {
					this.spawnAtLocation(itemstack);
				}
			}

		}
	}
	
	private void updateInventory() {
		if(this.getInventorySize() == 0)
		{
			return;
		}
		ItemStackHandler oldInv = this.inventory;
		ItemStackHandler newInv = new ItemStackHandler(this.getInventorySize());
		if(oldInv.getSlots() > newInv.getSlots())
		{
			return;
		}
		
		for(int i = 0; i < oldInv.getSlots(); i++)
		{
			newInv.setStackInSlot(i, oldInv.getStackInSlot(i));
		}
		this.inventory = newInv;
	}
	
	/**
	 *-------Move Controls----------------------------------------------------------------
	 */

	public static class ChestJumpControl extends JumpControl 
	{
	      private final LivingChest chest;
	      private boolean canJump;
	
	      public ChestJumpControl(LivingChest chest) 
	      {
	         super(chest);
	         this.chest = chest;
	      }
	
	      public boolean wantJump() 
	      {
	         return this.jump;
	      }
	
	      public boolean canJump() 
	      {
	         return this.canJump;
	      }
	
	      public void setCanJump(boolean p_29759_) 
	      {
	         this.canJump = p_29759_;
	      }
	
	      public void tick()
	      {
	         if (this.jump)
	         {
	            this.chest.startJumping();
	            this.jump = false;
	         }
	
	      }
	   }
	
	   static class ChestMoveControl extends MoveControl
	   {
	      private final LivingChest chest;
	      private double nextJumpSpeed;
	
	      public ChestMoveControl(LivingChest p_29766_) 
	      {
	         super(p_29766_);
	         this.chest = p_29766_;
	      }
	
	      public void tick()
	      {
	         if (this.chest.onGround && !this.chest.jumping && !((LivingChest.ChestJumpControl)this.chest.jumpControl).wantJump()) 
	         {
	            this.chest.setSpeedModifier(0.0D);
	         }
	         else if (this.hasWanted()) 
	         {
	            this.chest.setSpeedModifier(this.nextJumpSpeed);
	         }
	
	         super.tick();
	      }
	
	      public void setWantedPosition(double p_29769_, double p_29770_, double p_29771_, double p_29772_) 
	      {
	         if (this.chest.isInWater()) 
	         {
	            p_29772_ = 1.5D;
	         }
	
	         super.setWantedPosition(p_29769_, p_29770_, p_29771_, p_29772_);
	         if (p_29772_ > 0.0D) 
	         {
	            this.nextJumpSpeed = p_29772_;
	         }
	
	      }
	   }

}

