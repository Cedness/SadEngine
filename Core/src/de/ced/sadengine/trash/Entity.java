/*package de.ced.sadengine.trash;

import de.ced.sadengine.objects.SadOBJMesh;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Entity {
	
	private SadOBJMesh mesh;
	private Vector3f position;
	private Vector3f rotation;
	public float scale;
	private Matrix4f transformationMatrix;
	
	public Entity(SadOBJMesh mesh) {
		this.mesh = mesh;
		position = new Vector3f();
		rotation = new Vector3f();
		scale = 1f;
		transformationMatrix = new Matrix4f();
	}
	
	public void update() {
		transformationMatrix = new Matrix4f().identity();
		transformationMatrix.translate(position);
		transformationMatrix.rotateXYZ(
				(float) Math.toRadians(rotation.x),
				(float) Math.toRadians(rotation.y),
				(float) Math.toRadians(rotation.z)
		);
		transformationMatrix.scale(scale);
	}
	
	public SadOBJMesh getMesh() {
		return mesh;
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	public Vector3f getRotation() {
		return rotation;
	}
	
	public Matrix4f getTransformationMatrix() {
		return transformationMatrix;
	}
}
*/