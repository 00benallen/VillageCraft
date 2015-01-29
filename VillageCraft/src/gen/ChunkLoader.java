package gen;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

import main.Main;
import world.Chunk;
import world.Village;
import world.Villager;

public class ChunkLoader implements Runnable{
	private LinkedBlockingQueue<Point> toLoad = new LinkedBlockingQueue<Point>();
	private Thread loadingThread;
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
	
	public synchronized void beginLoading()
	{
		loadingThread = new Thread(this, "Chunk Loading Thread");
		loadingThread.start();
	}
	
	@Override
	public void run() {
		while (true)
		{
			Point p;
			try {
				p = toLoad.take();
			}catch (InterruptedException e) {
				e.printStackTrace();
				continue;
			}
			
			try {
				Main.getLoadedWorld().addChunk(load(p));
			}catch (NullPointerException e) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				Main.getLoadedWorld().addChunk(load(p));
			}
		}
	}

	private Chunk load(Point p)
	{
		return generate(p);
	}
	private Chunk generate(Point p)
	{
		Chunk c = new Chunk((new Random()).nextInt(Chunk.NUM_BIOMES-1), 0, p);
		if (p.x == 0 && p.y == 0)
		{
			ArrayList<Villager> population = new ArrayList<Villager>();
			population.add(new Villager());
			population.add(new Villager());
			population.add(new Villager());
			population.add(new Villager());
			population.add(new Villager());
			population.add(new Villager());
			population.add(new Villager());
			population.add(new Villager());
			population.add(new Villager());
			population.add(new Villager());
			population.add(new Villager());
			Village village = new Village(population, 0, 0);
			c.addVillage(village);
		}
		return c;
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
