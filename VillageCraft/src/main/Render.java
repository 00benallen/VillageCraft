package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

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
		//Buffer for manual double buffering
		BufferedImage buffer = new BufferedImage(GraphicsMain.WIDTH, GraphicsMain.HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics2D gBuffer = (Graphics2D)buffer.getGraphics(); 
		drawGround(gBuffer);
		drawWorld(gBuffer);
		g.drawImage(buffer, 0, 0, GraphicsMain.WIDTH, GraphicsMain.HEIGHT, null);
		//zoom in
		//g.drawImage(buffer, -1200, -1200, 5000, (int)(5000*((double)GraphicsMain.HEIGHT/GraphicsMain.WIDTH)), null);
	}
	
	public void drawWorld(Graphics2D g) {
		g.drawImage(Main.getLoadedWorld().draw(-2, -2), 0, 0, null);
	}

	public void drawGround(Graphics2D g) {
		g.setColor(new Color(1, 166, 17));
		g.fillRect(0, 0, GraphicsMain.WIDTH, GraphicsMain.HEIGHT);
	}	
}
