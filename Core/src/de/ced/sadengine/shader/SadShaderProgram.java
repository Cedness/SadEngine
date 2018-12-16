package de.ced.sadengine.shader;

import de.ced.sadengine.objects.SadTexture;
import de.ced.sadengine.utils.SadVector3;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import static org.lwjgl.opengl.GL20.*;

public abstract class SadShaderProgram {
	
	private static final float[] MATRIX_BUFFER = new float[16];
	
	protected int program;
	
	private int vertexShader;
	private int fragmentShader;
	
	public SadShaderProgram(String vertexFile, String fragmentFile) {
		vertexShader = SadShaderLoader.load(vertexFile, GL_VERTEX_SHADER);
		fragmentShader = SadShaderLoader.load(fragmentFile, GL_FRAGMENT_SHADER);
		program = glCreateProgram();
		
		glAttachShader(program, vertexShader);
		glAttachShader(program, fragmentShader);
		
		bindAttributes();
		
		glLinkProgram(program);
		glValidateProgram(program);
		
		getAllUniformLocations();
	}
	
	protected abstract void bindAttributes();
	
	protected void bindAttribute(int attribute, String variable) {
		glBindAttribLocation(program, attribute, variable);
	}
	
	public void enable(boolean enable) {
		glUseProgram(enable ? program : 0);
	}
	
	protected abstract void getAllUniformLocations();
	
	protected int getUniformLocation(String uniformName) {
		return glGetUniformLocation(program, uniformName);
	}
	
	protected void loadFloat(int location, float value) {
		glUniform1f(location, value);
	}
	
	protected void loadVector(int location, SadVector3 value) {
		glUniform3f(location,
				value.x(),
				value.y(),
				value.z()
		);
	}
	
	protected void loadMatrix(int location, Matrix4f value) {
		float[] val = value.get(MATRIX_BUFFER);
		glUniformMatrix4fv(location, false, val);
	}
	
	protected void loadMatrix(int location, Matrix3f value) {
		glUniformMatrix4fv(location, false, value.get(MATRIX_BUFFER));
	}
	
	protected void loadBoolean(int location, boolean value) {
		glUniform1f(location, value ? 1 : 0);
	}
	
	protected void loadTexture(int location, SadTexture texture, int textureSlot) {
		glUniform1i(location, textureSlot);
		glActiveTexture(GL_TEXTURE0 + textureSlot);
		glBindTexture(GL_TEXTURE_2D, texture != null ? texture.getID() : 0);
	}
	
	public void release() {
		enable(false);
		
		glDetachShader(program, vertexShader);
		glDetachShader(program, fragmentShader);
		glDeleteShader(vertexShader);
		glDeleteShader(fragmentShader);
		
		glDeleteProgram(program);
	}
}
