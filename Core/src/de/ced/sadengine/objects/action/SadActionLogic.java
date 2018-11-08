package de.ced.sadengine.objects.action;

import de.ced.sadengine.main.SadIllogic;
import de.ced.sadengine.main.Sadness;

public interface SadActionLogic extends SadIllogic {
	
	/**
	 * Called when the action's running state changes to true.
	 */
	default void startRunning(Sadness sadness) {
	}
	
	/**
	 * Called when the action's running state changes to false.
	 */
	default void stopRunning(Sadness sadness) {
	}
}
