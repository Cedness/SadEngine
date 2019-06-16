package de.ced.sadengine.objects;

import de.ced.sadengine.utils.SadVector;

public abstract class SadVectorset extends SadObject implements SadVectorsetI {
	
	SadVector position;
	SadVector rotation;
	SadVector scale;
	
	SadPositionable velocity = null;
	
	@Override
	public SadVector getPosition() {
		return position;
	}
	
	@Override
	public SadVector getRotation() {
		return rotation;
	}
	
	@Override
	public SadVector getScale() {
		return scale;
	}
}
