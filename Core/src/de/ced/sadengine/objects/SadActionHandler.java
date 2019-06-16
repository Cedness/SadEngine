package de.ced.sadengine.objects;

import java.util.ArrayList;
import java.util.List;

public class SadActionHandler {
	
	private List<SadAction> actions = new ArrayList<>();
	
	void setup() {
		for (SadAction action : actions) {
			setup(action);
		}
	}
	
	void update() {
		for (SadAction action : actions) {
			setup(action);
			start(action);
			update(action);
			stop(action);
			sync(action);
		}
	}
	
	void terminate() {
		for (SadAction action : actions) {
			stop(action);
			sync(action);
			terminate(action);
		}
	}
	
	public void add(SadAction action) {
		actions.add(action);
	}
	
	public void remove(SadAction action) {
		actions.remove(action);
	}
	
	public void clear() {
		actions = new ArrayList<>();
	}
	
	private void setup(SadAction action) {
		if (!action.isSetupDone()) {
			action.getLogic().setup();
			action.setSetupDone(true);
		}
	}
	
	private void start(SadAction action) {
		if (action.isRunning() && !action.wasRunning())
			action.getLogic().startRunning();
	}
	
	private void update(SadAction action) {
		if (action.isRunning())
			action.getLogic().update();
	}
	
	private void stop(SadAction action) {
		if (!action.isRunning() && action.wasRunning())
			action.getLogic().stopRunning();
	}
	
	private void sync(SadAction action) {
		action.setWasRunning(action.isRunning());
	}
	
	private void terminate(SadAction action) {
		action.getLogic().terminate();
	}
}
