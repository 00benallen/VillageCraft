package resources;

import java.util.ArrayList;

public class Village extends Chunk{
	private int sizeRank;
	private ArrayList<Villager> villagers;
	private ArrayList<Building> buildings;
	
	public Village(int biome, ArrayList<Villager> population) {
		super(biome);
		this.addPopulation(population);
		this.setSizeRank(population.size());
		buildings = new ArrayList<Building>();
	}
	
	public void addPopulation(ArrayList<Villager> newPopulation) {
		this.villagers.addAll(newPopulation);
	}
	public int getPopulation() {return villagers.size();}
	
	public void setSizeRank(int population) {
		sizeRank = population/10;
	}
	public int getSizeRank() {return this.sizeRank;}
	
	public void addBuilding(Building newBuilding) {
		this.buildings.add(newBuilding);
	}
	public void removeBuilding(int index) {
		this.buildings.remove(index);
	}
	public void removeBuilding(Building deleteBuilding) {
		this.buildings.remove(deleteBuilding);
	}

}
