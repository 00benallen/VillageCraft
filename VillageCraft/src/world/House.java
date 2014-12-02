package world;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class House extends Building {
	public static final int width = 1, height = 1;
	
	private ArrayList<Villager> people = new ArrayList<Villager>();

	protected House(ArrayList<Villager> people) {
		this.addPeople(people);
	}
	
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

	//TODO should this really be public?
	public void addPeople(ArrayList<Villager> newPeople) {people.addAll(newPeople);}
	public void addPerson(Villager newPerson) {people.add(newPerson);}
	public ArrayList<Villager> getPeople() {return this.people;}

	@Override
	public int getType() { return BuildingType.HOUSE.ordinal(); }
	@Override
	public int getWidth() { return width; }
	@Override
	public int getHeight() { return height; }
	@Override
	public boolean isInfrastructure() { return false; }
}
