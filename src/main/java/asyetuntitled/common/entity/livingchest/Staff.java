package asyetuntitled.common.entity.livingchest;

import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;

import asyetuntitled.common.entity.EntityRegistry;
import asyetuntitled.common.util.capability.LevelChestProvider;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

public class Staff extends Entity {
	
	private static final EntityDataAccessor<Boolean> HAS_CHEST = SynchedEntityData.defineId(Staff.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Optional<UUID>> CHEST_UUID = SynchedEntityData.defineId(Staff.class, EntityDataSerializers.OPTIONAL_UUID);
	private static final EntityDataAccessor<String> CHEST_NAME = SynchedEntityData.defineId(Staff.class, EntityDataSerializers.STRING);
	private static final EntityDataAccessor<Optional<UUID>> PLAYER_UUID = SynchedEntityData.defineId(Staff.class, EntityDataSerializers.OPTIONAL_UUID);
	private static final EntityDataAccessor<Integer> SPAWN_TICK = SynchedEntityData.defineId(Staff.class, EntityDataSerializers.INT);

	public Staff(EntityType<? extends Staff> staff, Level level) 
	{
		super(staff, level);
		this.blocksBuilding = true;
	}

	@Override
	protected void defineSynchedData()
	{
		this.entityData.define(HAS_CHEST, false);
		this.entityData.define(CHEST_UUID, Optional.empty());
		this.entityData.define(CHEST_NAME, "blank");
		this.entityData.define(PLAYER_UUID, Optional.empty());
		this.entityData.define(SPAWN_TICK, 0);
	}
	
	@Override
	public void push(Entity p_38373_) {
		if (p_38373_ instanceof Staff) {
			if (p_38373_.getBoundingBox().minY < this.getBoundingBox().maxY) {
				super.push(p_38373_);
			}
		} else if (p_38373_.getBoundingBox().minY <= this.getBoundingBox().minY) {
			super.push(p_38373_);
		}

	}
	
	@Override	
	public Direction getMotionDirection() {
		return this.getDirection().getClockWise();
	}


	@Override
	protected void readAdditionalSaveData(CompoundTag tag) 
	{
		this.setHasChest(tag.getBoolean("hasChest"));
		this.setChestName(tag.getString("chestName"));
		this.setSpawnTick(tag.getInt("spawnTick"));
		if (this.getPlayerUUID() != null) {
			tag.putUUID("Player", this.getPlayerUUID());
		}
		if (this.getChestUUID() != null) {
			tag.putUUID("Chest", this.getChestUUID());
		}
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag tag) 
	{
		tag.putBoolean("hasChest", this.entityData.get(HAS_CHEST));
		tag.putString("chestName", this.entityData.get(CHEST_NAME));
		tag.putInt("spawnTick", this.entityData.get(SPAWN_TICK));
		UUID uuid;
		if (tag.hasUUID("Player")) {
			uuid = tag.getUUID("Player");
			this.setPlayerUUID(uuid);
		}
		if (tag.hasUUID("Chest")) {
			uuid = tag.getUUID("Chest");
			this.setChestUUID(uuid);
		}
	}

	@Override
	public Packet<?> getAddEntityPacket() 
	{
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected float getEyeHeight(Pose p_38327_, EntityDimensions p_38328_) {
		return p_38328_.height * 0.85F;
	}

	@Override
	protected Entity.MovementEmission getMovementEmission() {
		return Entity.MovementEmission.NONE;
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
	
	@Override
	public boolean isPickable() 
	{
		return !this.isRemoved();
	}
	
	public void setHasChest(boolean b)
	{
		this.entityData.set(HAS_CHEST, b);
	}
	
	public boolean getHasChest()
	{
		return this.entityData.get(HAS_CHEST);
	}
	
	public void setChestName(String name)
	{
		this.entityData.set(CHEST_NAME, name);
	}
	
	public String getChestName()
	{
		return this.entityData.get(CHEST_NAME);
	}
	
	@Nullable
	public UUID getChestUUID() {
		return this.entityData.get(CHEST_UUID).orElse((UUID)null);
	}

	public void setChestUUID(@Nullable UUID uuid) {
		this.entityData.set(CHEST_UUID, Optional.ofNullable(uuid));
	}
	
	@Nullable
	public UUID getPlayerUUID() {
		return this.entityData.get(PLAYER_UUID).orElse((UUID)null);
	}

	public void setPlayerUUID(@Nullable UUID p_21817_) {
		this.entityData.set(PLAYER_UUID, Optional.ofNullable(p_21817_));
	}
	
	public int getSpawnTick()
	{
		return this.entityData.get(SPAWN_TICK);
	}
	
	public void setSpawnTick(int i)
	{
		this.entityData.set(SPAWN_TICK, i);
	}
	
	@Override
	public InteractionResult interact(Player player, InteractionHand hand) 
	{
		if(!this.level.isClientSide)
		{
			this.setPlayerUUID(player.getUUID());
		
			player.getLevel().getCapability(LevelChestProvider.LEVEL_CHESTS).ifPresent(levelChests -> {
				levelChests.printChests();
			});
			if(this.getChestUUID()!= null)
			{
//				player.getLevel().getCapability(LevelChestProvider.LEVEL_CHESTS).ifPresent(levelChests -> {
//					LivingChest chest = levelChests.getChest(this.getChestUUID().toString());
//				});
			}
		}
		return InteractionResult.CONSUME;
	}
	
	@Override
	public void tick() {
		super.tick();

		if(!this.level.isClientSide && this.getPlayerUUID() != null)
		{
			int spawnTick = this.getSpawnTick();
			this.setSpawnTick(spawnTick+1);
			
			if(spawnTick > 500 && this.random.nextInt(50) == 0)
			{
				UUID chestUUID = this.getChestUUID();

				if(chestUUID != null)
				{
					this.getLevel().getCapability(LevelChestProvider.LEVEL_CHESTS).ifPresent(levelChests -> {
						LivingChest chest = levelChests.getChest(this.getChestUUID().toString());
						if(chest == null || (chest != null && !chest.isAlive()))
						{
							this.setHasChest(false);
							this.setChestUUID(null);
							this.setSpawnTick(0);
						}
					});
//					LivingChest chest = UMM.CHESTLIST.getChest(this.getChestUUID().toString());
//					if(chest == null || (chest != null && !chest.isAlive()))
//					{
//						this.setHasChest(false);
//						this.setChestUUID(null);
//						this.setSpawnTick(0);
//					}
				}
				else
				{
					this.spawnNewChest();
				}
			}
		}
		
		this.checkInsideBlocks();
	}

	private void spawnNewChest()
	{
		String name = this.getChestName();
		UUID player = this.getPlayerUUID();
		if(name == null || name == "" || name == "blank")
		{
			name = EntityRegistry.NAMES[this.random.nextInt(EntityRegistry.NAMES.length)];
		}
		
		if(player != null)
		{
			LivingChest chest = new LivingChest(EntityRegistry.LIVING_CHEST.get(), this.level);
			chest.setOwnerUUID(player);
			chest.setTame(true);
			chest.setPos(this.getX(), this.getY(), this.getZ());
			chest.setName(name);;
			
			this.level.addFreshEntity(chest);
			
			this.setChestName(name);
			this.setChestUUID(chest.getUUID());
			this.setHasChest(true);
			this.setSpawnTick(0);
		}
		
	}
}
