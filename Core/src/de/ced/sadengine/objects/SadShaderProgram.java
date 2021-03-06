package de.ced.sadengine.objects;

import de.ced.sadengine.utils.SadGLMatrix;
import de.ced.sadengine.utils.SadVector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.lwjgl.opengl.GL20.*;

@SuppressWarnings("unused")
abstract class SadShaderProgram {
	
	private static final float[] MATRIX_BUFFER = new float[16];
	
	private int program;
	
	private int vertexShader;
	private int fragmentShader;
	
	SadShaderProgram(String vertexFile, String fragmentFile) {
		vertexShader = loadShader(vertexFile, GL_VERTEX_SHADER);
		fragmentShader = loadShader(fragmentFile, GL_FRAGMENT_SHADER);
		program = glCreateProgram();
		
		glAttachShader(program, vertexShader);
		glAttachShader(program, fragmentShader);
		
		bindAllAttributes();
		
		glLinkProgram(program);
		glValidateProgram(program);
		
		getAllUniformLocations();
	}
	
	abstract void bindAllAttributes();
	
	void bindAttribute(int attribute, String variable) {
		glBindAttribLocation(program, attribute, variable);
	}
	
	void enable(boolean enable) {
		glUseProgram(enable ? program : 0);
	}
	
	abstract void getAllUniformLocations();
	
	int getUniformLocation(String uniformName) {
		return glGetUniformLocation(program, uniformName);
	}
	
	void loadFloat(int location, float value) {
		glUniform1f(location, value);
	}
	
	void loadVector(int location, SadVector value) {
		if (value.getDimension() > 3) loadVector4(location, value);
		else loadVector3(location, value);
	}
	
	void loadVector3(int location, SadVector value) {
		glUniform3f(location,
				value.x(),
				value.y(),
				value.z()
		);
	}
	
	void loadVector4(int location, SadVector value) {
		glUniform4f(location,
				value.x(),
				value.y(),
				value.z(),
				value.a()
		);
	}
	
	void loadMatrix(int location, SadGLMatrix value) {
		glUniformMatrix4fv(location, false, value.toArray());
	}
	
	/*
	void loadMatrix(int location, Matrix4f value) {
		float[] val = value.get(MATRIX_BUFFER);
		glUniformMatrix4fv(location, false, val);
	}
	
	void loadMatrix(int location, Matrix3f value) {
		glUniformMatrix4fv(location, false, value.get(MATRIX_BUFFER));
	}
	*/
	
	void loadBoolean(int location, boolean value) {
		glUniform1f(location, value ? 1 : 0);
	}
	
	void loadTexture(int location, SadTexture texture, int textureSlot) {
		glUniform1i(location, textureSlot);
		glActiveTexture(GL_TEXTURE0 + textureSlot);
		glBindTexture(GL_TEXTURE_2D, texture != null ? texture.getTextureID() : 0);
	}
	
	void release() {
		enable(false);
		
		glDetachShader(program, vertexShader);
		glDetachShader(program, fragmentShader);
		glDeleteShader(vertexShader);
		glDeleteShader(fragmentShader);
		
		glDeleteProgram(program);
	}
	
	//Shader Loading
	
	private static int loadShader(String path, int shaderType) {
		StringBuilder builder = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(SadShaderProgram.class.getResourceAsStream("/shader/" + path)));
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
}
