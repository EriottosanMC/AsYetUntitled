package asyetuntitled.common.event;

import java.util.List;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.datafixers.util.Pair;
import asyetuntitled.AsYetUntitled;
import asyetuntitled.client.test.ClientSanityData;
import asyetuntitled.common.command.SanityCommands;
import asyetuntitled.common.entity.ShadowCreature;
import asyetuntitled.common.entity.livingchest.LivingChest;
import asyetuntitled.common.messages.MessagesRegistry;
import asyetuntitled.common.messages.ServerboundPacketSkyFlash;
import asyetuntitled.common.util.CommonResourceLocations;
import asyetuntitled.common.util.DarknessHelper;
import asyetuntitled.common.util.capability.LevelChestProvider;
import asyetuntitled.common.util.capability.PlayerDarknessProvider;
import asyetuntitled.common.util.capability.PlayerSanityProvider;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.Container;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityLeaveWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.ItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerChangeGameModeEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = AsYetUntitled.MODID, bus = Bus.FORGE)
public class CapabilityEventsHandler {
	
	//Registers sanity commands
	@SubscribeEvent
	public static void onRegisterCommandEvent(RegisterCommandsEvent event) {
		CommandDispatcher<CommandSourceStack> commandDispatcher = event.getDispatcher();
		SanityCommands.register(commandDispatcher);
	}	

	//Changes sanity based on food eaten
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public static void itemUsedEvent(LivingEntityUseItemEvent.Finish event)
	{
		ItemStack stack = event.getItem();
		if(stack.isEdible() && event.getEntity() instanceof Player player)
		{
			player.getCapability(PlayerSanityProvider.PLAYER_SANITY).ifPresent(sanity -> {
				
				FoodProperties food = stack.getItem().getFoodProperties();
				int sanityResult = 0;
				if(food.getEffects().isEmpty())
				{
					List<SmeltingRecipe> cooking = player.level.getRecipeManager().getRecipesFor(RecipeType.SMELTING, new Container() {
						@Override
						public void clearContent() {}
						@Override
						public boolean stillValid(Player p_18946_) {return true;}
						@Override
						public void setItem(int p_18944_, ItemStack p_18945_) {}
						@Override
						public void setChanged() {}
						@Override
						public ItemStack removeItemNoUpdate(int p_18951_) {return null;}
						@Override
						public ItemStack removeItem(int p_18942_, int p_18943_) {return null;}
						@Override
						public boolean isEmpty() {return false;}
						@Override
						public ItemStack getItem(int p_18941_) {return stack;}
						@Override
						public int getContainerSize() {return 1;}
					}, player.level);
						
					if(cooking.isEmpty())
					{
						sanityResult = food.getNutrition();
					}
					else
					{
						sanityResult = -food.getNutrition();
					}
				}
				else
				{
					List<Pair<MobEffectInstance, Float>> effects = food.getEffects();
					for(Pair<MobEffectInstance, Float> pair : effects)
					{
						sanityResult += pair.getFirst().getEffect().isBeneficial() ? food.getNutrition() : -food.getNutrition();
					}
				}
				
				sanityResult *= 10;
				sanity.changeSanity(player, sanityResult);
			});
		}
	}
	
	//Interact with item
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public static void onInteractWith(RightClickItem event)
	{
		
	}
	
	//Interact with block - used to give sanity when eating cake
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public static void onInteractWith(RightClickBlock event)
	{
		Level level = event.getWorld();
		if(!level.isClientSide)
		{
			BlockState state = level.getBlockState(event.getPos());
			Block block = state.getBlock();
			Player player = event.getPlayer();
			if(block instanceof CakeBlock && player.canEat(false))
			{
				player.getCapability(PlayerSanityProvider.PLAYER_SANITY).ifPresent(sanity -> {
					sanity.changeSanity(player, 50);
				});
			}
		}
		
		
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public static void playerUpdateEvent(LivingUpdateEvent event)
	{
		if(event.getEntityLiving() instanceof Player player)
		{
			if(!player.level.isClientSide)
			{
				if(DarknessHelper.isTrueDarkness(player.level, player.blockPosition()))
				{
					player.getCapability(PlayerDarknessProvider.PLAYER_DARKNESS).ifPresent(cap -> {
						cap.increaseDarkness(player);
					});
				}
				else
				{
					 player.getCapability(PlayerDarknessProvider.PLAYER_DARKNESS).ifPresent(cap -> {
						cap.resetDarkness(player);
					});
				}
			}
			else
			{
				ClientSanityData.tickSanity();
				if(((ClientLevel) player.level).getSkyFlashTime() > 0)
				{
					MessagesRegistry.sendToServer(new ServerboundPacketSkyFlash());
				}
			}
		}
		
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public static void onEvent(PlayerChangeGameModeEvent event)
	{
		Player player = event.getPlayer();
		player.getCapability(PlayerSanityProvider.PLAYER_SANITY).ifPresent(sanity -> {
			sanity.setSanity(player, sanity.getSanityRaw(), true);
		});
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public static void onEntityJoinEvent(EntityJoinWorldEvent event)
	{
		if(event.getEntity() instanceof Player player)
		{
			player.getCapability(PlayerSanityProvider.PLAYER_SANITY).ifPresent(sanity -> {
				sanity.setSanity(player, sanity.getSanityRaw(), true);
			});
		}
		else if(event.getEntity() instanceof LivingChest chest && !event.getWorld().isClientSide)
		{
			event.getWorld().getCapability(LevelChestProvider.LEVEL_CHESTS).ifPresent(levelChests -> {
				levelChests.addChest(chest);
			});
		}
		else if(event.getEntity() instanceof LightningBolt bolt)
		{
		}
		
	}
	
	
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public static void onEvent(EntityLeaveWorldEvent event)
	{
		if(event.getEntity() instanceof LivingChest chest && !event.getWorld().isClientSide && !chest.isAlive())
		{
			event.getWorld().getCapability(LevelChestProvider.LEVEL_CHESTS).ifPresent(levelChests -> {
				levelChests.removeChest(chest);
			});
		}
	}
	
	@SubscribeEvent
	public static void attachCapabilitiesLevel(AttachCapabilitiesEvent<Level> event)
	{
		Level level = event.getObject();
		if(!level.isClientSide)
		{
			if(!event.getObject().getCapability(LevelChestProvider.LEVEL_CHESTS).isPresent())
			{
				event.addCapability(CommonResourceLocations.LEVEL_CHEST_CAPABILITY, new LevelChestProvider());
			}
		}
	}
	
	@SubscribeEvent
	public static void attachCapabilitiesEntity(AttachCapabilitiesEvent<Entity> event)
	{
		if(event.getObject() instanceof Player player)
		{
			if(!event.getObject().getCapability(PlayerDarknessProvider.PLAYER_DARKNESS).isPresent())
			{
				event.addCapability(CommonResourceLocations.DARKNESS_CAPABILITY, new PlayerDarknessProvider());
			}
			if(!event.getObject().getCapability(PlayerSanityProvider.PLAYER_SANITY).isPresent())
			{
				event.addCapability(CommonResourceLocations.SANITY_CAPABILITY, new PlayerSanityProvider());
			}
		}
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public static void onEvent(PlayerEvent.Clone event)
	{
		if(event.isWasDeath())
		{
			event.getOriginal().getCapability(PlayerDarknessProvider.PLAYER_DARKNESS).ifPresent(oldStore -> {
				event.getPlayer().getCapability(PlayerDarknessProvider.PLAYER_DARKNESS).ifPresent(newStore -> {
					newStore.resetDarkness(event.getPlayer());
				});
			});
			event.getOriginal().getCapability(PlayerSanityProvider.PLAYER_SANITY).ifPresent(oldStore -> {
				event.getPlayer().getCapability(PlayerSanityProvider.PLAYER_SANITY).ifPresent(newStore -> {
					newStore.resetSanity(event.getPlayer());
				});
			});
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
		float amount = event.getAmount();
		if(entity != null && entity instanceof Player player) 
		{
			DamageSource source = event.getSource();
			Entity attacker = source.getEntity();
			if(attacker instanceof Monster && !(attacker instanceof ShadowCreature))
			{
				player.getCapability(PlayerSanityProvider.PLAYER_SANITY).ifPresent((sanity) -> {
					sanity.changeSanity(player, -Math.round(amount * 10));
				});
			}
		}
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public static void onEvent(LivingDeathEvent event)
	{
		LivingEntity entity = event.getEntityLiving();
		DamageSource damage = event.getSource();
		if(entity != null && entity instanceof Mob && damage.getEntity() != null && damage.getEntity() instanceof Player player) 
		{
			float amount = entity.getMaxHealth();
			player.getCapability(PlayerSanityProvider.PLAYER_SANITY).ifPresent((sanity) -> {
				sanity.changeSanity(player, Math.round(amount * 10));
			});
		}
	}
	
	
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public static void livingUpdateEvent(LivingUpdateEvent event)
	{
		if(event.getEntityLiving() != null)
		{
			if(event.getEntityLiving() instanceof Player player)
			{
				 if(DarknessHelper.isTrueDarkness(player.level, player.blockPosition()))
				{
					if(!player.level.isClientSide)
					{
						player.getCapability(PlayerDarknessProvider.PLAYER_DARKNESS).ifPresent(cap -> {
							cap.increaseDarkness(player);
						});
					}
				}
				else
				{
					 if(!player.level.isClientSide)
					 {
						 player.getCapability(PlayerDarknessProvider.PLAYER_DARKNESS).ifPresent(cap -> {
							cap.resetDarkness(player);
						});
					 }
				}
				 
				if(!player.level.isClientSide)
				{
					player.getCapability(PlayerSanityProvider.PLAYER_SANITY).ifPresent(cap -> {
					});
				}
				else
				{
					ClientSanityData.tickSanity();
				}
				
			}
		}
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public static void onEvent(ItemPickupEvent event)
	{
	}	
	
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public static void onEvent(EntityAttributeCreationEvent event)
	{
	}
}
