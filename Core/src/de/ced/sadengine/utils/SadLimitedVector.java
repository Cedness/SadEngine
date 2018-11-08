package de.ced.sadengine.utils;

public class SadLimitedVector extends SadVector {
	
	private final float min;
	private final float max;
	private final float range;
	
	public SadLimitedVector(float min, float max) {
		super();
		this.min = min;
		this.max = max;
		range = max - min;
	}
	
	public SadLimitedVector(float x, float y, float z, float min, float max) {
		super(x, y, z);
		this.min = min;
		this.max = max;
		range = max - min;
	}
	
	public SadLimitedVector(SadVector vector, float min, float max) {
		super(vector);
		this.min = min;
		this.max = max;
		range = max - min;
	}
	
	public SadLimitedVector(SadLimitedVector vector) {
		super(vector);
		min = vector.min;
		max = vector.max;
		range = max - min;
	}
	
	@Override
	protected SadLimitedVector update() {
		while (x < min)
			x += range;
		while (y < min)
			y += range;
		while (z < min)
			z += range;
		while (x > max)
			x -= range;
		while (y > max)
			y -= range;
		while (z > max)
			z -= range;
		return (SadLimitedVector) super.update();
	}
}
