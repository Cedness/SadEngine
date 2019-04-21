package de.ced.sadengine.objects;

@SuppressWarnings("unused")
public interface SadActionLogic extends SadLogic {
	
	/**
	 * Called when the action's running state changes to true.
	 */
	default void startRunning() {
	}
	
	/**
	 * Called when the action's running state changes to false.
	 */
	default void stopRunning() {
	}
}
