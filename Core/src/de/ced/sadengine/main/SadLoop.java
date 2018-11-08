package de.ced.sadengine.main;

import de.ced.sadengine.api.Saddings;
import de.ced.sadengine.utils.SadIntro;

public class SadLoop {
	
	private final SadEngine engine;
	private final Saddings settings;
	
	private boolean shutDown = false;
	
	public SadLoop(SadMainLogic logic, Saddings settings) {
		engine = new SadEngine(logic, settings, this);
		this.settings = settings;
	}
	
	public void start() {
		setup();
		loop();
		terminate();
	}
	
	public void stop() {
		shutDown = true;
	}
	
	private void setup() {
		new SadIntro(0.3f);
		engine.setup();
	}
	
	private long now() {
		return System.currentTimeMillis();
	}
	
	private void loop() {
		final long LOOP_SECOND = 1000;
		
		double interval = LOOP_SECOND / (double) settings.getUps();
		long t1, t2;
		double currentOffset = interval;
		
		int currentFrame = 0;
		int fpsDivider;
		
		
		while (!shutDown) {
			interval = LOOP_SECOND / (double) settings.getUps();
			fpsDivider = settings.getFpsDivider();
			
			
			t1 = now();
			
			
			//UPDATE
			
			while (currentOffset >= interval) {
				currentOffset -= interval;
				
				engine.update();
				//System.out.println("update");
			}
			
			engine.finishUpdate();
			
			
			//RENDER
			
			if (currentFrame == 0) {
				engine.render();
				//System.out.println("renderLayer");
			}
			
			currentFrame++;
			if (currentFrame >= fpsDivider)
				currentFrame = 0;
			
			engine.finishRender();
			
			//
			
			
			t2 = now();
			
			currentOffset += (t2 - t1);
		}
	}
	
	private void terminate() {
		engine.terminate();
	}
}

/*
while (currentOffset < interval) {
	currentOffset++;
	Thread.sleep(1);
}
*/
