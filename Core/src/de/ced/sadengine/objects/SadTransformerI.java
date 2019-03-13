package de.ced.sadengine.objects;

@SuppressWarnings({"unused", "UnusedReturnValue"})
interface SadTransformerI extends SadPositionableI {
	
	SadPositionable getPositionable();
	
	SadTransformer setPositionable(SadPositionable positionable);
	
	SadMovement getMovement();
	
	SadTransformer setMovement(SadMovement movement);
}
