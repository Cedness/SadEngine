package de.ced.sadengine.objects;

import de.ced.sadengine.objects.input.SadInput;
import de.ced.sadengine.objects.time.SadClockwork;

@SuppressWarnings({"unused", "WeakerAccess"})
public class SadEngine extends SadFrame implements SadMainLogic {
	
	private static SadLoop INSTANCE = null;
	private final SadMainLogic mainLogic;
	private SadRenderer renderer;
	private SadMover mover;
	private SadInput input;
	private SadActionHandler actionHandler;
	private SadClockwork clockwork;
	private float interval;
	private long startTime;
	private int width = 1280;
	private int height = 720;
	private int ups = 60;
	private boolean fullscreen;
	
	public SadEngine(SadMainLogic mainLogic) {
		this(mainLogic, 0);
		start();
	}
	
	public SadEngine(SadMainLogic mainLogic, int lol) {
		this.mainLogic = mainLogic;
	}
	
	protected SadEngine() {
		this(0);
		start();
	}
	
	protected SadEngine(int lol) {
		mainLogic = this;
	}
	
	public final void start() {
		if (INSTANCE != null)
			return;
		INSTANCE = new SadLoop(this);
	}
	
	public final void stop() {
		INSTANCE.stop();
	}
	
	SadMainLogic getLogic() {
		return mainLogic;
	}
	
	void setup(SadRenderer renderer, SadMover mover, SadInput input, SadActionHandler actionHandler, SadClockwork clockwork) {
		this.renderer = renderer;
		this.mover = mover;
		this.input = input;
		this.actionHandler = actionHandler;
		this.clockwork = clockwork;
	}
	
	public SadRenderer getRenderer() {
		return renderer;
	}
	
	public SadMover getMover() {
		return mover;
	}
	
	public SadInput getInput() {
		return input;
	}
	
	public SadActionHandler getActionHandler() {
		return actionHandler;
	}
	
	public SadClockwork getClockwork() {
		return clockwork;
	}
	
	void setInterval(float interval) {
		this.interval = interval;
	}
	
	public float getInterval() {
		return interval;
	}
	
	void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	
	public float getRunningTime() {
		return (System.nanoTime() - startTime) / 1000000000f;
	}
	
	@Override
	public final int getWidth() {
		return width;
	}
	
	@Override
	public final int getHeight() {
		return height;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getUps() {
		return ups;
	}
	
	public void setUps(int ups) {
		this.ups = ups;
	}
	
	@Deprecated
	boolean isAntialiasing() {
		return true;
	}
	
	public boolean isFullscreen() {
		return fullscreen;
	}
	
	public void setFullscreen(boolean fullscreen) {
		this.fullscreen = fullscreen;
	}
}
