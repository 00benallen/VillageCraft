package main;

import java.io.FileNotFoundException;

import world.World;

public class Update {
	private World loadedWorld;
	
	public void init() {
		try {
			loadedWorld = new World("World1.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		loadedWorld.generate();
	}
	
	public void update() {
		loadedWorld.update();
	}

	public World getLoadedWorld() {
		return loadedWorld;
	}
}
