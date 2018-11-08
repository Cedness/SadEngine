package de.ced.sadengine.objects;

import de.ced.sadengine.main.SadContent;

import java.util.ArrayList;

/**
 * Represents the model of an entity
 * Contains one or more Meshes stored in ModelParts, each has it's own coordinates (relating to the Entity)
 */
public class SadModel extends SadDrawable {
	
	private ArrayList<String> models = new ArrayList<>();
	
	protected String mesh = null;
	protected String texture = null;
	
	public SadModel(String name, SadContent content) {
		super(name, content);
	}
	
	public SadModel addModel(String name) {
		if (hasModel(name))
			return this;
		models.add(name);
		return this;
	}
	
	public SadModel removeModel(String name) {
		if (!hasModel(name))
			return this;
		models.remove(name);
		return this;
	}
	
	public SadModel getModel(String name) {
		if (!hasModel(name))
			return null;
		return content.getModel(name);
	}
	
	public boolean hasModel(String name) {
		return models.contains(name);
	}
	
	public ArrayList<String> getModels() {
		return models;
	}
	
	public SadMesh getMesh() {
		return content.getMesh(mesh);
	}
	
	public SadModel setMesh(String name) {
		mesh = name;
		return this;
	}
	
	public SadTexture getTexture() {
		return content.getTexture(texture);
	}
	
	public SadModel setTexture(String name) {
		texture = name;
		return this;
	}
}
