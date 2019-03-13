package de.ced.sadengine.objects;

import de.ced.sadengine.objects.input.SadInput;

public class Sadness {
	
	private final SadContent content;
	private final SadFrame window;
	private final SadInput input;
	
	Sadness() {
		content = new SadContent(this);
		window = new SadFrame();
		input = new SadInput();
	}
	
	public SadContent getContent() {
		return content;
	}
	
	public SadFrame getWindow() {
		return window;
	}
	
	public SadInput getInput() {
		return input;
	}
}