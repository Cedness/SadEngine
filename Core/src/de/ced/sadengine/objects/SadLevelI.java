package de.ced.sadengine.objects;

import java.util.List;

@SuppressWarnings({"unused", "UnusedReturnValue", "WeakerAccess"})
interface SadLevelI extends SadObjectI {
	
	SadLevel addEntity(SadEntity entity);
	
	void removeEntity(SadEntity entity);
	
	List<SadEntity> getEntities();
	
	boolean hasEntity(SadEntity entity);
	
	SadLevel clearAllEntities();
	
	SadMovement getMovement();
	
	SadLevel setMovement(SadMovement movement);
}
