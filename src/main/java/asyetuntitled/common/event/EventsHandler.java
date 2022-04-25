package asyetuntitled.common.event;

import java.util.Collection;

import asyetuntitled.AsYetUntitled;
import asyetuntitled.common.block.ModTags;
import asyetuntitled.common.entity.backpack.EntityBackpack;
import asyetuntitled.common.item.ItemBackpack;
import asyetuntitled.common.item.ItemsRegistry;
import asyetuntitled.common.util.capability.LevelSpawnsProvider;
import asyetuntitled.common.util.capability.PlayerSanityProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.CheckSpawn;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.entity.player.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.ItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.entity.player.SleepingTimeCheckEvent;
import net.minecraftforge.event.world.SleepFinishedTimeEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.server.ServerLifecycleHooks;

@Mod.EventBusSubscriber(modid = AsYetUntitled.MODID, bus = Bus.FORGE)
public class EventsHandler {

	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public static void onToolTip(ItemTooltipEvent event)
	{
		if(event.getItemStack().getItem() == ItemsRegistry.BLANK_SLOT.get())
		{
			event.getToolTip().clear();
		}
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public static void onSleepFinished(SleepFinishedTimeEvent event)
    {
	    ServerLevel server = (ServerLevel) event.getWorld();
	    long fullTime = server.dayTime();
	    long dayTime = fullTime % 24000L;
	    boolean isNight = dayTime > 13000L;
	    long addTime = 8000L;
	    long combinedTime = addTime + dayTime;
	   
	    if(isNight && combinedTime > 24000L)
	    {
	        addTime -= (combinedTime - 24000L);
	    }
	    else if(!isNight && combinedTime > 13000L)
	    {
	        addTime -= (combinedTime - 13000L);
	    }
	    event.setTimeAddition(fullTime + addTime);
	    server.players().stream().filter(Player::isSleepingLongEnough).forEach(player -> {
	        player.getCapability(PlayerSanityProvider.PLAYER_SANITY).ifPresent(sanity -> {
	           sanity.changeSanity(player, 1000); 
	        });
	    });
    }
	
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public static void onSleepInBed(PlayerSleepInBedEvent event)
    {
//	    Player player = event.getPlayer();
//        Level level = player.level;
//        BlockPos pos = event.getPos();
//        float timeSinceMidday =  event.getPlayer().level.getTimeOfDay(1.0F);
//        
//        Player.BedSleepingProblem problem = Result.DENY;
//        boolean isBed = level.getBlockState(pos).getBlock() instanceof BedBlock;
//        boolean isLeanTo = player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.ACACIA_BOAT;
//                //level.getBlockState(pos).getBlock() instanceof LeanToBlock;
//        System.out.println(timeSinceMidday + ";" + isBed + ";" + isLeanTo);
//        if(((timeSinceMidday < 0.25F || timeSinceMidday > 0.75F) && isLeanTo) || (timeSinceMidday > 0.25F && timeSinceMidday < 0.75F && isBed && !isLeanTo))
//        {
//            ret = Result.ALLOW;
//        }
//        event.setResult(ret);
//	    Player player = event.getPlayer();
//	    Player.BedSleepingProblem problem = event.getResultStatus();
//	    System.out.println(problem);
    }
	
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public static void onSleepTimeCheck(SleepingTimeCheckEvent event)
    {
	    event.setResult(Result.ALLOW);
	    Player player = event.getPlayer();
	    Level level = player.level;
	    BlockPos pos = event.getSleepingLocation().get();
	    float timeSinceMidday =  event.getPlayer().level.getTimeOfDay(1.0F);
	    Result ret = Result.DENY;
	    boolean isBed = level.getBlockState(pos).getBlock() instanceof BedBlock;
	    boolean isLeanTo = player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.ACACIA_BOAT;
	            //level.getBlockState(pos).getBlock() instanceof LeanToBlock;
	    if(((timeSinceMidday < 0.25F || timeSinceMidday > 0.75F) && isLeanTo) || (timeSinceMidday > 0.25F && timeSinceMidday < 0.75F && isBed && !isLeanTo))
	    {
	        ret = Result.ALLOW;
	    }
        event.setResult(ret);
    }
	
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public static void onBreakSpeed(BreakSpeed event)
	{
		BlockState state = event.getState();
		Player player = event.getPlayer();
		if(!state.is(ModTags.Blocks.DIGGABLE_BY_HAND) && (!(player.getMainHandItem().getItem() instanceof DiggerItem diggerItem) || !(diggerItem.isCorrectToolForDrops(player.getMainHandItem(), state))))
		{
			float f = event.getOriginalSpeed();
			event.setNewSpeed(f / 10);
		}
	}
	
	
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public static void onEvent(PlayerRespawnEvent event)
	{
	    
//        if(event.getPlayer() instanceof ServerPlayer player)
//        {
//            System.out.println("1");
//            ServerLifecycleHooks.getCurrentServer().getLevel(Level.OVERWORLD).getCapability(LevelSpawnsProvider.LEVEL_SPAWNS).ifPresent(spawns -> {
//                   System.out.println("2");
//                   spawns.respawn(player);
//            });
//        }
//		Player player = event.getPlayer();
//		if(player instanceof ServerPlayer serverPlayer)
//		{
//			BlockPos pos = new BlockPos(100, 100, 400);
//			ResourceKey<Level> dimension = serverPlayer.getRespawnDimension();
//			serverPlayer.setRespawnPosition(dimension, pos, 0, true, true);
//		}
//		PlayerList.
	}
	
	//Opening/closing GUI (except player inventory)
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public static void onEvent(PlayerContainerEvent event)
	{
	}
	
	
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public static void itemUsedEvent(LivingEntityUseItemEvent.Finish event)
	{
	}
	
	//Interact right/left click
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public static void onEvent(PlayerInteractEvent event)
	{
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public static void onEvent(EntityJoinWorldEvent event)
	{
		if(event.getEntity() instanceof Player player)
		{
			Inventory inventory = player.getInventory();
			ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
			int i = 0;
			if(chest.getItem() instanceof ItemBackpack)
			{
				i = ((ItemBackpack) chest.getItem()).getSize();
			}
			
			for(int j = 9; j < 36 - i; j++)
			{
				ItemStack is = inventory.getItem(j);
				if(!is.isEmpty() && !(is.getItem() == ItemsRegistry.BLANK_SLOT.get()))
				{
					player.drop(is, true);
				}
				inventory.setItem(j, new ItemStack(ItemsRegistry.BLANK_SLOT.get()));
			}
		}
		
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public static void onEvent(ItemCraftedEvent event)
	{
	}
	
	
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public static void onEvent(LivingHurtEvent event)
	{
		LivingEntity entity = event.getEntityLiving();
		if(entity != null) 
		{
			if(entity instanceof Zombie)
			{
				ItemStack is = entity.getItemBySlot(EquipmentSlot.CHEST);
				DamageSource source = event.getSource();
				float f = event.getAmount();
	
				if (!(f <= 0.0F)) {
			         f /= 4.0F;
			         if (f < 1.0F) {
			            f = 1.0F;
			         }
	
		            if ((!source.isFire() || !is.getItem().isFireResistant()) && is.getItem() instanceof ArmorItem) 
		            {
		            	is.hurtAndBreak((int)f, entity, (p_35997_) -> {
		                  p_35997_.broadcastBreakEvent(EquipmentSlot.CHEST);
		               });
		            }
	
			      }
			}
			else if(entity instanceof Player)
			{
				ItemStack is = entity.getItemBySlot(EquipmentSlot.CHEST);
				DamageSource source = event.getSource();
				Player player = (Player) entity;
				if(is != null && is.getItem() == ItemsRegistry.BACKPACK_BASE.get() && source instanceof EntityDamageSource)
				{
					EntityDamageSource attackSource = (EntityDamageSource) source;
					Entity attacker = attackSource.getEntity();
					if(attacker instanceof Zombie)
					{
						Zombie zombie = (Zombie) attacker;
						ItemStack isz = zombie.getMainHandItem();
						if(isz.isEmpty())
						{
							ItemBackpack bp = (ItemBackpack) is.getItem();
							int r = bp.getSize();
							
							int n = entity.getRandom().nextInt(r);
							int i = 36 - n;
							ItemStack b = player.inventoryMenu.getSlot(i).getItem();
							if(!b.isEmpty())
							{
								zombie.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(b.getItem(), 1));;
								b.setCount(b.getCount() - 1);	
								zombie.setDropChance(EquipmentSlot.MAINHAND, 1.0F);
							}
						}
					}
				}
				
		        if(player instanceof ServerPlayer sPlayer)
		        {
		            System.out.println("1");
		            ServerLifecycleHooks.getCurrentServer().getLevel(Level.OVERWORLD).getCapability(LevelSpawnsProvider.LEVEL_SPAWNS).ifPresent(spawns -> {
		                System.out.println("2");
		                spawns.respawn(sPlayer);
		            });
		        }
			}
		}
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public static void onEvent(LivingDeathEvent event)
	{
		LivingEntity entity = event.getEntityLiving();
		if(entity != null)
		{
			if(entity instanceof Zombie)
			{
				ItemStack is = entity.getItemBySlot(EquipmentSlot.CHEST);
				if(is.getItem() == ItemsRegistry.BACKPACK_BASE.get())
				{
					ItemBackpack backpack = (ItemBackpack) is.getItem();
					EntityBackpack entityBackpack = new EntityBackpack(backpack, entity, backpack.getColor(is));
					entityBackpack.setType(EntityBackpack.Type.NETHER);
					entityBackpack.setFromItemStack(is);
					entity.setItemSlot(EquipmentSlot.CHEST, ItemStack.EMPTY);
					
					if(!entity.level.isClientSide)
					{
						entity.level.addFreshEntity(entityBackpack);
					}
				}
			}
			else if(entity instanceof Player player)
			{
				ItemStack is = entity.getItemBySlot(EquipmentSlot.CHEST);
				if(is.getItem() == ItemsRegistry.BACKPACK_BASE.get())
				{
					ItemBackpack backpack = (ItemBackpack) is.getItem();
					EntityBackpack entityBackpack = new EntityBackpack(backpack, player, backpack.getColor(is));
					entityBackpack.setType(EntityBackpack.Type.NETHER);
					entityBackpack.setFromPlayerInventory(player);
					
					if(!entity.level.isClientSide)
					{
						entity.level.addFreshEntity(entityBackpack);
					}
				}
				
				for(int i = 0; i < 36; i++)
				{
					ItemStack d = player.inventoryMenu.getSlot(i).getItem();
					if(d.getItem() == ItemsRegistry.BLANK_SLOT.get())
					{
						player.inventoryMenu.getSlot(i).set(ItemStack.EMPTY);
					}
				}
			}
		}
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public static void onEvent(LivingEquipmentChangeEvent event)
	{
		LivingEntity entity = event.getEntityLiving();
		ItemStack itemOld = event.getFrom();
		ItemStack itemNew = event.getTo();
		
		if(entity == null)
		{
		}
		else if(entity instanceof Player && event.getSlot() == EquipmentSlot.CHEST)
		{
			if(itemOld.getItem() instanceof ItemBackpack bp && itemNew.getItem() instanceof ItemBackpack &&
					bp.isSameBackpackButHurtBestGuess(itemOld, itemNew))
			{
			}
			else
			{
				Player player = (Player) entity;
				if(player.isCreative())
				{
					for(int i = 0; i < player.inventoryMenu.getSize(); i++)
					{
						if(player.inventoryMenu.getSlot(i).getItem().getItem() == ItemsRegistry.BLANK_SLOT.get())
						{
							player.inventoryMenu.getSlot(i).set(ItemStack.EMPTY);
						}
					}
				}
				else
				{
					if(itemOld.getItem() instanceof ItemBackpack)
					{
						ItemBackpack backpack = (ItemBackpack) itemOld.getItem();
						EntityBackpack entityBackpack = new EntityBackpack(backpack, player, backpack.getColor(itemOld));
						entityBackpack.setType(EntityBackpack.Type.NETHER);
						entityBackpack.setItemDamage(itemOld.getDamageValue());
						entityBackpack.setFromPlayerInventory(player);
						if(!player.level.isClientSide)
						{
							player.level.addFreshEntity(entityBackpack);
						}
						player.inventoryMenu.setCarried(ItemStack.EMPTY);
					}
					
					NonNullList<ItemStack> items = null;
					int size = 0;
					
					if(itemNew.getItem() instanceof ItemBackpack)
					{
						size = ((ItemBackpack) itemNew.getItem()).getSize();
						CompoundTag tag = itemNew.getOrCreateTagElement("items");
						items = NonNullList.withSize(size, ItemStack.EMPTY);
						if(tag != null)
						{
							ContainerHelper.loadAllItems(tag, items);
						}
					}
	
					int n = 27 - size;
					int j = 0;
	
					for(int i = 9; i < 9 + n; i++)
					{
						ItemStack a = player.inventoryMenu.getSlot(i).getItem();
						if(a.getItem() != ItemsRegistry.BLANK_SLOT.get())
						{
							player.drop(a, true);
						}
						ItemStack blank = new ItemStack(ItemsRegistry.BLANK_SLOT.get());
						player.inventoryMenu.getSlot(i).set(blank);
					
					}
					for(int i = 9 + n; i < 36 ; i++)
					{
						player.inventoryMenu.getSlot(i).set(items.get(j));
						j++;
					}
					for(int i = 36; i < 46; i++)
					{
						ItemStack a = player.inventoryMenu.getSlot(i).getItem();
						if(a.getItem() instanceof ItemBackpack)
						{
							a.setCount(0);
						}
					}
				}
			}
		}
		else if(entity instanceof Zombie && itemOld != null && itemOld.getItem() == ItemsRegistry.BACKPACK_BASE.get() && (itemNew == null || itemNew.getItem() != ItemsRegistry.BACKPACK_BASE.get()))
		{
			CompoundTag tag = itemOld.getOrCreateTagElement("items");
			int r = ((ItemBackpack) itemOld.getItem()).getSize();
			NonNullList<ItemStack> items = NonNullList.withSize(r, ItemStack.EMPTY);
			if(tag != null)
			{
				ContainerHelper.loadAllItems(tag, items);
				if(!entity.level.isClientSide)
				{
					for(int i = 0; i < items.size(); i++)
					{
						entity.spawnAtLocation(items.get(i));
					}
				}
			}
		}
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public static void changeSpeedsEvent(LivingUpdateEvent event)
	{
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public static void onCheckSpawn(CheckSpawn event)
	{
//		LivingEntity entity = event.getEntityLiving();
//		MobSpawnType spawnType = event.getSpawnReason();
//		Result result = event.getResult();
//		if(entity instanceof Slime)
//		{
//			event.setResult(Result.DENY);
//			return;
//		}
////		System.out.println(entity + ";" + spawnType.name() + ";" + result.name());

	}	
	
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public static void onEvent(ItemPickupEvent event)
	{
	}	
	
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public static void onEvent(EntityAttributeCreationEvent event)
	{
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public static void onEvent(LivingDropsEvent event)
	{
		Collection<ItemEntity> c = event.getDrops();
		c.removeIf(i -> i.getItem().getItem() == ItemsRegistry.BLANK_SLOT.get());
	}
}
