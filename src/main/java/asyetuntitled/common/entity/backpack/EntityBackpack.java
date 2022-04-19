package asyetuntitled.common.entity.backpack;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import asyetuntitled.AsYetUntitled;
import asyetuntitled.common.entity.EntityRegistry;
import asyetuntitled.common.item.ItemBackpack;
import asyetuntitled.common.item.ItemsRegistry;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;

public class EntityBackpack extends Entity
{
	private static final EntityDataAccessor<Integer> DATA_ID_HURT = SynchedEntityData.defineId(EntityBackpack.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> DATA_ID_HURTDIR = SynchedEntityData.defineId(EntityBackpack.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Float> DATA_ID_DAMAGE = SynchedEntityData.defineId(EntityBackpack.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Integer> DATA_ID_TYPE = SynchedEntityData.defineId(EntityBackpack.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> DATA_ID_BUBBLE_TIME = SynchedEntityData.defineId(EntityBackpack.class, EntityDataSerializers.INT);
	public static final int BUBBLE_TIME = 60;
	private static final EntityDataAccessor<Integer> COLOR = SynchedEntityData.defineId(EntityBackpack.class, EntityDataSerializers.INT);
	private NonNullList<ItemStack> items;
	private static final EntityDataAccessor<Integer> ITEM_DAMAGE = SynchedEntityData.defineId(EntityBackpack.class, EntityDataSerializers.INT);
    public static final AttributeModifier CHILD_BACKPACK_SPEED = new AttributeModifier(AsYetUntitled.MODID + "childBackpackSpeed", -0.99F, AttributeModifier.Operation.MULTIPLY_TOTAL);
    private float invFriction;
	private float outOfControlTicks;
	private float deltaRotation;
	private int lerpSteps;
	private double lerpX;
	private double lerpY;
	private double lerpZ;
	private double lerpYRot;
	private double lerpXRot;
	private boolean inputLeft;
	private boolean inputRight;
	private boolean inputUp;
	private boolean inputDown;
	private double waterLevel;
	private float landFriction;
	private EntityBackpack.Status status;
	private EntityBackpack.Status oldStatus;
	private double lastYd;
	private boolean isAboveBubbleColumn;
	private boolean bubbleColumnDirectionIsDown;
	private float bubbleMultiplier;
	private float bubbleAngle;
	private float bubbleAngleO;

	private int size = 0;
	
	public EntityBackpack(EntityType<? extends EntityBackpack> p_38290_, Level p_38291_) {
		super(p_38290_, p_38291_);
		this.blocksBuilding = true;
	}

	public EntityBackpack(ItemBackpack backpack, LivingEntity wearer, int color)
	{
		super(EntityRegistry.BACKPACK.get(), wearer.level);
		this.size = backpack.getSize();
		this.items = NonNullList.withSize(this.size, ItemStack.EMPTY);
		this.setType(EntityBackpack.Type.NETHER);
		this.blocksBuilding = true;
		this.setColor(color);
		
		float f1 = (wearer.getYRot() + 180F) % 360F;
		this.setYRot(f1);
		
		float f2 = 1.0F;
		float f3 = Mth.sin(wearer.getYRot() * ((float)Math.PI / 180F));
		float f4 = Mth.cos(wearer.getYRot() * ((float)Math.PI / 180F));
		
		this.setPos(wearer.getX() + ((double)-f3 * f2), wearer.getEyeY() - 0.5F, wearer.getZ() + ((double) f4 * f2));
	}
	
	public void setFromPlayerInventory(Player player)
	{
//		int n = this.size;
//		int a = 0;
//		this.setItemDamage(player.getItemBySlot(EquipmentSlot.CHEST).getDamageValue());
//		for(int i = 36 - n; i < 36; i++)
//		{
//			this.items.set(a, player.inventoryMenu.getSlot(i).getItem());
//			player.inventoryMenu.getSlot(i).set(new ItemStack(UMMItems.BLANK_SLOT.get()));
//			a++;
//		}
		
		for (int i = 0; i < this.size; i++)
		{
			this.items.set(i, player.inventoryMenu.getSlot(36 - this.size + i).getItem());
			player.inventoryMenu.getSlot(36 - this.size + i).set(new ItemStack(ItemsRegistry.BLANK_SLOT.get()));
		}
	}
	
	public void setFromItemStack(ItemStack itemstack)
	{
		this.setItemDamage(itemstack.getDamageValue());
		CompoundTag tag = itemstack.getTagElement("items");
		if(tag != null)
		{
			ContainerHelper.loadAllItems(tag, items);
		}
	}

	@Override
	protected float getEyeHeight(Pose p_38327_, EntityDimensions p_38328_) {
		return p_38328_.height;
	}

	@Override
	protected Entity.MovementEmission getMovementEmission() {
		return Entity.MovementEmission.NONE;
	}

	@Override
	protected void defineSynchedData() {
		this.entityData.define(DATA_ID_HURT, 0);
		this.entityData.define(DATA_ID_HURTDIR, 1);
		this.entityData.define(DATA_ID_DAMAGE, 0.0F);
		this.entityData.define(DATA_ID_TYPE, EntityBackpack.Type.NETHER.ordinal());
		this.entityData.define(DATA_ID_BUBBLE_TIME, 0);
		this.entityData.define(COLOR, 0xA06540);
		this.entityData.define(ITEM_DAMAGE, 0);
	}
	
	@Override
	public boolean canCollideWith(Entity entity)
 	{
		return entity.canBeCollidedWith() || entity.isPushable();
 	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	@Override
	public boolean isPushable() {
		return true;
	}

	protected Vec3 getRelativePortalPosition(Direction.Axis p_38335_, BlockUtil.FoundRectangle p_38336_) {
		return LivingEntity.resetForwardDirectionOfRelativePortalPosition(super.getRelativePortalPosition(p_38335_, p_38336_));
	}

	public boolean hurt(DamageSource p_38319_, float p_38320_) {
		if (this.isInvulnerableTo(p_38319_)) {
			return false;
		} else if (!this.level.isClientSide && !this.isRemoved()) {
			this.setHurtDir(-this.getHurtDir());
			this.setHurtTime(10);
			this.setDamage(this.getDamage() + p_38320_ * 10.0F);
			this.markHurt();
			this.gameEvent(GameEvent.ENTITY_DAMAGED, p_38319_.getEntity());
			boolean flag = p_38319_.getEntity() instanceof Player && ((Player)p_38319_.getEntity()).getAbilities().instabuild;
			if (flag || this.getDamage() > 40.0F) {
				if (!flag && this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
					this.spawnAtLocation(this.getDropItem());
				}

				this.discard();
			}

			return true;
		} else {
			return true;
		}
	}

	public void onAboveBubbleCol(boolean p_38381_) {
		if (!this.level.isClientSide) {
			this.isAboveBubbleColumn = true;
			this.bubbleColumnDirectionIsDown = p_38381_;
			if (this.getBubbleTime() == 0) {
				this.setBubbleTime(60);
			}
		}

		this.level.addParticle(ParticleTypes.SPLASH, this.getX() + (double)this.random.nextFloat(), this.getY() + 0.7D, this.getZ() + (double)this.random.nextFloat(), 0.0D, 0.0D, 0.0D);
		if (this.random.nextInt(20) == 0) {
			this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), this.getSwimSplashSound(), this.getSoundSource(), 1.0F, 0.8F + 0.4F * this.random.nextFloat(), false);
		}

		this.gameEvent(GameEvent.SPLASH, this.getControllingPassenger());
	}

	public void push(Entity p_38373_) {
		if (p_38373_ instanceof EntityBackpack) {
			if (p_38373_.getBoundingBox().minY < this.getBoundingBox().maxY) {
				super.push(p_38373_);
			}
		} else if (p_38373_.getBoundingBox().minY <= this.getBoundingBox().minY) {
			super.push(p_38373_);
		}

	}

	public Item getDropItem() {
		switch(this.getBackpackType()) {
		case NETHER:
		default:
			return ItemsRegistry.BACKPACK_BASE.get();
		case SPRUCE:
			return Items.SPRUCE_BOAT;
		case BIRCH:
			return Items.BIRCH_BOAT;
		case JUNGLE:
			return Items.JUNGLE_BOAT;
		case ACACIA:
			return Items.ACACIA_BOAT;
		case DARK_OAK:
			return Items.DARK_OAK_BOAT;
		}
	}

	public void animateHurt() {
		this.setHurtDir(-this.getHurtDir());
		this.setHurtTime(10);
		this.setDamage(this.getDamage() * 11.0F);
	}

	public boolean isPickable() {
		return !this.isRemoved();
	}

	public void lerpTo(double p_38299_, double p_38300_, double p_38301_, float p_38302_, float p_38303_, int p_38304_, boolean p_38305_) {
		this.lerpX = p_38299_;
		this.lerpY = p_38300_;
		this.lerpZ = p_38301_;
		this.lerpYRot = (double)p_38302_;
		this.lerpXRot = (double)p_38303_;
		this.lerpSteps = 10;
	}

	public Direction getMotionDirection() {
		return this.getDirection().getClockWise();
	}

	public void tick() {
		this.oldStatus = this.status;
		this.status = this.getStatus();
		if (this.status != EntityBackpack.Status.UNDER_WATER && this.status != EntityBackpack.Status.UNDER_FLOWING_WATER) {
			this.outOfControlTicks = 0.0F;
		} else {
			++this.outOfControlTicks;
		}

		if (!this.level.isClientSide && this.outOfControlTicks >= 60.0F) {
			this.ejectPassengers();
		}

		if (this.getHurtTime() > 0) {
			this.setHurtTime(this.getHurtTime() - 1);
		}

		if (this.getDamage() > 0.0F) {
			this.setDamage(this.getDamage() - 1.0F);
		}
		super.tick();
		this.tickLerp();
		if (this.isControlledByLocalInstance()) {

			this.floatBoat();
			if (this.level.isClientSide) {
				this.controlBoat();
			}

			this.move(MoverType.SELF, this.getDeltaMovement());
		} else {
			this.setDeltaMovement(Vec3.ZERO);
		}

		this.tickBubbleColumn();

		this.checkInsideBlocks();
		
		List<Entity> list = this.level.getEntities(this, this.getBoundingBox().inflate((double)0.2F, (double)-0.01F, (double)0.2F), EntitySelector.pushableBy(this));
		
		if (!list.isEmpty()) {
			boolean flag = !this.level.isClientSide;

			for(int j = 0; j < list.size(); ++j) {
				Entity entity = list.get(j);
				if (entity instanceof Zombie) {
					Zombie zombie = (Zombie) entity;
					if (flag && zombie.getItemBySlot(EquipmentSlot.CHEST).isEmpty()) {
						ItemBackpack backpack = (ItemBackpack) ItemsRegistry.BACKPACK_BASE.get();
						ItemStack is = new ItemStack(ItemsRegistry.BACKPACK_BASE.get(), 1);
						CompoundTag tag = is.getOrCreateTagElement("items");
						ContainerHelper.saveAllItems(tag, this.items);
						is.save(tag);
						
						backpack.setColor(is, this.getColor());
						zombie.setItemSlot(EquipmentSlot.CHEST, is);
						zombie.setDropChance(EquipmentSlot.CHEST, 1.0F);
						if(zombie.isBaby())
						{
							AttributeInstance attributeinstance = zombie.getAttribute(Attributes.MOVEMENT_SPEED);
							attributeinstance.addTransientModifier(CHILD_BACKPACK_SPEED );
						}
						this.kill();
	               } else {
	                  this.push(entity);
	               }
	            }
	         }
	      }
	}

	private void tickBubbleColumn() {
		if (this.level.isClientSide) {
			int i = this.getBubbleTime();
			if (i > 0) {
				this.bubbleMultiplier += 0.05F;
			} else {
				this.bubbleMultiplier -= 0.1F;
			}

			this.bubbleMultiplier = Mth.clamp(this.bubbleMultiplier, 0.0F, 1.0F);
			this.bubbleAngleO = this.bubbleAngle;
			this.bubbleAngle = 10.0F * (float)Math.sin((double)(0.5F * (float)this.level.getGameTime())) * this.bubbleMultiplier;
		} else {
			if (!this.isAboveBubbleColumn) {
				this.setBubbleTime(0);
			}

			int k = this.getBubbleTime();
			if (k > 0) {
				--k;
				this.setBubbleTime(k);
				int j = 60 - k - 1;
				if (j > 0 && k == 0) {
					this.setBubbleTime(0);
					Vec3 vec3 = this.getDeltaMovement();
					if (this.bubbleColumnDirectionIsDown) {
						this.setDeltaMovement(vec3.add(0.0D, -0.7D, 0.0D));
					} else {
						this.setDeltaMovement(vec3.x, this.hasPassenger((p_150274_) -> {
							return p_150274_ instanceof Player;
						}) ? 2.7D : 0.6D, vec3.z);
					}
				}

				this.isAboveBubbleColumn = false;
			}
		}

	}

	@Nullable
	protected SoundEvent getPaddleSound() {
		switch(this.getStatus()) {
		case IN_WATER:
		case UNDER_WATER:
		case UNDER_FLOWING_WATER:
			return SoundEvents.BOAT_PADDLE_WATER;
		case ON_LAND:
			return SoundEvents.BOAT_PADDLE_LAND;
		case IN_AIR:
		default:
			return null;
		}
	}

	private void tickLerp() {
		if (this.isControlledByLocalInstance()) {
			this.lerpSteps = 0;
			this.setPacketCoordinates(this.getX(), this.getY(), this.getZ());
		}

		if (this.lerpSteps > 0) {
			double d0 = this.getX() + (this.lerpX - this.getX()) / (double)this.lerpSteps;
			double d1 = this.getY() + (this.lerpY - this.getY()) / (double)this.lerpSteps;
			double d2 = this.getZ() + (this.lerpZ - this.getZ()) / (double)this.lerpSteps;
			double d3 = Mth.wrapDegrees(this.lerpYRot - (double)this.getYRot());
			this.setYRot(this.getYRot() + (float)d3 / (float)this.lerpSteps);
			this.setXRot(this.getXRot() + (float)(this.lerpXRot - (double)this.getXRot()) / (float)this.lerpSteps);
			--this.lerpSteps;
			this.setPos(d0, d1, d2);
			this.setRot(this.getYRot(), this.getXRot());
		}
	}

	private EntityBackpack.Status getStatus() {
		EntityBackpack.Status boat$status = this.isUnderwater();
		if (boat$status != null) {
			this.waterLevel = this.getBoundingBox().maxY;
			return boat$status;
		} else if (this.checkInWater()) {
			return EntityBackpack.Status.IN_WATER;
		} else {
			float f = this.getGroundFriction();
			if (f > 0.0F) {
				this.landFriction = f;
				return EntityBackpack.Status.ON_LAND;
			} else {
				return EntityBackpack.Status.IN_AIR;
			}
		}
	}

	public float getWaterLevelAbove() {
		AABB aabb = this.getBoundingBox();
		int i = Mth.floor(aabb.minX);
		int j = Mth.ceil(aabb.maxX);
		int k = Mth.floor(aabb.maxY);
		int l = Mth.ceil(aabb.maxY - this.lastYd);
		int i1 = Mth.floor(aabb.minZ);
		int j1 = Mth.ceil(aabb.maxZ);
		BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

		label39:
		for(int k1 = k; k1 < l; ++k1) {
			float f = 0.0F;

			for(int l1 = i; l1 < j; ++l1) {
				for(int i2 = i1; i2 < j1; ++i2) {
					blockpos$mutableblockpos.set(l1, k1, i2);
					FluidState fluidstate = this.level.getFluidState(blockpos$mutableblockpos);
					if (fluidstate.is(FluidTags.WATER)) {
						f = Math.max(f, fluidstate.getHeight(this.level, blockpos$mutableblockpos));
					}

					if (f >= 1.0F) {
						continue label39;
					}
				}
			}

			if (f < 1.0F) {
				return (float)blockpos$mutableblockpos.getY() + f;
			}
		}

		return (float)(l + 1);
	}

	public float getGroundFriction() {
		AABB aabb = this.getBoundingBox();
		AABB aabb1 = new AABB(aabb.minX, aabb.minY - 0.001D, aabb.minZ, aabb.maxX, aabb.minY, aabb.maxZ);
		int i = Mth.floor(aabb1.minX) - 1;
		int j = Mth.ceil(aabb1.maxX) + 1;
		int k = Mth.floor(aabb1.minY) - 1;
		int l = Mth.ceil(aabb1.maxY) + 1;
		int i1 = Mth.floor(aabb1.minZ) - 1;
		int j1 = Mth.ceil(aabb1.maxZ) + 1;
		VoxelShape voxelshape = Shapes.create(aabb1);
		float f = 0.0F;
		int k1 = 0;
		BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

		for(int l1 = i; l1 < j; ++l1) {
			for(int i2 = i1; i2 < j1; ++i2) {
				int j2 = (l1 != i && l1 != j - 1 ? 0 : 1) + (i2 != i1 && i2 != j1 - 1 ? 0 : 1);
				if (j2 != 2) {
					for(int k2 = k; k2 < l; ++k2) {
						if (j2 <= 0 || k2 != k && k2 != l - 1) {
							blockpos$mutableblockpos.set(l1, k2, i2);
							BlockState blockstate = this.level.getBlockState(blockpos$mutableblockpos);
							if (!(blockstate.getBlock() instanceof WaterlilyBlock) && Shapes.joinIsNotEmpty(blockstate.getCollisionShape(this.level, blockpos$mutableblockpos).move((double)l1, (double)k2, (double)i2), voxelshape, BooleanOp.AND)) {
								f += blockstate.getFriction(this.level, blockpos$mutableblockpos, this);
								++k1;
							}
						}
					}
				}
			}
		}

		return f / (float)k1;
	}

	private boolean checkInWater() {
		AABB aabb = this.getBoundingBox();
		int i = Mth.floor(aabb.minX);
		int j = Mth.ceil(aabb.maxX);
		int k = Mth.floor(aabb.minY);
		int l = Mth.ceil(aabb.minY + 0.001D);
		int i1 = Mth.floor(aabb.minZ);
		int j1 = Mth.ceil(aabb.maxZ);
		boolean flag = false;
		this.waterLevel = -Double.MAX_VALUE;
		BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

		for(int k1 = i; k1 < j; ++k1) {
			for(int l1 = k; l1 < l; ++l1) {
				for(int i2 = i1; i2 < j1; ++i2) {
					blockpos$mutableblockpos.set(k1, l1, i2);
					FluidState fluidstate = this.level.getFluidState(blockpos$mutableblockpos);
					if (fluidstate.is(FluidTags.WATER)) {
						float f = (float)l1 + fluidstate.getHeight(this.level, blockpos$mutableblockpos);
						this.waterLevel = Math.max((double)f, this.waterLevel);
						flag |= aabb.minY < (double)f;
					}
				}
			}
		}

		return flag;
	}

	@Nullable
	private EntityBackpack.Status isUnderwater() {
		AABB aabb = this.getBoundingBox();
		double d0 = aabb.maxY + 0.001D;
		int i = Mth.floor(aabb.minX);
		int j = Mth.ceil(aabb.maxX);
		int k = Mth.floor(aabb.maxY);
		int l = Mth.ceil(d0);
		int i1 = Mth.floor(aabb.minZ);
		int j1 = Mth.ceil(aabb.maxZ);
		boolean flag = false;
		BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

		for(int k1 = i; k1 < j; ++k1) {
			for(int l1 = k; l1 < l; ++l1) {
				for(int i2 = i1; i2 < j1; ++i2) {
					blockpos$mutableblockpos.set(k1, l1, i2);
					FluidState fluidstate = this.level.getFluidState(blockpos$mutableblockpos);
					if (fluidstate.is(FluidTags.WATER) && d0 < (double)((float)blockpos$mutableblockpos.getY() + fluidstate.getHeight(this.level, blockpos$mutableblockpos))) {
						if (!fluidstate.isSource()) {
							return EntityBackpack.Status.UNDER_FLOWING_WATER;
						}

						flag = true;
					}
				}
			}
		}

		return flag ? EntityBackpack.Status.UNDER_WATER : null;
	}

	private void floatBoat() {
		double d1 = this.isNoGravity() ? 0.0D : (double)-0.04F;
		double d2 = 0.0D;
		this.invFriction = 0.05F;
		if (this.oldStatus == EntityBackpack.Status.IN_AIR && this.status != EntityBackpack.Status.IN_AIR && this.status != EntityBackpack.Status.ON_LAND) {
			this.waterLevel = this.getY(1.0D);
			this.setPos(this.getX(), (double)(this.getWaterLevelAbove() - this.getBbHeight()) + 0.101D, this.getZ());
			this.setDeltaMovement(this.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D));
			this.lastYd = 0.0D;
			this.status = EntityBackpack.Status.IN_WATER;
		} else {
			if (this.status == EntityBackpack.Status.IN_WATER) {
				d2 = (this.waterLevel - this.getY()) / (double)this.getBbHeight();
				this.invFriction = 0.9F;
			} else if (this.status == EntityBackpack.Status.UNDER_FLOWING_WATER) {
				d1 = -7.0E-4D;
				this.invFriction = 0.9F;
			} else if (this.status == EntityBackpack.Status.UNDER_WATER) {
				d2 = (double)0.01F;
				this.invFriction = 0.45F;
			} else if (this.status == EntityBackpack.Status.IN_AIR) {
				this.invFriction = 0.9F;
			} else if (this.status == EntityBackpack.Status.ON_LAND) {
				this.invFriction = this.landFriction;
				if (this.getControllingPassenger() instanceof Player) {
					this.landFriction /= 2.0F;
				}
			}

			Vec3 vec3 = this.getDeltaMovement();
			this.setDeltaMovement(vec3.x * (double)this.invFriction, vec3.y + d1, vec3.z * (double)this.invFriction);
			this.deltaRotation *= this.invFriction;
			if (d2 > 0.0D) {
				Vec3 vec31 = this.getDeltaMovement();
				this.setDeltaMovement(vec31.x, (vec31.y + d2 * 0.06153846016296973D) * 0.75D, vec31.z);
			}
		}

	}

	private void controlBoat() {
		if (this.isVehicle()) {
			float f = 0.0F;
			if (this.inputLeft) {
				--this.deltaRotation;
			}

			if (this.inputRight) {
				++this.deltaRotation;
			}

			if (this.inputRight != this.inputLeft && !this.inputUp && !this.inputDown) {
				f += 0.005F;
			}

			this.setYRot(this.getYRot() + this.deltaRotation);
			if (this.inputUp) {
				f += 0.04F;
			}

			if (this.inputDown) {
				f -= 0.005F;
			}

			this.setDeltaMovement(this.getDeltaMovement().add((double)(Mth.sin(-this.getYRot() * ((float)Math.PI / 180F)) * f), 0.0D, (double)(Mth.cos(this.getYRot() * ((float)Math.PI / 180F)) * f)));
		}
	}

	public void positionRider(Entity p_38379_) {
		if (this.hasPassenger(p_38379_)) {
			float f = 0.0F;
			float f1 = (float)((this.isRemoved() ? (double)0.01F : this.getPassengersRidingOffset()) + p_38379_.getMyRidingOffset());
			if (this.getPassengers().size() > 1) {
				int i = this.getPassengers().indexOf(p_38379_);
				if (i == 0) {
					f = 0.2F;
				} else {
					f = -0.6F;
				}

				if (p_38379_ instanceof Animal) {
					f = (float)((double)f + 0.2D);
				}
			}

			Vec3 vec3 = (new Vec3((double)f, 0.0D, 0.0D)).yRot(-this.getYRot() * ((float)Math.PI / 180F) - ((float)Math.PI / 2F));
			p_38379_.setPos(this.getX() + vec3.x, this.getY() + (double)f1, this.getZ() + vec3.z);
			p_38379_.setYRot(p_38379_.getYRot() + this.deltaRotation);
			p_38379_.setYHeadRot(p_38379_.getYHeadRot() + this.deltaRotation);
			this.clampRotation(p_38379_);
			if (p_38379_ instanceof Animal && this.getPassengers().size() > 1) {
				int j = p_38379_.getId() % 2 == 0 ? 90 : 270;
				p_38379_.setYBodyRot(((Animal)p_38379_).yBodyRot + (float)j);
				p_38379_.setYHeadRot(p_38379_.getYHeadRot() + (float)j);
			}

		}
	}

	public Vec3 getDismountLocationForPassenger(LivingEntity p_38357_) {
		Vec3 vec3 = getCollisionHorizontalEscapeVector((double)(this.getBbWidth() * Mth.SQRT_OF_TWO), (double)p_38357_.getBbWidth(), p_38357_.getYRot());
		double d0 = this.getX() + vec3.x;
		double d1 = this.getZ() + vec3.z;
		BlockPos blockpos = new BlockPos(d0, this.getBoundingBox().maxY, d1);
		BlockPos blockpos1 = blockpos.below();
		if (!this.level.isWaterAt(blockpos1)) {
			List<Vec3> list = Lists.newArrayList();
			double d2 = this.level.getBlockFloorHeight(blockpos);
			if (DismountHelper.isBlockFloorValid(d2)) {
				list.add(new Vec3(d0, (double)blockpos.getY() + d2, d1));
			}

			double d3 = this.level.getBlockFloorHeight(blockpos1);
			if (DismountHelper.isBlockFloorValid(d3)) {
				list.add(new Vec3(d0, (double)blockpos1.getY() + d3, d1));
			}

			for(Pose pose : p_38357_.getDismountPoses()) {
				for(Vec3 vec31 : list) {
					if (DismountHelper.canDismountTo(this.level, vec31, p_38357_, pose)) {
						p_38357_.setPose(pose);
						return vec31;
					}
				}
			}
		}

		return super.getDismountLocationForPassenger(p_38357_);
	}

	protected void clampRotation(Entity p_38322_) {
		p_38322_.setYBodyRot(this.getYRot());
		float f = Mth.wrapDegrees(p_38322_.getYRot() - this.getYRot());
		float f1 = Mth.clamp(f, -105.0F, 105.0F);
		p_38322_.yRotO += f1 - f;
		p_38322_.setYRot(p_38322_.getYRot() + f1 - f);
		p_38322_.setYHeadRot(p_38322_.getYRot());
	}

	public void onPassengerTurned(Entity p_38383_) {
		this.clampRotation(p_38383_);
	}

	protected void addAdditionalSaveData(CompoundTag tag) {
		tag.putString("Type", this.getBackpackType().getName());
		tag.putInt("Color", this.getColor());
		tag.putInt("Size", this.size);
		ContainerHelper.saveAllItems(tag, items);
		tag.putInt("Damage", this.getItemDamage());
	}

	protected void readAdditionalSaveData(CompoundTag tag) {
		if (tag.contains("Type", 8)) {
			this.setType(EntityBackpack.Type.byName(tag.getString("Type")));
		}
		this.setColor(tag.getInt("Color"));
		this.size = tag.getInt("Size");
		this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
     	ContainerHelper.loadAllItems(tag, this.items);
     	this.setItemDamage(tag.getInt("Damage"));
	}
	
	private int getContainerSize() {
		return this.size;
	}

	public InteractionResult interact(Player player, InteractionHand hand) {
		if (player.isSecondaryUseActive()) {
			return InteractionResult.PASS;
			
		} else if (this.outOfControlTicks < 60.0F) {
			
			
			if (!this.level.isClientSide) {
				ItemStack held = player.getItemInHand(hand);
				if(!held.isEmpty() && held.getItem() instanceof DyeItem) 
				{
					SoundEvent s = SoundEvents.FOX_BITE;
					player.playNotifySound(s, SoundSource.HOSTILE, 1F, 1F);
					int[] aint = new int[3];
					int i = 0;
					int j = 0;
					
					DyeItem dyeitem = (DyeItem) held.getItem();
					float[] afloat = dyeitem.getDyeColor().getTextureDiffuseColors();
		            int i2 = (int)(afloat[0] * 255.0F);
		            int l = (int)(afloat[1] * 255.0F);
		            int i1 = (int)(afloat[2] * 255.0F);
		            i += Math.max(i2, Math.max(l, i1));
		            aint[0] += i2;
		            aint[1] += l;
		            aint[2] += i1;
		            ++j;
		            
		            int j1 = aint[0] / j;
		            int k1 = aint[1] / j;
		            int l1 = aint[2] / j;
		            float f3 = (float)i / (float)j;
		            float f4 = (float)Math.max(j1, Math.max(k1, l1));
		            j1 = (int)((float)j1 * f3 / f4);
		            k1 = (int)((float)k1 * f3 / f4);
		            l1 = (int)((float)l1 * f3 / f4);
		            int j2 = (j1 << 8) + k1;
		            j2 = (j2 << 8) + l1;
		            this.setColor(j2);
		            held.setCount(held.getCount() - 1);

					return InteractionResult.CONSUME;
				}
				
				else 
				{
					ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
					if(!chest.isEmpty())
					{
						if(chest.getItem() == ItemsRegistry.BACKPACK_BASE.get())
						{
						}
						else {
							player.drop(chest, true);
						}
					}
					
					ItemBackpack backpack = (ItemBackpack) ItemsRegistry.BACKPACK_BASE.get();
					ItemStack is = new ItemStack(ItemsRegistry.BACKPACK_BASE.get(), 1);
					backpack.setColor(is, this.getColor());
					CompoundTag tag = is.getOrCreateTagElement("items");
					ContainerHelper.saveAllItems(tag, this.items);
					is.setDamageValue(this.getItemDamage());
					is.save(tag);
					
					is.getOrCreateTag().putInt("partsSize", 4);
					tag = is.getOrCreateTagElement("parts");
					NonNullList<ItemStack> parts = NonNullList.withSize(4, ItemStack.EMPTY);	
					parts.set(0, new ItemStack(ItemsRegistry.BACKPACK_MAIN.get()));
					parts.set(1, new ItemStack(ItemsRegistry.BACKPACK_FRONT.get()));
					parts.set(2, new ItemStack(ItemsRegistry.BACKPACK_MAIN_BUTTONS.get()));
					parts.set(3, new ItemStack(ItemsRegistry.BACKPACK_FRONT_BUTTONS.get()));
					ContainerHelper.saveAllItems(tag, parts);
					is.save(tag);
					
					player.setItemSlot(EquipmentSlot.CHEST, is);

//					NonNullList<ItemStack> list = this.items;
//					int n = 27 - list.size();
//					int a = 0;
//
//					for(int i = 9 + n; i < 36 ; i++)
//					{
//						player.inventoryMenu.getSlot(i).set(list.get(a));
//						a++;
//					}
					
					this.kill();
					return InteractionResult.CONSUME;
				}
			} else {
				return InteractionResult.SUCCESS;
			}
		} else {
			return InteractionResult.PASS;
		}
	}

	protected void checkFallDamage(double p_38307_, boolean p_38308_, BlockState p_38309_, BlockPos p_38310_) {
		this.lastYd = this.getDeltaMovement().y;
		if (!this.isPassenger()) {
			if (p_38308_) {
				if (this.fallDistance > 3.0F) {
					if (this.status != EntityBackpack.Status.ON_LAND) {
						this.resetFallDistance();
						return;
					}

					this.causeFallDamage(this.fallDistance, 1.0F, DamageSource.FALL);
					if (!this.level.isClientSide && !this.isRemoved()) {
						this.kill();
						if (this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
							for(int i = 0; i < 3; ++i) {
								this.spawnAtLocation(this.getBackpackType().getPlanks());
							}

							for(int j = 0; j < 2; ++j) {
								this.spawnAtLocation(Items.STICK);
							}
						}
					}
				}

				this.resetFallDistance();
			} else if (!this.level.getFluidState(this.blockPosition().below()).is(FluidTags.WATER) && p_38307_ < 0.0D) {
				this.fallDistance = (float)((double)this.fallDistance - p_38307_);
			}

		}
	}

	public void setDamage(float p_38312_) {
		this.entityData.set(DATA_ID_DAMAGE, p_38312_);
	}

	public float getDamage() {
		return this.entityData.get(DATA_ID_DAMAGE);
	}

	public void setHurtTime(int p_38355_) {
		this.entityData.set(DATA_ID_HURT, p_38355_);
	}

	public int getHurtTime() {
		return this.entityData.get(DATA_ID_HURT);
	}

	private void setBubbleTime(int p_38367_) {
		this.entityData.set(DATA_ID_BUBBLE_TIME, p_38367_);
	}

	private int getBubbleTime() {
		return this.entityData.get(DATA_ID_BUBBLE_TIME);
	}

	public float getBubbleAngle(float p_38353_) {
		return Mth.lerp(p_38353_, this.bubbleAngleO, this.bubbleAngle);
	}

	public void setHurtDir(int p_38363_) {
		this.entityData.set(DATA_ID_HURTDIR, p_38363_);
	}

	public int getHurtDir() {
		return this.entityData.get(DATA_ID_HURTDIR);
	}

	public void setType(EntityBackpack.Type p_38333_) {
		this.entityData.set(DATA_ID_TYPE, p_38333_.ordinal());
	}

	public EntityBackpack.Type getBackpackType() {
		return EntityBackpack.Type.byId(this.entityData.get(DATA_ID_TYPE));
	}

	protected boolean canAddPassenger(Entity p_38390_) {
		return this.getPassengers().size() < 2 && !this.isEyeInFluid(FluidTags.WATER);
	}

	public void setInput(boolean p_38343_, boolean p_38344_, boolean p_38345_, boolean p_38346_) {
		this.inputLeft = p_38343_;
		this.inputRight = p_38344_;
		this.inputUp = p_38345_;
		this.inputDown = p_38346_;
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	public boolean isUnderWater() {
		return this.status == EntityBackpack.Status.UNDER_WATER || this.status == EntityBackpack.Status.UNDER_FLOWING_WATER;
	}

	// Forge: Fix MC-119811 by instantly completing lerp on board
	@Override
	protected void addPassenger(Entity passenger) {
		super.addPassenger(passenger);
		if (this.isControlledByLocalInstance() && this.lerpSteps > 0) {
			this.lerpSteps = 0;
			this.absMoveTo(this.lerpX, this.lerpY, this.lerpZ, (float)this.lerpYRot, (float)this.lerpXRot);
		}
	}

	public ItemStack getPickResult() {
		return new ItemStack(this.getDropItem());
	}

	public static enum Status {
		IN_WATER,
		UNDER_WATER,
		UNDER_FLOWING_WATER,
		ON_LAND,
		IN_AIR;
	}

	public static enum Type {
		NETHER(Blocks.WARPED_PLANKS, "nether"),
		SPRUCE(Blocks.SPRUCE_PLANKS, "spruce"),
		BIRCH(Blocks.BIRCH_PLANKS, "birch"),
		JUNGLE(Blocks.JUNGLE_PLANKS, "jungle"),
		ACACIA(Blocks.ACACIA_PLANKS, "acacia"),
		DARK_OAK(Blocks.DARK_OAK_PLANKS, "dark_oak");

		private final String name;
		private final Block planks;

		private Type(Block p_38427_, String p_38428_) {
			this.name = p_38428_;
			this.planks = p_38427_;
		}

		public String getName() {
			return this.name;
		}

		public Block getPlanks() {
			return this.planks;
		}

		public String toString() {
			return this.name;
		}

		public static EntityBackpack.Type byId(int p_38431_) {
			EntityBackpack.Type[] aboat$type = values();
			if (p_38431_ < 0 || p_38431_ >= aboat$type.length) {
				p_38431_ = 0;
			}

			return aboat$type[p_38431_];
		}

		public static EntityBackpack.Type byName(String p_38433_) {
			EntityBackpack.Type[] aboat$type = values();

			for(int i = 0; i < aboat$type.length; ++i) {
				if (aboat$type[i].getName().equals(p_38433_)) {
					return aboat$type[i];
				}
			}

			return aboat$type[0];
		}
	}
	
	public int getColor()
	{
		return this.entityData.get(COLOR);
	}
	
	public void setColor(int c)
	{
		this.entityData.set(COLOR, c);
	}
	
	public int getItemDamage()
	{
		return this.entityData.get(ITEM_DAMAGE);
	}
	
	public void setItemDamage(int d)
	{
		this.entityData.set(ITEM_DAMAGE, d);
	}
}
