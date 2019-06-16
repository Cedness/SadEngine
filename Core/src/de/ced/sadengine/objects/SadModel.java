package de.ced.sadengine.objects;

import de.ced.sadengine.utils.SadVector;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the model of an entity
 * Contains one Mesh and/or more Models
 */
public class SadModel extends SadPositionable {
	
	private ArrayList<SadModel> models = new ArrayList<>();
	private SadOBJMesh mesh = null;
	private SadTexture texture = null;
	private SadVector color = new SadVector(1f, 1f, 1f, 1f);
	private boolean renderBack = false;
	
	public SadModel addModel(SadModel model) {
		if (hasModel(model))
			return this;
		models.add(model);
		return this;
	}
	
	public SadModel removeModel(SadModel model) {
		if (!hasModel(model))
			return this;
		models.remove(model);
		return this;
	}
	
	public boolean hasModel(SadModel model) {
		return models.contains(model);
	}
	
	public List<SadModel> getModels() {
		return models;
	}
	
	public SadOBJMesh getMesh() {
		return mesh;
	}
	
	public SadModel setMesh(SadOBJMesh mesh) {
		this.mesh = mesh;
		return this;
	}
	
	public SadTexture getTexture() {
		return texture;
	}
	
	public SadModel setTexture(SadTexture texture) {
		this.texture = texture;
		return this;
	}
	
	public boolean isRenderBack() {
		return renderBack;
	}
	
	public SadModel setRenderBack(boolean renderBack) {
		this.renderBack = renderBack;
		return this;
	}
	
	public SadVector getColor() {
		return color;
	}
}
