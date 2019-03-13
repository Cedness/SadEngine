package de.ced.sadengine.objects;

@SuppressWarnings({"unused", "UnusedReturnValue", "WeakerAccess"})
interface SadEntityI extends SadPositionableI {
	
	SadModel getModel();
	
	SadEntity setModel(SadModel model);
	
	SadBody getBody();
	
	SadEntity setBody(SadBody body);
}
