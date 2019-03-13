package de.ced.sadengine.objects;

import de.ced.sadengine.utils.SadVector;
import org.joml.Matrix4f;

import static de.ced.sadengine.utils.SadValue.*;

public class SadCamera extends SadPositionable implements SadCameraI {
	
	private float fov = 70f;
	private float near = 0.1f;
	private float far = 1000f;
	private Matrix4f projectionMatrix = new Matrix4f();
	private Matrix4f viewMatrix = new Matrix4f();
	private SadVector direction = new SadVector(3);
	private SadVector target = new SadVector(3);
	private boolean windowMode = false;
	private SadEntity window = null;
	private SadVector viewerPosition = new SadVector(3);
	private boolean ortho = false;
	private boolean lookingAtPosition = false;
	private float distanceToPosition = 1f;
	private SadVector cursorVector = new SadVector();
	private SadLevel level = null;
	
	@Override
	public float getFov() {
		return fov;
	}
	
	@Override
	public SadCamera setFov(float fov) {
		if (fov <= 0 || fov > 180)
			return this;
		this.fov = fov;
		return this;
	}
	
	@Override
	public float getNear() {
		return near;
	}
	
	@Override
	public SadCamera setNear(float near) {
		if (near <= 0 || near >= far)
			return this;
		this.near = near;
		return this;
	}
	
	@Override
	public float getFar() {
		return far;
	}
	
	@Override
	public SadCamera setFar(float far) {
		if (far <= near)
			return this;
		this.far = far;
		return this;
	}
	
	Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}
	
	Matrix4f getViewMatrix() {
		return viewMatrix;
	}
	
	@Override
	public boolean isWindowMode() {
		return windowMode;
	}
	
	@Override
	public SadCamera setWindowMode(boolean windowMode) {
		this.windowMode = windowMode;
		return this;
	}
	
	@Override
	public boolean isOrtho() {
		return ortho;
	}
	
	@Override
	public SadCamera setOrtho(boolean ortho) {
		this.ortho = ortho;
		return this;
	}
	
	
	@SuppressWarnings("ALL")
	public void update(float width, float height) {
		/*SadVector3 position = new SadVector3(this.position);
		SadVector3 rotation = new SadVector3(this.position);
		SadEntity entity = getEntity();
		if (entity != null) {
			position.add(entity.getPosition());
			rotation.add(entity.getRotation());
		}*/
		getDirection(direction);
		
		SadVector position = this.position.clone();
		if (lookingAtPosition) {
			float alpha = toRadians(rotation.y());
			float beta = toRadians(rotation.x());
			SadVector thirdPersonOffset = new SadVector(3);
			thirdPersonOffset.y(sin(beta) * distanceToPosition);
			float flatDistance = cos(beta) * distanceToPosition;
			thirdPersonOffset.x(sin(alpha) * flatDistance);
			thirdPersonOffset.z(cos(alpha) * flatDistance);
			position.add(thirdPersonOffset.negate());
		}
		
		viewMatrix.setLookAt(position.toVector3f(), position.toVector3f().add(direction.toVector3f(), target.toVector3f()), UP_VECTOR);
		
		float aspectRatio = width / height;
		projectionMatrix.identity();
		float left, right, bottom, top, near, far;
		
		if (windowMode) {
			SadVector pointA = null, pointB = null, pointC = null, pointD = null;
			float ar = pointB.clone().add(viewerPosition).getLength();
			float br = pointA.clone().add(viewerPosition).getLength();
			float cr = pointA.clone().add(pointB).getLength();
			float ap = pointD.clone().add(viewerPosition).getLength();
			float bp = pointA.clone().add(viewerPosition).getLength();
			float cp = pointA.clone().add(pointD).getLength();
			float betaR = acos(pow(br) - pow(cr) - pow(ar)) / (-2f * cr * ar);
			float r = br * cos(betaR);
			float betaP = acos(pow(bp) - pow(cp) - pow(ap)) / (-2f * cp * ap);
			float p = bp * cos(betaP);
			
			SadVector pointR = pointA.clone().negate().add(pointB).setLength(r).add(pointA);
			
			SadVector pointQ = pointA.clone().negate().add(pointD).setLength(p).add(pointA);
			
			SadVector zero = pointR.clone().add(pointQ);
			
			near = zero.clone().negate().add(viewerPosition).getLength();
			
			left = -r;
			right = left + cr;
			bottom = -p;
			top = bottom + cp;
		} else {
			
		}
		
		if (ortho) {
			float lr = 1 / scale.x() * aspectRatio;
			float bt = 1 / scale.y();
			projectionMatrix.setOrtho(-lr, lr, -bt, bt, this.near, this.far);
		} else {
			//projectionMatrix.setFrustum(left, right, bottom, top, near, far);
			projectionMatrix.setPerspective((float) Math.toRadians(fov), aspectRatio, this.near, this.far);
		}
	}
	
	@Override
	public boolean isLookingAtPosition() {
		return lookingAtPosition;
	}
	
	@Override
	public SadCamera setLookingAtPosition(boolean lookingAtPosition) {
		this.lookingAtPosition = lookingAtPosition;
		return this;
	}
	
	@Override
	public float getDistanceToPosition() {
		return distanceToPosition;
	}
	
	@Override
	public SadCamera setDistanceToPosition(float distanceToPosition) {
		this.distanceToPosition = distanceToPosition;
		return this;
	}
	
	@Override
	public SadVector getCursorVector() {
		return cursorVector;
	}
	
	@SuppressWarnings("unused")
	void setCursorVector(SadVector cursorVector) {
		this.cursorVector = cursorVector;
	}
	
	@Override
	public SadLevel getLevel() {
		return level;
	}
	
	@Override
	public SadCamera setLevel(SadLevel level) {
		this.level = level;
		return this;
	}
	
	@Override
	public SadEntity getWindow() {
		return window;
	}
	
	@Override
	public SadCamera setWindow(SadEntity window) {
		this.window = window;
		return this;
	}
	
	@Override
	public SadVector getViewerPosition() {
		return viewerPosition;
	}
	
	@SuppressWarnings("unused")
	public enum SadDirection {
		FORWARD,
		BACKWARD,
		LEFT,
		RIGHT,
		UP,
		DOWN
	}
	
	/*
	@Override
	public void end(float width, float height) {
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
		
		super.end(width, height);
	}
	*/
}
