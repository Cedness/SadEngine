package de.ced.sadengine.objects;

import static org.lwjgl.opengl.GL11.glDeleteTextures;

public class SadTexture extends SadObject {
	
	private final int id;
	
	private float lightDamper;
	private float reflectivity;
	
	public SadTexture(String name, int id) {
		super(name);
		this.id = id;
	}
	
	public int getID() {
		return id;
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
		glDeleteTextures(id);
	}
}
