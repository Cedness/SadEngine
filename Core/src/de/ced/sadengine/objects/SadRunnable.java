package de.ced.sadengine.objects;

public abstract class SadRunnable extends SadObject implements SadRunnableI {
	
	private boolean running = true;
	
	public SadRunnable(String name) {
		super(name);
	}
	
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
