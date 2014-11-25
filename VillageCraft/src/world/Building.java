package world;

public abstract class Building implements ScreenComponent{
	public static final int lengthOfBuilding = 8;
	
	public enum BuildingType {
		CITYHALL, HOUSE, FARM
	}
	public abstract int getType();
	
	public abstract void update();
}
