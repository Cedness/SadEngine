package de.ced.sadengine.objects;

import org.joml.Vector2f;
import org.joml.Vector3f;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;

/**
 * The mesh of a Model
 */
public class SadMesh extends SadObject implements SadMeshI {
	
	private int[] indices = new int[0];
	private float[] positions = new float[0];
	private float[] textureCoordinates = null;
	private float[] normals = null;
	
	private int vaoId;
	private int[] vboIds;
	private int vertexCount;
	private boolean useIndexBuffer;
	private int indexVboId;
	
	private float radius = 0f;
	
	public SadMesh(File file) {
		InputStream stream;
		try {
			stream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			meshException("OBJFile " + file.getAbsolutePath() + " not found.", e);
			return;
		}
		InputStreamReader streamReader = new InputStreamReader(stream);
		BufferedReader reader = new BufferedReader(streamReader);
		
		List<Integer> indices = new ArrayList<>();
		List<Vector3f> positions = new ArrayList<>();
		List<Vector2f> textureCoordinates = new ArrayList<>();
		List<Vector3f> normals = new ArrayList<>();
		
		String line;
		String[] parts;
		while (true) {
			try {
				if (!reader.ready())
					break;
				line = reader.readLine();
			} catch (IOException e) {
				meshException("Reading Error in OBJFile " + file.getAbsolutePath() + ".", e);
				return;
			}
			try {
				parts = line.split(" ");
			} catch (PatternSyntaxException e) {
				meshDataException("Positions, texture coordinates or normals", file, e);
				return;
			}
			
			if (line.startsWith("v ")) {
				try {
					positions.add(new Vector3f(
							Float.parseFloat(parts[1]),
							Float.parseFloat(parts[2]),
							Float.parseFloat(parts[3])
					));
				} catch (NumberFormatException e) {
					meshDataException("Positions", file, e);
					return;
				}
			} else if (line.startsWith("vt ")) {
				try {
					textureCoordinates.add(new Vector2f(
							Float.parseFloat(parts[1]),
							Float.parseFloat(parts[2])
					));
				} catch (NumberFormatException e) {
					meshDataException("Texture coordinates", file, e);
					return;
				}
			} else if (line.startsWith("vn ")) {
				try {
					normals.add(new Vector3f(
							Float.parseFloat(parts[1]),
							Float.parseFloat(parts[2]),
							Float.parseFloat(parts[3])
					));
				} catch (NumberFormatException e) {
					meshDataException("Normals", file, e);
					return;
				}
			}
		}
		
		
		boolean isTextured = textureCoordinates.size() > 0;
		boolean hasNormals = normals.size() > 0;
		
		
		float[] positionsArray = new float[positions.size() * 3];
		float[] textureCoordinatesArray = isTextured ? new float[positions.size() * 2] : null;
		float[] normalsArray = new float[positions.size() * 3];
		try {
			stream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			meshException("OBJFile " + file.getAbsolutePath() + " not found.", e);
			return;
		}
		streamReader = new InputStreamReader(stream);
		reader = new BufferedReader(streamReader);
		/*
		try {
			reader.reset();
		} catch (IOException e) {
			System.out.println("reeeeee");
		}
		*/
		
		while (true) {
			try {
				if (!reader.ready())
					break;
				line = reader.readLine();
			} catch (IOException e) {
				meshException("Reading Error in OBJFile " + file.getAbsolutePath() + ".", e);
				return;
			}
			if (!line.startsWith("f"))
				continue;
			
			try {
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
			} catch (PatternSyntaxException | NumberFormatException e) {
				meshDataException("Indices", file, e);
				return;
			}
		}
		
		try {
			reader.close();
		} catch (IOException e) {
			meshException("Reading Error in OBJFile " + file.getAbsolutePath() + ".", e);
			return;
		}
		
		int[] indicesArray = new int[indices.size()];
		
		for (int i = 0; i < positions.size(); i++) {
			positionsArray[i * 3] = positions.get(i).x;
			positionsArray[i * 3 + 1] = positions.get(i).y;
			positionsArray[i * 3 + 2] = positions.get(i).z;
		}
		
		for (int i = 0; i < indices.size(); i++) {
			indicesArray[i] = indices.get(i);
		}
		
		if (hasNormals)
			this.normals = normalsArray;
		if (isTextured)
			this.textureCoordinates = textureCoordinatesArray;
		this.positions = positionsArray;
		this.indices = indicesArray;
		bind();
	}
	
	private static void meshDataException(String data, File file, Exception e) {
		meshException(data + " in OBJFile " + file.getAbsolutePath() + " are invalid.", e);
	}
	
	private static void meshException(String output, Exception e) {
		System.out.println(output);
		e.printStackTrace();
	}
	
	SadMesh(int[] indices, float[] positions) {
		this(indices, positions, null);
		//System.out.println(positions.length + " " + indices.length);
	}
	
	SadMesh(int[] indices, float[] positions, float[] textureCoordinates) {
		this(indices, positions, textureCoordinates, null);
	}
	
	SadMesh(int[] indices, float[] positions, float[] textureCoordinates, float[] normals) {
		this.indices = indices;
		this.positions = positions;
		this.textureCoordinates = textureCoordinates;
		this.normals = normals;
		bind();
	}
	
	int[] getIndices() {
		return indices;
	}
	
	float[] getPositions() {
		return positions;
	}
	
	private void bind() {
		if (positions == null)
			return;
		bindArrays();
		indices = null;
		positions = null;
		textureCoordinates = null;
		normals = null;
	}
	
	private void bindArrays() {
		if (indices == null) {
			vaoId = glGenVertexArrays();
			glBindVertexArray(vaoId);
			
			int positionVBO = addStaticAttribute(0, positions, 3);
			
			vboIds = new int[]{positionVBO};
			vertexCount = positions.length / 3;
			
			return;
		}
		
		vaoId = glGenVertexArrays();
		glBindVertexArray(vaoId);
		
		indexVboId = attachIndexBuffer(indices);
		
		vertexCount = indices.length;
		
		useIndexBuffer = true;
		
		
		int positionVBO = addStaticAttribute(0, positions, 3);
		
		//Build sphere
		for (int i = 0; i < positions.length; i += 3) {
			float radius = (float) Math.sqrt(
					Math.pow(positions[i], 2)
							+ Math.pow(Math.sqrt(Math.pow(positions[i + 1], 2) + Math.pow(positions[i + 2], 2)), 2)
			);
			if (radius < this.radius)
				continue;
			this.radius = radius;
		}
		
		if (textureCoordinates == null) {
			vboIds = new int[]{positionVBO};
			return;
		}
		
		int textureCoordsVBO = addStaticAttribute(1, textureCoordinates, 2);
		
		if (normals == null) {
			vboIds = new int[]{positionVBO, textureCoordsVBO};
			return;
		}
		
		int normalsVBO = addStaticAttribute(2, normals, 3);
		
		vboIds = new int[]{positionVBO, textureCoordsVBO, normalsVBO};
	}
	
	void loadVao(boolean load) {
		if (load) {
			glBindVertexArray(vaoId);
			for (int i = 0; i < vboIds.length; i++) {
				glEnableVertexAttribArray(i);
			}
		} else {
			for (int i = 0; i < vboIds.length; i++) {
				glDisableVertexAttribArray(i);
			}
			glBindVertexArray(0);
		}
	}
	
	void draw() {
		if (useIndexBuffer)
			glDrawElements(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT, 0);
		else
			glDrawArrays(GL_TRIANGLES, 0, vertexCount);
		//System.out.println(vertexCount);
	}
	
	@Override
	void release() {
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glDeleteVertexArrays(vaoId);
		for (int id : vboIds) {
			glDeleteBuffers(id);
		}
		if (useIndexBuffer)
			glDeleteBuffers(indexVboId);
	}
	
	private int addStaticAttribute(int index, float[] data, int dataSize) {
		int vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
		glVertexAttribPointer(index, dataSize, GL_FLOAT, false, 0, 0);
		return vbo;
	}
	
	private int attachIndexBuffer(int[] indices) {
		int vbo = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vbo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
		return vbo;
	}
}
