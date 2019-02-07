package de.ced.sadengine.test;

import de.ced.sadengine.objects.*;
import de.ced.sadengine.objects.input.SadInput;

public class Papa extends SadEngine {
	
	public static void main(String[] args) {
		new Papa();
	}
	
	private Papa() {
		start();
	}
	
	@Override
	public void setup(Sadness sadness) {
		SadContent c = sadness.getContent();
		SadWindow w = sadness.getWindow();
		SadInput i = sadness.getInput();
		
		SadCamera camera = c.createCamera("camera");
	}
	
	@Override
	public void update(Sadness sadness) {
	
	}
}
