package de.ced.sadengine.objects;

import java.util.ArrayList;
import java.util.HashMap;

public class SadLevel extends SadObject implements SadLevelI {
	
	private ArrayList<String> entities = new ArrayList<>();
	private HashMap<String, ArrayList<String>> index = new HashMap<>();
	
	public SadLevel(String name, SadContent content) {
		super(name, content);
	}
	
	@SuppressWarnings("ConstantConditions")
	@Override
	public SadLevel addEntity(String name) {
		if (content.getEntity(name) == null || hasEntity(name))
			return this;
		entities.add(name);
		addToIndex(name);
		return this;
	}
	
	@SuppressWarnings("ConstantConditions")
	private void addToIndex(String entity) {
		String model = content.getEntity(entity).getModel().getName();
		
		if (!index.containsKey(model))
			index.put(model, new ArrayList<>());
		
		index.get(model).add(entity);
	}
	
	HashMap<String, ArrayList<String>> getIndex() {
		return index;
	}
	
	@SuppressWarnings("ConstantConditions")
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
	
	@Override
	public void removeEntity(String name) {
		if (!hasEntity(name))
			return;
		removeFromIndex(name);
		entities.remove(name);
	}
	
	@SuppressWarnings("ConstantConditions")
	@Override
	public SadEntity getEntity(String name) {
		if (!hasEntity(name))
			return null;
		return content.getEntity(name);
	}
	
	@Override
	public boolean hasEntity(String name) {
		return entities.contains(name);
	}
	
	@Override
	public SadLevel clearAllEntities() {
		entities = new ArrayList<>();
		index = new HashMap<>();
		return this;
	}
}
