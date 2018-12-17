package de.ced.sadengine.test;

import de.ced.sadengine.main.SadEngine;
import de.ced.sadengine.main.SadMainLogic;
import de.ced.sadengine.main.Sadness;

public class Main implements SadMainLogic {
	
	public static void main(String[] args) {
		new SadEngine(new Main());
	}
	
	@Override
	public void setup(Sadness sadness) {
	
	}
	
	@Override
	public void update(Sadness sadness) {
	
	}
}
