package de.ced.sadengine.test;

import de.ced.sadengine.api.SadEngine3D;
import de.ced.sadengine.main.SadMainLogic;
import de.ced.sadengine.main.Sadness;

public class Main implements SadMainLogic {
	
	public static void main(String[] args) {
		SadEngine3D.start(new Main());
	}
	
	@Override
	public void setup(Sadness sadness) {
	
	}
	
	@Override
	public void update(Sadness sadness) {
	
	}
}
