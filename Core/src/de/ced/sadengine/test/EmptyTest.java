package de.ced.sadengine.test;

import de.ced.sadengine.objects.SadCamera;
import de.ced.sadengine.objects.SadEngine;
import de.ced.sadengine.objects.SadLevel;

public class EmptyTest extends SadEngine {
	
	private EmptyTest() {
		super(0);
		setName("Peter");
		start();
	}
	
	public static void main(String[] args) {
		new EmptyTest();
	}
	
	@Override
	public void setup() {
		setCamera(new SadCamera());
		SadLevel level = new SadLevel();
		getCamera().setLevel(level);
		
	}
}
