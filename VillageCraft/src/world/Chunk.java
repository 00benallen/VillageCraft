package world;

import java.util.ArrayList;

import resources.Material;

public class Chunk {
	private int biome;
	public static final int BIOME_BARREN = 0, BIOME_FOREST = 1;
	
	private int rsrceQuantity = 0;
	//TODO fill this in
	private static final int[] biomeRsrceType = {Material.RSRCE_NOTHING, Material.RSRCE_LUMBER};
	
	//TODO decide what to make this
	public static final int initResources = 0;
	
	public Chunk(int biome) {
		this.setBiome(biome);
	}
	
	public int getBiome() {
		return biome;
	}
	public void setBiome(int biome) {
		this.biome = biome;
	}
	
	public int getRsrceType() {
		return biomeRsrceType[this.getBiome()];
	}
	public int getNumRsrces() {
		return resources.size();
	}
	public ArrayList<Material> harvest(int quantity)
	{
		ArrayList<Material> harvested = new ArrayList<Material>();
		for (int i = 0; i < quantity; ++i)
		{
			harvested.add(resources.remove(0));
		}
		return harvested;
	}
	
	/**
	 * 
	 * @deprecated use instance of Village
	 */
	public boolean hasVillage() {
		return this instanceof Village;
	}
}
