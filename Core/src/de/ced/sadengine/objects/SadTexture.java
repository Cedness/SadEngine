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
	
	private int textureID;
	int width;
	int height;
	private boolean transparent;
	
	private boolean renderBack = false;
	
	private float lightDamper;
	private float reflectivity;
	
	private String info = "";
	
	public SadTexture(URL url) {
		this(url.getFile());
	}
	
	public SadTexture(URI uri) {
		this(uri.getPath());
	}
	
	public SadTexture(String path) {
		this(new File(path), path);
	}
	
	public SadTexture(String path, String info) {
		this(new File(path), info);
	}
	
	public SadTexture(File file) {
		this(file, file.getAbsolutePath());
	}
	
	public SadTexture(File file, String info) {
		this.info = info;
		load(file);
	}
	
	private void load(File file) {
		InputStream stream = null;
		try {
			stream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		if (stream == null)
			throw new RuntimeException("Resource not found: " + info);
		
		load(stream);
	}
	
	private void load(InputStream stream) {
		//Resource to ByteBuffer
		ByteBuffer rawBytes;
		
		try (ReadableByteChannel rbc = Channels.newChannel(stream)) {
			rawBytes = BufferUtils.createByteBuffer(16384);
			
			while (true) {
				int bytes = rbc.read(rawBytes);
				if (bytes == -1)
					break;
				if (rawBytes.remaining() == 0) {
					//Resize ByteBuffer
					ByteBuffer old = rawBytes;
					rawBytes = BufferUtils.createByteBuffer(old.capacity() * 2);
					old.flip();
					rawBytes.put(old);
				}
			}
		} catch (Throwable t) {
			throw new NullPointerException(info + ": Input stream not valid!");
		}
		
		rawBytes.flip();
		
		IntBuffer w = BufferUtils.createIntBuffer(1);
		IntBuffer h = BufferUtils.createIntBuffer(1);
		IntBuffer c = BufferUtils.createIntBuffer(1);
		
		ByteBuffer image = STBImage.stbi_load_from_memory(rawBytes, w, h, c, 0);
		if (image == null)
			throw new RuntimeException("Image file '" + info + "' could not be decoded: " + STBImage.stbi_failure_reason());
		
		width = w.get();
		height = h.get();
		transparent = c.get() > 3;
		
		textureID = glGenTextures();
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, textureID);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, transparent ? GL_RGBA : GL_RGB, GL_UNSIGNED_BYTE, image);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		
		STBImage.stbi_image_free(image);
		
		glBindTexture(GL_TEXTURE_2D, 0);
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
}
