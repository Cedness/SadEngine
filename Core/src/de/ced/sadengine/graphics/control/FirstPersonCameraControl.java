package de.ced.sadengine.graphics.control;

public class FirstPersonCameraControl {
	
	/*
	private SadCamera camera;
	
	private boolean flyMode = true;
	private boolean keyControl = true;
	private boolean lookWithKeys = true;
	private HashMap<FPCAction, Integer> keyMap = new HashMap<>();
	private float moveAcceleration = 0.05f;
	private float lookAcceleration = 0.1f;
	private float baseMoveSpeed = 12f;
	private float baseLookSpeed = 80f;
	
	private Vector3f moveSpeed = new Vector3f();
	private Vector2f lookSpeed = new Vector2f();
	
	public FirstPersonCameraControl() {
		for (FPCAction action : FPCAction.values()) {
			keyMap.put(action, action.getDefaultKey());
		}
	}
	
	public FirstPersonCameraControl(SadCamera camera) {
		this();
		setCamera(camera);
	}
	
	private void moveTo(float x, float y, float z, float speed) {
		speed *= baseMoveSpeed;
		camera.getPosition().add(speed * x, speed * y, speed * z);
	}
	
	private void lookAt(float x, float y, float speed) {
		speed *= baseLookSpeed;
		camera.getRotation().add(speed * x, speed * y, 0f);
		if (camera.getRotation().x() >= 90 || camera.getRotation().x() <= -90)
			lookSpeed.x = 0;
	}
	
	private float change(float value, boolean look, boolean incr, boolean decr) {
		Boolean direction = incr ^ decr ? incr : null;
		boolean decelerate = direction == null;
		int limit = decelerate ? 0 : 1;
		if (decelerate)
			direction = value < 0f;
		value += (direction ? 1 : -1) * (look ? lookAcceleration : moveAcceleration);
		return direction ? (value < limit ? value : limit) : (value > -limit ? value : -limit);
	}
	
	@Override
	public void move(SadContent content, SadInput input) {
		if (keyControl) {
			if (flyMode) {
				
				moveSpeed.x = change(moveSpeed.x, false, input.isPressed(keyMap.get(FPCAction.MOVE_LEFT)), input.isPressed(keyMap.get(FPCAction.MOVE_RIGHT)));
				moveSpeed.y = change(moveSpeed.y, false, input.isPressed(keyMap.get(FPCAction.MOVE_UP)), input.isPressed(keyMap.get(FPCAction.MOVE_DOWN)));
				moveSpeed.z = change(moveSpeed.z, false, input.isPressed(keyMap.get(FPCAction.MOVE_FORWARD)), input.isPressed(keyMap.get(FPCAction.MOVE_BACK)));
				
				lookSpeed.x = change(lookSpeed.x, true, input.isPressed(keyMap.get(FPCAction.LOOK_UP)), input.isPressed(keyMap.get(FPCAction.LOOK_DOWN)));
				lookSpeed.y = change(lookSpeed.y, true, input.isPressed(keyMap.get(FPCAction.LOOK_LEFT)), input.isPressed(keyMap.get(FPCAction.LOOK_RIGHT)));
				
				float yaw = (float) (camera.getRotation().y() / (PI / 2));
				float zMove = -(Math.abs(yaw) - 1f);
				float xMove = yaw > 1f ? -(yaw - 2f) : (yaw < -1 ? -(yaw + 2f) : yaw);
				
				moveTo(xMove, 0, zMove, moveSpeed.z * content.getInterval());
				moveTo(zMove, 0, -xMove, moveSpeed.x * content.getInterval());
				moveTo(0, 1, 0, moveSpeed.y * content.getInterval());
				
				lookAt(1f, 0f, lookSpeed.x * content.getInterval());
				lookAt(0f, 1f, lookSpeed.y * content.getInterval());
			}
		}
	}
	
	public HashMap<FPCAction, Integer> getKeyMap() {
		return keyMap;
	}
	
	public boolean isFlyMode() {
		return flyMode;
	}
	
	public void setFlyMode(boolean flyMode) {
		this.flyMode = flyMode;
	}
	
	public boolean isKeyControl() {
		return keyControl;
	}
	
	public void setKeyControl(boolean keyControl) {
		this.keyControl = keyControl;
	}
	
	public boolean isLookWithKeys() {
		return lookWithKeys;
	}
	
	public void setLookWithKeys(boolean lookWithKeys) {
		this.lookWithKeys = lookWithKeys;
	}
	
	public float getMoveSpeed() {
		return baseMoveSpeed;
	}
	
	public void setMoveSpeed(float moveSpeed) {
		this.baseMoveSpeed = moveSpeed;
	}
	
	public float getLookSpeed() {
		return baseLookSpeed;
	}
	
	public void setLookSpeed(float lookSpeed) {
		this.baseLookSpeed = lookSpeed;
	}
	
	public float getMoveAcceleration() {
		return moveAcceleration;
	}
	
	public void setMoveAcceleration(float acceleration) {
		this.moveAcceleration = acceleration;
	}
	
	public float getLookAcceleration() {
		return lookAcceleration;
	}
	
	public void setLookAcceleration(float acceleration) {
		this.lookAcceleration = acceleration;
	}
	*/
}