package de.ced.sadengine.objects;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public interface SadTextureI extends SadObjectI {
	
	float getReflectivity();
	
	SadTexture setReflectivity(float reflectivity);
	
	float getLightDamper();
	
	SadTexture setLightDamper(float lightDamper);
}
