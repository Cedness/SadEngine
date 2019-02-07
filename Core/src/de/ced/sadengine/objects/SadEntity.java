package de.ced.sadengine.objects;

public class SadEntity extends SadPositionable implements SadEntityI {
	
	private String model;
	private String hitbox;
	
	public SadEntity(String name, SadContent content) {
		super(name, content);
	}
	
	@SuppressWarnings("ConstantConditions")
	@Override
	public SadModel getModel() {
		return content.getModel(model);
	}
	
	@Override
	public SadEntity setModel(String name) {
		model = name;
		return this;
	}
	
	@SuppressWarnings("ConstantConditions")
	@Override
	public SadHitbox getHitbox() {
		return content.getHitbox(hitbox);
	}
	
	@Override
	public SadEntity setHitbox(String name) {
		hitbox = name;
		return this;
	}
}
