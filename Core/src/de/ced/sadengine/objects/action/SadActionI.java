package de.ced.sadengine.objects.action;

import de.ced.sadengine.objects.SadRunnableI;

@SuppressWarnings({"unused", "UnusedReturnValue", "WeakerAccess"})
public interface SadActionI extends SadRunnableI {
	
	SadActionLogic getLogic();
	
	@Override
	SadAction setRunning(boolean running);
}
