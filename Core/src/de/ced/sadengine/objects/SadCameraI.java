package de.ced.sadengine.objects;

import de.ced.sadengine.utils.SadVector;

@SuppressWarnings({"unused", "UnusedReturnValue", "WeakerAccess", "BooleanMethodIsAlwaysInverted"})
interface SadCameraI extends SadPositionableI {
	
	SadVector getUpWorld();
	
	float getFov();
	
	SadCamera setFov(float fov);
	
	boolean isHorizontalFov();
	
	SadCamera setHorizontalFov(boolean horizontalFov);
	
	float getNear();
	
	SadCamera setNear(float near);
	
	float getFar();
	
	SadCamera setFar(float far);
	
	SadVector getForward();
	
	SadVector getUp();
	
	SadVector getLeft();
	
	boolean isWindowMode();
	
	SadCamera setWindowMode(boolean windowMode);
	
	boolean isOrtho();
	
	SadCamera setOrtho(boolean ortho);
	
	boolean isLookingAtPosition();
	
	SadCamera setLookingAtPosition(boolean lookingAtPosition);
	
	float getDistanceToPosition();
	
	SadCamera setDistanceToPosition(float distanceToPosition);
	
	SadVector getRay(SadVector ndc);
	
	SadVector getCursorVector();
	
	SadLevel getLevel();
	
	SadCamera setLevel(SadLevel level);
	
	SadEntity getWindow();
	
	SadCamera setWindow(SadEntity window);
	
	SadVector getViewerPosition();
}
