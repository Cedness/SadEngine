package de.ced.sadengine.objects;

import de.ced.sadengine.main.SadContent;

public class SadEntity extends SadDrawable {
	
	private String model;
	private String hitbox;
	
	public SadEntity(String name, SadContent content) {
		super(name, content);
	}
	
	public SadModel getModel() {
		return content.getModel(model);
	}
	
	public SadEntity setModel(String name) {
		model = name;
		return this;
	}
	
	public SadHitbox getHitbox() {
		return content.getHitbox(hitbox);
	}
	
	public SadEntity setHitbox(String name) {
		hitbox = name;
		return this;
	}
}
