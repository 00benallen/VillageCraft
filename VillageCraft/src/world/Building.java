package world;

import java.awt.Image;

public abstract class Building {
	
	public enum BuildingType {
		CITYHALL, HOUSE, FARM
	}
	public abstract int getType();
	
	public abstract void update();
	
	public abstract Image draw();
}
