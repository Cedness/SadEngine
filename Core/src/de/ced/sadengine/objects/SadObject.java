package de.ced.sadengine.objects;

public abstract class SadObject implements SadObjectI {
	
	protected String name;
	
	public SadObject() {
		this.name = String.valueOf(hashCode());
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return getName();
	}
	
	void release() {
	}
}
