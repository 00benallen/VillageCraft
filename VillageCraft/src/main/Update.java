package main;

import java.awt.Point;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.FileNotFoundException;

import listeners.Listener;
import world.World;

public class Update {
	private World loadedWorld;
	
	public void init() {
		try {
			loadedWorld = new World("World1.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		loadedWorld.load();
	}
	
	public void update() {
		processInput();
		loadedWorld.update();
	}
	
	private void processInput()
	{
		for(InputEvent e : Listener.universal)
		{
			if (e instanceof KeyEvent)
			{
				processEvent((KeyEvent) e);
			}
			else if (e instanceof MouseEvent)
			{
				processEvent((MouseEvent) e);
			}
			
			e.consume();
			Listener.universal.removeEvent();
		}
	}
	
	private void processEvent(KeyEvent e)
	{
		if (e.getID() != KeyEvent.KEY_RELEASED)
		{
			if (e.getKeyCode() == KeyEvent.VK_EQUALS || e.getKeyCode() == KeyEvent.VK_PLUS)
			{
				Render.zoom(1);
			}
			else if (e.getKeyCode() == KeyEvent.VK_MINUS)
			{
				Render.zoom(-1);
			}
		}
	}
	private void processEvent(MouseEvent e)
	{
		if (e instanceof MouseWheelEvent)
		{
			Rectangle2D screen = Render.getScreen();
			double pixelRatio = screen.getHeight()/GraphicsMain.HEIGHT;
			Point p = new Point((int) (e.getPoint().x*pixelRatio+screen.getMinX()), (int) (e.getPoint().y*pixelRatio+screen.getMinY()));
			Render.zoom(((MouseWheelEvent) e).getWheelRotation(), p);
		}
	}

	public World getLoadedWorld() {
		return loadedWorld;
	}
}
