package gen;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

import world.Chunk;

public class ChunkLoader implements Runnable{
	private LinkedBlockingQueue<Point> toLoad = new LinkedBlockingQueue<Point>();
	private Thread loadingThread;
	//TODO !!!!!!!!!!!!!!!!!!!
	//TODO fill in this class!
	private final File saveFile;
	public ChunkLoader(String saveFileName) throws FileNotFoundException
	{
		this.saveFile = new File(saveFileName);
		loadingThread = new Thread(this, "Chunk Loading Thread");
		loadingThread.start();
	}
	public ChunkLoader(File saveFile)
	{
		this.saveFile = saveFile;
		loadingThread = new Thread(this, "Chunk Loading Thread");
		loadingThread.start();
	}
	
	@Override
	public void run() {
		while (true)
		{
			try {
				load(toLoad.take());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private Chunk load(Point p)
	{
		return generate(p);
	}
	private Chunk generate(Point p)
	{
		return new Chunk((new Random()).nextInt(Chunk.NUM_BIOMES-1), 0, p);
	}
	
	public void queueLoad(Point p)
	{
		try {
			toLoad.put(p);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
