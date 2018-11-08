package de.ced.sadengine.api;

public final class Saddings {
	
	private String name = "SadEngine3D";
	private int width = 1280;
	private int height = 720;
	private boolean resized = false;
	private boolean fullscreen = false;
	private int monitor = 0;
	private int ups = 60;
	private int fpsDivider = 1;
	private boolean antialiasing = true;
	
	public String getName() {
		return name;
	}
	
	public Saddings setName(String name) {
		this.name = name;
		return this;
	}
	
	public int getWidth() {
		return width;
	}
	
	public Saddings setWidth(int width) {
		this.width = width;
		resized = true;
		return this;
	}
	
	public int getHeight() {
		return height;
	}
	
	public Saddings setHeight(int height) {
		this.height = height;
		resized = true;
		return this;
	}
	
	public boolean isResized() {
		return resized;
	}
	
	public boolean isFullscreen() {
		return fullscreen;
	}
	
	public Saddings setFullscreen(boolean fullscreen) {
		this.fullscreen = fullscreen;
		return this;
	}
	
	public int getMonitor() {
		return monitor;
	}
	
	public Saddings setMonitor(int monitor) {
		this.monitor = monitor;
		return this;
	}
	
	public int getUps() {
		return ups;
	}
	
	public Saddings setUps(int ups) {
		this.ups = ups;
		return this;
	}
	
	public int getFpsDivider() {
		return fpsDivider;
	}
	
	public Saddings setFpsDivider(int fpsDivider) {
		if (fpsDivider < 1)
			fpsDivider = 1;
		
		this.fpsDivider = fpsDivider;
		return this;
	}
	
	public boolean isAntialiasing() {
		return antialiasing;
	}
	
	public Saddings setAntialiasing(boolean antialiasing) {
		this.antialiasing = antialiasing;
		return this;
	}
}
