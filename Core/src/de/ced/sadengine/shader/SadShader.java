package de.ced.sadengine.shader;

import de.ced.sadengine.graphics.light.SadLight;
import de.ced.sadengine.objects.SadTexture;
import org.joml.Matrix4f;

public class SadShader extends SadShaderProgram {
	
	protected int transformationMatrix;
	protected int viewMatrix;
	
	protected int frameMatrix;
	
	protected int diffuseTexture;
	private int projectionMatrix;
	
	private int lightColor;
	private int lightPosition;
	private int lightDamper;
	private int reflectivity;
	
	public SadShader() {
		super("world.vs", "world.fs");
	}
	
	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoordinates");
		super.bindAttribute(2, "normal");
	}
	
	@Override
	protected void getAllUniformLocations() {
		transformationMatrix = super.getUniformLocation("transformationMatrix");
		viewMatrix = super.getUniformLocation("viewMatrix");
		
		frameMatrix = super.getUniformLocation("frameMatrix");
		
		projectionMatrix = super.getUniformLocation("projectionMatrix");
		
		diffuseTexture = super.getUniformLocation("diffuseTexture");
		
		lightColor = super.getUniformLocation("lightColor");
		lightPosition = super.getUniformLocation("lightPosition");
		lightDamper = super.getUniformLocation("lightDamper");
		reflectivity = super.getUniformLocation("reflectivity");
	}
	
	public void uploadTransformationMatrix(Matrix4f matrix) {
		super.loadMatrix(transformationMatrix, matrix);
	}
	
	public void uploadViewMatrix(Matrix4f matrix) {
		super.loadMatrix(viewMatrix, matrix);
	}
	
	public void uploadProjectionMatrix(Matrix4f matrix) {
		super.loadMatrix(projectionMatrix, matrix);
	}
	
	public void uploadFrameMatrix(Matrix4f matrix) {
		super.loadMatrix(frameMatrix, matrix);
	}
	
	@Deprecated
	public void uploadLight(SadLight light) {
		super.loadVector(lightColor, light.getColor());
		super.loadVector(lightPosition, light.getPosition());
	}
	
	public void uploadShine(float lightDamper, float reflectivity) {
		super.loadFloat(this.lightDamper, lightDamper);
		super.loadFloat(this.reflectivity, reflectivity);
	}
	
	public void uploadTexture(SadTexture texture, int textureSlot) {
		super.loadTexture(diffuseTexture, texture, textureSlot);
		//if (texture != null)
		//	uploadShine(texture.getLightDamper(), texture.getReflectivity());
	}
}
