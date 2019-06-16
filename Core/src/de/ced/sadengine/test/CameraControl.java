package de.ced.sadengine.test;

import de.ced.sadengine.objects.SadActionLogic;
import de.ced.sadengine.objects.SadCamera;
import de.ced.sadengine.objects.input.SadInput;
import de.ced.sadengine.utils.SadVector;

import static de.ced.sadengine.utils.SadValue.*;

public class CameraControl implements SadActionLogic {
	
	private final SadCamera camera;
	private SadInput input;
	private float interval;
	private float movementSpeed = 1f;
	private float movementAcceleration = 1f;
	private float lookingSpeed = 1f;
	
	public CameraControl(SadCamera camera, SadInput input, float interval) {
		this.camera = camera;
		this.input = input;
		this.interval = interval;
	}
	
	@Override
	public void setup() {
		camera.setVelocityEnabled(true);
	}
	
	@Override
	public void update() {
		boolean forward = input.isPressed(KEY_W);
		boolean backward = input.isPressed(KEY_S);
		boolean left = input.isPressed(KEY_A);
		boolean right = input.isPressed(KEY_D);
		boolean up = input.isPressed(KEY_SPACE);
		boolean down = input.isPressed(KEY_C);
		boolean yawLeft = input.isPressed(KEY_Q);
		boolean yawRight = input.isPressed(KEY_E);
		boolean lookLeft = input.isPressed(KEY_LEFT);
		boolean lookRight = input.isPressed(KEY_RIGHT);
		boolean lookUp = input.isPressed(KEY_UP);
		boolean lookDown = input.isPressed(KEY_DOWN);
		
		SadVector p = camera.getVelocity().getPosition();
		SadVector r = camera.getVelocity().getRotation();
		
		SadVector f = camera.getForward();
		SadVector u = camera.getUp();
		SadVector l = camera.getLeft();
		
		
		float pAbs = p.getLength();
		float rAbs = r.getLength();
		
		p.addLength((pAbs < movementAcceleration ? movementAcceleration : pAbs) * -interval);
	}
}
