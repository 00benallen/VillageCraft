package world;

import java.awt.image.BufferedImage;

public class House extends Building {
	private int people;

	public House(int people) {
		this.setPeople(people);
	}
	
	@Override
	public void update() {
		
	}

	@Override
	public BufferedImage draw()
	{
		return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
	}

	//TODO should this really be public?
	public void setPeople(int people) {this.people = people;}
	public int getPeople() {return this.people;}

	@Override
	public int getType() {
		return BuildingType.HOUSE.ordinal();
	}
}
