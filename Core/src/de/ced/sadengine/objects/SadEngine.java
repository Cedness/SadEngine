package de.ced.sadengine.objects;

@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class SadEngine implements SadMainLogic {
	
	private static SadLoop INSTANCE = null;
	private final SadMainLogic mainLogic;
	private int width = 1280;
	private int height = 720;
	private int ups = 60;
	private String name = "SadEngine";
	
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
	
	public final int width() {
		return width;
	}
	
	public final int height() {
		return height;
	}
	
	public int getUps() {
		return ups;
	}
	
	public void setUps(int ups) {
		this.ups = ups;
	}
	
	public String getName() {
		return name;
	}
	
	void setWidth(int width) {
		this.width = width;
	}
	
	void setHeight(int height) {
		this.height = height;
	}
	
	boolean isAntialiasing() {
		return true;
	}
}
