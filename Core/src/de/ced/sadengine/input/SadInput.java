package de.ced.sadengine.input;

import de.ced.sadengine.main.SadGlWindow;
import org.lwjgl.glfw.GLFWCursorPosCallback;

public class SadInput {
	
	private SadKeyboard keyboardCallback = new SadKeyboard(this);
	private SadMouse mouseCallback = new SadMouse(this);
	private SadCursor cursor = new SadCursor();
	
	private boolean[] keyStates = new boolean[512];
	private boolean[] buttonStates = new boolean[512];
	private boolean[] changedKeys = new boolean[512];
	private boolean[] changedButtons = new boolean[512];
	private boolean[] keyStatesBuffer = new boolean[512];
	private boolean[] buttonStatesBuffer = new boolean[512];
	
	public void setup(SadGlWindow window) {
		cursor.setup(window);
	}
	
	public void update() {
		cursor.update();
		for (int i = 0; i < 512; i++) {
			if (keyStates[i] == keyStatesBuffer[i]) {
				changedKeys[i] = false;
			} else {
				changedKeys[i] = true;
				keyStates[i] = keyStatesBuffer[i];
			}
			if (buttonStates[i] == buttonStatesBuffer[i]) {
				changedButtons[i] = false;
			} else {
				changedButtons[i] = true;
				buttonStates[i] = buttonStatesBuffer[i];
			}
		}
	}
	
	void setKeyPressed(int key, boolean pressed) {
		keyStatesBuffer[key] = pressed;
	}
	
	void setButtonPressed(int button, boolean pressed) {
		buttonStatesBuffer[button] = pressed;
	}
	
	
	//API
	
	public boolean isPressed(int key) {
		return isKey(key) ? keyStates[key] : buttonStates[-key];
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
	
	public GLFWCursorPosCallback getCursorCallback() {
		return cursor.getCallback();
	}
}
