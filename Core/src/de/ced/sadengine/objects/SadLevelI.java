package de.ced.sadengine.objects;

@SuppressWarnings({"unused", "UnusedReturnValue", "WeakerAccess"})
public interface SadLevelI extends SadObjectI {
	
	SadLevel addEntity(String name);
	
	void removeEntity(String name);
	
	SadEntity getEntity(String name);
	
	boolean hasEntity(String name);
	
	SadLevel clearAllEntities();
}
