package de.ced.sadengine.utils;

import static de.ced.sadengine.utils.SadValue.*;

public class SadVector implements SadVectorI {
	
	@SuppressWarnings("WeakerAccess")
	protected float[] values;
	@SuppressWarnings("WeakerAccess")
	protected SadVectorListener listener;
	
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
	
	public void setListener(SadVectorListener listener) {
		this.listener = listener;
	}
	
	@Override
	public SadVector identify() {
		return set(0);
	}
	
	@Override
	public float getLength() {
		return (float) Math.sqrt(getSquaredLength());
	}
	
	@Override
	public float getSquaredLength() {
		float squaredLength = 0;
		for (float value : values) {
			squaredLength += pow(value);
		}
		return squaredLength;
	}
	
	@Override
	public SadVector setLength(float length) {
		return mul(length / getLength());
	}
	
	@Override
	public SadVector addLength(float length) {
		float oldLength = getLength();
		return mul((oldLength + length) / oldLength);
	}
	
	@Override
	public SadVector mulLength(float length) {
		return mul(length);
	}
	
	@Override
	public SadVector normalize() {
		return setLength(1);
	}
	
	@Override
	public float dotProduct(SadVector vector) {
		return dotProduct(vector.values);
	}
	
	@Override
	public float dotProduct(float... values) {
		float result = 0;
		for (int i = 0; i < getDimension(); i++) {
			if (i >= values.length)
				break;
			
			result += (this.values[i] * values[i]);
		}
		return result;
	}
	
	@Override
	public SadVector crossProduct(float... values) {
		float[] a = this.values.clone();
		this.values[0] = a[1] * values[2] - a[2] * values[1];
		this.values[1] = a[2] * values[0] - a[0] * values[2];
		this.values[2] = a[0] * values[1] - a[1] * values[0];
		return end();
	}
	
	@Override
	public SadVector crossProduct(SadVector vector) {
		return crossProduct(vector.values);
	}
	
	@Override
	public float checkIndex(float v) {
		return v > values.length ? v - values.length : v;
	}
	
	@Override
	public int getDimension() {
		return values.length;
	}
	
	@Override
	public SadVector setDimension(int dimension) {
		float[] values = this.values.clone();
		this.values = new float[dimension];
		return set(values);
	}
	
	@Override
	public float x() {
		return get(0);
	}
	
	@Override
	public float y() {
		return get(1);
	}
	
	@Override
	public float z() {
		return get(2);
	}
	
	@Override
	public float a() {
		return get(3);
	}
	
	@Override
	public float get(int i) {
		return values[i];
	}
	
	@Override
	public SadVector x(float x) {
		return set(0, x);
	}
	
	@Override
	public SadVector y(float y) {
		return set(1, y);
	}
	
	@Override
	public SadVector z(float z) {
		return set(2, z);
	}
	
	@Override
	public SadVector a(float a) {
		return set(3, a);
	}
	
	@Override
	public SadVector set(int i, float value) {
		values[i] = value;
		return end();
	}
	
	@Override
	public SadVector set(float... values) {
		for (int i = 0; i < getDimension(); i++) {
			if (i >= values.length)
				break;
			
			this.values[i] = values[i];
		}
		return end();
	}
	
	@Override
	public SadVector set(float scalar) {
		for (int i = 0; i < getDimension(); i++) {
			values[i] = scalar;
		}
		return end();
	}
	
	@Override
	public SadVector set(SadVector vector) {
		return set(vector.values);
	}
	
	@Override
	public SadVector mulX(float x) {
		return mul(0, x);
	}
	
	@Override
	public SadVector mulY(float y) {
		return mul(1, y);
	}
	
	@Override
	public SadVector mulZ(float z) {
		return mul(2, z);
	}
	
	@Override
	public SadVector mulA(float a) {
		return mul(3, a);
	}
	
	@Override
	public SadVector mul(int i, float value) {
		values[i] *= value;
		return end();
	}
	
	@Override
	public SadVector mul(float... values) {
		for (int i = 0; i < getDimension(); i++) {
			if (i >= values.length)
				break;
			
			this.values[i] *= values[i];
		}
		return end();
	}
	
	@Override
	public SadVector mul(float scalar) {
		for (int i = 0; i < getDimension(); i++) {
			values[i] *= scalar;
		}
		return end();
	}
	
	@Override
	public SadVector mul(SadVector vector) {
		return mul(vector.values);
	}
	
	@Override
	public SadVector invert() {
		return mul(-1f);
	}
	
	@Override
	public SadVector addX(float x) {
		return add(0, x);
	}
	
	@Override
	public SadVector addY(float y) {
		return add(1, y);
	}
	
	@Override
	public SadVector addZ(float z) {
		return add(2, z);
	}
	
	@Override
	public SadVector addA(float a) {
		return add(3, a);
	}
	
	@Override
	public SadVector add(int i, float value) {
		values[i] += value;
		return end();
	}
	
	@Override
	public SadVector add(float... values) {
		for (int i = 0; i < getDimension(); i++) {
			if (i >= values.length)
				break;
			
			this.values[i] += values[i];
		}
		return end();
	}
	
	@Override
	public SadVector add(float scalar) {
		for (int i = 0; i < getDimension(); i++) {
			values[i] += scalar;
		}
		return end();
	}
	
	@Override
	public SadVector add(SadVector vector) {
		return add(vector.values);
	}
	
	@Override
	public SadVector rot(int i, float radAngle) {
		if (i < 3) {
			int x, y;
			switch (i) {
				case 1:
					x = 2;
					y = 0;
					break;
				case 2:
					x = 0;
					y = 1;
					break;
				default:
					x = 1;
					y = 2;
					break;
			}
			float sin = sin(radAngle);
			float cos = cos(radAngle);
			
			float a = cos * values[x] - sin * values[y];
			float b = sin * values[x] + cos * values[y];
			values[x] = a;
			values[y] = b;
		}
		return end();
	}
	
	@Override
	public boolean dimensionEquals(SadVector vector) {
		return dimensionEquals(vector.values);
	}
	
	@Override
	public boolean dimensionEquals(float[] values) {
		return this.values.length == values.length;
	}
	
	@Override
	public boolean contentEquals(SadVector vector) {
		return contentEquals(vector.values);
	}
	
	@Override
	public boolean contentEquals(float[] values) {
		if (!dimensionEquals(values))
			return false;
		for (int i = 0; i < values.length; i++) {
			if (this.values[i] != values[i])
				return false;
		}
		return true;
	}
	
	@Override
	public SadVector sync(SadVector vector) {
		if (vector == null)
			return unSync();
		values = vector.values;
		return end();
	}
	
	@Override
	public SadVector unSync() {
		float[] values = this.values;
		this.values = new float[values.length];
		return set(values);
	}
	
	protected final SadVector end() {
		update();
		if (listener != null)
			listener.changeEvent();
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
}
