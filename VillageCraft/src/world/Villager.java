package world;
import resources.Proffession;
import resources.Tool;

public class Villager {
	private boolean adult, alive;
	private int health, hunger, thirst, armor;
	private Tool tool;
	public static final int initHealth = 20, initHunger = 20, initThirst = 20;
	private Proffession proffession;
	
	public Villager() {
		this.setAdult(true);
		this.setAlive(true);
		this.setProffession(null);
		this.setHealth(initHealth);
		this.setHunger(initHunger);
		this.setThirst(initThirst);
		this.setTool(null);
		this.setArmor(0);
	}
	
	public boolean isAdult() {return adult;}

	public void setAdult(boolean adult) {this.adult = adult;}

	public boolean isAlive() {return alive;}

	public void setAlive(boolean alive) {this.alive = alive;}

	public Proffession getProffession() {return proffession;}

	public void setProffession(Proffession proffession) {this.proffession = proffession;}

	public int getHealth() {return health;}

	public void setHealth(int health) {this.health = health;}

	public int getHunger() {return hunger;}

	public void setHunger(int hunger) {this.hunger = hunger;}

	public int getThirst() {return thirst;}

	public void setThirst(int thirst) {this.thirst = thirst;}

	public Tool getTool() {return tool;}

	public void setTool(Tool tool) {this.tool = tool;}

	public int getArmor() {return armor;}

	public void setArmor(int armor) {this.armor = armor;}
	
	public int getHeldRsrceType() {return this.proffession.getHeldRsrceType();}
	
	public int getHeldRsrce() {return this.proffession.getRsrceQuantity();}
}
