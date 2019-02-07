package de.ced.sadengine.objects.input;

import org.lwjgl.glfw.GLFWMouseButtonCallback;

import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class SadMouse extends GLFWMouseButtonCallback {
	
	private SadInput input;
	
	SadMouse(SadInput input) {
		this.input = input;
	}
	
	@Override
	public void invoke(long window, int button, int action, int mods) {
		input.setButtonPressed(button, action != GLFW_RELEASE);
	}
}
