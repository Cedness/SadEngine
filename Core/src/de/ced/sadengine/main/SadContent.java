package de.ced.sadengine.main;

import de.ced.sadengine.io.SadResourceLoader;
import de.ced.sadengine.objects.*;
import de.ced.sadengine.objects.action.SadAction;
import de.ced.sadengine.objects.action.SadActionHandler;
import de.ced.sadengine.objects.action.SadActionLogic;
import de.ced.sadengine.objects.time.SadClock;
import de.ced.sadengine.objects.time.SadClockwork;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

/**
 * Contains all the stuff processed by the engine.
 * Objects in here won't be processed directly, they first have to be added to the SadWindow instance.
 */
public class SadContent {
	
	private float interval;
	
	//The big Q
	private final HashMap<Class<? extends SadObject>, HashMap<String, SadObject>> contents = new HashMap<>();
	
	private final SadActionHandler actionHandler;
	private final SadClockwork clockwork;
	
	public SadContent(Sadness sadness) {
		contents.put(SadEntity.class, new HashMap<>());
		contents.put(SadHitbox.class, new HashMap<>());
		contents.put(SadModel.class, new HashMap<>());
		contents.put(SadMesh.class, new HashMap<>());
		contents.put(SadTexture.class, new HashMap<>());
		contents.put(SadLevel.class, new HashMap<>());
		contents.put(SadCamera.class, new HashMap<>());
		contents.put(SadFrame.class, new HashMap<>());
		contents.put(SadClock.class, new HashMap<>());
		contents.put(SadAction.class, new HashMap<>());
		
		actionHandler = new SadActionHandler(contents.get(SadAction.class), sadness);
		clockwork = new SadClockwork(contents.get(SadClock.class));
	}
	
	public void update(float secInterval) {
		interval = secInterval;
		
		clockwork.increaseClocks(interval);
	}
	
	SadActionHandler getActionHandler() {
		return actionHandler;
	}
	
	private void put(SadObject object) {
		if (object.getName() == null || object.getName().isEmpty())
			return;
		contents.get(object.getClass()).put(object.getName(), object);
	}
	
	private SadObject get(Class<? extends SadObject> clazz, String name) {
		if (name == null || name.isEmpty())
			return null;
		return contents.get(clazz).getOrDefault(name, null);
	}
	
	private void delete(Class<? extends SadObject> clazz, String name) {
		SadObject obj = contents.get(clazz).remove(name);
		if (obj == null)
			return;
		obj.release();
	}
	
	public float getInterval() {
		return interval;
	}
	
	public SadEntity createEntity(String name) {
		SadEntity entity = new SadEntity(name, this);
		put(entity);
		return entity;
	}
	
	public SadEntity getEntity(String name) {
		return (SadEntity) get(SadEntity.class, name);
	}
	
	public void deleteEntity(String name) {
		delete(SadEntity.class, name);
	}
	
	public SadMesh createMesh(String name, File file) {
		SadMesh mesh = null;
		try {
			mesh = SadOBJLoader.loadObj(name, file);
		} catch (FileNotFoundException e) {
			System.out.println("OBJFile " + file.getAbsolutePath() + " not found.");
			e.printStackTrace();
		} catch (NumberFormatException e) {
			System.out.println("OBJFile " + file.getAbsolutePath() + " is invalid.");
			e.printStackTrace();
		}
		if (mesh == null)
			return null;
		put(mesh);
		return mesh;
	}
	
	public SadMesh getMesh(String name) {
		return (SadMesh) get(SadMesh.class, name);
	}
	
	public void deleteMesh(String name) {
		delete(SadMesh.class, name);
	}
	
	public SadTexture createTexture(String name, File file) {
		SadTexture texture = SadResourceLoader.loadTexture(name, file);
		put(texture);
		return texture;
	}
	
	public SadTexture createTexture(String name, File file, boolean alpha) {
		SadTexture texture = SadResourceLoader.loadTexture(name, file, alpha);
		put(texture);
		return texture;
	}
	
	public SadTexture getTexture(String name) {
		return (SadTexture) get(SadTexture.class, name);
	}
	
	public void deleteTexture(String name) {
		delete(SadTexture.class, name);
	}
	
	public SadFont createFont(String name, File atlas, File data) {
		SadFont font = new SadFont(name, atlas, data);
		put(font);
		return font;
	}
	
	public SadFont getFont(String name) {
		return (SadFont) get(SadFont.class, name);
	}
	
	public void deleteFont(String name) {
		delete(SadFont.class, name);
	}
	
	public SadHitbox createHitbox(String name) {
		SadHitbox hitbox = new SadHitbox(name, this);
		put(hitbox);
		return hitbox;
	}
	
	public SadHitbox getHitbox(String name) {
		return (SadHitbox) get(SadHitbox.class, name);
	}
	
	public void deleteHitbox(String name) {
		delete(SadHitbox.class, name);
	}
	
	public SadModel createModel(String name) {
		SadModel model = new SadModel(name, this);
		put(model);
		return model;
	}
	
	public SadModel getModel(String name) {
		return (SadModel) get(SadModel.class, name);
	}
	
	public void deleteModel(String name) {
		delete(SadModel.class, name);
	}
	
	public SadLevel createLevel(String name) {
		SadLevel level = new SadLevel(name, this);
		put(level);
		return level;
	}
	
	public SadLevel getLevel(String name) {
		return (SadLevel) get(SadLevel.class, name);
	}
	
	public void deleteLevel(String name) {
		delete(SadLevel.class, name);
	}
	
	public SadCamera createCamera(String name) {
		SadCamera camera = new SadCamera(name, this);
		put(camera);
		return camera;
	}
	
	public SadCamera getCamera(String name) {
		return (SadCamera) get(SadCamera.class, name);
	}
	
	public void deleteCamera(String name) {
		delete(SadCamera.class, name);
	}
	
	public SadFrame createFrame(String name) {
		SadFrame frame = new SadFrame(name, this);
		put(frame);
		return frame;
	}
	
	public SadFrame getFrame(String name) {
		return (SadFrame) get(SadFrame.class, name);
	}
	
	public void deleteFrame(String name) {
		delete(SadFrame.class, name);
	}
	
	public SadClock createClock(String name) {
		SadClock clock = new SadClock(name);
		put(clock);
		return clock;
	}
	
	public SadClock getClock(String name) {
		return (SadClock) get(SadClock.class, name);
	}
	
	public void deleteClock(String name) {
		delete(SadClock.class, name);
	}
	
	public SadAction createAction(String name, SadActionLogic logic) {
		SadAction action = new SadAction(name, logic);
		put(action);
		return action;
	}
	
	public SadAction getAction(String name) {
		return (SadAction) get(SadAction.class, name);
	}
	
	public void deleteAction(String name) {
		delete(SadAction.class, name);
	}
}
