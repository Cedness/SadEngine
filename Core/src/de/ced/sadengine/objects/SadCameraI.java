package de.ced.sadengine.objects;

import de.ced.sadengine.utils.SadVector;

@SuppressWarnings({"unused", "UnusedReturnValue", "WeakerAccess", "BooleanMethodIsAlwaysInverted"})
public interface SadCameraI extends SadPositionableI {
	
	float getFov();
	
	SadCamera setFov(float fov);
	
	float getNear();
	
	SadCamera setNear(float near);
	
	float getFar();
	
	SadCamera setFar(float far);
	
	boolean isWindowMode();
	
	SadCamera setWindowMode(boolean windowMode);
	
	boolean isOrtho();
	
	SadCamera setOrtho(boolean ortho);
	
	boolean isLookingAtPosition();
	
	SadCamera setLookingAtPosition(boolean lookingAtPosition);
	
	float getDistanceToPosition();
	
	SadCamera setDistanceToPosition(float distanceToPosition);
	
	SadVector getCursorVector();
	
	SadLevel getLevel();
	
	SadCamera setLevel(String name);
	
	SadEntity getWindow();
	
	SadCamera setWindow(String name);
	
	SadVector getViewerPosition();
}
