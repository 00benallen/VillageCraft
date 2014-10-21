package world;

import java.awt.image.BufferedImage;

public class CityHall extends Building {
	private int unrest = 0;
	
	@Override
	public void update()
	{
		
	}
	
	@Override
	public BufferedImage draw() {
		return null;
	}
	
	@Override
	public int getType() {
		return BuildingType.CITYHALL.ordinal();
	}
}
