package gen;

import java.awt.Point;
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
	
	public ArrayList<Chunk> loadWorld()
	{
		return generateWorld();
	}
	
	public ArrayList<Chunk> generateWorld()
	{		
		ArrayList<Chunk> chunks = new ArrayList<Chunk>();
		
		int initSize = 10;
		for(int j = initSize/(-2); j < initSize/2.0; j++)
		{
			for (int k = initSize/(-2); k < initSize/2.0; ++k)
			{
				chunkLoader.queueLoad(new Point(k, j));
			}
		}
		
		int center = 0;
		ArrayList<Villager> population = new ArrayList<Villager>();
		population.add(new Villager());
		Village village = new Village(population, center, center);
		chunks.get((int)((initSize/2+0.5)*initSize)).addVillage(village);
		population.add(new Villager());
		population.add(new Villager());
		population.add(new Villager());
		population.add(new Villager());
		population.add(new Villager());
		population.add(new Villager());
		population.add(new Villager());
		population.add(new Villager());
		population.add(new Villager());
		village.addPopulation(population);
		
		return chunks;
	}

	public ChunkLoader getChunkLoader()
	{
		return chunkLoader;
	}
}
