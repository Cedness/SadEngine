package de.ced.sadengine.utils;

import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class SadVector {
	
	protected float x, y, z;
	protected List<SadVector> syncedVectors = new ArrayList<>();
	
	public SadVector() {
		set(0);
	}
	
	public SadVector(float x, float y, float z) {
		set(x, y, z);
	}
	
	public SadVector(SadVector vector) {
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
	
	public SadVector normalize() {
		return mul(1f / getLength());
	}
	
	public SadVector set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return update();
	}
	
	public SadVector set(float scalar) {
		return set(scalar, scalar, scalar);
	}
	
	public SadVector set(SadVector vector) {
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
	
	public SadVector x(float x) {
		this.x = x;
		return update();
	}
	
	public SadVector y(float y) {
		this.y = y;
		return update();
	}
	
	public SadVector z(float z) {
		this.z = z;
		return update();
	}
	
	public SadVector mul(float x, float y, float z) {
		this.x *= x;
		this.y *= y;
		this.z *= z;
		return update();
	}
	
	public SadVector mulX(float scalar) {
		x *= scalar;
		return update();
	}
	
	public SadVector mulY(float scalar) {
		y *= scalar;
		return update();
	}
	
	public SadVector mulZ(float scalar) {
		z *= scalar;
		return update();
	}
	
	public SadVector mul(float scalar) {
		return mul(scalar, scalar, scalar);
	}
	
	public SadVector mul(SadVector vector) {
		return mul(vector.x, vector.y, vector.z);
	}
	
	public SadVector add(float x, float y, float z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return update();
	}
	
	public SadVector addX(float scalar) {
		x += scalar;
		return update();
	}
	
	public SadVector addY(float scalar) {
		y += scalar;
		return update();
	}
	
	public SadVector addZ(float scalar) {
		z += scalar;
		return update();
	}
	
	public SadVector add(float scalar) {
		return add(scalar, scalar, scalar);
	}
	
	public SadVector add(SadVector vector) {
		return add(vector.x, vector.y, vector.z);
	}
	
	public boolean contentEquals(SadVector vector) {
		return x == vector.x && y == vector.y && z == vector.z;
	}
	
	/**
	 * Keeps the xyz values of two vector synchronized.
	 * The vector you call this method on will get changed.
	 *
	 * @param vector The vector to synchronize with. It won't get changed in this method.
	 * @return This vector
	 */
	public SadVector synchronize(SadVector vector) {
		set(vector);
		if (!syncedVectors.contains(vector))
			syncedVectors.add(vector);
		if (!vector.syncedVectors.contains(vector))
			vector.syncedVectors.add(this);
		return this;
	}
	
	public SadVector unsynchronize(SadVector vector) {
		syncedVectors.remove(vector);
		vector.syncedVectors.remove(this);
		return this;
	}
	
	public SadVector unsynchronizeAll() {
		for (SadVector vector : syncedVectors) {
			vector.syncedVectors.remove(this);
		}
		syncedVectors = new ArrayList<>();
		return this;
	}
	
	protected SadVector update() {
		for (SadVector vector : syncedVectors) {
			vector.x = x;
			vector.y = y;
			vector.z = z;
		}
		return this;
	}
}
