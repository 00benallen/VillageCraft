package world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import main.Render;

public class Chunk implements ScreenComponent{
	private final int x, y;
	
	private volatile Village village;
	
	private volatile int biome, resources;
	public static final int BIOME_BARREN = 0, BIOME_FOREST = 1;
	public static final int RSRCE_NOTHING = 0, RSRCE_WOOD  = 1;
	public static final int NUM_RSRCE_TYPES = 1;
	private static final int[] biomeRsrceType = {RSRCE_NOTHING, RSRCE_WOOD};

	public static final int lengthOfChunk = 16;
	public static final int lengthOfBuilding = 8;

	public Chunk(int biome, int initResources, int x, int y) {
		this.x = x;
		this.y = y;
		this.setBiome(biome);
		this.setResources(initResources);
	}
	
	public void addVillage(Village v, int x, int y) { addVillage(v, new Point2D.Double(x, y)); }
	public void addVillage(Village v, Point2D villageCoords)
	{
		this.village = v;
	}
	
	public void update()
	{
		
	}
	
	@Override
	public BufferedImage draw()
	{
		BufferedImage image = new BufferedImage(Chunk.getPixelLength()+1, Chunk.getPixelLength()+1, BufferedImage.TYPE_INT_ARGB);
		Graphics gI = image.getGraphics();
		if (Render.drawChunkBoundaries)
		{
			if (village == null)
			{
				gI.setColor(Color.black);
			}
			else
			{
				gI.setColor(Village.chunkBoundColor);
			}
			gI.drawRect(0, 0, Chunk.getPixelLength(), Chunk.getPixelLength());
		}
		
		if (village != null)
		{
			gI.drawImage(village.draw((int)(getX()-village.getX()+village.getRelativeChunkCenter().getX()), (int)(getY()-village.getY()+village.getRelativeChunkCenter().getY())), 0, 0, Chunk.getPixelLength(), Chunk.getPixelLength(), null);
		}
		return image;
	}
	
	public void setResources(int resources) {this.resources = resources;}

	public int getBiome() {return biome;}
	public void setBiome(int biome) {this.biome = biome;}
	
	public int getRsrceType() {return biomeRsrceType[this.getBiome()];}
	
	public int getNumRsrces() {return resources;}
	
	public static int getPixelLength(){return lengthOfChunk*lengthOfBuilding;}
	
	public void harvest(int quantity, Villager villager)
	{
		resources -= quantity;
		villager.getProffession().setRsrceQuantity(villager.getProffession().getRsrceQuantity() + quantity);
	}
	
	public boolean hasVillage() { return village != null; }
	public Village getVillage() { return village; }

	/**
	 * returns the coordinates of this chunk relative to the origin (measured in chunks
	 * @return the coordinates of this chunk
	 */
	public Point2D getCoords() { return new Point2D.Double(x, y); }   
	public int getX() { return x; }
	public int getY() { return y; }
	
	/*
	public Point2D getVillageLoc()
	{
		return villageCoords;
	}
	public int getVillageX() { return (int) villageCoords.getX(); }
	public int getVillageY() { return (int) villageCoords.getY(); }
	*/
}
