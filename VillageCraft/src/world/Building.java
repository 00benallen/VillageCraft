package world;

public abstract class Building {
	
	public enum BuildingType {
		CITYHALL, HOUSE, FARM
	}
	public abstract int getType();
	
	public abstract void update();
}
