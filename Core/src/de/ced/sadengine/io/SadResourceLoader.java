package de.ced.sadengine.io;

import de.ced.sadengine.objects.SadTexture;
import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import static org.lwjgl.opengl.GL11.*;

public class SadResourceLoader {
	
	//Texture
	
	public static SadTexture loadTexture(String name, File file) {
		return loadTexture(name, file, false);
	}
	
	public static SadTexture loadTexture(String name, File file, boolean alpha) {
		InputStream is = null;
		try {
			is = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		if (is == null)
			throw new RuntimeException("Resource not found: " + file.getAbsolutePath());
		
		ByteBuffer rawBytes;
		rawBytes = ioResourceToByteBuffer(is, 16384);
		
		IntBuffer w = BufferUtils.createIntBuffer(1);
		IntBuffer h = BufferUtils.createIntBuffer(1);
		IntBuffer c = BufferUtils.createIntBuffer(1);
		
		ByteBuffer decodedImage = STBImage.stbi_load_from_memory(rawBytes, w, h, c, 0);
		if (decodedImage == null)
			throw new RuntimeException("Image file '" + file + "' could not be decoded: " + STBImage.stbi_failure_reason());
		
		int width = w.get();
		int height = h.get();
		
		int id = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, id);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, alpha ? GL_RGBA : GL_RGB, GL_UNSIGNED_BYTE, decodedImage);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		
		STBImage.stbi_image_free(decodedImage);
		
		glBindTexture(GL_TEXTURE_2D, 0);
		
		return new SadTexture(name, id);
	}
	
	private static ByteBuffer ioResourceToByteBuffer(InputStream source, int bufferSize) {
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
