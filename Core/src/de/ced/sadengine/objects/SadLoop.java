package de.ced.sadengine.objects;

import de.ced.sadengine.objects.input.SadInput;
import de.ced.sadengine.objects.time.SadClockwork;
import de.ced.sadengine.utils.SadIntro;
import de.ced.threadpool.Task;
import de.ced.threadpool.ThreadPool;

import static de.ced.sadengine.utils.SadIntro.Stage.BETA;

public class SadLoop implements Task {
	
	private final ThreadPool threadPool;
	
	private SadEngine window;
	private SadActionHandler actionHandler;
	private SadClockwork clockwork;
	private SadInput input;
	private SadRenderer renderer;
	private SadMover mover;
	
	private static final long LOOP_SECOND = 1000;
	
	private double interval;
	@SuppressWarnings("FieldCanBeLocal")
	private long t1, t2;
	private double currentOffset = interval;
	
	private float secInterval;
	
	private final SadHelpTask helpTask;
	private final SadLogicTask logicTask;
	private final SadInputTask inputTask;
	private final SadRenderTask renderTask;
	
	SadLoop(SadEngine engine) {
		ThreadPool threadPool = new ThreadPool(new boolean[]{false, false});
		threadPool.lockWorker(0, true);
		threadPool.addTask(new SadIntro(BETA, "1.1.13"));
		this.threadPool = threadPool;
		window = engine;
		
		interval = LOOP_SECOND / (double) engine.getUps();
		
		helpTask = new SadHelpTask();
		logicTask = new SadLogicTask();
		inputTask = new SadInputTask();
		renderTask = new SadRenderTask();
		
		threadPool.addTask(this, 0);
	}
	
	@Override
	public void run() {
		input = new SadInput();
		mover = new SadMover();
		actionHandler = new SadActionHandler();
		renderer = new SadRenderer(window, input);
		clockwork = new SadClockwork();
		window.setup(renderer, mover, input, actionHandler, clockwork);
		window.setStartTime(now());
		input.setup2(secInterval);
		input.update(0);
		window.getLogic().setup();
		actionHandler.setup();
		input.update(0);
		
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
				interval = LOOP_SECOND / (double) window.getUps();
				
				secInterval = 1.0f / (float) window.getUps();
			}
			
			t1 = now();
			
			currentOffset -= interval;
			
			threadPool.addTask(logicTask, 0);
		}
	}
	
	public class SadLogicTask implements Task {
		@Override
		public void run() {
			mover.invoke(secInterval);
			clockwork.increaseClocks(secInterval);
			window.setInterval(secInterval);
			window.getLogic().update();
			actionHandler.update();
			window.getLogic().updateFinally();
			
			threadPool.addTask(inputTask, 0);
		}
	}
	
	public class SadInputTask implements Task {
		@Override
		public void run() {
			input.update(secInterval);
			
			if (renderer.shouldClose()) {
				window.getLogic().preTerminate();
				actionHandler.terminate();
				window.getLogic().terminate();
				
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
