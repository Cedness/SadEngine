package de.ced.sadengine.graphics.control;

import static org.lwjgl.glfw.GLFW.*;

public enum FPCAction {
	
	MOVE_FORWARD(GLFW_KEY_W),
	MOVE_BACK(GLFW_KEY_S),
	MOVE_LEFT(GLFW_KEY_A),
	MOVE_RIGHT(GLFW_KEY_D),
	MOVE_UP(GLFW_KEY_SPACE),
	MOVE_DOWN(GLFW_KEY_LEFT_SHIFT),
	LOOK_LEFT(GLFW_KEY_LEFT),
	LOOK_RIGHT(GLFW_KEY_RIGHT),
	LOOK_UP(GLFW_KEY_UP),
	LOOK_DOWN(GLFW_KEY_DOWN);
	
	private final int key;
	
	FPCAction(int key) {
		this.key = key;
	}
	
	public int getDefaultKey() {
		return key;
	}
}
