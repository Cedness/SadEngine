package de.ced.sadengine.objects;

/**
 * An object a SadContent instance can handle.
 */
public abstract class SadObject implements SadObjectI {
	
	protected final String name;
	protected final SadContent content;
	
	public SadObject(String name) {
		this.name = name;
		content = null;
	}
	
	public SadObject(String name, SadContent content) {
		this.name = name;
		this.content = content;
	}
	
	public final String getName() {
		return name;
	}
	
	void release() {
	}
}
