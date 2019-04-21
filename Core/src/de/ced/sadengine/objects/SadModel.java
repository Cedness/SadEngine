package de.ced.sadengine.objects;

import de.ced.sadengine.utils.SadVector;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the model of an entity
 * Contains one Mesh and/or more Models
 */
public class SadModel extends SadPositionable implements SadModelI {
	
	private ArrayList<SadModel> models = new ArrayList<>();
	private SadMesh mesh = null;
	private SadTexture texture = null;
	private SadVector color = new SadVector(1f, 1f, 1f, 1f);
	private boolean renderBack = false;
	
	@Override
	public SadModel addModel(SadModel model) {
		if (hasModel(model))
			return this;
		models.add(model);
		return this;
	}
	
	@Override
	public SadModel removeModel(SadModel model) {
		if (!hasModel(model))
			return this;
		models.remove(model);
		return this;
	}
	
	@Override
	public boolean hasModel(SadModel model) {
		return models.contains(model);
	}
	
	@Override
	public List<SadModel> getModels() {
		return models;
	}
	
	@Override
	public SadMesh getMesh() {
		return mesh;
	}
	
	@Override
	public SadModel setMesh(SadMesh mesh) {
		this.mesh = mesh;
		return this;
	}
	
	@SuppressWarnings("ConstantConditions")
	@Override
	public SadTexture getTexture() {
		return texture;
	}
	
	@Override
	public SadModel setTexture(SadTexture texture) {
		this.texture = texture;
		return this;
	}
	
	@Override
	public boolean isRenderBack() {
		return renderBack;
	}
	
	@Override
	public SadModel setRenderBack(boolean renderBack) {
		this.renderBack = renderBack;
		return this;
	}
	
	@Override
	public SadVector getColor() {
		return color;
	}
}
