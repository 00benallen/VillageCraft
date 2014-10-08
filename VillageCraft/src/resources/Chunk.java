package resources;

public class Chunk {
	private int biome;
	private boolean hasVillage, isPlayerVillage;
	
	public Chunk(int biome, boolean hasVillage, boolean isPlayerVillage) {
		this.setBiome(biome);
		this.setHasVillage(hasVillage);
		this.setPlayerVillage(isPlayerVillage);
	}
	
	public int getBiome() {
		return biome;
	}
	public void setBiome(int biome) {
		this.biome = biome;
	}
	public boolean isHasVillage() {
		return hasVillage;
	}
	public void setHasVillage(boolean hasVillage) {
		this.hasVillage = hasVillage;
	}

	public boolean isPlayerVillage() {
		return isPlayerVillage;
	}

	public void setPlayerVillage(boolean isPlayerVillage) {
		this.isPlayerVillage = isPlayerVillage;
	}

}
