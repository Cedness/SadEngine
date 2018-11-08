package de.ced.sadengine.objects;

import de.ced.sadengine.main.SadContent;

import java.util.ArrayList;
import java.util.HashMap;

public class SadLevel extends SadObject {
	
	private ArrayList<String> entities = new ArrayList<>();
	private HashMap<String, ArrayList<String>> index = new HashMap<>();
	
	public SadLevel(String name, SadContent content) {
		super(name, content);
	}
	
	public SadLevel addEntity(String name) {
		if (content.getEntity(name) == null || hasEntity(name))
			return this;
		entities.add(name);
		addToIndex(name);
		return this;
	}
	
	private void addToIndex(String entity) {
		String model = content.getEntity(entity).getModel().getName();
		
		if (!index.containsKey(model))
			index.put(model, new ArrayList<>());
		
		index.get(model).add(entity);
	}
	
	public HashMap<String, ArrayList<String>> getIndex() {
		return index;
	}
	
	private void removeFromIndex(String entity) {
		String model = content.getEntity(entity).getModel().getName();
		
		if (!index.containsKey(model))
			return;
		if (!(index.get(model).size() == 1)) {
			index.remove(model);
		} else {
			index.get(model).remove(entity);
		}
	}
	
	public void removeEntity(String name) {
		if (!hasEntity(name))
			return;
		removeFromIndex(name);
		entities.remove(name);
	}
	
	public SadEntity getEntity(String name) {
		if (!hasEntity(name))
			return null;
		return content.getEntity(name);
	}
	
	public boolean hasEntity(String name) {
		return entities.contains(name);
	}
	
	public SadLevel clearAllEntities() {
		entities = new ArrayList<>();
		index = new HashMap<>();
		return this;
	}
}
