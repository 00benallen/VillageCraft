package main;

import world.World;
import gen.WorldBuilder;

public class Update {
	private World world1;
	
	public void init() {
		world1 = WorldBuilder.generateWorld("World1.txt");
	}
	
	public void update() {
		
	}

	public World getWorld1() {
		return world1;
	}
}
