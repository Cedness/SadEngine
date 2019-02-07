package de.ced.sadengine.objects;

import java.util.ArrayList;

@SuppressWarnings({"unused", "UnusedReturnValue", "WeakerAccess"})
public interface SadModelI extends SadPositionableI {
	
	SadModel addModel(String name);
	
	SadModel removeModel(String name);
	
	SadModel getModel(String name);
	
	boolean hasModel(String name);
	
	ArrayList<String> getModels();
	
	SadMesh getMesh();
	
	SadModel setMesh(String name);
	
	SadTexture getTexture();
	
	SadModel setTexture(String name);
	
	boolean isRenderBack();
	
	void setRenderBack(boolean renderBack);
}
