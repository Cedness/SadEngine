package de.ced.sadengine.trash;

import de.ced.sadengine.objects.SadTexture;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import static org.lwjgl.opengl.GL20.*;

public class GLSLProgramXXX {
	
	private static final float[] MATRIX_BUFFER = new float[16];
	private int program;
	private HashMap<String, Integer> uniformVariables = new HashMap<>();
	private final String shaderDirectory = "/shader/";
	
	public GLSLProgramXXX(String vertexShaderFile, String fragmentShaderFile, String... vertexAttributes) {
		program = glCreateProgram();
		attachShader(GL_VERTEX_SHADER, shaderDirectory + vertexShaderFile);
		attachShader(GL_FRAGMENT_SHADER, shaderDirectory + fragmentShaderFile);
		
		for (int i = 0; i < vertexAttributes.length; i++) {
			glBindAttribLocation(program, i, vertexAttributes[i]);
		}
		
		glLinkProgram(program);
		glValidateProgram(program);
	}
	
	public void addUniformVariable(String variable) {
		int location = glGetUniformLocation(program, variable);
		uniformVariables.put(variable, location);
	}
	
	public void setVec3(String variable, Vector3f vec3) {
		if (!uniformVariables.containsKey(variable))
			return;
		enable();
		int location = uniformVariables.get(variable);
		glUniform3f(location, vec3.x, vec3.y, vec3.z);
	}
	
	public void setTexture(String variable, SadTexture texture, int textureSlot) {
		if (!uniformVariables.containsKey(variable))
			return;
		enable();
		int location = uniformVariables.get(variable);
		glUniform1i(location, textureSlot);
		glActiveTexture(GL_TEXTURE0 + textureSlot);
		glBindTexture(GL_TEXTURE_2D, texture != null ? texture.getID() : 0);
	}
	
	public void setMatrix(String variable, Matrix4f matrix) {
		Integer location = enableVariable(variable);
		if (location == null)
			return;
		glUniformMatrix4fv(location, false, matrix.get(MATRIX_BUFFER));
	}
	
	private Integer enableVariable(String variable) {
		if (!uniformVariables.containsKey(variable))
			return null;
		enable();
		return uniformVariables.get(variable);
	}
	
	public void enable() {
		glUseProgram(program);
	}
	
	public void disable() {
		glUseProgram(0);
	}
	
	public void release() {
		disable();
		glDeleteProgram(program);
	}
	
	private void attachShader(int shaderType, String file) {
		StringBuilder builder = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(GLSLProgramXXX.class.getResourceAsStream(file)));
			while (reader.ready()) {
				builder.append(reader.readLine() + System.lineSeparator());
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
		glAttachShader(program, id);
		glDeleteShader(id);
	}
}
