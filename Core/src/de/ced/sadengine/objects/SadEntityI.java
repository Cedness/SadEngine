package de.ced.sadengine.objects;

@SuppressWarnings({"unused", "UnusedReturnValue", "WeakerAccess"})
interface SadEntityI extends SadPositionableI {
	
	boolean isEnabled();
	
	SadEntity setEnabled(boolean enabled);
	
	boolean isVisible();
	
	SadEntity setVisible(boolean visible);
	
	SadModel getModel();
	
	SadEntity setModel(SadModel model);
	
	SadBody getBody();
	
	SadEntity setBody(SadBody body);
}
