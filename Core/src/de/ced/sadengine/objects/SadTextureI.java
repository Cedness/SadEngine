package de.ced.sadengine.objects;

@SuppressWarnings({"unused", "UnusedReturnValue"})
interface SadTextureI extends SadObjectI {
	
	int getWidth();
	
	int getHeight();
	
	boolean isTransparent();
	
	boolean isRenderBack();
	
	void setRenderBack(boolean forceRenderBack);
	
	float getReflectivity();
	
	SadTexture setReflectivity(float reflectivity);
	
	float getLightDamper();
	
	SadTexture setLightDamper(float lightDamper);
}
