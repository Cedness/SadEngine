package de.ced.sadengine.objects.input;

import org.lwjgl.glfw.GLFWScrollCallback;

public class SadScroll extends GLFWScrollCallback {
	
	private final SadInput input;
	
	SadScroll(SadInput input) {
		this.input = input;
	}
	
	@Override
	public void invoke(long window, double x, double y) {
		input.setScrolled((float) x, (float) y);
	}
}
