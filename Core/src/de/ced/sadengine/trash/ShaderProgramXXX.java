package de.ced.sadengine.trash;

import static org.lwjgl.opengl.GL20.*;

public class ShaderProgramXXX {
	
	private final int programId;
	
	private int vertexShaderId;
	
	private int fragmentShaderId;
	
	public ShaderProgramXXX() throws Exception {
		programId = glCreateProgram();
		if (programId == 0)
			throw new Exception("Could not create SadShader");
	}
	
	public void createVertexShader(String shaderCode) throws Exception {
		vertexShaderId = createShader(shaderCode, GL_VERTEX_SHADER);
	}
	
	public void createFragmentShader(String shaderCode) throws Exception {
		fragmentShaderId = createShader(shaderCode, GL_FRAGMENT_SHADER);
	}
	
	protected int createShader(String shaderCode, int shaderType) throws Exception {
		int shaderId = glCreateShader(shaderType);
		if (shaderId == 0)
			throw new Exception("Error creating SadShader. Type: " + shaderType);
		
		glShaderSource(shaderId, shaderCode);
		glCompileShader(shaderId);
		
		if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0)
			throw new Exception("Error compiling SadShader code: " + glGetShaderInfoLog(shaderId, 1024));
		
		glAttachShader(programId, shaderId);
		
		return shaderId;
	}
	
	public void link() throws Exception {
		glLinkProgram(programId);
		if (glGetProgrami(programId, GL_LINK_STATUS) == 0)
			throw new Exception("Error linking SadShader code: " + glGetShaderInfoLog(programId, 1024));
		
		if (vertexShaderId != 0)
			glDetachShader(programId, vertexShaderId);
		
		if (fragmentShaderId != 0)
			glDetachShader(programId, fragmentShaderId);
		
		glValidateProgram(programId);
		if (glGetProgrami(programId, GL_VALIDATE_STATUS) == 0)
			System.err.println("Warning validating SadShader code: " + glGetShaderInfoLog(programId, 1024));
	}
	
	public void bind() {
		glUseProgram(programId);
	}
	
	public void unbind() {
		glUseProgram(0);
	}
	
	public void cleanup() {
		unbind();
		if (programId != 0)
			glDeleteProgram(programId);
	}

		/*
		SadMesh mesh = new SadMesh(
				new int[]{1, 3, 0, 7, 5, 4, 4, 2, 0, 5, 2, 1, 2, 7, 3, 0, 7, 4, 1, 2, 3, 7, 6, 5, 4, 5, 1, 5, 6, 2, 2, 6, 7, 0, 3, 7},
				new float[]{1, -1, -1, 1, -1, 1, -1, -1, 1, -1, -1, -1, 1, 1, -1, 1, 1, 1, -1, 1, 1, -1, 1, -1}
		);
		*/

		/*
		SadMesh mesh = new SadMesh(
				new int[]{0, 1, 3, 3, 1, 2, 4, 5, 7, 7, 5, 6, 8, 9, 11, 11, 9, 10, 12, 13, 15, 15, 13, 14, 16, 17, 19, 19, 17, 18, 20, 21, 23, 23, 21, 22},
				new float[]{-0.5f, 0.5f, -0.5f, -0.5f, -0.5f, -0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f, -0.5f,
				-0.5f, 0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f, -0.5f, 0.5f, 0.5f, 0.5f, 0.5f,
				0.5f, 0.5f, -0.5f, 0.5f, -0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f, 0.5f, 0.5f,
				-0.5f, 0.5f, -0.5f, -0.5f, -0.5f, -0.5f, -0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f,
				-0.5f, 0.5f, 0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f, -0.5f, 0.5f, 0.5f, 0.5f,
				-0.5f, -0.5f, 0.5f, -0.5f, -0.5f, -0.5f, 0.5f, -0.5f, -0.5f, 0.5f, -0.5f, 0.5f},
				new float[]{0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0})
		;
		*/
}
