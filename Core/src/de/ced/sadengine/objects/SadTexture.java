package de.ced.sadengine.objects;

import static org.lwjgl.opengl.GL11.glDeleteTextures;

public class SadTexture extends SadObject implements SadTextureI {
	
	private final int textureID;
	
	private float lightDamper;
	private float reflectivity;
	
	public SadTexture(String name, int textureID) {
		super(name);
		this.textureID = textureID;
	}
	
	public int getTextureID() {
		return textureID;
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
	public void release() {
		glDeleteTextures(textureID);
	}
}
