package main;

import java.awt.Color;
import java.awt.Graphics2D;

public class Render implements Runnable {
	Graphics2D g;
	Thread renderThread;
	boolean waiting;
	
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
		int cnt = 0;
		while(Main.running) {
			cnt++;
			synchronized(this) {
				while(waiting) {
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			g.setColor(Color.red);
			g.fillRect(0, 0, GraphicsMain.WIDTH, GraphicsMain.HEIGHT);
			
			g.setColor(Color.cyan);
			g.fillRect(500 + cnt, 500, 50, 50);
		}
	}

}
