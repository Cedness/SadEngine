package de.ced.sadengine.objects;

import static org.lwjgl.opengl.GL11.glDeleteTextures;

public class SadTexture extends SadObject {
	
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
	
	public float getLightDamper() {
		return lightDamper;
	}
	
	public void setLightDamper(float lightDamper) {
		this.lightDamper = lightDamper;
	}
	
	public float getReflectivity() {
		return reflectivity;
	}
	
	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}
	
	@Override
	public void release() {
		glDeleteTextures(textureID);
	}
}
