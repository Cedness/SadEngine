package de.ced.sadengine.objects;

public abstract class SadObject {
	
	protected String name;
	
	public SadObject() {
		this.name = String.valueOf(hashCode());
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		return getName();
	}
	
	void release() {
	}
}
