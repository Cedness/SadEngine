package de.ced.sadengine.io;

import de.ced.sadengine.objects.SadMesh;
import de.ced.sadengine.objects.SadTexture;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL20.*;

public class SadResourceLoader {
	
	//Shader
	
	public static int loadShader(String path, int shaderType) {
		StringBuilder builder = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(SadResourceLoader.class.getResourceAsStream("/shader/" + path)));
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
	
	public static SadMesh loadMesh(String name, File file) throws FileNotFoundException, NumberFormatException {
		InputStream stream = new FileInputStream(file);
		if (stream == null)
			System.out.println("lol");
		InputStreamReader streamReader = new InputStreamReader(stream);
		BufferedReader reader = new BufferedReader(streamReader);
		
		List<Integer> indices = new ArrayList<>();
		List<Vector3f> positions = new ArrayList<>();
		List<Vector2f> textureCoordinates = new ArrayList<>();
		List<Vector3f> normals = new ArrayList<>();
		
		String line = null;
		String[] parts;
		try {
			while (reader.ready()) {
				line = reader.readLine();
				parts = line.split(" ");
				
				if (line.startsWith("v ")) {
					positions.add(new Vector3f(
							Float.parseFloat(parts[1]),
							Float.parseFloat(parts[2]),
							Float.parseFloat(parts[3])
					));
				} else if (line.startsWith("vt ")) {
					textureCoordinates.add(new Vector2f(
							Float.parseFloat(parts[1]),
							Float.parseFloat(parts[2])
					));
				} else if (line.startsWith("vn ")) {
					normals.add(new Vector3f(
							Float.parseFloat(parts[1]),
							Float.parseFloat(parts[2]),
							Float.parseFloat(parts[3])
					));
				}
			}
			
			
			boolean isTextured = textureCoordinates.size() > 0;
			boolean hasNormals = normals.size() > 0;
			
			
			float[] positionsArray = new float[positions.size() * 3];
			float[] textureCoordinatesArray = isTextured ? new float[positions.size() * 2] : null;
			float[] normalsArray = new float[positions.size() * 3];
			
			streamReader = new InputStreamReader(new FileInputStream(file));
			reader = new BufferedReader(streamReader);
			
			while (reader.ready()) {
				line = reader.readLine();
				if (!line.startsWith("f"))
					continue;
				
				parts = line.split(" ");
				
				for (int i = 1; i < parts.length; i++) {
					String[] part = parts[i].split("/");
					
					int index = Integer.parseInt(part[0]) - 1;
					indices.add(index);
					
					if (isTextured) {
						Vector2f textureCoordinate = textureCoordinates.get(Integer.parseInt(part[1]) - 1);
						textureCoordinatesArray[index * 2] = textureCoordinate.x;
						textureCoordinatesArray[index * 2 + 1] = textureCoordinate.y;
					}
					
					if (hasNormals) {
						Vector3f normal = normals.get(Integer.parseInt(part[2]) - 1);
						normalsArray[index * 3] = normal.x;
						normalsArray[index * 3 + 1] = normal.y;
						normalsArray[index * 3 + 2] = normal.z;
					}
				}
			}
			
			reader.close();
			
			int[] indicesArray = new int[indices.size()];
			
			for (int i = 0; i < positions.size(); i++) {
				positionsArray[i * 3] = positions.get(i).x;
				positionsArray[i * 3 + 1] = positions.get(i).y;
				positionsArray[i * 3 + 2] = positions.get(i).z;
			}
			
			for (int i = 0; i < indices.size(); i++) {
				indicesArray[i] = indices.get(i);
			}
			
			return isTextured ? hasNormals ?
					new SadMesh(name, indicesArray, positionsArray, textureCoordinatesArray, normalsArray) :
					new SadMesh(name, indicesArray, positionsArray, textureCoordinatesArray) :
					new SadMesh(name, indicesArray, positionsArray);
			
		} catch (IOException | NumberFormatException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
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
