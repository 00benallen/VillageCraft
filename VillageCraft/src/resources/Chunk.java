package resources;

public class Chunk {
	private int biome, x, y;
	private boolean hasVillage, isPlayerVillage;
	
	public Chunk(int x, int y, int biome, boolean hasVillage, boolean isPlayerVillage) {
		this.setX(x);
		this.setY(y);
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

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isPlayerVillage() {
		return isPlayerVillage;
	}

	public void setPlayerVillage(boolean isPlayerVillage) {
		this.isPlayerVillage = isPlayerVillage;
	}

}
