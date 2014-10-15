package world;

import java.util.ArrayList;

import resources.Material;
import resources.Proffession;

public class Chunk {
	private int biome, resources;
	public static final int BIOME_BARREN = 0, BIOME_FOREST = 1;
	public static final int RSRCE_NOTHING = 0, RSRCE_WOOD  = 1;
	private static final int[] biomeRsrceType = {RSRCE_NOTHING, RSRCE_WOOD};
	
	public Chunk(int biome, int initResources) {
		this.setBiome(biome);
		this.setResources(initResources);
	}
	
	public void setResources(int resources) {this.resources = resources;}

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
		return resources;
	}
	public void harvest(int quantity, Villager villager)
	{
		resources -= quantity;
		villager.getProffession().setRsrceQuantity(villager.getProffession().getRsrceQuantity() + quantity);
		
	}
	
	/**
	 * 
	 * @deprecated use instance of Village
	 */
	public boolean hasVillage() {
		return this instanceof Village;
	}
}