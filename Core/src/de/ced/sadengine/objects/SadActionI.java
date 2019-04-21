package de.ced.sadengine.objects;

@SuppressWarnings({"unused", "UnusedReturnValue", "WeakerAccess"})
public interface SadActionI extends SadRunnableI {
	
	SadActionLogic getLogic();
	
	@Override
	SadAction setRunning(boolean running);
}
