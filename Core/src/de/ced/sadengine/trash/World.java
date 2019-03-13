package de.ced.sadengine.trash;

import de.ced.sadengine.objects.SadMesh;
import de.ced.sadengine.objects.light.SadLight;
import de.ced.sadengine.utils.SadVector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class World {
	
	private HashMap<SadMesh, List<Entity>> indexEntities = new HashMap<>();
	private List<Entity> entities = new ArrayList<>();
	
	private SadLight light = new SadLight(new SadVector(1f, 0f, 0f), new SadVector(0, 0, -20));
	
	public List<Entity> getEntities() {
		return entities;
	}
	
	public HashMap<SadMesh, List<Entity>> getIndexEntities() {
		return indexEntities;
	}
	
	public boolean hasEntity(Entity entity) {
		return entities.contains(entity);
	}
	
	public boolean hasEntity(int index) {
		return entities.get(index) != null;
	}
	
	public Entity getEntity(int index) {
		return entities.get(index);
	}
	
	public void addEntity(Entity entity) {
		if (entities.contains(entity))
			return;
		entities.add(entity);
		if (!indexEntities.containsKey(entity.getMesh())) {
			List<Entity> entityList = new ArrayList<>();
			entityList.add(entity);
			indexEntities.put(entity.getMesh(), entityList);
		} else {
			indexEntities.get(entity.getMesh()).add(entity);
		}
	}
	
	public void addEntities(Collection<? extends Entity> entities) {
		for (Entity entity : entities) {
			addEntity(entity);
		}
	}
	
	public void removeEntity(Entity entity) {
		if (!hasEntity(entity))
			return;
		entities.remove(entity);
		if (indexEntities.get(entity.getMesh()).size() <= 1) {
			indexEntities.remove(entity.getMesh());
			//entity.getMesh().release();
		} else {
			indexEntities.get(entity.getMesh()).remove(entity);
		}
	}
	
	public void removeEntity(int index) {
		if (!hasEntity(index))
			return;
		removeEntity(getEntity(index));
	}
	
	public void removeEntities(Collection<? extends Entity> entities) {
		for (Entity entity : entities) {
			removeEntity(entity);
		}
	}
	
	public SadLight getLight() {
		return light;
	}
	
	public void release() {
		for (SadMesh mesh : indexEntities.keySet()) {
			//mesh.release();
		}
		indexEntities = new HashMap<>();
		entities = new ArrayList<>();
	}
}
