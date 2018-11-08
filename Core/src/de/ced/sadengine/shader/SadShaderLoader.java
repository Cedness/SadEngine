package de.ced.sadengine.shader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

public class SadShaderLoader {
	
	public static int load(String path, int shaderType) {
		StringBuilder builder = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(SadShaderLoader.class.getResourceAsStream("/shader/" + path)));
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
