package de.ced.sadengine.objects;

import de.ced.sadengine.objects.action.SadAction;
import de.ced.sadengine.objects.action.SadActionLogic;
import de.ced.sadengine.objects.time.SadClock;

import java.io.File;

@SuppressWarnings({"unused", "UnusedReturnValue", "WeakerAccess"})
public interface SadContentI {
	
	float getInterval();
	
	SadEntity createEntity(String name);
	
	SadEntity getEntity(String name);
	
	void deleteEntity(String name);
	
	SadMesh createMesh(String name, File file);
	
	SadMesh getMesh(String name);
	
	void deleteMesh(String name);
	
	SadTexture createTexture(String name, File file);
	
	SadTexture createTexture(String name, File file, boolean alpha);
	
	SadTexture getTexture(String name);
	
	void deleteTexture(String name);
	
	SadFont createFont(String name, File atlas, File data);
	
	SadFont getFont(String name);
	
	void deleteFont(String name);
	
	SadHitbox createHitbox(String name);
	
	SadHitbox getHitbox(String name);
	
	void deleteHitbox(String name);
	
	SadModel createModel(String name);
	
	SadModel getModel(String name);
	
	void deleteModel(String name);
	
	SadLevel createLevel(String name);
	
	SadLevel getLevel(String name);
	
	void deleteLevel(String name);
	
	SadCamera createCamera(String name);
	
	SadCamera getCamera(String name);
	
	void deleteCamera(String name);
	
	SadFrame createFrame(String name, int width, int height);
	
	SadFrame getFrame(String name);
	
	void deleteFrame(String name);
	
	SadClock createClock(String name);
	
	SadClock getClock(String name);
	
	void deleteClock(String name);
	
	SadAction createAction(String name, SadActionLogic logic);
	
	SadAction getAction(String name);
	
	void deleteAction(String name);
}
