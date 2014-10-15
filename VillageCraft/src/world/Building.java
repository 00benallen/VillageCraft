package world;

public abstract class Building {
	
	public enum BuildingType {
		CITYHALL, HOUSE
	}
	public abstract int getType();
}
