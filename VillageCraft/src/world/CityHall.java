package world;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class CityHall extends Building {
	private int unrest = 0;
	
	@Override
	public void update()
	{
		
	}
	
	@Override
	public BufferedImage draw(Graphics2D g) {
		return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
	}
	
	@Override
	public int getType() {
		return BuildingType.CITYHALL.ordinal();
	}
}
