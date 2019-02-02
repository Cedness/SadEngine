package de.ced.sadengine.main;

import de.ced.sadengine.api.Saddings;
import de.ced.sadengine.input.SadInput;
import de.ced.sadengine.objects.SadRenderer;
import de.ced.sadengine.objects.SadWindow;

import static de.ced.sadengine.utils.SadValue.KEY_ESCAPE;

public class SadGLThread extends SadThread {
	
	private final SadMainLogic logic;
	private final Saddings settings;
	private final Sadness sadness;
	private final SadWindow window;
	private final SadContent content;
	private final SadInput input;
	private final SadRenderer renderer;
	
	private static final long LOOP_SECOND = 1000;
	
	private double interval;
	private long t1, t2;
	private double currentOffset = interval;
	
	private int currentFrame = 0;
	private int fpsDivider;
	
	private float secInterval;
	
	public SadGLThread(SadThreadHandler threadHandler, SadMainLogic logic, Saddings settings) {
		super("GL-Thread", threadHandler);
		this.logic = logic;
		this.settings = settings;
		sadness = new Sadness();
		content = sadness.getContent();
		window = sadness.getWindow();
		input = sadness.getInput();
		renderer = new SadRenderer();
		interval = LOOP_SECOND / (double) settings.getUps();
		setAction(new SadHelpTA(), new boolean[]{true, false, false, false});
		setAction(new SadLogicTA(), new boolean[]{false, true, false, false});
		setAction(new SadInputTA(), new boolean[]{false, false, true, false});
		setAction(new SadRenderTA(), new boolean[]{false, false, false, true});
	}
	
	@Override
	public void run() {
		renderer.setup(window, content, settings, input);
		input.update();
		logic.setup(sadness);
		content.getActionHandler().setup();
		input.update();
		super.run();
	}
	
	private long now() {
		return System.currentTimeMillis();
	}
	
	public class SadHelpTA extends SadThreadAction {
		@Override
		public void run() {
			if (currentOffset < interval) {
				interval = LOOP_SECOND / (double) settings.getUps();
				fpsDivider = settings.getFpsDivider();
				
				secInterval = 1.0f / (float) settings.getUps();
			}
			
			t1 = now();
			
			currentOffset -= interval;
			
			done = true;
		}
	}
	
	public class SadLogicTA extends SadThreadAction {
		@Override
		public void run() {
			content.update(secInterval);
			logic.update(sadness);
			content.getActionHandler().update();
			logic.updateFinally(sadness);
			
			done = true;
		}
	}
	
	public class SadInputTA extends SadThreadAction {
		@Override
		public void run() {
			input.update();
			
			if (input.isPressed(KEY_ESCAPE))
				threadHandler.interrupt();
			
			done = true;
		}
	}
	
	public class SadRenderTA extends SadThreadAction {
		@Override
		public void run() {
			if (currentOffset < interval) {
				
				//RENDER
				System.out.println(getName() + ": Rendering");
				
				if (currentFrame == 0) {
					renderer.render();
					//System.out.println("renderLayer");
				}
				
				currentFrame++;
				if (currentFrame >= fpsDivider)
					currentFrame = 0;
				
				renderer.updateWindow();
				
				//
				
			}
			
			t2 = now();
			
			currentOffset += (t2 - t1);
			
			done = true;
		}
	}
	
	@Override
	public void terminate() {
		logic.preTerminate(sadness);
		content.getActionHandler().terminate();
		logic.terminate(sadness);
		
		renderer.release();
		
		System.out.println("SadEngine3D");
		System.out.println("(ɔ) 2018 by Ced");
	}
}
