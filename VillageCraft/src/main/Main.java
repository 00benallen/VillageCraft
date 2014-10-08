package main;

public class Main implements Runnable {
	boolean running = true;
	
	public static void main(String[] args) {
		
		GraphicsMain.init();
		GraphicsMain.start();
		
	}
	
	public void init() {
		
	}
	
	public void run() {
		while(running) {
			long lastTime = System.nanoTime();
			double nanoPerUpdate = 1000000000D/60D;
			
			long lastTimer = System.currentTimeMillis();
			double delta = 0D;
			
			init();
			
			while(running) {
				long now = System.nanoTime();
				delta += (now - lastTime) / nanoPerUpdate;
				lastTime = now;
				boolean shouldRender = false;
				
				while(delta >= 1) {
					//Main.update();
					delta--;
					shouldRender = true;
				}
				
				if(shouldRender) {
					GraphicsMain.render();
					
				}
			}
		}
	}
}
