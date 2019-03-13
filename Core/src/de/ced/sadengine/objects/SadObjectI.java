package de.ced.sadengine.objects;

@SuppressWarnings({"unused", "UnusedReturnValue", "WeakerAccess"})
interface SadObjectI {
	
	String getName();
	
	void setName(String name);
	
	@Override
	String toString();
}
