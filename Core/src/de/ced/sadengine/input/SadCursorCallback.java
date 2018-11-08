package de.ced.sadengine.input;

import de.ced.sadengine.main.SadGlWindow;
import de.ced.sadengine.utils.SadVector;
import org.lwjgl.glfw.GLFWCursorPosCallback;

public class SadCursorCallback extends GLFWCursorPosCallback {
	
	private final SadGlWindow window;
	private final SadVector position;
	private final SadVector positionLast;
	
	public SadCursorCallback(SadGlWindow window, SadVector position, SadVector positionLast) {
		this.window = window;
		this.position = position;
		this.positionLast = positionLast;
	}
	
	@Override
	public void invoke(long glWindow, double xPos, double yPos) {
		position.x((float) (2 * (xPos - window.getWidth() / 2f)));
		position.y((float) (2 * -(yPos - window.getHeight() / 2f)));
		position.z(0);
		positionLast.set(position);
		System.out.println(xPos + "" + yPos);
	}
}
