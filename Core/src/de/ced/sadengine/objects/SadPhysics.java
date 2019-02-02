package de.ced.sadengine.objects;

import org.joml.Vector3f;

public class SadPhysics {
	
	private float mass = 1f;
	private Vector3f velocity = new Vector3f();
	
	public float getMass() {
		return mass;
	}
	
	public SadPhysics setMass(float mass) {
		this.mass = mass;
		return this;
	}
	
	public Vector3f getVelocity() {
		return velocity;
	}
	
	public Vector3f getImpulse() {
		return new Vector3f(velocity).mul(mass);
	}
}
