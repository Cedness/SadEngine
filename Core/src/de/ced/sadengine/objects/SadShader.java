package de.ced.sadengine.objects;

import de.ced.sadengine.objects.light.SadLight;
import de.ced.sadengine.utils.SadVector;
import org.joml.Matrix4f;

@SuppressWarnings("unused")
class SadShader extends SadShaderProgram {
	
	private int transformationMatrix;
	private int viewMatrix;
	private int projectionMatrix;
	
	private int diffuseTexture;
	private int textureActive;
	private int color;
	
	private int lightColor;
	private int lightPosition;
	private int lightDamper;
	private int reflectivity;
	
	SadShader() {
		super("world.vs", "world.fs");
	}
	
	@Override
	protected void bindAllAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoordinates");
		super.bindAttribute(2, "normal");
	}
	
	@Override
	protected void getAllUniformLocations() {
		transformationMatrix = super.getUniformLocation("transformationMatrix");
		
		viewMatrix = super.getUniformLocation("viewMatrix");
		projectionMatrix = super.getUniformLocation("projectionMatrix");
		
		diffuseTexture = super.getUniformLocation("diffuseTexture");
		textureActive = super.getUniformLocation("textureActive");
		color = super.getUniformLocation("color");
		
		lightColor = super.getUniformLocation("lightColor");
		lightPosition = super.getUniformLocation("lightPosition");
		lightDamper = super.getUniformLocation("lightDamper");
		reflectivity = super.getUniformLocation("reflectivity");
	}
	
	void uploadTransformationMatrix(Matrix4f matrix) {
		super.loadMatrix(transformationMatrix, matrix);
	}
	
	void uploadViewMatrix(Matrix4f matrix) {
		super.loadMatrix(viewMatrix, matrix);
	}
	
	void uploadProjectionMatrix(Matrix4f matrix) {
		super.loadMatrix(projectionMatrix, matrix);
	}
	
	@SuppressWarnings("DeprecatedIsStillUsed")
	@Deprecated
	void uploadLight(SadLight light) {
		super.loadVector3(lightColor, light.getColor());
		super.loadVector3(lightPosition, light.getPosition());
	}
	
	void uploadShine(float lightDamper, float reflectivity) {
		super.loadFloat(this.lightDamper, lightDamper);
		super.loadFloat(this.reflectivity, reflectivity);
	}
	
	void uploadTexture(SadTexture texture, @SuppressWarnings("SameParameterValue") int textureSlot) {
		super.loadTexture(diffuseTexture, texture, textureSlot);
		super.loadBoolean(textureActive, texture != null);
		//if (texture != null)
		//	uploadShine(texture.getLightDamper(), texture.getReflectivity());
	}
	
	void uploadColor(SadVector color) {
		super.loadVector4(this.color, color);
	}
}
