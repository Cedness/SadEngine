package de.ced.sadengine.objects;

import de.ced.sadengine.utils.SadVector;

public class SadWindow implements SadFrameI {
	
	private int width;
	private int height;
	
	public void setup(SadEngine engine) {
		width = engine.width();
		height = engine.height();
	}
	
	@Override
	public SadCamera getCamera() {
		return null;
	}
	
	@Override
	public SadFrame setCamera(SadCamera camera) {
		return null;
	}
	
	@Override
	public SadVector getColor() {
		return null;
	}
	
	@Override
	public int getWidth() {
		return width;
	}
	
	@Override
	public int getHeight() {
		return height;
	}
	
	@Override
	public boolean isTransparent() {
		return false;
	}
	
	@Override
	public boolean isRenderBack() {
		return false;
	}
	
	@Override
	public void setRenderBack(boolean forceRenderBack) {
	}
	
	@Override
	public float getReflectivity() {
		return 0;
	}
	
	@Override
	public SadTexture setReflectivity(float reflectivity) {
		return null;
	}
	
	@Override
	public float getLightDamper() {
		return 0;
	}
	
	@Override
	public SadTexture setLightDamper(float lightDamper) {
		return null;
	}
	
	@Override
	public int getGeniousParadoxPreventer() {
		return 0;
	}
	
	@Override
	public void setGeniousParadoxPreventer(int geniousParadoxPreventer) {
	}
	
	@Override
	public String getName() {
		return null;
	}
	
	@Override
	public void setName(String name) {
	}
}
