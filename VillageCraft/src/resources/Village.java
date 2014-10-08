package resources;

import java.util.ArrayList;

public class Village {
	private int population, sizeRank;
	private ArrayList<Building> buildings;
	
	public Village(int population) {
		this.setPopulation(population);
		this.setSizeRank(population);
		buildings = new ArrayList<Building>();
	}
	
	public void setPopulation(int population) {this.population = population;}
	public int getPopulation() {return population;}
	
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
