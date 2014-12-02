package world;

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
		return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
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
