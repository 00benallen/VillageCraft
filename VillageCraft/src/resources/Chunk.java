package resources;

public class Chunk {
	private int biome;
	
	public Chunk(int biome) {
		this.setBiome(biome);
	}
	
	public int getBiome() {
		return biome;
	}
	public void setBiome(int biome) {
		this.biome = biome;
	}
	public boolean hasVillage() {
		return this instanceof Village;
	}
}
