package asyetuntitled.common.util.capability;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import asyetuntitled.common.block.BlockTouchStone;
import asyetuntitled.common.messages.ClientboundPacketSpawnpoint;
import asyetuntitled.common.messages.MessagesRegistry;
import asyetuntitled.common.player.spawn.SpawnPoint;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.PlayerRespawnLogic;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.server.ServerLifecycleHooks;

public class LevelSpawns 
{
    private Map<UUID, List<SpawnPoint>> playerSpawnPointList = new HashMap<>();
    private int respawnRadius = 101;
    
    public void addPlayerSpawn(ServerPlayer player, SpawnPoint point)
    {
        UUID uuid = player.getUUID();
        List<SpawnPoint> points = playerSpawnPointList.getOrDefault(uuid, new ArrayList<>());
        points.add(point);
        playerSpawnPointList.put(uuid, points);
        MessagesRegistry.sendToPlayer(new ClientboundPacketSpawnpoint(point, true), player);
    }
    
    public boolean consumeSpawn(ServerPlayer player, SpawnPoint point)
    {
        UUID uuid = player.getUUID();
        if(playerSpawnPointList.containsKey(uuid))
        {
            List<SpawnPoint> points = playerSpawnPointList.get(uuid);
            for(SpawnPoint point2 : points)
            {
                if(point2.isSamePoint(point))
                {
                    MessagesRegistry.sendToPlayer(new ClientboundPacketSpawnpoint(point, false), player);
                }
            }
            points.removeIf(point2 -> point2.isSamePoint(point));
            playerSpawnPointList.put(uuid, points);
        }
        return false;
    }
    
    public void destroySpawnForAll(SpawnPoint point)
    {
        for(UUID uuid : playerSpawnPointList.keySet())
        {
            List<SpawnPoint> points = playerSpawnPointList.get(uuid);
            for(SpawnPoint point2 : points)
            {
                if(point2.isSamePoint(point))
                {
                    PlayerList players = ServerLifecycleHooks.getCurrentServer().getPlayerList();
                    MessagesRegistry.sendToPlayer(new ClientboundPacketSpawnpoint(point, false), players.getPlayer(uuid));
                }
            }
            points.removeIf(point2 -> point2.isSamePoint(point));
            playerSpawnPointList.put(uuid, points);
        }
    }

    public void respawn(ServerPlayer serverPlayer)
    {
        MinecraftServer server = serverPlayer.server;
        SpawnPoint spawn = getFixedSpawn(serverPlayer);
        System.out.println("3: " + (spawn == null ? "null" : spawn.getBlockPos()));
        while(spawn == null)
        {
            spawn = getRandomSpawn(server.getLevel(Level.OVERWORLD), serverPlayer);
        }
        System.out.println("5: " + (spawn == null ? "null" : spawn.getBlockPos()));
        serverPlayer.setRespawnPosition(spawn.getDimension(), spawn.getBlockPos(), serverPlayer.yBodyRot, true, false);        
    }
    
    private SpawnPoint getFixedSpawn(ServerPlayer serverPlayer)
    {
        List<SpawnPoint> points = playerSpawnPointList.get(serverPlayer.getUUID());
        if(points == null || points.isEmpty())
        {
            return null;
        }
        else
        {
            SpawnPoint point = points.get(serverPlayer.level.random.nextInt(points.size()));
            Optional<Vec3> optional = BlockTouchStone.findStandUpPosition(serverPlayer.getType(), serverPlayer.level, point.getBlockPos());
            if(optional.isPresent())
            {
                return new SpawnPoint(new BlockPos(optional.get()), point.getDimension());
            }
            else
            {
                return null;
            }
        }
    }
    
    private SpawnPoint getRandomSpawn(ServerLevel level, ServerPlayer serverPlayer)
    {
        System.out.println("4");
        ChunkGenerator chunkgenerator = level.getChunkSource().getGenerator();
        ChunkPos chunkpos = new ChunkPos(level.random.nextInt(this.respawnRadius) - ((this.respawnRadius-1)/2), level.random.nextInt(this.respawnRadius) - ((this.respawnRadius-1)/2));
        int i = chunkgenerator.getSpawnHeight(level);
        if (i < level.getMinBuildHeight()) {
            BlockPos blockpos = chunkpos.getWorldPosition();
            i = level.getHeight(Heightmap.Types.WORLD_SURFACE, blockpos.getX() + 8, blockpos.getZ() + 8);
        }

        int k1 = 0;
        int j = 0;
        int k = 0;
        int l = -1;

         for(int j1 = 0; j1 < Mth.square(11); ++j1) {
            if (k1 >= -5 && k1 <= 5 && j >= -5 && j <= 5) {
               BlockPos blockpos1 = PlayerRespawnLogic.getSpawnPosInChunk(level, new ChunkPos(chunkpos.x + k1, chunkpos.z + j));
               if (blockpos1 != null) {
                  return new SpawnPoint(blockpos1, Level.OVERWORLD);
               }
            }

            if (k1 == j || k1 < 0 && k1 == -j || k1 > 0 && k1 == 1 - j) {
               int l1 = k;
               k = -l;
               l = l1;
            }

            k1 += k;
            j += l;
         }
         return null;
    }
}
