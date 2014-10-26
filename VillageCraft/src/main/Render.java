package main;

import java.awt.Color;
import java.awt.Graphics2D;

public class Render implements Runnable {
	Graphics2D g;
	Thread renderThread;
	boolean waiting;
	
	
	public static final boolean drawChunkBoundaries = false;
	public Render(Graphics2D g) {
		this.g = g;
	}
	
	public synchronized void start() {
		renderThread = new Thread(this, "Render Thread");
		renderThread.start();
	}
	
	public void pause() {
		waiting = true;
	}
	
	public synchronized void resume() {
		waiting = false;
		notify();
	}

	public void run() {
		while(Main.running) {
			//*
			synchronized(this) {
				if(waiting) {
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			//*/
			
			draw();
			pause();
			
			/*	Why not just put the wait() down here like this, and get rid of waiting?
			synchronized(this) 
			{
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			//*/
		}
	}
	
	public void draw()
	{
		drawWorld();
		//drawGround();
	}
	
	public void drawWorld() {
		g.drawImage(Main.getLoadedWorld().draw(), 0, 0, null);
	}

	public void drawGround() {
		g.setColor(new Color(1, 166, 17));
		g.fillRect(0, 0, GraphicsMain.WIDTH, GraphicsMain.HEIGHT);
	}	
}
