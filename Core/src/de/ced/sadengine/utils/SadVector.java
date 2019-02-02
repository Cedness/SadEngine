package de.ced.sadengine.utils;

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
	
	@Deprecated
	public SadVector normalize() {
		return mul(1f / getLength());
	}
	
	public int getDimension() {
		return values.length;
	}
	
	public SadVector setDimension(int dimension) {
		float[] values = this.values.clone();
		this.values = new float[dimension];
		return set(values);
	}
	
	public float get(int i) {
		return values[i];
	}
	
	public SadVector set(int i, float value) {
		values[i] = value;
		return update();
	}
	
	public SadVector set(float... values) {
		for (int i = 0; i < getDimension(); i++) {
			if (i >= values.length)
				break;
			
			this.values[i] = values[i];
		}
		return update();
	}
	
	public SadVector set(float scalar) {
		for (int i = 0; i < getDimension(); i++) {
			values[i] = scalar;
		}
		return update();
	}
	
	public SadVector set(SadVector vector) {
		return set(vector.values);
	}
	
	public SadVector mul(float... values) {
		for (int i = 0; i < getDimension(); i++) {
			if (i >= values.length)
				break;
			
			this.values[i] *= values[i];
		}
		return update();
	}
	
	public SadVector mul(float scalar) {
		for (int i = 0; i < getDimension(); i++) {
			values[i] *= scalar;
		}
		return update();
	}
	
	public SadVector mul(SadVector vector) {
		return mul(vector.values);
	}
	
	public SadVector negate() {
		return mul(-1f);
	}
	
	public SadVector add(float... values) {
		for (int i = 0; i < getDimension(); i++) {
			if (i >= values.length)
				break;
			
			this.values[i] += values[i];
		}
		return update();
	}
	
	public SadVector add(float scalar) {
		for (int i = 0; i < getDimension(); i++) {
			values[i] += scalar;
		}
		return update();
	}
	
	public SadVector add(SadVector vector) {
		return add(vector.values);
	}
	
	protected SadVector update() {
		return this;
	}
	
	public SadVector clone() {
		try {
			return (SadVector) super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (float value : values) {
			builder.append(value).append(" ");
		}
		return builder.deleteCharAt(builder.length() - 1).toString();
	}
}
