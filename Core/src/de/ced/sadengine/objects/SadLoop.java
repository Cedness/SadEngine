package de.ced.sadengine.objects;

import de.ced.sadengine.objects.input.SadInput;
import de.ced.sadengine.utils.SadIntro;
import de.ced.threadpool.Task;
import de.ced.threadpool.ThreadPool;

import static de.ced.sadengine.utils.SadIntro.Stage.ALPHA;
import static de.ced.sadengine.utils.SadValue.KEY_ESCAPE;

public class SadLoop implements Task {
	
	private final ThreadPool threadPool;
	
	private final SadMainLogic logic;
	private final Saddings settings;
	private final Sadness sadness;
	private final SadWindow window;
	private final SadContent content;
	private final SadInput input;
	private final SadRenderer renderer;
	
	private static final long LOOP_SECOND = 1000;
	
	private double interval;
	@SuppressWarnings("FieldCanBeLocal")
	private long t1, t2;
	private double currentOffset = interval;
	
	private int currentFrame = 0;
	private int fpsDivider;
	
	private float secInterval;
	
	private final SadHelpTask helpTask;
	private final SadLogicTask logicTask;
	private final SadInputTask inputTask;
	private final SadRenderTask renderTask;
	
	SadLoop(SadMainLogic logic, Saddings settings) {
		ThreadPool threadPool = new ThreadPool(new boolean[]{false, false});
		threadPool.lockWorker(0, true);
		threadPool.addTask(new SadIntro(ALPHA, "0.8.1"));
		this.threadPool = threadPool;
		this.logic = logic;
		this.settings = settings;
		sadness = new Sadness();
		content = sadness.getContent();
		window = sadness.getWindow();
		input = sadness.getInput();
		renderer = new SadRenderer();
		
		interval = LOOP_SECOND / (double) settings.getUps();
		
		helpTask = new SadHelpTask();
		logicTask = new SadLogicTask();
		inputTask = new SadInputTask();
		renderTask = new SadRenderTask();
		
		threadPool.addTask(this, 0);
	}
	
	@Override
	public void run() {
		renderer.setup(window, content, settings, input);
		input.update();
		logic.setup(sadness);
		content.getActionHandler().setup();
		input.update();
		
		threadPool.addTask(helpTask, 0);
	}
	
	private long now() {
		return System.currentTimeMillis();
	}
	
	public class SadHelpTask implements Task {
		@Override
		public void run() {
			if (currentOffset < interval) {
				interval = LOOP_SECOND / (double) settings.getUps();
				fpsDivider = settings.getFpsDivider();
				
				secInterval = 1.0f / (float) settings.getUps();
			}
			
			t1 = now();
			
			currentOffset -= interval;
			
			threadPool.addTask(logicTask, 0);
		}
	}
	
	public class SadLogicTask implements Task {
		@Override
		public void run() {
			content.update(secInterval);
			logic.update(sadness);
			content.getActionHandler().update();
			logic.updateFinally(sadness);
			
			threadPool.addTask(inputTask, 0);
		}
	}
	
	public class SadInputTask implements Task {
		@Override
		public void run() {
			input.update();
			
			if (input.isPressed(KEY_ESCAPE)) {
				logic.preTerminate(sadness);
				content.getActionHandler().terminate();
				logic.terminate(sadness);
				
				renderer.release();
				
				System.out.println("SadEngine3D");
				System.out.println("(ɔ) 2018-2019 by Ced");
				
				threadPool.destroy();
				return;
			}
			
			threadPool.addTask(renderTask, 0);
		}
	}
	
	public class SadRenderTask implements Task {
		@Override
		public void run() {
			if (currentOffset < interval) {
				
				if (currentFrame == 0) {
					renderer.render();
				}
				
				currentFrame++;
				if (currentFrame >= fpsDivider)
					currentFrame = 0;
				
				renderer.updateWindow();
			}
			
			t2 = now();
			
			currentOffset += (t2 - t1);
			
			threadPool.addTask(helpTask, 0);
		}
	}
}
