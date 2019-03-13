package de.ced.sadengine.objects;

import de.ced.sadengine.utils.SadVector;

@SuppressWarnings({"unused", "UnusedReturnValue", "WeakerAccess"})
interface SadFrameI extends SadTextureI {
	
	SadCamera getCamera();
	
	SadFrame setCamera(SadCamera camera);
	
	SadVector getColor();
	
	int getWidth();
	
	int getHeight();
	
	int getGeniousParadoxPreventer();
	
	void setGeniousParadoxPreventer(int geniousParadoxPreventer);
}
