package asyetuntitled.client.util;

import java.util.List;

import com.google.common.collect.Lists;

import asyetuntitled.common.player.spawn.SpawnPoint;

public class ClientPlayerSpawnPoints
{
    private static final List<SpawnPoint> spawnPoints = Lists.newArrayList();
	
	public static void addSpawnPoint(SpawnPoint point)
	{
	    spawnPoints.add(point);
	}
	
	public static boolean isSpawnPoint(SpawnPoint point)
	{
	    for(SpawnPoint point2 : spawnPoints)
	    {
	        if(point2.isSamePoint(point))
	        {
	            return true;
	        }
	    }
	    return false;
	}
	
	public static void removeSpawnPoint(SpawnPoint point)
	{
	    spawnPoints.removeIf(point2 -> (point2.isSamePoint(point)));
	}
	
}