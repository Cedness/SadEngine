package de.ced.sadengine.trash;

import de.ced.sadengine.objects.SadCamera;
import de.ced.sadengine.objects.SadEngine;
import de.ced.sadengine.objects.SadFrame;
import de.ced.sadengine.objects.SadTexture;
import de.ced.sadengine.utils.SadVector;

public class SadWindi {
	
	private int width;
	private int height;
	
	public void setup(SadEngine engine) {
		width = engine.getWidth();
		height = engine.getHeight();
	}
	
	public SadCamera getCamera() {
		return null;
	}
	
	public SadFrame setCamera(SadCamera camera) {
		return null;
	}
	
	public SadVector getColor() {
		return null;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public boolean isTransparent() {
		return false;
	}
	
	public boolean isRenderBack() {
		return false;
	}
	
	public void setRenderBack(boolean forceRenderBack) {
	}
	
	public float getReflectivity() {
		return 0;
	}
	
	public SadTexture setReflectivity(float reflectivity) {
		return null;
	}
	
	public float getLightDamper() {
		return 0;
	}
	
	public SadTexture setLightDamper(float lightDamper) {
		return null;
	}
	
	public int getGeniousParadoxPreventer() {
		return 0;
	}
	
	public void setGeniousParadoxPreventer(int geniousParadoxPreventer) {
	}
	
	public String getName() {
		return null;
	}
	
	public void setName(String name) {
	}
}
