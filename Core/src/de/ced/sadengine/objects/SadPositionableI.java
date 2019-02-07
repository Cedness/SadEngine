package de.ced.sadengine.objects;

import de.ced.sadengine.utils.SadVector;

@SuppressWarnings("unused")
public interface SadPositionableI {
	
	SadVector getPosition();
	
	SadVector getRotation();
	
	SadVector getScale();
	
	SadVector getYawDirection(SadVector yawDirection);
	
	SadVector getYawDirection();
	
	SadVector getDirection(SadVector direction);
	
	SadVector getDirection();
}
