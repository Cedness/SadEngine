package de.ced.sadengine.objects;

import de.ced.sadengine.utils.SadVector;

public class SadPhysics {
	
	private float mass = 1f;
	private SadVector velocity = new SadVector(3);
	
	public float getMass() {
		return mass;
	}
	
	public SadPhysics setMass(float mass) {
		this.mass = mass;
		return this;
	}
	
	public SadVector getVelocity() {
		return velocity;
	}
	
	public SadVector getImpulse() {
		return new SadVector(velocity).mul(mass);
	}
}
