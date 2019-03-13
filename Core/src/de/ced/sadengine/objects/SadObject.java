package de.ced.sadengine.objects;

/**
 * An object which can be handled by an instance of SadContent.
 */
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
	public final String toString() {
		return getName();
	}
	
	void release() {
	}
}
