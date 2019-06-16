package de.ced.sadengine.objects;

import de.ced.sadengine.objects.input.SadInput;
import de.ced.sadengine.objects.time.SadClockwork;

public class Sadness {
	
	private final SadFrame window;
	private final SadInput input;
	private final SadActionHandler actionHandler;
	private final SadClockwork clockwork;
	private final SadRenderer renderer;
	private final SadMover mover;
	private float interval;
	
	Sadness(SadFrame window, SadRenderer renderer, SadMover mover, SadInput input, SadActionHandler actionHandler, SadClockwork clockwork) {
		this.window = window;
		this.renderer = renderer;
		this.mover = mover;
		this.input = input;
		this.actionHandler = actionHandler;
		this.clockwork = clockwork;
	}
	
	public SadFrame getWindow() {
		return window;
	}
	
	public SadInput getInput() {
		return input;
	}
	
	public SadActionHandler getActionHandler() {
		return actionHandler;
	}
	
	public SadClockwork getClockwork() {
		return clockwork;
	}
	
	public SadRenderer getRenderer() {
		return renderer;
	}
	
	public SadMover getMover() {
		return mover;
	}
	
	void setInterval(float secInterval) {
		interval = secInterval;
	}
	
	public float getInterval() {
		return interval;
	}
}
