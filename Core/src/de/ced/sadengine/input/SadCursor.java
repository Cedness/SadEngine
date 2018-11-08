package de.ced.sadengine.input;

import de.ced.sadengine.main.SadGlWindow;
import de.ced.sadengine.utils.SadVector;
import org.lwjgl.glfw.GLFWCursorPosCallback;

import static org.lwjgl.glfw.GLFW.*;

public class SadCursor {
	
	private SadGlWindow window;
	private GLFWCursorPosCallback callback;
	private SadVector position = new SadVector(), positionLast = new SadVector();
	private boolean hidden, hiddenLast;
	private boolean locked, lockedLast;
	
	public void setup(SadGlWindow window) {
		this.window = window;
		callback = new CursorCallback();
		//callback = new SadCursorCallback(window, position, positionLast);
	}
	
	void update() {
		if (!position.contentEquals(positionLast)) {
			positionLast.set(position);
			glfwSetCursorPos(window.getGlWindow(), position.x() / 2f + window.getWidth() / 2f, position.y() / 2f + window.getHeight() / 2f);
		}
		
		if (hidden ^ hiddenLast || locked ^ lockedLast) {
			hiddenLast = hidden;
			lockedLast = locked;
			glfwSetInputMode(window.getGlWindow(), GLFW_CURSOR, locked ? GLFW_CURSOR_DISABLED : (hidden ? GLFW_CURSOR_HIDDEN : GLFW_CURSOR_NORMAL));
		}
		
		//System.out.println(position.x() + " " + position.y());
	}
	
	public boolean isHidden() {
		return hidden;
	}
	
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	
	public boolean isLocked() {
		return locked;
	}
	
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	
	public SadVector getPosition() {
		return position;
	}
	
	GLFWCursorPosCallback getCallback() {
		return callback;
	}
	
	class CursorCallback extends GLFWCursorPosCallback {
		@Override
		public void invoke(long glWindow, double xPos, double yPos) {
			position.x((float) (2 * (xPos - window.getWidth() / 2f)));
			position.y((float) (2 * -(yPos - window.getHeight() / 2f)));
			position.z(0);
			positionLast.set(position);
			//System.out.println(xPos + "" + yPos);
		}
	}
}
