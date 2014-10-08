package main;

public class Main implements Runnable {
	public static boolean running = true;
	private static Main main;
	Update update;
	Render render;
	Thread mainThread;
	
	
	public static void main(String[] args) {
		GraphicsMain.init();
		GraphicsMain.start();
		main = new Main();
		main.start();
	}
	
	public void init() {
		update = new Update();
		render = new Render(GraphicsMain.getGraphics());
		render.start();
		render.pause();
	}
	
	public synchronized void start() {
		running = true;
		mainThread = new Thread(main, "Main Thread");
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
}
