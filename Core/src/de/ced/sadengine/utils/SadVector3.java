package de.ced.sadengine.utils;

import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class SadVector3 {
	
	protected float x, y, z;
	protected List<SadVector3> syncedVectors = new ArrayList<>();
	
	public SadVector3() {
		set(0);
	}
	
	public SadVector3(float x, float y, float z) {
		set(x, y, z);
	}
	
	public SadVector3(SadVector3 vector) {
		set(vector);
	}
	
	public Vector3f toVector3f() {
		return new Vector3f(x, y, z);
	}
	
	public float getLength() {
		return (float) Math.sqrt(getSquaredLength());
	}
	
	public float getSquaredLength() {
		return pow2(x) + pow2(y) + pow2(z);
	}
	
	private float pow2(float scalar) {
		return scalar * scalar;
	}
	
	public SadVector3 normalize() {
		return mul(1f / getLength());
	}
	
	public SadVector3 set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return update();
	}
	
	public SadVector3 set(float scalar) {
		return set(scalar, scalar, scalar);
	}
	
	public SadVector3 set(SadVector3 vector) {
		return set(vector.x, vector.y, vector.z);
	}
	
	public float x() {
		return x;
	}
	
	public float y() {
		return y;
	}
	
	public float z() {
		return z;
	}
	
	public SadVector3 x(float x) {
		this.x = x;
		return update();
	}
	
	public SadVector3 y(float y) {
		this.y = y;
		return update();
	}
	
	public SadVector3 z(float z) {
		this.z = z;
		return update();
	}
	
	public SadVector3 mul(float x, float y, float z) {
		this.x *= x;
		this.y *= y;
		this.z *= z;
		return update();
	}
	
	public SadVector3 mulX(float scalar) {
		x *= scalar;
		return update();
	}
	
	public SadVector3 mulY(float scalar) {
		y *= scalar;
		return update();
	}
	
	public SadVector3 mulZ(float scalar) {
		z *= scalar;
		return update();
	}
	
	public SadVector3 mul(float scalar) {
		return mul(scalar, scalar, scalar);
	}
	
	public SadVector3 mul(SadVector3 vector) {
		return mul(vector.x, vector.y, vector.z);
	}
	
	public SadVector3 add(float x, float y, float z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return update();
	}
	
	public SadVector3 addX(float scalar) {
		x += scalar;
		return update();
	}
	
	public SadVector3 addY(float scalar) {
		y += scalar;
		return update();
	}
	
	public SadVector3 addZ(float scalar) {
		z += scalar;
		return update();
	}
	
	public SadVector3 add(float scalar) {
		return add(scalar, scalar, scalar);
	}
	
	public SadVector3 add(SadVector3 vector) {
		return add(vector.x, vector.y, vector.z);
	}
	
	public boolean contentEquals(SadVector3 vector) {
		return x == vector.x && y == vector.y && z == vector.z;
	}
	
	/**
	 * Keeps the xyz values of two vector synchronized.
	 * The vector you call this method on will get changed.
	 *
	 * @param vector The vector to synchronize with. It won't get changed in this method.
	 * @return This vector
	 */
	public SadVector3 synchronize(SadVector3 vector) {
		set(vector);
		if (!syncedVectors.contains(vector))
			syncedVectors.add(vector);
		if (!vector.syncedVectors.contains(vector))
			vector.syncedVectors.add(this);
		return this;
	}
	
	public SadVector3 unsynchronize(SadVector3 vector) {
		syncedVectors.remove(vector);
		vector.syncedVectors.remove(this);
		return this;
	}
	
	public SadVector3 unsynchronizeAll() {
		for (SadVector3 vector : syncedVectors) {
			vector.syncedVectors.remove(this);
		}
		syncedVectors = new ArrayList<>();
		return this;
	}
	
	protected SadVector3 update() {
		for (SadVector3 vector : syncedVectors) {
			vector.x = x;
			vector.y = y;
			vector.z = z;
		}
		return this;
	}
}
