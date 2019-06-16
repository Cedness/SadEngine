package de.ced.sadengine.objects;

import de.ced.sadengine.utils.SadRotationVector;
import de.ced.sadengine.utils.SadVector;

import static de.ced.sadengine.utils.SadValue.*;

public class SadPositionable extends SadVectorset {
	
	private boolean directionalRotation = false;
	
	public SadPositionable() {
		position = new SadVector(3);
		rotation = new SadRotationVector(3);
		scale = new SadVector(3).set(1f);
	}
	
	public boolean isDirectionalRotation() {
		return directionalRotation;
	}
	
	public SadPositionable setDirectionalRotation(boolean directionalRotation) {
		if (this.directionalRotation == directionalRotation)
			return this;
		this.directionalRotation = directionalRotation;
		rotation = directionalRotation ? new SadVector(3).z(1f) : new SadRotationVector(3);
		return this;
	}
	
	public SadPositionable setVelocityEnabled(boolean enabled) {
		if (!enabled) {
			velocity = null;
			return this;
		}
		if (velocity != null)
			return this;
		velocity = new SadVelocity();
		return this;
	}
	
	public SadPositionable getVelocity() {
		return velocity;
	}
	
	public SadVector getYawDirection(SadVector yawDirection) {
		if (yawDirection.getDimension() < 3)
			return yawDirection;
		float yaw = toRadians(rotation.y());
		yawDirection.x(sin(yaw));
		yawDirection.z(cos(yaw));
		return yawDirection;
	}
	
	public SadVector getYawDirection() {
		return getYawDirection(new SadVector(3));
	}
	
	public SadVector getDirection(SadVector direction) {
		if (direction.getDimension() < 3)
			return direction;
		float pitch = toRadians(rotation.x());
		float yaw = toRadians(rotation.y());
		float cosPitch = cos(pitch);
		direction.x(cosPitch * sin(yaw));
		direction.y(sin(pitch));
		direction.z(cosPitch * cos(yaw));
		return direction;
	}
	
	public SadVector getDirection() {
		return getDirection(new SadVector(3));
	}
}
