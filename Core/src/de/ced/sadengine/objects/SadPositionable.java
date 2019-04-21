package de.ced.sadengine.objects;

import de.ced.sadengine.utils.SadRotationVector;
import de.ced.sadengine.utils.SadVector;

import static de.ced.sadengine.utils.SadValue.*;

public class SadPositionable extends SadObject implements SadPositionableI {
	
	private boolean usePitchWhenMoving;
	
	SadVector position = new SadVector(3);
	SadVector rotation = new SadRotationVector(3);
	SadVector scale = new SadVector(1f, 1f, 1f);
	
	SadPositionable velocity = null;
	
	SadPositionable() {
	}
	
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
	
	@Override
	public void setVelocityEnabled(boolean enabled) {
		if (!enabled) {
			velocity = null;
			return;
		}
		if (velocity != null)
			return;
		velocity = new SadPositionable();
		velocity.getScale().set(0);
	}
	
	@Override
	public SadPositionable getVelocity() {
		return velocity;
	}
	
	@Override
	public SadVector getYawDirection(SadVector yawDirection) {
		if (yawDirection.getDimension() < 3)
			return yawDirection;
		float yaw = toRadians(rotation.y());
		yawDirection.x(sin(yaw));
		yawDirection.z(cos(yaw));
		return yawDirection;
	}
	
	@Override
	public SadVector getYawDirection() {
		return getYawDirection(new SadVector(3));
	}
	
	@Override
	public SadVector getDirection(SadVector direction) {
		if (direction.getDimension() < 3)
			return direction;
		float pitch = toRadians(rotation.x());
		float yaw = toRadians(rotation.y());
		//float roll = toRadians(rotation.z());
		float cosPitch = cos(pitch);
		direction.x(cosPitch * sin(yaw));
		direction.y(sin(pitch));
		direction.z(cosPitch * cos(yaw));
		return direction;
	}
	
	@Override
	public SadVector getDirection() {
		return getDirection(new SadVector(3));
	}
	
	@Override
	public boolean isUsePitchWhenMoving() {
		return usePitchWhenMoving;
	}
	
	@Override
	public SadPositionable setUsePitchWhenMoving(boolean usePitchWhenMoving) {
		this.usePitchWhenMoving = usePitchWhenMoving;
		return this;
	}
	
	/*
	protected boolean physicsEnabled = false;
	protected SadPhysics physics = new SadPhysics();
	
	public SadPhysics getPhysics() {
		return physics;
	}
	
	public void enablePhysics(boolean enable) {
		physicsEnabled = enable;
	}
	*/
}
