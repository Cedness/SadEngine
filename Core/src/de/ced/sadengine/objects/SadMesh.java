package de.ced.sadengine.objects;

public abstract class SadMesh extends SadObject {
	
	abstract void draw();
	
	abstract void loadVao();
	
	abstract void unloadVao();
}
