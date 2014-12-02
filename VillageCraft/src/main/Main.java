package main;

import world.World;

public class Main implements Runnable {
	protected static boolean running = true;
	private static Main main;
	private Update update;
	private Render render;
	private Thread mainThread;
	private static World loadedWorld;
	
	
	public static void main(String[] args) {
		GraphicsMain.init();
		GraphicsMain.start();
		main = new Main();
		main.start();
	}
	
	public void init() {
		update = new Update();
		update.init();
		loadedWorld = update.getLoadedWorld();
		render = new Render(GraphicsMain.getGraphics());
		render.start();
	}
	
	public synchronized void start() {
		running = true;
		mainThread = new Thread(this, "Main Thread");
		mainThread.start();
	}
	
	public synchronized void stop() {
		running = false;
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		double nanoPerUpdate = 1000000000D/60D;
		double delta = 0D;
		
		init();
		
		while(running) {
			
			long now = System.nanoTime();
			delta += (now - lastTime) / nanoPerUpdate;
			lastTime = now;
			boolean shouldRender = false;
			
			while(delta >= 1) {
				update.update();
				delta--;
				shouldRender = true;
			}
			
			if(shouldRender) {
				render.resume();
			}
		}
	}
	
	public static World getLoadedWorld() {
		return loadedWorld;
	}
}
