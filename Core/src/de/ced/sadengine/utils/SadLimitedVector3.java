package de.ced.sadengine.utils;

public class SadLimitedVector3 extends SadVector3 {
	
	private final float min;
	private final float max;
	private final float range;
	
	public SadLimitedVector3(float min, float max) {
		super();
		this.min = min;
		this.max = max;
		range = max - min;
	}
	
	public SadLimitedVector3(float x, float y, float z, float min, float max) {
		super(x, y, z);
		this.min = min;
		this.max = max;
		range = max - min;
	}
	
	public SadLimitedVector3(SadVector3 vector, float min, float max) {
		super(vector);
		this.min = min;
		this.max = max;
		range = max - min;
	}
	
	public SadLimitedVector3(SadLimitedVector3 vector) {
		super(vector);
		min = vector.min;
		max = vector.max;
		range = max - min;
	}
	
	@Override
	protected SadLimitedVector3 update() {
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
		return (SadLimitedVector3) super.update();
	}
}
