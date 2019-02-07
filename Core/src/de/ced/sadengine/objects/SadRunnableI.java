package de.ced.sadengine.objects;

@SuppressWarnings({"unused", "UnusedReturnValue", "WeakerAccess"})
public interface SadRunnableI extends SadObjectI {
	
	boolean isRunning();
	
	SadRunnable setRunning(boolean running);
}
