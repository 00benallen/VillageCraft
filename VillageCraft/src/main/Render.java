package main;

import java.awt.Color;
import java.awt.Graphics2D;

public class Render implements Runnable {
	Graphics2D g;
	Thread renderThread;
	public static final boolean drawChunkBoundaries = true;
	
	public Render(Graphics2D g) {
		this.g = g;
	}
	
	public synchronized void start() {
		renderThread = new Thread(this, "Render Thread");
		renderThread.start();
	}
	
	public synchronized void resume() {
		notify();
	}

	public void run() {
		while(Main.running) {
			draw();
			synchronized(this) 
			{
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void draw() {
		drawGround();
		drawWorld();
	}
	
	public void drawWorld() {
		g.drawImage(Main.getLoadedWorld().draw(), 0, 0, null);
	}

	public void drawGround() {
		g.setColor(new Color(1, 166, 17));
		g.fillRect(0, 0, GraphicsMain.WIDTH, GraphicsMain.HEIGHT);
	}	
}
