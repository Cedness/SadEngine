package de.ced.sadengine.objects;

public class SadEngine implements SadMainLogic {
	
	protected final Saddings settings = new Saddings();
	private static SadEngine INSTANCE = null;
	
	protected SadEngine() {
	}
	
	protected final void start() {
		new SadLoop(this, settings);
	}
}
