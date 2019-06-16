package de.ced.sadengine.objects;

import de.ced.sadengine.utils.SadVector;

@SuppressWarnings({"unused", "UnusedReturnValue"})
interface SadPositionableI extends SadObjectI {
	
	boolean isDirectionalRotation();
	
	SadPositionable setDirectionalRotation(boolean directionalRotation);
	
	SadPositionable setVelocityEnabled(boolean enabled);
	
	SadPositionable getVelocity();
	
	SadVector getYawDirection(SadVector yawDirection);
	
	SadVector getYawDirection();
	
	SadVector getDirection(SadVector direction);
	
	SadVector getDirection();
}
