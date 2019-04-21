package de.ced.sadengine.objects.input;

import de.ced.sadengine.objects.SadGlWindow;
import de.ced.sadengine.utils.SadVector;
import org.lwjgl.glfw.GLFWCursorEnterCallback;
import org.lwjgl.glfw.GLFWCursorPosCallback;

public class SadInput {
	
	private static final int ARRAY_SIZE = 512;
	
	private SadKeyboard keyboardCallback = new SadKeyboard(this);
	private SadMouse mouseCallback = new SadMouse(this);
	private SadScroll scrollCallback = new SadScroll(this);
	private SadCursor cursor = new SadCursor();
	
	private float interval;
	private long now;
	
	private long[] keyTimes = new long[ARRAY_SIZE];
	private boolean[] keyStates = new boolean[ARRAY_SIZE];
	private boolean[] changedKeys = new boolean[ARRAY_SIZE];
	private boolean[] keyStatesBuffer = new boolean[ARRAY_SIZE];
	
	private long[] buttonTimes = new long[ARRAY_SIZE];
	private boolean[] buttonStates = new boolean[ARRAY_SIZE];
	private boolean[] changedButtons = new boolean[ARRAY_SIZE];
	private boolean[] buttonStatesBuffer = new boolean[ARRAY_SIZE];
	
	private SadVector scrollState = new SadVector(2);
	private SadVector scrolled = new SadVector(2);
	
	public void setup(SadGlWindow window) {
		cursor.setup(window);
	}
	
	public void setup2(float interval) {
		this.interval = interval;
		now();
		for (int i = 0; i < 512; i++) {
			keyTimes[i] = now;
			buttonTimes[i] = now;
		}
	}
	
	private void now() {
		now = System.nanoTime();
	}
	
	public void update(float interval) {
		this.interval = interval;
		cursor.update();
		scrolled.identify();
		now();
		for (int i = 0; i < 512; i++) {
			
			keyTimes[i] += (interval * (keyStates[i] ? 1 : -1));
			if (keyStates[i] == keyStatesBuffer[i]) {
				changedKeys[i] = false;
			} else {
				changedKeys[i] = true;
				keyStates[i] = keyStatesBuffer[i];
				keyTimes[i] = now;
			}
			
			buttonTimes[i] += (interval * (buttonStates[i] ? 1 : -1));
			if (buttonStates[i] == buttonStatesBuffer[i]) {
				changedButtons[i] = false;
			} else {
				changedButtons[i] = true;
				buttonStates[i] = buttonStatesBuffer[i];
				buttonTimes[i] = now;
			}
			
		}
	}
	
	void setKeyPressed(int key, boolean pressed) {
		keyStatesBuffer[key] = pressed;
	}
	
	void setButtonPressed(int button, boolean pressed) {
		buttonStatesBuffer[button] = pressed;
	}
	
	void setScrolled(float x, float y) {
		scrolled.set(x, y);
		scrollState.add(scrolled);
	}
	
	
	//API
	
	public boolean isPressed(int key) {
		return isKey(key) ? keyStates[key] : buttonStates[-key];
	}
	
	public float getPressTime(int key) {
		return (now - (isKey(key) ? keyTimes[key] : buttonTimes[-key])) / 1000000000f;
		
	}
	
	public boolean isJustPressed(int key) {
		return isKey(key) ? keyStates[key] && changedKeys[key] : buttonStates[-key] && changedButtons[-key];
	}
	
	public boolean isJustReleased(int key) {
		return isKey(key) ? !keyStates[key] && changedKeys[key] : !buttonStates[-key] && changedButtons[-key];
	}
	
	private boolean isKey(int key) {
		return key > 0;
	}
	
	public SadVector getScrollState() {
		return scrollState;
	}
	
	public SadVector getScrolled() {
		return scrolled;
	}
	
	public SadCursor getCursor() {
		return cursor;
	}
	
	
	//Callbacks
	
	public SadMouse getMouseCallback() {
		return mouseCallback;
	}
	
	public SadKeyboard getKeyboardCallback() {
		return keyboardCallback;
	}
	
	public SadScroll getScrollCallback() {
		return scrollCallback;
	}
	
	public GLFWCursorPosCallback getCursorPosCallback() {
		return cursor.getPosCallback();
	}
	
	public GLFWCursorEnterCallback getCursorEnterCallback() {
		return cursor.getEnterCallback();
	}
}
