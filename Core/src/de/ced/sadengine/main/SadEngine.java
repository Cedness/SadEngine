package de.ced.sadengine.main;

import de.ced.sadengine.api.Saddings;
import de.ced.sadengine.utils.SadIntro;

public class SadEngine {
	
	public static SadEngine INSTANCE = null;
	
	public SadEngine(SadMainLogic logic) {
		this(logic, new Saddings());
	}
	
	public SadEngine(SadMainLogic logic, Saddings saddings) {
		if (INSTANCE != null)
			return;
		
		SadIntro.show(0.5f);
		
		System.out.println("Initializing Thread Handler...");
		SadThreadHandler threadHandler = new SadThreadHandler();
		
		System.out.println("Initializing GL Thread...");
		SadGLThread glThread = new SadGLThread(threadHandler, logic, saddings);
		threadHandler.addThread(glThread);
		
		threadHandler.start();
	}
}
