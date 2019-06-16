package de.ced.sadengine.objects;

import de.ced.sadengine.utils.SadVector;

public class SadVelocity extends SadPositionable {
	
	public SadVelocity() {
		rotation = new SadVector(3);
		scale.set(0f);
	}
}
