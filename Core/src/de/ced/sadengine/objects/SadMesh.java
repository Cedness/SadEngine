package de.ced.sadengine.objects;

public abstract class SadMesh extends SadObject implements SadMeshI {
	
	abstract void draw();
	
	abstract void loadVao();
	
	abstract void unloadVao();
}
