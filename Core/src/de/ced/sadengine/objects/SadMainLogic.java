package de.ced.sadengine.objects;

/**
 * The main logic given to the engine.
 * Only one instance of this per instance of the engine is allowed.
 */
@SuppressWarnings("unused")
public interface SadMainLogic extends SadIllogic {
	
	/**
	 * Called after all SadAction objects have been updated.
	 */
	default void updateFinally(Sadness sadness) {
	}
	
	/**
	 * Called before all SadAction objects terminate.
	 */
	default void preTerminate(Sadness sadness) {
	}
}
