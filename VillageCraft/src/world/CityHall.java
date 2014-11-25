package world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class CityHall extends Building {
	private volatile int unrest = 0;
	
	@Override
	public void update()
	{
		
	}
	
	@Override
	public BufferedImage draw()
	{
		BufferedImage image = new BufferedImage(Building.lengthOfBuilding, Building.lengthOfBuilding, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) image.getGraphics();
		g.setColor(Color.orange);
		g.fillRect(0, 0, lengthOfBuilding-2, lengthOfBuilding-2);
		return image;
	}
	
	@Override
	public int getType() {
		return BuildingType.CITYHALL.ordinal();
	}
}
