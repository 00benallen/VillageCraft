package gen;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;

import world.Chunk;

public class ChunkLoader {
	//TODO !!!!!!!!!!!!!!!!!!!
	//TODO fill in this class!
	private final File saveFile;
	public ChunkLoader(String saveFileName) throws FileNotFoundException
	{
		this.saveFile = new File(saveFileName);
	}
	public ChunkLoader(File saveFile)
	{
		this.saveFile = saveFile;
	}
	
	public Chunk load(int x, int y)
	{
		//TODO check if it's already generated, just not yet loaded
		return new Chunk((new Random()).nextInt(Chunk.NUM_BIOMES-1), 0, x, y);
	}
}
