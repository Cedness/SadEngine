package de.ced.sadengine.objects;

import de.ced.sadengine.main.SadContent;
import de.ced.sadengine.utils.SadVector3;
import org.joml.Matrix4f;

import static de.ced.sadengine.utils.SadValue.UP_VECTOR;

public class SadCamera extends SadDrawable {
	
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
	protected SadVector3 cursorVector = new SadVector3();
	protected String level = null;
	
	public SadCamera(String name, SadContent content) {
		super(name, content);
	}
	
	public float getFov() {
		return fov;
	}
	
	public SadCamera setFov(float fov) {
		if (fov <= 0 || fov > 180)
			return this;
		this.fov = fov;
		return this;
	}
	
	public float getNear() {
		return near;
	}
	
	public SadCamera setNear(float near) {
		if (near <= 0 || near >= far)
			return this;
		this.near = near;
		return this;
	}
	
	public float getFar() {
		return far;
	}
	
	public SadCamera setFar(float far) {
		if (far <= near)
			return this;
		this.far = far;
		return this;
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
	
	public SadCamera setOrtho(boolean ortho) {
		this.ortho = ortho;
		return this;
	}
	
	public void update(float width, float height) {
		/*SadVector3 position = new SadVector3(this.position);
		SadVector3 rotation = new SadVector3(this.position);
		SadEntity entity = getEntity();
		if (entity != null) {
			position.add(entity.getPosition());
			rotation.add(entity.getRotation());
		}*/
		double pitch = Math.toRadians(rotation.x());
		double yaw = Math.toRadians(rotation.y());
		double roll = Math.toRadians(rotation.z());
		
		double cosPitch = Math.cos(pitch);
		direction.x((float) (cosPitch * Math.sin(yaw)));
		direction.y((float) Math.sin(pitch));
		direction.z((float) (cosPitch * Math.cos(yaw)));
		
		viewMatrix.setLookAt(position.toVector3f(), position.toVector3f().add(direction.toVector3f(), target.toVector3f()), UP_VECTOR.toVector3f());
		
		float aspectRatio = width / height;
		projectionMatrix.identity();
		if (ortho) {
			float lr = 1 / scale.x() * aspectRatio;
			float bt = 1 / scale.y();
			projectionMatrix.setOrtho(-lr, lr, -bt, bt, near, far);
		} else {
			projectionMatrix.setPerspective((float) Math.toRadians(fov), aspectRatio, near, far);
		}
	}
	
	public boolean isLookingAtPosition() {
		return lookingAtPosition;
	}
	
	public SadCamera setLookingAtPosition(boolean lookingAtPosition) {
		this.lookingAtPosition = lookingAtPosition;
		return this;
	}
	
	public float getDistanceToPosition() {
		return distanceToPosition;
	}
	
	public SadCamera setDistanceToPosition(float distanceToPosition) {
		this.distanceToPosition = distanceToPosition;
		return this;
	}
	
	public SadVector3 getCursorVector() {
		return cursorVector;
	}
	
	void setCursorVector(SadVector3 cursorVector) {
		this.cursorVector = cursorVector;
	}
	
	public SadLevel getLevel() {
		return content.getLevel(level);
	}
	
	public SadCamera setLevel(String name) {
		level = name;
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
