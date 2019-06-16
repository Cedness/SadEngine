package de.ced.sadengine.utils;

@SuppressWarnings("unused")
public class SadNotifyingVector extends SadVector {
	
	private final SadVectorListener listener;
	
	public SadNotifyingVector(SadVectorListener listener, int dimension) {
		super(dimension);
		this.listener = listener;
	}
	
	public SadNotifyingVector(SadVectorListener listener, int dimension, float... values) {
		super(dimension, values);
		this.listener = listener;
	}
	
	public SadNotifyingVector(SadVectorListener listener, float... values) {
		super(values);
		this.listener = listener;
	}
	
	public SadNotifyingVector(SadVectorListener listener, int dimension, SadVector vector) {
		super(dimension, vector);
		this.listener = listener;
	}
	
	public SadNotifyingVector(SadVectorListener listener, SadVector vector) {
		super(vector);
		this.listener = listener;
	}
	
	@Override
	protected void update() {
		if (listener != null)
			listener.changeEvent();
	}
}
