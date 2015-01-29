package main;

import java.awt.Point;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.FileNotFoundException;

import listeners.Listener;
import world.World;
import main.Render;

public class Update {
	private World loadedWorld;
	private InputProcessor inputProcessor = new InputProcessor();
	
	public void init() {
		try {
			loadedWorld = new World("World1.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		loadedWorld.load();
	}
	
	public void update() {
		inputProcessor.processInput();
		loadedWorld.update();
	}
	

	public World getLoadedWorld() {
		return loadedWorld;
	}
}
