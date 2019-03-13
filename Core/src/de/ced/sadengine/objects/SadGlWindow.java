package de.ced.sadengine.objects;

import de.ced.sadengine.objects.input.SadInput;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class SadGlWindow {
	
	private SadEngine engine;
	private long glWindow;
	
	private GLFWKeyCallback keyCallback;
	private GLFWMouseButtonCallback mouseCallback;
	private GLFWCursorPosCallback cursorPosCallback;
	private GLFWCursorEnterCallback cursorEnterCallback;
	
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
		
		glWindow = glfwCreateWindow(engine.width(), engine.height(), engine.getName(), NULL, NULL);
		if (glWindow == NULL)
			throw new RuntimeException("Failed to create window");
		
		input.setup(this);
		
		glfwSetKeyCallback(glWindow, keyCallback = input.getKeyboardCallback());
		glfwSetMouseButtonCallback(glWindow, mouseCallback = input.getMouseCallback());
		glfwSetCursorPosCallback(glWindow, cursorPosCallback = input.getCursorPosCallback());
		glfwSetCursorEnterCallback(glWindow, cursorEnterCallback = input.getCursorEnterCallback());
		
		glfwSetFramebufferSizeCallback(glWindow, (window, width, height) -> {
			engine.setWidth(width);
			engine.setHeight(height);
		});
		
		try (MemoryStack stack = stackPush()) {
			IntBuffer pWidth = stack.mallocInt(1);
			IntBuffer pHeight = stack.mallocInt(1);
			
			glfwGetWindowSize(glWindow, pWidth, pHeight);
			
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
			
			//noinspection ConstantConditions
			glfwSetWindowPos(glWindow,
					(vidmode.width() - pWidth.get(0)) / 2,
					(vidmode.height() - pHeight.get(0)) / 2
			);
			
		}
		
		glfwMakeContextCurrent(glWindow);
		
		
		glfwSwapInterval(1);
		
		glfwShowWindow(glWindow);
		
		
		GL.createCapabilities();
		
		glClearColor(0.1f, 0.1f, 0.1f, 1f);
		
		glEnable(GL_DEPTH_TEST);
		
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
		
		return glWindow;
	}
	
	public void update() {
		glfwSwapBuffers(glWindow);
		glfwPollEvents();
	}
	
	public long getGlWindow() {
		return glWindow;
	}
	
	@Deprecated
	public int getWidth() {
		return engine.width();
	}
	
	@Deprecated
	public int getHeight() {
		return engine.height();
	}
	
	boolean shouldClose() {
		return glfwWindowShouldClose(glWindow);
	}
	
	void close() {
		glfwSetWindowShouldClose(glWindow, true);
	}
}
