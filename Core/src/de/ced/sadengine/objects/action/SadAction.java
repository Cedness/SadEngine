package de.ced.sadengine.objects.action;

import de.ced.sadengine.objects.SadRunnable;

public class SadAction extends SadRunnable {
	
	private final SadActionLogic logic;
	private boolean runningLast = false;
	private boolean setupDone = false;
	
	public SadAction(String name, SadActionLogic logic) {
		super(name);
		this.logic = logic;
	}
	
	public final SadActionLogic getLogic() {
		return logic;
	}
	
	@Override
	public SadAction setRunning(boolean running) {
		super.setRunning(running);
		return this;
	}
	
	boolean wasRunning() {
		return runningLast;
	}
	
	void setWasRunning(boolean running) {
		runningLast = running;
	}
	
	boolean isSetupDone() {
		return setupDone;
	}
	
	void setSetupDone(boolean setupDone) {
		this.setupDone = setupDone;
	}
}
