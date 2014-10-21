package world;

import java.awt.Image;

public class CityHall extends Building {
	private int unrest = 0;
	
	@Override
	public void update()
	{
		
	}
	
	@Override
	public Image draw() {
		return null;
	}
	
	@Override
	public int getType() {
		return BuildingType.CITYHALL.ordinal();
	}
}
