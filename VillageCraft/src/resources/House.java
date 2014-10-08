package resources;

public class House extends Building {
	private int people;

	public House(int people) {
		super(0);
		this.setPeople(people);
	}
	
	public void setPeople(int people) {this.people = people;}
	public int getPeople() {return this.people;}

}
