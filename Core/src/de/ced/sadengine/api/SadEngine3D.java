package de.ced.sadengine.api;

import de.ced.sadengine.main.SadLoop;
import de.ced.sadengine.main.SadMainLogic;

public class SadEngine3D {
	
	private static SadLoop loop = null;
	
	public static void start(SadMainLogic logic, Saddings settings) {
		if (loop != null)
			return;
		loop = new SadLoop(logic, settings);
		loop.start();
	}
	
	public static void start(SadMainLogic logic) {
		start(logic, new Saddings());
	}
	
	public static void stop() {
		loop.stop();
		loop = null;
	}
}
