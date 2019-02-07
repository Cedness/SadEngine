package de.ced.sadengine.objects;

import de.ced.sadengine.utils.SadVector;
import org.joml.Matrix4f;

import static de.ced.sadengine.utils.SadValue.*;

public abstract class SadPositionable extends SadObject implements SadPositionableI {
	
	@SuppressWarnings("unused")
	public SadPositionable(String name) {
		super(name);
	}
	
	SadPositionable(String name, SadContent content) {
		super(name, content);
	}
	
	SadVector position = new SadVector(3);
	SadVector rotation = new SadVector(3);
	SadVector scale = new SadVector(1f, 1f, 1f);
	
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
	
	Matrix4f writeToMatrix(Matrix4f matrix) {
		matrix.translate(position.toVector3f());
		matrix.rotateXYZ(
				toRadians(rotation.x()),
				toRadians(rotation.y()),
				toRadians(rotation.z()));
		matrix.scale(scale.toVector3f());
		return matrix;
	}
	
	Matrix4f writeToMatrix() {
		return writeToMatrix(new Matrix4f());
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
