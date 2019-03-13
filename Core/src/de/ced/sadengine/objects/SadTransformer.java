package de.ced.sadengine.objects;

import de.ced.sadengine.utils.SadVector;

import static de.ced.sadengine.objects.SadMovement.WHEN_DISPLAYED;

@SuppressWarnings("ConstantConditions")
public class SadTransformer extends SadPositionable implements SadTransformerI {
	
	private SadPositionable positionable = null;
	private SadMovement movement = WHEN_DISPLAYED;
	
	@Override
	public SadPositionable getPositionable() {
		return positionable;
	}
	
	@Override
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
	
	@Override
	public SadMovement getMovement() {
		return movement;
	}
	
	@Override
	public SadTransformer setMovement(SadMovement movement) {
		this.movement = movement;
		return this;
	}
}
