package de.ced.sadengine.objects;

import de.ced.sadengine.main.SadContent;
import de.ced.sadengine.utils.SadLimitedVector3;
import de.ced.sadengine.utils.SadVector3;
import org.joml.Matrix4f;

import static de.ced.sadengine.utils.SadValue.UP_VECTOR;

public class SadCamera extends SadObject {
	
	protected SadVector3 position = new SadVector3();
	protected SadLimitedVector3 rotation = new SadLimitedVector3(0, 360);
	protected float fov = 70f;
	protected float near = 0.1f;
	protected float far = 1000f;
	protected Matrix4f projectionMatrix = new Matrix4f();
	protected Matrix4f viewMatrix = new Matrix4f();
	protected SadVector3 direction = new SadVector3();
	protected SadVector3 target = new SadVector3();
	protected boolean ortho = false;
	protected boolean lookingAtPosition = false;
	protected float distanceToPosition = 1f;
	protected String level = null;
	protected String entity = null;
	
	public SadCamera(String name, SadContent content) {
		super(name, content);
	}
	
	public SadVector3 getPosition() {
		return position;
	}
	
	public SadVector3 getRotation() {
		return rotation;
	}
	
	public float getFov() {
		return fov;
	}
	
	public void setFov(float fov) {
		this.fov = fov;
	}
	
	public float getNear() {
		return near;
	}
	
	public void setNear(float near) {
		this.near = near;
	}
	
	public float getFar() {
		return far;
	}
	
	public void setFar(float far) {
		this.far = far;
	}
	
	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}
	
	public Matrix4f getViewMatrix() {
		return viewMatrix;
	}
	
	public boolean isOrtho() {
		return ortho;
	}
	
	public void setOrtho(boolean ortho) {
		this.ortho = ortho;
	}
	
	public void update(float width, float height) {
		SadVector3 position = new SadVector3(this.position);
		SadVector3 rotation = new SadVector3(this.position);
		SadEntity entity = getEntity();
		if (entity != null) {
			position.add(entity.getPosition());
			rotation.add(entity.getRotation());
		}
		double pitch = Math.toRadians(rotation.x());
		double yaw = Math.toRadians(rotation.y());
		double roll = Math.toRadians(rotation.z());
		
		double cosPitch = Math.cos(pitch);
		direction.x((float) (cosPitch * Math.sin(yaw)));
		direction.y((float) Math.sin(pitch));
		direction.z((float) (cosPitch * Math.cos(yaw)));
		
		viewMatrix.setLookAt(position.toVector3f(), position.toVector3f().add(direction.toVector3f(), target.toVector3f()), UP_VECTOR.toVector3f());
		projectionMatrix.setPerspective((float) Math.toRadians(fov), width / height, near, far);
	}
	
	public boolean isLookingAtPosition() {
		return lookingAtPosition;
	}
	
	public void setLookingAtPosition(boolean lookingAtPosition) {
		this.lookingAtPosition = lookingAtPosition;
	}
	
	public float getDistanceToPosition() {
		return distanceToPosition;
	}
	
	public void setDistanceToPosition(float distanceToPosition) {
		this.distanceToPosition = distanceToPosition;
	}
	
	public SadLevel getLevel() {
		return content.getLevel(level);
	}
	
	public SadCamera setLevel(String name) {
		level = name;
		return this;
	}
	
	public SadEntity getEntity() {
		return content.getEntity(entity);
	}
	
	public SadCamera setEntity(String name) {
		entity = name;
		return this;
	}
	
	/*
	@Override
	public void update(float width, float height) {
		while (rotation.y() > 180) {
			rotation.add(0, -360, 0);
		}
		while (rotation.y() < -180) {
			rotation.add(0, 360, 0);
		}
		while (rotation.x() >= 90) {
			rotation.x(89.9999f);
		}
		while (rotation.x() <= -90) {
			rotation.x(-89.9999f);
		}
		
		super.update(width, height);
	}
	*/
}
