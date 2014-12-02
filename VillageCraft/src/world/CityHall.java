package world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class CityHall extends Building {
	public static final int width = 2, height = 2;
	private volatile int unrest = 0;
	
	protected CityHall() {}
	
	@Override
	public void update()
	{
		
	}
	
	@Override
	public BufferedImage draw()
	{
		BufferedImage image = new BufferedImage(Building.lengthOfBuilding, Building.lengthOfBuilding, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		g.setColor(Color.orange);
		g.fillRect(0, 0, lengthOfBuilding-2, lengthOfBuilding-2);
		return image;
	}
	
	@Override
	public int getType() { return BuildingType.CITYHALL.ordinal(); }
	@Override
	public int getWidth() { return width; }
	@Override
	public int getHeight() { return height; }
	@Override
	public boolean isInfrastructure() { return true; }
}
