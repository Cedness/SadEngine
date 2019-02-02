package de.ced.sadengine.objects;

import de.ced.sadengine.main.SadContent;
import de.ced.sadengine.utils.SadLimitedVector3;
import de.ced.sadengine.utils.SadVector3;
import org.joml.Matrix4f;

import static de.ced.sadengine.utils.SadValue.toRadians;

public abstract class SadDrawable extends SadObject {
	
	public SadDrawable(String name) {
		super(name);
	}
	
	public SadDrawable(String name, SadContent content) {
		super(name, content);
	}
	
	protected SadVector3 position = new SadVector3();
	protected SadLimitedVector3 rotation = new SadLimitedVector3(0, 360);
	protected SadVector3 scale = new SadVector3(1f, 1f, 1f);
	
	public SadVector3 getPosition() {
		return position;
	}
	
	public SadVector3 getRotation() {
		return rotation;
	}
	
	public SadVector3 getScale() {
		return scale;
	}
	
	public Matrix4f writeToMatrix(Matrix4f matrix) {
		matrix.translate(position.toVector3f());
		matrix.rotateXYZ(
				toRadians(rotation.x()),
				toRadians(rotation.y()),
				toRadians(rotation.z()));
		matrix.scale(scale.toVector3f());
		return matrix;
	}
	
	public Matrix4f writeToMatrix() {
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
