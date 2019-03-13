package de.ced.sadengine.objects;

import de.ced.sadengine.utils.SadVector;

import java.util.List;

@SuppressWarnings({"unused", "UnusedReturnValue", "WeakerAccess"})
interface SadModelI extends SadPositionableI {
	
	SadModel addModel(SadModel model);
	
	SadModel removeModel(SadModel model);
	
	boolean hasModel(SadModel model);
	
	List<SadModel> getModels();
	
	SadMesh getMesh();
	
	SadModel setMesh(SadMesh mesh);
	
	SadTexture getTexture();
	
	SadModel setTexture(SadTexture texture);
	
	boolean isRenderBack();
	
	void setRenderBack(boolean renderBack);
	
	SadVector getColor();
}