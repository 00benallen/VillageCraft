package world;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Farm extends Building {
	public static final int width = 2, height = 2;
	
	protected Farm() {}
	
	@Override
	public void update() {
		
	}

	@Override
	public BufferedImage draw()
	{
		BufferedImage image = new BufferedImage(Chunk.getPixelLength(), Chunk.getPixelLength(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D gI = image.createGraphics();
		return image;
	}

	@Override
	public int getType() { return BuildingType.FARM.ordinal(); }
	@Override
	public int getWidth() { return width; }
	@Override
	public int getHeight() { return height; }
	@Override
	public boolean isInfrastructure() { return false; }
}
