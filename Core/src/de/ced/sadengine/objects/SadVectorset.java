package de.ced.sadengine.objects;

import de.ced.sadengine.utils.SadVector;

public abstract class SadVectorset extends SadObject {
	
	SadVector position;
	SadVector rotation;
	SadVector scale;
	
	SadPositionable velocity = null;
	
	public SadVector getPosition() {
		return position;
	}
	
	public SadVector getRotation() {
		return rotation;
	}
	
	public SadVector getScale() {
		return scale;
	}
}
