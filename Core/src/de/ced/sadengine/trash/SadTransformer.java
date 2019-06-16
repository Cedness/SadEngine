package de.ced.sadengine.trash;

import de.ced.sadengine.objects.SadCamera;
import de.ced.sadengine.objects.SadMovement;
import de.ced.sadengine.objects.SadPositionable;
import de.ced.sadengine.utils.SadVector;

import static de.ced.sadengine.objects.SadMovement.WHEN_DISPLAYED;

@SuppressWarnings("ConstantConditions")
public class SadTransformer extends SadPositionable {
	
	private SadPositionable positionable = null;
	private SadMovement movement = WHEN_DISPLAYED;
	
	public SadPositionable getPositionable() {
		return positionable;
	}
	
	public SadTransformer setPositionable(SadPositionable positionable) {
		this.positionable = positionable;
		return this;
	}
	
	public SadPositionable move(SadCamera.SadDirection direction, float way) {
		move(direction, way, true);
		return this;
	}
	
	public SadPositionable move(SadCamera.SadDirection direction, float way, boolean usePitch) {
		SadVector rawDirection = usePitch ? getDirection() : getYawDirection();
		return this;
	}
	
	public SadMovement getMovement() {
		return movement;
	}
	
	public SadTransformer setMovement(SadMovement movement) {
		this.movement = movement;
		return this;
	}
}
