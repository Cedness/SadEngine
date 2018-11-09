package de.ced.sadengine.objects;

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
public class SadMesh extends SadObject {
	
	private int[] indices = null;
	private float[] positions;
	private float[] textureCoordinates = null;
	private float[] normals = null;
	
	private int vaoId;
	private int[] vboIds;
	private int vertexCount;
	private boolean useIndexBuffer;
	private int indexVboId;
	
	private float radius = 0f;
	
	@Deprecated
	public SadMesh(String name, float[] positions) {
		super(name);
		this.positions = positions;
		bind();
	}
	
	public SadMesh(String name, int[] indices, float[] positions) {
		this(name, indices, positions, null);
		//System.out.println(positions.length + " " + indices.length);
	}
	
	public SadMesh(String name, int[] indices, float[] positions, float[] textureCoordinates) {
		this(name, indices, positions, textureCoordinates, null);
	}
	
	public SadMesh(String name, int[] indices, float[] positions, float[] textureCoordinates, float[] normals) {
		super(name);
		this.indices = indices;
		this.positions = positions;
		this.textureCoordinates = textureCoordinates;
		this.normals = normals;
		bind();
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
	
	public void loadVao(boolean load) {
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
	
	public void draw() {
		if (useIndexBuffer)
			glDrawElements(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT, 0);
		else
			glDrawArrays(GL_TRIANGLES, 0, vertexCount);
		//System.out.println(vertexCount);
	}
	
	@Override
	public void release() {
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
