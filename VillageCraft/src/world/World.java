package world;

import gen.ChunkLoader;
import gen.WorldBuilder;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class World implements ScreenComponent{
	
	private volatile ArrayList<Chunk> chunks;
	
	private WorldBuilder worldBuilder;
	private ChunkLoader chunkLoader;
	//I don't like this, and would like to make worldBuilder have a chunkLoader object, which it would use to initially generate the world,
	//and generate all new chunks after that, with current calls to the chunkLoader instead calling a method in world builder,
	//but I'm not sure what the best way of doing that is
	
	public World(String fileName) throws FileNotFoundException {
		worldBuilder = new WorldBuilder(fileName);
		chunkLoader = new ChunkLoader(fileName);
	}
	
	public void generate()
	{
		this.chunks = worldBuilder.generateWorld();
	}
	
	public void update() {
		growVillages();
		for (Chunk c : chunks)
		{
			c.update();
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
			int maxDX = (int)(v.getRelativeChunkCenter().getX());
			int maxDY = (int)(v.getRelativeChunkCenter().getY());
			int oldVillageChunkLength = v.getChunkSideLength(v.getSizeRank()-v.getGrowth());
			for (int dX = (int) (-1*v.getRelativeChunkCenter().getX()); dX <= maxDX; ++dX)
			{
				for (int dY = (int) (-1*v.getRelativeChunkCenter().getY()); dY <= maxDY; ++dY)
				{
					if (dX < oldVillageChunkLength*-1 || dX > oldVillageChunkLength || dY < oldVillageChunkLength*-1 || dY > oldVillageChunkLength)
					{
						Chunk cur = getChunk(v.getX()+dX, v.getY()+dY);
						if (cur == null)
						{
							chunkLoader.load(v.getX()+dX, v.getY()+dY);
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
	public BufferedImage draw() { return draw(getSize()/-2, getSize()/-2, getSize(), getSize()); }
	public BufferedImage draw(int x, int y) { return draw(x, y, getSize(), getSize()); }
	public BufferedImage draw(int x, int y, int width, int height)
	{
		BufferedImage image = new BufferedImage(width*Chunk.getPixelLength(), height*Chunk.getPixelLength(), BufferedImage.TYPE_INT_ARGB);
		Graphics gI = image.getGraphics();
		
		drawChunks(x, y, width, height, gI);
		drawVillagers(x, y, gI);
		gI.dispose();
		return image;
	}
	
	private void drawChunks(int x0, int y0, int width, int height, Graphics gI)
	{
		for (int x = x0; x < x0+width; ++x)
		{
			for (int y = y0; y < y0+height; ++y)
			{
				Chunk c = getChunk(x, y);
				int cScreenX = (x-x0)*Chunk.getPixelLength(), cScreenY = (y-y0)*Chunk.getPixelLength();
				if (cScreenX + Chunk.getPixelLength() <= width*Chunk.getPixelLength() && cScreenX >= 0 && cScreenY+Chunk.getPixelLength() <= width*Chunk.getPixelLength() && cScreenY >= 0)
				{
					BufferedImage cI = c.draw();
					gI.drawImage(cI, cScreenX, cScreenY, Chunk.getPixelLength(), Chunk.getPixelLength(), null);
				}
			}
		}
	}
	
	private void drawVillagers(int x0, int y0, Graphics gI)
	{
		ArrayList<Village> villages = getVillages();
		for (Village v : villages)
		{
			for (Villager villager : v.getPopulation())
			{
				double vAbsX = villager.getRelativeX()+(v.getX()-x0+0.5)*Chunk.lengthOfChunk-0.5, vAbsY = villager.getRelativeY()+(v.getY()-y0+0.5)*Chunk.lengthOfChunk-.5;
				gI.drawImage(villager.draw(), (int)(vAbsX*Chunk.lengthOfBuilding), (int)(vAbsY*Chunk.lengthOfBuilding), null);
				//TODO will draw villager's multiple times right now
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
		for (Chunk c : chunks)
		{
			if (c.hasVillage() && c.getVillage().getGrowth() != 0)
			{
				grownVillageChunks.add(c);
			}
		}
		return grownVillageChunks;
	}
	
	private ArrayList<Village> getVillages()
	{
		ArrayList<Village> villages = new ArrayList<Village>();
		for (Chunk c : chunks)
		{
			if (c.hasVillage() && !villages.contains(c.getVillage()))
			{
				villages.add(c.getVillage());
			}
		}
		return villages;
	}
	
	private ArrayList<Chunk> getVillageChunks()
	{
		ArrayList<Chunk> villageChunks = new ArrayList<Chunk>();
		for (Chunk c : chunks)
		{
			if (c.hasVillage())
			{
				villageChunks.add(c);
			}
		}
		return villageChunks;
	}

	/*
	public Chunk getChunk(int index) {
		return chunks.get(index);
	}
	/*/
	public Chunk getChunk(int x, int y)
	{
		for (Chunk c : chunks)
		{
			if (c.getX() == x && c.getY() == y)
			{
				return c;
			}
		}
		return null;
	}
	//*/

	public void addChunk(Chunk chunk) {
		this.chunks.add(chunk);
	}
	
	public void setChunks(ArrayList<Chunk> chunks) {
		this.chunks = chunks;
	}

	/**
	 * @return the size of the world, in chunks
	 */
	public int getSize() {
		return (int)Math.sqrt(chunks.size());
	}
}
