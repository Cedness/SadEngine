package de.ced.sadengine.objects;

import de.ced.sadengine.utils.SadVector;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

@SuppressWarnings("WeakerAccess")
public class SadOBJMesh extends SadMesh {
	
	private int[] indices = null;
	private float[] positions = null;
	private float[] textureCoordinates = null;
	private float[] normals = null;
	
	private int vaoId;
	private int[] vboIds;
	private int vertexCount;
	private boolean useIndices;
	private int indexVboId;
	
	private float radius = 0f;
	
	private String info = "";
	
	public SadOBJMesh(URL url) {
		this(url.getFile());
	}
	
	public SadOBJMesh(URI uri) {
		this(uri.getPath());
	}
	
	public SadOBJMesh(String path) {
		this(path, path);
	}
	
	public SadOBJMesh(String path, String info) {
		this(new File(path), info);
	}
	
	public SadOBJMesh(File file) {
		this(file, file.getAbsolutePath());
	}
	
	public SadOBJMesh(File file, String info) {
		this.info = info;
		InputStream stream;
		try {
			stream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			meshException("OBJFile " + info + " not found.", e);
			return;
		}
		load(stream);
	}
	
	public SadOBJMesh(InputStream stream) {
		this(stream, String.valueOf(stream.hashCode()));
	}
	
	public SadOBJMesh(InputStream stream, String info) {
		this.info = info;
		load(stream);
	}
	
	private void load(InputStream stream) {
		InputStreamReader streamReader = new InputStreamReader(stream);
		BufferedReader reader = new BufferedReader(streamReader);
		
		List<String> lines = new ArrayList<>();
		try {
			while (reader.ready()) {
				lines.add(reader.readLine());
			}
			reader.close();
		} catch (IOException e) {
			meshException("Reading Error in OBJFile " + info + ".", e);
			return;
		}
		load(lines);
	}
	
	private void load(List<String> lines) {
		List<Integer> indices = new ArrayList<>();
		List<SadVector> positions = new ArrayList<>();
		List<SadVector> textureCoordinates = new ArrayList<>();
		List<SadVector> normals = new ArrayList<>();
		
		String[] parts;
		for (String line : lines) {
			try {
				parts = line.split(" ");
			} catch (PatternSyntaxException e) {
				meshDataException("Format", e);
				return;
			}
			
			if (line.startsWith("v ")) {
				try {
					positions.add(new SadVector(
							Float.parseFloat(parts[1]),
							Float.parseFloat(parts[2]),
							Float.parseFloat(parts[3])
					));
				} catch (NumberFormatException e) {
					meshDataException("Positions", e);
					return;
				}
			} else if (line.startsWith("vt ")) {
				try {
					textureCoordinates.add(new SadVector(
							Float.parseFloat(parts[1]),
							Float.parseFloat(parts[2])
					));
				} catch (NumberFormatException e) {
					meshDataException("Texture coordinates", e);
					return;
				}
			} else if (line.startsWith("vn ")) {
				try {
					normals.add(new SadVector(
							Float.parseFloat(parts[1]),
							Float.parseFloat(parts[2]),
							Float.parseFloat(parts[3])
					));
				} catch (NumberFormatException e) {
					meshDataException("Normals", e);
					return;
				}
			}
		}
		
		
		boolean isTextured = textureCoordinates.size() > 0;
		boolean hasNormals = normals.size() > 0;
		
		
		float[] positionsArray = new float[positions.size() * 3];
		float[] textureCoordinatesArray = isTextured ? new float[positions.size() * 2] : null;
		float[] normalsArray = new float[positions.size() * 3];
		
		
		for (String line : lines) {
			if (!line.startsWith("f"))
				continue;
			
			try {
				parts = line.split(" ");
				
				for (int i = 1; i < parts.length; i++) {
					String[] part = parts[i].split("/");
					
					int index = Integer.parseInt(part[0]) - 1;
					indices.add(index);
					
					if (isTextured) {
						SadVector textureCoordinate = textureCoordinates.get(Integer.parseInt(part[1]) - 1);
						textureCoordinatesArray[index * 2] = textureCoordinate.x();
						textureCoordinatesArray[index * 2 + 1] = textureCoordinate.y();
					}
					
					if (hasNormals) {
						SadVector normal = normals.get(Integer.parseInt(part[2]) - 1);
						normalsArray[index * 3] = normal.x();
						normalsArray[index * 3 + 1] = normal.y();
						normalsArray[index * 3 + 2] = normal.z();
					}
				}
			} catch (PatternSyntaxException | NumberFormatException e) {
				meshDataException("Indices", e);
				return;
			}
		}
		
		int[] indicesArray = new int[indices.size()];
		
		for (int i = 0; i < positions.size(); i++) {
			positionsArray[i * 3] = positions.get(i).x();
			positionsArray[i * 3 + 1] = positions.get(i).y();
			positionsArray[i * 3 + 2] = positions.get(i).z();
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
	
	private void meshDataException(String data, Exception e) {
		meshException(data + " invalid.", e);
	}
	
	private void meshException(String output, Exception e) {
		System.out.println("Error while loading OBJFile " + info + ": " + output);
		e.printStackTrace();
	}
	
	SadOBJMesh(int[] indices, float[] positions) {
		this(indices, positions, null);
		//System.out.println(positions.length + " " + indices.length);
	}
	
	SadOBJMesh(int[] indices, float[] positions, float[] textureCoordinates) {
		this(indices, positions, textureCoordinates, null);
	}
	
	SadOBJMesh(int[] indices, float[] positions, float[] textureCoordinates, float[] normals) {
		this.indices = indices;
		this.positions = positions;
		this.textureCoordinates = textureCoordinates;
		this.normals = normals;
		bind();
	}
	
	@SuppressWarnings("unused")
	int[] getIndices() {
		return indices;
	}
	
	float[] getPositions() {
		return positions;
	}
	
	private void bind() {
		bindArrays();
		indices = null;
		positions = null;
		textureCoordinates = null;
		normals = null;
	}
	
	private void bindArrays() {
		if (positions == null)
			return;
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
		
		useIndices = true;
		
		
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
	
	@Override
	void loadVao() {
		glBindVertexArray(vaoId);
		for (int i = 0; i < vboIds.length; i++) {
			glEnableVertexAttribArray(i);
		}
	}
	
	@Override
	void unloadVao() {
		for (int i = 0; i < vboIds.length; i++) {
			glDisableVertexAttribArray(i);
		}
		glBindVertexArray(0);
	}
	
	@Override
	void draw() {
		if (useIndices)
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
		if (useIndices)
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
