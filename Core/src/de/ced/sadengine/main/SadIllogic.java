package de.ced.sadengine.main;

/**
 * Just for inheritance.
 * Do not use directly!
 */
public interface SadIllogic {
	
	/**
	 * Main logic:
	 * Called when the engine has started.
	 * <p>
	 * Action:
	 * Called after the SadAction object has been created.
	 */
	default void setup(Sadness sadness) {
	}
	
	/**
	 * General:
	 * Called ups times per second.
	 * Ups is adjustable in the settings.
	 * <p>
	 * This method NEVER gets called before setup() has been called
	 * or after terminate() has been called.
	 * <p>
	 * Actions:
	 * Only called if the SadAction object's isRunning() method returns true.
	 */
	default void update(Sadness sadness) {
	}
	
	/**
	 * General:
	 * Called before the engine stops.
	 * <p>
	 * Main logic:
	 * Called after all SadAction objects have been terminated.
	 */
	default void terminate(Sadness sadness) {
	}
}
