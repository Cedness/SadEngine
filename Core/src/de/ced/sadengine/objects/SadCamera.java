package de.ced.sadengine.objects;

import de.ced.sadengine.utils.SadGLMatrix;
import de.ced.sadengine.utils.SadVector;
import de.ced.sadengine.utils.SadVectorListener;

import static de.ced.sadengine.utils.SadValue.*;

public class SadCamera extends SadPositionable implements SadCameraI, SadVectorListener {
	
	private SadVector upWorld = new SadVector(0f, 1f, 0f);
	private float fov = 70f;
	private boolean horizontalFov = false;
	private float near = 0.1f;
	private float far = 1000f;
	private float aspectRatio = 16f / 9f;
	
	private SadVector l = new SadVector(-1f, 0f, 0f);
	private SadVector u = new SadVector(0f, 1f, 0f);
	private SadVector f = new SadVector(0f, 0f, 1f);
	private boolean viewChanged = true;
	private boolean projectionChanged = true;
	
	private SadGLMatrix projectionMatrix = new SadGLMatrix();
	private SadGLMatrix viewMatrix = new SadGLMatrix().identity();
	
	private boolean windowMode = false;
	private SadEntity window = null;
	private SadVector viewerPosition = new SadVector(3);
	private boolean ortho = false;
	private boolean lookingAtPosition = false;
	private float distanceToPosition = 1f;
	private SadVector cursorVector = new SadVector();
	private SadLevel level = null;
	
	public SadCamera() {
		position.setListener(this);
		rotation.setListener(this);
		scale.setListener(this);
		upWorld.setListener(this);
	}
	
	@Override
	public SadVector getUpWorld() {
		return upWorld;
	}
	
	@Override
	public float getFov() {
		return fov;
	}
	
	@Override
	public SadCamera setFov(float fov) {
		if (fov <= 0 || fov > 180)
			return this;
		projectionChanged = true;
		this.fov = fov;
		return this;
	}
	
	@Override
	public boolean isHorizontalFov() {
		return horizontalFov;
	}
	
	@Override
	public SadCamera setHorizontalFov(boolean horizontalFov) {
		projectionChanged = true;
		this.horizontalFov = horizontalFov;
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
		projectionChanged = true;
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
		projectionChanged = true;
		this.far = far;
		return this;
	}
	
	@Override
	public SadVector getForward() {
		return f;
	}
	
	@Override
	public SadVector getUp() {
		return u;
	}
	
	@Override
	public SadVector getLeft() {
		return l;
	}
	
	SadGLMatrix getProjectionMatrix() {
		return projectionMatrix;
	}
	
	SadGLMatrix getViewMatrix() {
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
		projectionChanged = true;
		this.ortho = ortho;
		return this;
	}
	
	
	@SuppressWarnings("ALL")
	public void update(float width, float height) {
		
		SadVector position = this.position.clone();
		if (lookingAtPosition) {
			float alpha = toRadians(rotation.y());
			float beta = toRadians(rotation.x());
			SadVector thirdPersonOffset = new SadVector(3);
			thirdPersonOffset.y(sin(beta) * distanceToPosition);
			float flatDistance = cos(beta) * distanceToPosition;
			thirdPersonOffset.x(sin(alpha) * flatDistance);
			thirdPersonOffset.z(cos(alpha) * flatDistance);
			position.add(thirdPersonOffset.invert());
		}
		
		if (viewChanged) {
			viewMatrix.viewMatrix(this, l, u, f);
			viewChanged = false;
		}
		
		aspectRatio = width / height;
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
			
			SadVector pointR = pointA.clone().invert().add(pointB).setLength(r).add(pointA);
			
			SadVector pointQ = pointA.clone().invert().add(pointD).setLength(p).add(pointA);
			
			SadVector zero = pointR.clone().add(pointQ);
			
			near = zero.clone().invert().add(viewerPosition).getLength();
			
			left = -r;
			right = left + cr;
			bottom = -p;
			top = bottom + cp;
		}
		
		
		float lr = 1 / scale.x() * aspectRatio;
		float bt = 1 / scale.y();
		if (projectionChanged) {
			projectionMatrix.projectionMatrix(this, aspectRatio);
			projectionChanged = false;
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
	public SadVector getRay(SadVector ndc) {
		float t = tan(toRadians(fov)) * near;
		if (horizontalFov) {
			t /= aspectRatio;
		}
		float r = t * aspectRatio;
		
		ndc = ndc.clone();
		ndc.mul(r, t);
		
		SadVector ray = f.clone();
		ray.add(u.clone().mul(ndc.y()));
		ray.add(l.clone().mul(ndc.x()));
		
		return ray.normalize();
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
		if (level != null)
			level.removeCamera(this);
		this.level = level;
		if (level != null)
			level.addCamera(this);
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
	
	@Override
	public void changeEvent() {
		viewChanged = true;
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
	public void end(float getWidth, float getHeight) {
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
		
		super.end(getWidth, getHeight);
	}
	*/
}
