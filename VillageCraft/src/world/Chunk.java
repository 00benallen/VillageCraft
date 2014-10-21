package world;

import java.awt.image.BufferedImage;

public class Chunk implements ScreenComponent{
	private int biome, resources;
	public static final int BIOME_BARREN = 0, BIOME_FOREST = 1;
	public static final int RSRCE_NOTHING = 0, RSRCE_WOOD  = 1;
	public static final int NUM_RSRCE_TYPES = 1;
	private static final int[] biomeRsrceType = {RSRCE_NOTHING, RSRCE_WOOD};

	public static final int lengthOfChunk = 16;
	public static final int lengthOfBuilding = 8;

	public Chunk(int biome, int initResources) {
		this.setBiome(biome);
		this.setResources(initResources);
	}
	
	public void update()
	{
		
	}
	
	@Override
	public BufferedImage draw()
	{
		return null;
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
	
	/**
	 * @deprecated use instance of Village
	 */
	public boolean hasVillage() {
		return this instanceof Village;
	}
}
