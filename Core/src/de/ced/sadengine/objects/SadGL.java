package de.ced.sadengine.objects;

import de.ced.sadengine.utils.SadVector;
import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL30.GL_CLAMP_TO_BORDER;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL30.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL30.GL_CULL_FACE;
import static org.lwjgl.opengl.GL30.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL30.GL_DEPTH_COMPONENT;
import static org.lwjgl.opengl.GL30.GL_DEPTH_COMPONENT32;
import static org.lwjgl.opengl.GL30.GL_FLOAT;
import static org.lwjgl.opengl.GL30.GL_TEXTURE0;
import static org.lwjgl.opengl.GL30.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL30.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL30.glActiveTexture;
import static org.lwjgl.opengl.GL30.glClear;
import static org.lwjgl.opengl.GL30.glClearColor;
import static org.lwjgl.opengl.GL30.glDisable;
import static org.lwjgl.opengl.GL30.glDrawBuffer;
import static org.lwjgl.opengl.GL30.glEnable;
import static org.lwjgl.opengl.GL30.glViewport;
import static org.lwjgl.opengl.GL32.glFramebufferTexture;

public class SadGL {
	
	//Shader
	
	public static int loadShader(String path, int shaderType) {
		StringBuilder builder = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(SadGL.class.getResourceAsStream("/shader/" + path)));
			while (reader.ready()) {
				builder.append(reader.readLine()).append(System.lineSeparator());
			}
			reader.close();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		
		int id = glCreateShader(shaderType);
		glShaderSource(id, builder);
		glCompileShader(id);
		if (glGetShaderi(id, GL_COMPILE_STATUS) == GL_FALSE)
			throw new RuntimeException("Failed to compile SadShader" + System.lineSeparator() + glGetShaderInfoLog(id));
		
		return id;
	}
	
	//Mesh
	
	@SuppressWarnings("ConstantConditions")
	
	
	//Texture
	
	static int[] loadTexture(File file) {
		InputStream is = null;
		try {
			is = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		if (is == null)
			throw new RuntimeException("Resource not found: " + file.getAbsolutePath());
		
		ByteBuffer rawBytes = ioResourceToByteBuffer(is, 16384);
		
		IntBuffer w = BufferUtils.createIntBuffer(1);
		IntBuffer h = BufferUtils.createIntBuffer(1);
		IntBuffer c = BufferUtils.createIntBuffer(1);
		
		ByteBuffer image = STBImage.stbi_load_from_memory(rawBytes, w, h, c, 0);
		if (image == null)
			throw new RuntimeException("Image file '" + file + "' could not be decoded: " + STBImage.stbi_failure_reason());
		
		int[] result = new int[4];
		result[1] = w.get();
		result[2] = h.get();
		result[3] = c.get();
		
		result[0] = glGenTextures();
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, result[0]);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, result[1], result[2], 0, result[3] > 3 ? GL_RGBA : GL_RGB, GL_UNSIGNED_BYTE, image);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		
		STBImage.stbi_image_free(image);
		
		glBindTexture(GL_TEXTURE_2D, 0);
		
		return result;
	}
	
	private static ByteBuffer ioResourceToByteBuffer(InputStream source, @SuppressWarnings("SameParameterValue") int bufferSize) {
		ByteBuffer buffer;
		
		try (ReadableByteChannel rbc = Channels.newChannel(source)) {
			buffer = BufferUtils.createByteBuffer(bufferSize);
			
			while (true) {
				int bytes = rbc.read(buffer);
				if (bytes == -1)
					break;
				if (buffer.remaining() == 0)
					buffer = resizeBuffer(buffer, buffer.capacity() * 2);
			}
		} catch (Throwable t) {
			throw new NullPointerException("Input stream not valid!");
		}
		
		buffer.flip();
		return buffer;
	}
	
	private static ByteBuffer resizeBuffer(ByteBuffer buffer, int newCapacity) {
		ByteBuffer newBuffer = BufferUtils.createByteBuffer(newCapacity);
		buffer.flip();
		newBuffer.put(buffer);
		return newBuffer;
	}
	
	//FrameBuffer
	
	static int createFrameBuffer() {
		int frameBuffer = glGenFramebuffers();
		glBindFramebuffer(GL_FRAMEBUFFER, frameBuffer);
		glDrawBuffer(GL_COLOR_ATTACHMENT0);
		return frameBuffer;
	}
	
	static int createTextureAttachment(int width, int height) {
		int texture = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, texture);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, (ByteBuffer) null);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glFramebufferTexture(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, texture, 0);
		return texture;
	}
	
	@SuppressWarnings("unused")
	static int createDepthTextureAttachment(int width, int height) {
		int depthTexture = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, depthTexture);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT32, width, height, 0, GL_DEPTH_COMPONENT, GL_FLOAT, (ByteBuffer) null);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glFramebufferTexture(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, depthTexture, 0);
		return depthTexture;
	}
	
	static int createDepthBufferAttachment(int width, int height) {
		int depthBuffer = glGenRenderbuffers();
		glBindRenderbuffer(GL_RENDERBUFFER, depthBuffer);
		glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT, width, height);
		glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, depthBuffer);
		return depthBuffer;
	}
	
	static void bindFrameBuffer(SadFrame frame) {
		glBindTexture(GL_TEXTURE_2D, 0);
		glBindFramebuffer(GL_FRAMEBUFFER, frame.getFboID());
		glBindRenderbuffer(GL_RENDERBUFFER, frame.getDepthID());
		glViewport(0, 0, frame.getWidth(), frame.getHeight());
	}
	
	@SuppressWarnings("unused")
	public static void unbindFrameBuffer(SadGlWindow glWindow) {
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
		glBindRenderbuffer(GL_RENDERBUFFER, 0);
		glViewport(0, 0, glWindow.getWidth(), glWindow.getHeight());
	}
	
	static void enableBackRendering() {
		glDisable(GL_CULL_FACE);
	}
	
	static void disableBackRendering() {
		glEnable(GL_CULL_FACE);
	}
	
	static void clear(SadFrame frame) {
		bindFrameBuffer(frame);
		SadVector c = frame.getColor();
		glClearColor(c.get(0), c.get(1), c.get(2), c.get(3));
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
}
