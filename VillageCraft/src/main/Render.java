package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import world.Chunk;

public class Render implements Runnable {
	public static final double zoomFactor = (7.0/8.0);
	private static Rectangle2D screen = new Rectangle2D.Double(GraphicsMain.WIDTH/-2, GraphicsMain.HEIGHT/-2, GraphicsMain.WIDTH, GraphicsMain.HEIGHT);
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
	
	private void drawWorld(Graphics2D g) {
		BufferedImage i = Main.getLoadedWorld().draw((int)screen.getMinX(), (int)screen.getMinY(), (int)Math.ceil(screen.getWidth()), (int)Math.ceil(screen.getHeight()));
		g.drawImage(i, 0, 0, GraphicsMain.WIDTH, GraphicsMain.HEIGHT, null);
	}

	private void drawGround(Graphics2D g) {
		g.setColor(new Color(1, 166, 17));
		g.fillRect(0, 0, GraphicsMain.WIDTH, GraphicsMain.HEIGHT);
	}
	
	public static void zoom(int zoomCount){ zoom(zoomCount, new Point2D.Double(screen.getCenterX(), screen.getCenterY())); }
	public static void zoom(int zoomCount, Point2D origin)
	{
		double curZoomFactor = Math.pow(zoomFactor, zoomCount);
		double x0 = screen.getMinX(), y0 = screen.getMinY(), cX = origin.getX(), cY = origin.getY();
		screen = new Rectangle2D.Double(cX+(x0-cX)*curZoomFactor, cY+(y0-cY)*curZoomFactor, screen.getWidth()*curZoomFactor, screen.getHeight()*curZoomFactor);
	}
}
