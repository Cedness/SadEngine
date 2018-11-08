package de.ced.sadengine.main;

import de.ced.sadengine.objects.*;
import de.ced.sadengine.objects.action.SadAction;
import de.ced.sadengine.objects.action.SadActionHandler;
import de.ced.sadengine.objects.action.SadActionLogic;
import de.ced.sadengine.objects.time.SadClock;
import de.ced.sadengine.objects.time.SadClockwork;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class SadContent {
	
	private float interval;
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
		HashMap<String, SadObject> map = contents.get(clazz);
		return map.getOrDefault(name, null);
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
	
	public SadTexture createTexture(String name, File file) {
		SadTexture texture = new SadTexture(name, file);
		put(texture);
		return texture;
	}
	
	public SadTexture getTexture(String name) {
		return (SadTexture) get(SadTexture.class, name);
	}
	
	public SadHitbox createHitbox(String name) {
		SadHitbox hitbox = new SadHitbox(name, this);
		put(hitbox);
		return hitbox;
	}
	
	public SadHitbox getHitbox(String name) {
		return (SadHitbox) get(SadHitbox.class, name);
	}
	
	public SadModel createModel(String name) {
		SadModel model = new SadModel(name, this);
		put(model);
		return model;
	}
	
	public SadModel getModel(String name) {
		return (SadModel) get(SadModel.class, name);
	}
	
	public SadLevel createLevel(String name) {
		SadLevel level = new SadLevel(name, this);
		put(level);
		return level;
	}
	
	public SadLevel getLevel(String name) {
		return (SadLevel) get(SadLevel.class, name);
	}
	
	public SadCamera createCamera(String name) {
		SadCamera camera = new SadCamera(name, this);
		put(camera);
		return camera;
	}
	
	public SadCamera getCamera(String name) {
		return (SadCamera) get(SadCamera.class, name);
	}
	
	public SadFrame createFrame(String name) {
		SadFrame frame = new SadFrame(name, this);
		put(frame);
		return frame;
	}
	
	public SadFrame getFrame(String name) {
		return (SadFrame) get(SadFrame.class, name);
	}
	
	public SadClock createClock(String name) {
		SadClock clock = new SadClock(name);
		put(clock);
		return clock;
	}
	
	public SadClock getClock(String name) {
		return (SadClock) get(SadClock.class, name);
	}
	
	public SadAction createAction(String name, SadActionLogic logic) {
		SadAction action = new SadAction(name, logic);
		put(action);
		return action;
	}
	
	public SadAction getAction(String name) {
		return (SadAction) get(SadAction.class, name);
	}
}
