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
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Phaser;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import main.GraphicsMain;

public class World implements ScreenComponent{
	
	private volatile ArrayList<Chunk> chunks = new ArrayList<Chunk>();
	private volatile ReentrantReadWriteLock chunksLock = new ReentrantReadWriteLock(true);
	private volatile LinkedBlockingQueue<Chunk> toDraw = new LinkedBlockingQueue<Chunk>();
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
		chunksLock.readLock().lock();
		try {
			for (Chunk c : chunks)
			{
				if (c.update() && !toDraw.contains(c))
				{
					toDraw.add(c);
				}
			}
		} finally {
			chunksLock.readLock().unlock();
		}
		
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
						toDraw.add(cur);
					}
				}
			}
			if (complete)
			{
				v.resetGrowth();
			}
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
				if (cScreenX > width || cScreenX+Chunk.getPixelLength() < 0 || cScreenY > height || cScreenY+Chunk.getPixelLength() < 0) //Check that shouldn't fail!
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

		chunksLock.readLock().lock();
		try {
			for (Chunk c : chunks)
			{
				if (c.hasVillage() && c.getVillage().getGrowth() != 0)
				{
					grownVillageChunks.add(c);
				}
			}
		} finally {
			chunksLock.readLock().unlock();
		}
		return grownVillageChunks;
	}
	
	private ArrayList<Village> getVillages()
	{
		ArrayList<Village> villages = new ArrayList<Village>();
		chunksLock.readLock().lock();
		try {
			for (Chunk c : chunks)
			{
				if (c.hasVillage() && !villages.contains(c.getVillage()))
				{
					villages.add(c.getVillage());
				}
			}
		} finally {
			chunksLock.readLock().unlock();
		}
		return villages;
	}
	
	private ArrayList<Chunk> getVillageChunks()
	{
		ArrayList<Chunk> villageChunks = new ArrayList<Chunk>();
		
		chunksLock.readLock().lock();
		try {
			for (Chunk c : chunks)
			{
				if (c.hasVillage())
				{
					villageChunks.add(c);
				}
			}
		} finally {
			chunksLock.readLock().unlock();
		}
		return villageChunks;
	}

	public Chunk getChunk(int x, int y) throws ChunkNotLoadedException
	{
		chunksLock.readLock().lock();
		try {
			for (Chunk c : chunks)
			{
				if (c.getX() == x && c.getY() == y)
				{
					return c;
				}
			}
		} finally {
			chunksLock.readLock().unlock();
		}
		
		worldBuilder.getChunkLoader().queueLoad(new Point(x, y));
		
		throw new ChunkNotLoadedException();
	}
	
	public void addChunk(Chunk C)
	{
		chunksLock.writeLock().lock();
		try {
			chunks.add(C);
		} finally {
			chunksLock.writeLock().unlock();
		}
	}
}
