package main;

import world.World;
import gen.WorldBuilder;

public class Update {
	private World loadedWorld;
	
	public void init() {
		loadedWorld = WorldBuilder.generateWorld("World1.txt");
	}
	
	public void update() {
		loadedWorld.update();
	}

	public World getLoadedWorld() {
		return loadedWorld;
	}
}
