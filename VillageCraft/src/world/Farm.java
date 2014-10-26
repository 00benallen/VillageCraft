package world;

import java.awt.image.BufferedImage;

public class Farm extends Building {

	@Override
	public void update() {
		
	}

	@Override
	public BufferedImage draw()
	{
		return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
	}

	@Override
	public int getType() {
		return BuildingType.FARM.ordinal();
	}
}
