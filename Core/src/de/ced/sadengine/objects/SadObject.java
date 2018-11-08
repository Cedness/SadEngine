package de.ced.sadengine.objects;

import de.ced.sadengine.main.SadContent;

public abstract class SadObject {
	
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
}
