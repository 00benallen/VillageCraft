package world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class SideWalk extends Building {
	public static final int width = 1, height = 1;
	@Override
	public BufferedImage draw() {
		BufferedImage image = new BufferedImage(Building.lengthOfBuilding, Building.lengthOfBuilding, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		g.setColor(Color.darkGray);
		g.fillRect(1, 1, lengthOfBuilding-2, lengthOfBuilding-2);
		return image;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public int getType() { return BuildingType.SIDEWALK.ordinal(); }
	@Override
	public int getWidth() { return width; }
	@Override
	public int getHeight() { return height;	}
	@Override
	public boolean isInfrastructure() { return true; }
}
