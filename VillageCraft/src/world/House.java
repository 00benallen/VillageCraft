package world;

public class House extends Building {
	private int people;

	public House(int people) {
		this.setPeople(people);
	}
	
	//TODO should this really be public?
	public void setPeople(int people) {this.people = people;}
	public int getPeople() {return this.people;}

	@Override
	public int getType() {
		return Building.TYPE_HOUSE;
	}

}
