package de.ced.sadengine.main;

import de.ced.sadengine.input.SadInput;
import de.ced.sadengine.objects.SadWindow;

public class Sadness {
	
	private final SadContent content;
	private final SadWindow window;
	private final SadInput input;
	
	public Sadness() {
		content = new SadContent(this);
		window = new SadWindow(content);
		input = new SadInput();
	}
	
	public SadContent getContent() {
		return content;
	}
	
	public SadWindow getWindow() {
		return window;
	}
	
	public SadInput getInput() {
		return input;
	}
}
