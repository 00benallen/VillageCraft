package world;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Farm extends Building {

	@Override
	public void update() {
		
	}

	@Override
	public BufferedImage draw(Graphics2D g) {
		return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
	}

	@Override
	public int getType() {
		return BuildingType.FARM.ordinal();
	}
}
