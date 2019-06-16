package de.ced.sadengine.objects;

public abstract class SadRunnable extends SadObject {
	
	private boolean running = true;
	
	public boolean isRunning() {
		return running;
	}
	
	public SadRunnable setRunning(boolean running) {
		this.running = running;
		return this;
	}
}
