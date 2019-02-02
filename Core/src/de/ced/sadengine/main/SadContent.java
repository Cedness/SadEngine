package de.ced.sadengine.main;

import de.ced.sadengine.io.SadGL;
import de.ced.sadengine.objects.*;
import de.ced.sadengine.objects.action.SadAction;
import de.ced.sadengine.objects.action.SadActionHandler;
import de.ced.sadengine.objects.action.SadActionLogic;
import de.ced.sadengine.objects.time.SadClock;
import de.ced.sadengine.objects.time.SadClockwork;
import de.ced.sadengine.utils.SadVector3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashMap;

/**
 * Contains all the stuff processed by the engine.
 * Objects in here won't be processed directly, they first have to be added to the SadWindow instance.
 */
public class SadContent {
	
	private float interval;
	private SadVector3 clearColor = new SadVector3(0.1f, 0.1f, 0.1f);
	
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
	
	public float getInterval() {
		return interval;
	}
	
	public SadVector3 getClearColor() {
		return clearColor;
	}
	
	SadActionHandler getActionHandler() {
		return actionHandler;
	}
	
	//Basic metods
	
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
	
	private boolean delete(Class<? extends SadObject> clazz, String name) {
		SadObject obj = contents.get(clazz).remove(name);
		if (obj == null)
			return false;
		obj.release();
		return true;
	}
	
	//Individual methods
	
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
			mesh = SadGL.loadMesh(name, file);
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
		deleteFrame(name);
		SadTexture texture = SadGL.loadTexture(name, file);
		put(texture);
		return texture;
	}
	
	public SadTexture createTexture(String name, File file, boolean alpha) {
		deleteFrame(name);
		SadTexture texture = SadGL.loadTexture(name, file, alpha);
		put(texture);
		return texture;
	}
	
	public SadTexture getTexture(String name) {
		SadTexture texture = (SadTexture) get(SadTexture.class, name);
		return texture != null ? texture : getFrame(name);
	}
	
	public void deleteTexture(String name) {
		if (delete(SadTexture.class, name))
			return;
		deleteFrame(name);
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
	
	public SadFrame createFrame(String name, int width, int height) {
		deleteTexture(name);
		SadFrame frame = new SadFrame(name, this, SadGL.createFrameBuffer(), SadGL.createTextureAttachment(width, height),
				SadGL.createDepthBufferAttachment(width, height), width, height);
		put(frame);
		return frame;
	}
	
	public SadFrame getFrame(String name) {
		return (SadFrame) get(SadFrame.class, name);
	}
	
	public void deleteFrame(String name) {
		delete(SadFrame.class, name);
	}
	
	public Collection<SadObject> getFrames() {
		return contents.get(SadFrame.class).values();
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
