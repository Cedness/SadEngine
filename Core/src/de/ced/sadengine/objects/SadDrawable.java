package de.ced.sadengine.objects;

import de.ced.sadengine.main.SadContent;
import de.ced.sadengine.utils.SadLimitedVector;
import de.ced.sadengine.utils.SadVector;
import org.joml.Matrix4f;

import static de.ced.sadengine.utils.SadValue.toRadians;

public abstract class SadDrawable extends SadObject {
	
	public SadDrawable(String name) {
		super(name);
	}
	
	public SadDrawable(String name, SadContent content) {
		super(name, content);
	}
	
	protected SadVector position = new SadVector();
	protected SadLimitedVector rotation = new SadLimitedVector(0, 360);
	protected SadVector scale = new SadVector(1f, 1f, 1f);
	
	public SadVector getPosition() {
		return position;
	}
	
	public SadVector getRotation() {
		return rotation;
	}
	
	public SadVector getScale() {
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
