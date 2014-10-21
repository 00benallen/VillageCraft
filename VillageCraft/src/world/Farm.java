package world;

import java.awt.Image;

public class Farm extends Building {

	@Override
	public void update() {
		
	}

	@Override
	public Image draw() {
		return null;
	}

	@Override
	public int getType() {
		return BuildingType.FARM.ordinal();
	}
}
