package de.ced.sadengine.objects.input;

import de.ced.sadengine.objects.SadWindow;
import de.ced.sadengine.utils.SadVector;
import org.lwjgl.glfw.GLFWCursorEnterCallback;
import org.lwjgl.glfw.GLFWCursorPosCallback;

import static org.lwjgl.glfw.GLFW.*;

public class SadCursor {
	
	private SadWindow window;
	private GLFWCursorPosCallback posCallback = new CursorPosCallback();
	private GLFWCursorEnterCallback enterCallback = new CursorEnterCallback();
	private SadVector position = new SadVector(3), positionLast = new SadVector(3);
	private boolean hidden, hiddenLast;
	private boolean locked, lockedLast;
	private boolean inside, insideChanged;
	
	public void setup(SadWindow window) {
		this.window = window;
		//posCallback = new SadCursorCallback(window, position, positionLast);
	}
	
	void update() {
		if (!position.contentEquals(positionLast)) {
			positionLast.set(position);
			glfwSetCursorPos(window.getWindowID(), position.x() / 2f + window.getWidth() / 2f, position.y() / 2f + window.getHeight() / 2f);
		}
		
		if (hidden ^ hiddenLast || locked ^ lockedLast) {
			hiddenLast = hidden;
			lockedLast = locked;
			glfwSetInputMode(window.getWindowID(), GLFW_CURSOR, locked ? GLFW_CURSOR_DISABLED : (hidden ? GLFW_CURSOR_HIDDEN : GLFW_CURSOR_NORMAL));
		}
		
		if (insideChanged)
			insideChanged = false;
		
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
	
	public SadVector getNormalizedPosition() {
		return new SadVector(position).mul(
				1f / (window.getWidth() / 2f),
				1f / (window.getHeight() / 2f),
				0);
	}
	
	public boolean isInWindow() {
		return inside;
	}
	
	public boolean hasJustEntered() {
		return inside && insideChanged;
	}
	
	public boolean hasJustLeft() {
		return !inside && insideChanged;
	}
	
	GLFWCursorPosCallback getPosCallback() {
		return posCallback;
	}
	
	GLFWCursorEnterCallback getEnterCallback() {
		return enterCallback;
	}
	
	class CursorPosCallback extends GLFWCursorPosCallback {
		@Override
		public void invoke(long glWindow, double xPos, double yPos) {
			position.set(
					(float) (xPos - window.getWidth() / 2f),
					(float) -(yPos - window.getHeight() / 2f),
					0);
			positionLast.set(position);
			//System.out.println(xPos + "" + yPos);
		}
	}
	
	class CursorEnterCallback extends GLFWCursorEnterCallback {
		@Override
		public void invoke(long glWindow, boolean entered) {
			inside = entered;
			insideChanged = true;
		}
	}
}
