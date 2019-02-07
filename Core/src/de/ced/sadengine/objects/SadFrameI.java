package de.ced.sadengine.objects;

import de.ced.sadengine.utils.SadVector;

@SuppressWarnings({"unused", "UnusedReturnValue", "WeakerAccess"})
public interface SadFrameI extends SadTextureI {
	
	SadCamera getCamera();
	
	SadFrame setCamera(String name);
	
	SadVector getColor();
	
	int getWidth();
	
	int getHeight();
	
	int getGeniousParadoxPreventer();
	
	void setGeniousParadoxPreventer(int geniousParadoxPreventer);
}
