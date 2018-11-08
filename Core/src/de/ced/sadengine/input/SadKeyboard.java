package de.ced.sadengine.input;

import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class SadKeyboard extends GLFWKeyCallback {
	
	private final SadInput input;
	
	public SadKeyboard(SadInput input) {
		this.input = input;
	}
	
	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		input.setKeyPressed(key, action != GLFW_RELEASE);
	}
}
