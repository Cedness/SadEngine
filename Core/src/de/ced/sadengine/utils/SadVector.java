package de.ced.sadengine.utils;

import org.joml.Vector3f;

@SuppressWarnings({"unused", "WeakerAccess", "UnusedReturnValue"})
public class SadVector {
	
	protected float[] values;
	
	public SadVector(int dimension) {
		values = new float[dimension];
	}
	
	public SadVector(int dimension, float... values) {
		this(dimension);
		set(values);
	}
	
	public SadVector(float... values) {
		this(values.length, values);
	}
	
	public SadVector(int dimension, SadVector vector) {
		this(dimension, vector.values);
	}
	
	public SadVector(SadVector vector) {
		this(vector.getDimension(), vector);
	}
	
	public float getLength() {
		return (float) Math.sqrt(getSquaredLength());
	}
	
	public float getSquaredLength() {
		float squaredLength = 0;
		for (float value : values) {
			squaredLength += pow2(value);
		}
		return squaredLength;
	}
	
	protected float pow2(float scalar) {
		return scalar * scalar;
	}
	
	public SadVector setLength(float length) {
		return mul(length / getLength());
	}
	
	public SadVector normalize() {
		return setLength(1);
	}
	
	public SadVector dotProduct(SadVector vector) {
		return mul(vector);
	}
	
	public SadVector dotProduct(float... values) {
		return mul(values);
	}
	
	public SadVector crossProduct(float... values) {
		float[] a = this.values.clone();
		if (a.length == 3 && values.length == 3) {
			this.values[0] = a[1] * values[2] - a[2] * values[1];
			this.values[1] = a[2] * values[0] - a[0] * values[2];
			this.values[2] = a[0] * values[1] - a[1] * values[0];
		}
		return end();
	}
	
	public SadVector crossProduct(SadVector vector) {
		return crossProduct(vector.values);
	}
	
	private float normalize(float v) {
		return v > values.length ? v - values.length : v;
	}
	
	public int getDimension() {
		return values.length;
	}
	
	public SadVector setDimension(int dimension) {
		float[] values = this.values.clone();
		this.values = new float[dimension];
		return set(values);
	}
	
	public float x() {
		return get(0);
	}
	
	public float y() {
		return get(1);
	}
	
	public float z() {
		return get(2);
	}
	
	public float a() {
		return get(3);
	}
	
	public float get(int i) {
		return values[i];
	}
	
	public SadVector x(float x) {
		return set(0, x);
	}
	
	public SadVector y(float y) {
		return set(1, y);
	}
	
	public SadVector z(float z) {
		return set(2, z);
	}
	
	public SadVector a(float a) {
		return set(3, a);
	}
	
	public SadVector set(int i, float value) {
		values[i] = value;
		return end();
	}
	
	public SadVector set(float... values) {
		for (int i = 0; i < getDimension(); i++) {
			if (i >= values.length)
				break;
			
			this.values[i] = values[i];
		}
		return end();
	}
	
	public SadVector set(float scalar) {
		for (int i = 0; i < getDimension(); i++) {
			values[i] = scalar;
		}
		return end();
	}
	
	public SadVector set(SadVector vector) {
		return set(vector.values);
	}
	
	public SadVector mulX(float x) {
		return mul(0, x);
	}
	
	public SadVector mulY(float y) {
		return mul(1, y);
	}
	
	public SadVector mulZ(float z) {
		return mul(2, z);
	}
	
	public SadVector mulA(float a) {
		return mul(3, a);
	}
	
	public SadVector mul(int i, float value) {
		values[i] *= value;
		return end();
	}
	
	public SadVector mul(float... values) {
		for (int i = 0; i < getDimension(); i++) {
			if (i >= values.length)
				break;
			
			this.values[i] *= values[i];
		}
		return end();
	}
	
	public SadVector mul(float scalar) {
		for (int i = 0; i < getDimension(); i++) {
			values[i] *= scalar;
		}
		return end();
	}
	
	public SadVector mul(SadVector vector) {
		return mul(vector.values);
	}
	
	public SadVector negate() {
		return mul(-1f);
	}
	
	public SadVector addX(float x) {
		return add(0, x);
	}
	
	public SadVector addY(float y) {
		return add(1, y);
	}
	
	public SadVector addZ(float z) {
		return add(2, z);
	}
	
	public SadVector addA(float a) {
		return add(3, a);
	}
	
	public SadVector add(int i, float value) {
		values[i] += value;
		return end();
	}
	
	public SadVector add(float... values) {
		for (int i = 0; i < getDimension(); i++) {
			if (i >= values.length)
				break;
			
			this.values[i] += values[i];
		}
		return end();
	}
	
	public SadVector add(float scalar) {
		for (int i = 0; i < getDimension(); i++) {
			values[i] += scalar;
		}
		return end();
	}
	
	public SadVector add(SadVector vector) {
		return add(vector.values);
	}
	
	public SadVector rot(int i, float radAngle) {
		if (i < 3) {
			float length = getLength();
			float currentAngle;
		}
		return end();
	}
	
	protected final SadVector end() {
		update();
		return this;
	}
	
	protected void update() {
	}
	
	@SuppressWarnings("MethodDoesntCallSuperMethod")
	@Override
	public SadVector clone() {
		return new SadVector(this);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (float value : values) {
			builder.append(value).append(" ");
		}
		return builder.deleteCharAt(builder.length() - 1).toString();
	}
	
	public boolean contentEquals(SadVector vector) {
		float[] values = vector.values;
		if (this.values.length != values.length)
			return false;
		for (int i = 0; i < values.length; i++) {
			if (this.values[i] != values[i])
				return false;
		}
		return true;
	}
	
	public Vector3f toVector3f() {
		return new Vector3f(values[0], values[1], values[2]);
	}
}
