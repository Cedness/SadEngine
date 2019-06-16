package de.ced.sadengine.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static de.ced.sadengine.objects.SadMovement.WHEN_DISPLAYED;

public class SadLevel extends SadObject {
	
	private ArrayList<SadEntity> entities = new ArrayList<>();
	private HashMap<SadModel, ArrayList<SadEntity>> index = new HashMap<>();
	private SadMovement movement = WHEN_DISPLAYED;
	private List<SadCamera> cameras = new ArrayList<>();
	
	void addCamera(SadCamera camera) {
		cameras.add(camera);
	}
	
	void removeCamera(SadCamera camera) {
		cameras.remove(camera);
	}
	
	List<SadCamera> getCameras() {
		return cameras;
	}
	
	public SadLevel addEntity(SadEntity entity) {
		if (entity == null || hasEntity(entity))
			return this;
		entities.add(entity);
		
		SadModel model = entity.getModel();
		if (!index.containsKey(model))
			index.put(model, new ArrayList<>());
		index.get(model).add(entity);
		
		return this;
	}
	
	HashMap<SadModel, ArrayList<SadEntity>> getIndex() {
		return index;
	}
	
	public void removeEntity(SadEntity entity) {
		if (!hasEntity(entity))
			return;
		
		entity.getLevels().remove(this);
		
		SadModel model = entity.getModel();
		if (!index.containsKey(model))
			return;
		if (index.get(model).size() == 1) {
			index.remove(model);
		} else {
			index.get(model).remove(entity);
		}
		
		entities.remove(entity);
	}
	
	public List<SadEntity> getEntities() {
		return entities;
	}
	
	public boolean hasEntity(SadEntity entity) {
		return entities.contains(entity);
	}
	
	public SadLevel clearAllEntities() {
		for (SadEntity entity : entities) {
			entity.getLevels().remove(this);
		}
		entities = new ArrayList<>();
		index = new HashMap<>();
		return this;
	}
	
	public SadMovement getMovement() {
		return movement;
	}
	
	public SadLevel setMovement(SadMovement movement) {
		this.movement = movement;
		return this;
	}
}
