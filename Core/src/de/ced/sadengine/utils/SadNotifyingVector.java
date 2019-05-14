package de.ced.sadengine.utils;

@SuppressWarnings("unused")
public class SadRotationVector extends SadVector {
	
	public SadRotationVector(int dimension) {
		super(dimension);
	}
	
	public SadRotationVector(int dimension, float... values) {
		super(dimension, values);
	}
	
	public SadRotationVector(float... values) {
		super(values);
	}
	
	public SadRotationVector(int dimension, SadVector vector) {
		super(dimension, vector);
	}
	
	public SadRotationVector(SadVector vector) {
		super(vector);
	}
	
	@Override
	protected void update() {
		for (int i = 0; i < values.length; i++) {
			while (values[i] >= 360) {
				values[i] -= 360;
			}
			while (values[i] < 0) {
				values[i] += 360;
			}
		}
	}
}
