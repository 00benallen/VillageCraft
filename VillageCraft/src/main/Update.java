package main;

public class Update implements Runnable {
	Thread updateThread;
	boolean waiting;
	
	public synchronized void start() {
		updateThread = new Thread(this, "Update Thread");
		updateThread.start();
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
			synchronized(this) {
				while(waiting) {
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
