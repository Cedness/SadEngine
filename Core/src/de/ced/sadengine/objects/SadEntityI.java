package de.ced.sadengine.objects;

@SuppressWarnings({"unused", "UnusedReturnValue", "WeakerAccess"})
public interface SadEntityI extends SadPositionableI {
	
	SadModel getModel();
	
	SadEntity setModel(String name);
	
	SadHitbox getHitbox();
	
	SadEntity setHitbox(String name);
}
