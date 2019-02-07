package de.ced.sadengine.objects.action;

import de.ced.sadengine.objects.SadObject;
import de.ced.sadengine.objects.Sadness;

import java.util.HashMap;

public class SadActionHandler {
	
	private final HashMap<String, SadObject> actions;
	private final Sadness sadness;
	
	public SadActionHandler(HashMap<String, SadObject> actions, Sadness sadness) {
		this.actions = actions;
		this.sadness = sadness;
	}
	
	public void setup() {
		for (SadObject obj : actions.values()) {
			SadAction action = (SadAction) obj;
			
			setup(action);
		}
	}
	
	public void update() {
		for (SadObject obj : actions.values()) {
			SadAction action = (SadAction) obj;
			
			setup(action);
			start(action);
			update(action);
			stop(action);
			sync(action);
		}
	}
	
	public void terminate() {
		for (SadObject obj : actions.values()) {
			SadAction action = (SadAction) obj;
			
			stop(action);
			sync(action);
			terminate(action);
		}
	}
	
	private void setup(SadAction action) {
		if (!action.isSetupDone()) {
			action.getLogic().setup(sadness);
			action.setSetupDone(true);
		}
	}
	
	private void start(SadAction action) {
		if (action.isRunning() && !action.wasRunning())
			action.getLogic().startRunning(sadness);
	}
	
	private void update(SadAction action) {
		if (action.isRunning())
			action.getLogic().update(sadness);
	}
	
	private void stop(SadAction action) {
		if (!action.isRunning() && action.wasRunning())
			action.getLogic().stopRunning(sadness);
	}
	
	private void sync(SadAction action) {
		action.setWasRunning(action.isRunning());
	}
	
	private void terminate(SadAction action) {
		action.getLogic().terminate(sadness);
	}
}
