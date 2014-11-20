package gen;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import world.Chunk;
import world.Village;
import world.Villager;

public class WorldBuilder {
	private final ChunkLoader chunkLoader;
	public WorldBuilder(String saveFileName) throws FileNotFoundException
	{
		this.chunkLoader = new ChunkLoader(saveFileName);
	}
	public WorldBuilder(File saveFile)
	{
		this.chunkLoader = new ChunkLoader(saveFile);
	}
	
	public ArrayList<Chunk> generateWorld()
	{		
		ArrayList<Chunk> chunks = new ArrayList<Chunk>();
		
		int size = 5;
		for(int j = size/(-2); j < size/2.0; j++)
		{
			for (int k = size/(-2); k < size/2.0; ++k)
			{
				chunks.add(chunkLoader.load(k, j));
			}
		}
		
		int center = 0;
		ArrayList<Villager> populationCenter = new ArrayList<Villager>();
		populationCenter.add(new Villager());
		Village village = new Village(populationCenter, center, center);
		chunks.get(chunks.size()/2).addVillage(village);
		populationCenter.add(new Villager());
		populationCenter.add(new Villager());
		populationCenter.add(new Villager());
		populationCenter.add(new Villager());
		populationCenter.add(new Villager());
		populationCenter.add(new Villager());
		populationCenter.add(new Villager());
		populationCenter.add(new Villager());
		populationCenter.add(new Villager());
		village.addPopulation(populationCenter);
		
		return chunks;
	}

}
