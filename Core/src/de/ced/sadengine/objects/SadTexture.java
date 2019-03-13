package de.ced.sadengine.objects;

import java.io.File;

import static org.lwjgl.opengl.GL11.glDeleteTextures;

public class SadTexture extends SadObject implements SadTextureI {
	
	private final int textureID;
	final int width;
	final int height;
	private final boolean transparent;
	
	private boolean renderBack = false;
	
	private float lightDamper;
	private float reflectivity;
	
	public SadTexture(File file) {
		this(SadGL.loadTexture(file));
	}
	
	SadTexture(int[] data) {
		textureID = data[0];
		width = data[1];
		height = data[2];
		transparent = data[3] > 3;
	}
	
	public int getTextureID() {
		return textureID;
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
		return transparent;
	}
	
	@Override
	public boolean isRenderBack() {
		return renderBack;
	}
	
	@Override
	public void setRenderBack(boolean renderBack) {
		this.renderBack = renderBack;
	}
	
	@Override
	public float getLightDamper() {
		return lightDamper;
	}
	
	@Override
	public SadTexture setLightDamper(float lightDamper) {
		this.lightDamper = lightDamper;
		return this;
	}
	
	@Override
	public float getReflectivity() {
		return reflectivity;
	}
	
	@Override
	public SadTexture setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
		return this;
	}
	
	@Override
	void release() {
		glDeleteTextures(textureID);
	}
}
