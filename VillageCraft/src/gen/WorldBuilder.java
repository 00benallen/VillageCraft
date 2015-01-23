package gen;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import main.Main;
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
	
	public void loadWorld()
	{
		chunkLoader.beginLoading();
		generateWorld();
	}
	
	private void generateWorld()
	{
		int initSize = 10;
		for(int j = initSize/(-2); j < initSize/2.0; j++)
		{
			for (int k = initSize/(-2); k < initSize/2.0; ++k)
			{
				chunkLoader.queueLoad(new Point(k, j));
			}
		}
	}

	public ChunkLoader getChunkLoader()
	{
		return chunkLoader;
	}
}
