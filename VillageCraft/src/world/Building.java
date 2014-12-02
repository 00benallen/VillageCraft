package world;

public abstract class Building implements ScreenComponent{
	/** the length of a building in pixels */
	public static final int lengthOfBuilding = 8;
	
	public enum BuildingType {
		SIDEWALK, CITYHALL, HOUSE, FARM
	}
	
	public abstract int getType();
	public abstract int getWidth();
	public abstract int getHeight();
	public abstract boolean isInfrastructure();
	
	public abstract void update();
}
