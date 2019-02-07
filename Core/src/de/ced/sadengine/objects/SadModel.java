package de.ced.sadengine.objects;

import java.util.ArrayList;

/**
 * Represents the model of an entity
 * Contains one Mesh and/or more Models
 */
public class SadModel extends SadPositionable implements SadModelI {
	
	private ArrayList<String> models = new ArrayList<>();
	private String mesh = null;
	private String texture = null;
	private boolean renderBack = false;
	
	SadModel(String name, SadContent content) {
		super(name, content);
	}
	
	@Override
	public SadModel addModel(String name) {
		if (hasModel(name))
			return this;
		models.add(name);
		return this;
	}
	
	@Override
	public SadModel removeModel(String name) {
		if (!hasModel(name))
			return this;
		models.remove(name);
		return this;
	}
	
	@SuppressWarnings("ConstantConditions")
	@Override
	public SadModel getModel(String name) {
		if (!hasModel(name))
			return null;
		return content.getModel(name);
	}
	
	@Override
	public boolean hasModel(String name) {
		return models.contains(name);
	}
	
	@Override
	public ArrayList<String> getModels() {
		return models;
	}
	
	@SuppressWarnings("ConstantConditions")
	@Override
	public SadMesh getMesh() {
		return content.getMesh(mesh);
	}
	
	@Override
	public SadModel setMesh(String name) {
		mesh = name;
		return this;
	}
	
	@SuppressWarnings("ConstantConditions")
	@Override
	public SadTexture getTexture() {
		return content.getTexture(texture);
	}
	
	@Override
	public SadModel setTexture(String name) {
		texture = name;
		return this;
	}
	
	@Override
	public boolean isRenderBack() {
		return renderBack;
	}
	
	@Override
	public void setRenderBack(boolean renderBack) {
		this.renderBack = renderBack;
	}
}
