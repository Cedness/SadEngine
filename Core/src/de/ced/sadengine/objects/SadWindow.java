package de.ced.sadengine.objects;

import de.ced.sadengine.objects.input.SadInput;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class SadWindow {
	
	private SadEngine engine;
	private long windowID;
	
	public long setup(SadEngine engine, SadInput input) {
		this.engine = engine;
		GLFWErrorCallback.createPrint(System.err).set();
		if (!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");
		
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
		if (engine.isAntialiasing())
			glfwWindowHint(GLFW_SAMPLES, 4);
		windowID = glfwCreateWindow(engine.getWidth(), engine.getHeight(), engine.getName(), engine.isFullscreen() ? glfwGetPrimaryMonitor() : 0, NULL);
		if (windowID == NULL)
			throw new RuntimeException("Failed to create window");
		
		input.setup(this);
		glfwSetKeyCallback(windowID, input.getKeyboardCallback());
		glfwSetMouseButtonCallback(windowID, input.getMouseCallback());
		glfwSetScrollCallback(windowID, input.getScrollCallback());
		glfwSetCursorPosCallback(windowID, input.getCursorPosCallback());
		glfwSetCursorEnterCallback(windowID, input.getCursorEnterCallback());
		glfwSetFramebufferSizeCallback(windowID, (window, width, height) -> {
			engine.setWidth(width);
			engine.setHeight(height);
		});
		
		try (MemoryStack stack = stackPush()) {
			IntBuffer pWidth = stack.mallocInt(1);
			IntBuffer pHeight = stack.mallocInt(1);
			
			glfwGetWindowSize(windowID, pWidth, pHeight);
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
			glfwSetWindowPos(windowID,
					(vidmode.width() - pWidth.get(0)) / 2,
					(vidmode.height() - pHeight.get(0)) / 2
			);
		}
		
		glfwMakeContextCurrent(windowID);
		glfwSwapInterval(1);
		glfwShowWindow(windowID);
		
		GL.createCapabilities();
		glClearColor(0.1f, 0.1f, 0.1f, 1f);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
		
		return windowID;
	}
	
	public void update() {
		glfwSwapBuffers(windowID);
		glfwPollEvents();
	}
	
	public long getWindowID() {
		return windowID;
	}
	
	@Deprecated
	public int getWidth() {
		return engine.getWidth();
	}
	
	@Deprecated
	public int getHeight() {
		return engine.getHeight();
	}
	
	boolean shouldClose() {
		return glfwWindowShouldClose(windowID);
	}
	
	void close() {
		glfwSetWindowShouldClose(windowID, true);
	}
}
