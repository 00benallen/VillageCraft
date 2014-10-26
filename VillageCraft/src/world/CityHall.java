package world;

import java.awt.image.BufferedImage;

public class CityHall extends Building {
	private int unrest = 0;
	
	@Override
	public void update()
	{
		
	}
	
	@Override
	public BufferedImage draw()
	{
		return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
	}
	
	@Override
	public int getType() {
		return BuildingType.CITYHALL.ordinal();
	}
}
