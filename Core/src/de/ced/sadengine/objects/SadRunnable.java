package de.ced.sadengine.objects;

public abstract class SadRunnable extends SadObject implements SadRunnableI {
	
	private boolean running = true;
	
	@Override
	public boolean isRunning() {
		return running;
	}
	
	@Override
	public SadRunnable setRunning(boolean running) {
		this.running = running;
		return this;
	}
}
