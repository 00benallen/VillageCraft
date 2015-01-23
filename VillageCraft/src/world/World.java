package world;

import exceptions.ChunkNotLoadedException;
import gen.ChunkLoader;
import gen.WorldBuilder;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.concurrent.Phaser;

import main.GraphicsMain;

public class World implements ScreenComponent{
	
	private volatile ArrayList<Chunk> chunks = new ArrayList<Chunk>();
	private volatile int chunksStability = 0; //+'ive = # threads accessing, -'ive = # threads editing
	private volatile Object chunkStabilityCountLock = new Object();
	private volatile Phaser chunkStabilityPhaser = new Phaser() {
		protected boolean onAdvance(int phase, int registeredParties) {
			synchronized(chunkStabilityCountLock)
			{
				if (registeredParties == 0)
				{
					chunksStability = 0;
				}
				else
				{
					chunksStability = Math.max(Math.min(chunksStability*-1, 1), -1);
				}
			}
			return false;
		}
	};
	
	private WorldBuilder worldBuilder;
	
	public World(String fileName) throws FileNotFoundException {
		worldBuilder = new WorldBuilder(fileName);
	}
	
	public void load()
	{
		worldBuilder.loadWorld();
	}
	
	public void update() {
		growVillages();
		lockEditChunks();
		for (Chunk c : chunks)
		{
			c.update();
		}
		unlockEditChunks();
		
		ArrayList<Village> villages = getVillages();
		for (Village v : villages)
		{
			v.update();
		}
	}
	
	public void growVillages()
	{
		ArrayList<Village> toGrow = getGrownVillages();
		for (Village v : toGrow)
		{
			boolean complete = true;
			int maxDX = (int)(v.getRelativeChunkCenter().getX());
			int maxDY = (int)(v.getRelativeChunkCenter().getY());
			int oldVillageChunkLength = v.getChunkSideLength(v.getSizeRank()-v.getGrowth());
			for (int dX = (int) (-1*v.getRelativeChunkCenter().getX()); dX <= maxDX; ++dX)
			{
				for (int dY = (int) (-1*v.getRelativeChunkCenter().getY()); dY <= maxDY; ++dY)
				{
					if (dX < oldVillageChunkLength*-1 || dX > oldVillageChunkLength || dY < oldVillageChunkLength*-1 || dY > oldVillageChunkLength)
					{
						Chunk cur;
						try {
							cur = getChunk(v.getX()+dX, v.getY()+dY);
						} catch (ChunkNotLoadedException e) {
							complete = false;
							continue;
						}
						//TODO decide what to do if there is already a village there
						cur.addVillage(v);
					}
				}
			}
			v.resetGrowth();
		}
	}
	
	@Override
	public BufferedImage draw() { return draw(GraphicsMain.WIDTH/-2, GraphicsMain.HEIGHT/-2, GraphicsMain.WIDTH, GraphicsMain.HEIGHT); }
	public BufferedImage draw(int x, int y) { return draw(x, y, GraphicsMain.WIDTH, GraphicsMain.HEIGHT); }
	public BufferedImage draw(int x, int y, int width, int height)
	{
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D gI = image.createGraphics();
		
		drawChunks(x, y, width, height, gI);
		drawVillagers(x, y, gI);
		gI.dispose();
		return image;
	}
	
	private void drawChunks(int x0, int y0, int width, int height, Graphics2D gI)
	{
		for (int x = (int) ((x0-.0)/Chunk.getPixelLength()-0.5); x <= Math.ceil(((double)x0+width)/Chunk.getPixelLength()); ++x)
		{
			for (int y = (int) ((y0-.0)/Chunk.getPixelLength()-0.5); y <= Math.ceil(((double)y0+height)/Chunk.getPixelLength()); ++y)
			{
				Chunk c;
				try {
					c = getChunk(x, y);
				} catch (ChunkNotLoadedException e) {
					continue;
				}
				
				int cScreenX = (int)((x-0.5)*Chunk.getPixelLength()-x0), cScreenY = (int)((y-0.5)*Chunk.getPixelLength()-y0);
				if (!(cScreenX <= width && cScreenX+Chunk.getPixelLength() >= 0 && cScreenY <= width && cScreenY+Chunk.getPixelLength() >= 0)) //Check that shouldn't fail!
				{
					System.out.println("WHOOPS! Check src.world.World.drawChunks()!");
				}
				BufferedImage cI = c.draw();
				gI.drawImage(cI, cScreenX, cScreenY, Chunk.getPixelLength(), Chunk.getPixelLength(), null);
			}
		}
	}
	
	private void drawVillagers(int screenX0, int screenY0, Graphics2D gI)
	{
		ArrayList<Village> villages = getVillages();
		for (Village v : villages)
		{
			for (Villager villager : v.getPopulation())
			{
				double vAbsX = villager.getRelativeX()+(v.getX()-screenX0+0.5)*Chunk.lengthOfChunk-0.5, vAbsY = villager.getRelativeY()+(v.getY()-screenY0+0.5)*Chunk.lengthOfChunk-0.5;
				//gI.drawImage(villager.draw(), (int)(vAbsX*Building.lengthOfBuilding), (int)(vAbsY*Building.lengthOfBuilding), null);
			}
		}
	}
	
	private ArrayList<Village> getGrownVillages()
	{
		ArrayList<Village> villages = getVillages();
		ArrayList<Village> grownVillages = new ArrayList<Village>();
		for (int i = 0; i < villages.size(); ++i)
		{
			if (villages.get(i).getGrowth() != 0)
			{
				grownVillages.add(villages.get(i));
			}
		}
		return grownVillages;
	}
	
	private ArrayList<Chunk> getGrownVillageChunks()
	{
		ArrayList<Chunk> grownVillageChunks = new ArrayList<Chunk>();

		lockEditChunks();
		for (Chunk c : chunks)
		{
			if (c.hasVillage() && c.getVillage().getGrowth() != 0)
			{
				grownVillageChunks.add(c);
			}
		}
		unlockEditChunks();
		return grownVillageChunks;
	}
	
	private ArrayList<Village> getVillages()
	{
		ArrayList<Village> villages = new ArrayList<Village>();
		// Iterator has issues when the ArrayList is modified. 
		// if the draw thread tries to zoom out and load new chunks during the time this loop is iterating,
		// the thread will crash
		lockEditChunks();
		for (Chunk c : chunks)
		{
			if (c.hasVillage() && !villages.contains(c.getVillage()))
			{
				villages.add(c.getVillage());
			}
		}
		unlockEditChunks();
		return villages;
	}
	
	private ArrayList<Chunk> getVillageChunks()
	{
		ArrayList<Chunk> villageChunks = new ArrayList<Chunk>();
		
		lockEditChunks();
		for (Chunk c : chunks)
		{
			if (c.hasVillage())
			{
				villageChunks.add(c);
			}
		}
		unlockEditChunks();
		return villageChunks;
	}

	public Chunk getChunk(int x, int y) throws ChunkNotLoadedException
	{
		lockEditChunks();
		for (Chunk c : chunks)
		{
			if (c.getX() == x && c.getY() == y)
			{
				unlockEditChunks();
				return c;
			}
		}
		unlockEditChunks();
		
		worldBuilder.getChunkLoader().queueLoad(new Point(x, y));
		
		throw new ChunkNotLoadedException();
	}
	
	public void addChunk(Chunk C)
	{
		destabalizeChunks();
		chunks.add(C);
		stabalizeChunks();
	}
		
	
	/**
	 *  Prevents other threads from editing <b>World.chunks</b>. 
	 *  Calling this will freeze the thread if another thread has called <b>World.destabalizeChunks()</b>
	 *  without calling <b>World.stabalizeChunks()</b>
	 */
	/*
	 * #UnArrivedParties = # threads currently requiring stability level
	 * #ArrivedParties = # threads waiting for opposite stability level
	 * #RegisteredParites == 0 = no one is accessing/modifying
	 */
	public void lockEditChunks()
	{
		chunkStabilityPhaser.register();
		if (this.chunkStabilityPhaser.getUnarrivedParties() > 1 && this.chunksStability < 0) //number threads currently editing > 0
		{
			this.chunkStabilityPhaser.arriveAndAwaitAdvance(); //wait until threads editing finish
		}
		//SOLVED I THINK
		//#registered threads > 0 then happened at momentary blip before decrement of chunk stability by threads waiting to modify chunks
		//#retistered threads == 0 then no threads waiting
		//other thread performs above check, but for a chunk stability of > 0
		//continues past believing that no threads are waiting for stable access
		//then begins editing, and we have a problem
		//not only that but increment operator messes up
		synchronized(chunkStabilityCountLock)
		{
			++this.chunksStability;
		}
	}
	public void unlockEditChunks()
	{
		chunkStabilityPhaser.arriveAndDeregister();
	}
	
	/**
	 *  Prevents other threads requiring stability of <b>World.chunks</b> from continuing 
	 *  Calling this will freeze the thread if another thread has called <b>World.lockEditChunks()</b>
	 *  without calling <b>World.unlockEditChunks()</b>
	 */
	public void destabalizeChunks()
	{
		chunkStabilityPhaser.register();
		if (this.chunkStabilityPhaser.getUnarrivedParties() > 1 && this.chunksStability > 0) //number threads currently editing > 0
		{
			this.chunkStabilityPhaser.arriveAndAwaitAdvance(); //wait until threads editing finish
		}
		
		synchronized(chunkStabilityCountLock)
		{
			--this.chunksStability;
		}
	}
	
	public void stabalizeChunks()
	{
		chunkStabilityPhaser.arriveAndDeregister();
	}
}
