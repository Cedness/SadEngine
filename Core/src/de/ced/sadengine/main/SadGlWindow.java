package de.ced.sadengine.main;

import de.ced.sadengine.api.Saddings;
import de.ced.sadengine.input.SadInput;
import de.ced.sadengine.utils.SadVector3;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class SadGlWindow {
	
	private long glWindow;
	
	private GLFWKeyCallback keyCallback;
	private GLFWMouseButtonCallback mouseCallback;
	private GLFWCursorPosCallback cursorPosCallback;
	private GLFWCursorEnterCallback cursorEnterCallback;
	
	private int width;
	private int height;
	
	public long setup(Saddings settings, SadInput input) {
		GLFWErrorCallback.createPrint(System.err).set();
		
		if (!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");
		
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
		
		if (settings.isAntialiasing())
			glfwWindowHint(GLFW_SAMPLES, 4);
		
		glWindow = glfwCreateWindow(settings.getWidth(), settings.getHeight(), settings.getName(), NULL, NULL);
		if (glWindow == NULL)
			throw new RuntimeException("Failed to create window");
		
		input.setup(this);
		
		glfwSetKeyCallback(glWindow, keyCallback = input.getKeyboardCallback());
		glfwSetMouseButtonCallback(glWindow, mouseCallback = input.getMouseCallback());
		glfwSetCursorPosCallback(glWindow, cursorPosCallback = input.getCursorPosCallback());
		glfwSetCursorEnterCallback(glWindow, cursorEnterCallback = input.getCursorEnterCallback());
		
		glfwSetFramebufferSizeCallback(glWindow, (window, width, height) -> {
			settings.setWidth(width);
			settings.setHeight(height);
			this.width = width;
			this.height = height;
		});
		
		try (MemoryStack stack = stackPush()) {
			IntBuffer pWidth = stack.mallocInt(1);
			IntBuffer pHeight = stack.mallocInt(1);
			
			glfwGetWindowSize(glWindow, pWidth, pHeight);
			
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
			
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
	
	public void update(SadContent content) {
		SadVector3 cC = content.getClearColor();
		glClearColor(cC.x(), cC.y(), cC.z(), 1f);
		
		glfwSwapBuffers(glWindow);
		glfwPollEvents();
	}
	
	public long getGlWindow() {
		return glWindow;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public boolean shouldClose() {
		return glfwWindowShouldClose(glWindow);
	}
}
