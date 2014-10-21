package world;

import java.awt.image.BufferedImage;

public class Farm extends Building {

	@Override
	public void update() {
		
	}

	@Override
	public BufferedImage draw() {
		return null;
	}

	@Override
	public int getType() {
		return BuildingType.FARM.ordinal();
	}
}
