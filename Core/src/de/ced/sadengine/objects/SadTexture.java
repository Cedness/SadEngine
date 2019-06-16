package de.ced.sadengine.objects;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

public class SadTexture extends SadObject {
	
	private final int textureID;
	final int width;
	final int height;
	private final boolean transparent;
	
	private boolean renderBack = false;
	
	private float lightDamper;
	private float reflectivity;
	
	public SadTexture(URL url) {
		this(url.getFile());
	}
	
	public SadTexture(URI uri) {
		this(uri.getPath());
	}
	
	public SadTexture(String path) {
		this(new File(path));
	}
	
	public SadTexture(File file) {
		this(loadTexture(file));
		setName(file.getAbsolutePath());
	}
	
	SadTexture(int[] data) {
		textureID = data[0];
		width = data[1];
		height = data[2];
		transparent = data[3] > 3;
	}
	
	public int getTextureID() {
		return textureID;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public boolean isTransparent() {
		return transparent;
	}
	
	public boolean isRenderBack() {
		return renderBack;
	}
	
	public void setRenderBack(boolean renderBack) {
		this.renderBack = renderBack;
	}
	
	public float getLightDamper() {
		return lightDamper;
	}
	
	public SadTexture setLightDamper(float lightDamper) {
		this.lightDamper = lightDamper;
		return this;
	}
	
	public float getReflectivity() {
		return reflectivity;
	}
	
	public SadTexture setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
		return this;
	}
	
	@Override
	void release() {
		glDeleteTextures(textureID);
	}
	
	@SuppressWarnings("ConstantConditions")
	
	//Texture GL
	
	private static int[] loadTexture(File file) {
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
}
