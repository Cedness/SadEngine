package de.ced.sadengine.objects;

import de.ced.sadengine.objects.input.SadInput;
import de.ced.sadengine.utils.SadIntro;
import de.ced.threadpool.Task;
import de.ced.threadpool.ThreadPool;

import static de.ced.sadengine.utils.SadIntro.Stage.BETA;

public class SadLoop implements Task {
	
	private final ThreadPool threadPool;
	
	private final SadEngine engine;
	private final Sadness sadness;
	private final SadFrame window;
	private final SadContent content;
	private final SadInput input;
	private SadRenderer renderer;
	private SadMover mover;
	
	private static final long LOOP_SECOND = 1000;
	
	private double interval;
	@SuppressWarnings("FieldCanBeLocal")
	private long t1, t2;
	private double currentOffset = interval;
	
	private float secInterval;
	
	private final SadHelpTask helpTask;
	private final SadMoveTask moveTask;
	private final SadLogicTask logicTask;
	private final SadInputTask inputTask;
	private final SadRenderTask renderTask;
	
	SadLoop(SadEngine engine) {
		ThreadPool threadPool = new ThreadPool(new boolean[]{false, false});
		threadPool.lockWorker(0, true);
		threadPool.addTask(new SadIntro(BETA, "0.10.3"));
		this.threadPool = threadPool;
		this.engine = engine;
		sadness = new Sadness();
		content = sadness.getContent();
		window = sadness.getWindow();
		input = sadness.getInput();
		
		interval = LOOP_SECOND / (double) engine.getUps();
		
		helpTask = new SadHelpTask();
		moveTask = new SadMoveTask();
		logicTask = new SadLogicTask();
		inputTask = new SadInputTask();
		renderTask = new SadRenderTask();
		
		threadPool.addTask(this, 0);
	}
	
	@Override
	public void run() {
		renderer = new SadRenderer(window, content, input, engine);
		mover = new SadMover(window, content, input, engine);
		input.update();
		engine.getLogic().setup(sadness);
		content.getActionHandler().setup();
		input.update();
		
		threadPool.addTask(helpTask, 0);
	}
	
	void stop() {
		renderer.stop();
	}
	
	private long now() {
		return System.currentTimeMillis();
	}
	
	public class SadHelpTask implements Task {
		@Override
		public void run() {
			if (currentOffset < interval) {
				interval = LOOP_SECOND / (double) engine.getUps();
				
				secInterval = 1.0f / (float) engine.getUps();
			}
			
			t1 = now();
			
			currentOffset -= interval;
			
			threadPool.addTask(moveTask, 0);
		}
	}
	
	public class SadMoveTask implements Task {
		@Override
		public void run() {
			mover.invoke();
			
			threadPool.addTask(logicTask, 0);
		}
	}
	
	public class SadLogicTask implements Task {
		@Override
		public void run() {
			content.update(secInterval);
			engine.getLogic().update(sadness);
			content.getActionHandler().update();
			engine.getLogic().updateFinally(sadness);
			
			threadPool.addTask(inputTask, 0);
		}
	}
	
	public class SadInputTask implements Task {
		@Override
		public void run() {
			input.update();
			
			if (renderer.shouldClose()) {
				engine.getLogic().preTerminate(sadness);
				content.getActionHandler().terminate();
				engine.getLogic().terminate(sadness);
				
				renderer.release();
				
				synchronized (System.out) {
					System.out.println("SadEngine3D");
					System.out.println("(ɔ) 2018-2019 by Ced");
				}
				
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
				renderer.invoke();
			}
			
			t2 = now();
			currentOffset += (t2 - t1);
			
			threadPool.addTask(helpTask, 0);
		}
	}
}
