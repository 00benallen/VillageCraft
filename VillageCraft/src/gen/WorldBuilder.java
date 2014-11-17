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
		for(int j = 0; j < size*size; j++) {
			chunks.add(chunkLoader.load(j%size, j/size));
		}
		
		int center = (int) Math.sqrt(chunks.size())/2;
		ArrayList<Villager> populationCenter = new ArrayList<Villager>();
		populationCenter.add(new Villager());
		Village village = new Village(populationCenter, center, center);
		chunks.get(chunks.size()/2).addVillage(village, new Point2D.Double());
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
