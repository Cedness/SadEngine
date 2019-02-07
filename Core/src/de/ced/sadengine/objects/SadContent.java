package de.ced.sadengine.objects;

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
public class SadContent implements SadContentI {
	
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
	
	void update(float secInterval) {
		interval = secInterval;
		
		clockwork.increaseClocks(interval);
	}
	
	SadActionHandler getActionHandler() {
		return actionHandler;
	}
	
	@Override
	public float getInterval() {
		return interval;
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
	
	@Override
	public SadEntity createEntity(String name) {
		SadEntity entity = new SadEntity(name, this);
		put(entity);
		return entity;
	}
	
	@Override
	public SadEntity getEntity(String name) {
		return (SadEntity) get(SadEntity.class, name);
	}
	
	@Override
	public void deleteEntity(String name) {
		delete(SadEntity.class, name);
	}
	
	@Override
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
	
	@Override
	public SadMesh getMesh(String name) {
		return (SadMesh) get(SadMesh.class, name);
	}
	
	@Override
	public void deleteMesh(String name) {
		delete(SadMesh.class, name);
	}
	
	@Override
	public SadTexture createTexture(String name, File file) {
		deleteFrame(name);
		SadTexture texture = SadGL.loadTexture(name, file);
		put(texture);
		return texture;
	}
	
	@Override
	public SadTexture createTexture(String name, File file, boolean alpha) {
		deleteFrame(name);
		SadTexture texture = SadGL.loadTexture(name, file, alpha);
		put(texture);
		return texture;
	}
	
	@Override
	public SadTexture getTexture(String name) {
		SadTexture texture = (SadTexture) get(SadTexture.class, name);
		return texture != null ? texture : getFrame(name);
	}
	
	@Override
	public void deleteTexture(String name) {
		if (delete(SadTexture.class, name))
			return;
		deleteFrame(name);
	}
	
	@Override
	public SadFont createFont(String name, File atlas, File data) {
		SadFont font = new SadFont(name, atlas, data);
		put(font);
		return font;
	}
	
	@Override
	public SadFont getFont(String name) {
		return (SadFont) get(SadFont.class, name);
	}
	
	@Override
	public void deleteFont(String name) {
		delete(SadFont.class, name);
	}
	
	@Override
	public SadHitbox createHitbox(String name) {
		SadHitbox hitbox = new SadHitbox(name, this);
		put(hitbox);
		return hitbox;
	}
	
	@Override
	public SadHitbox getHitbox(String name) {
		return (SadHitbox) get(SadHitbox.class, name);
	}
	
	@Override
	public void deleteHitbox(String name) {
		delete(SadHitbox.class, name);
	}
	
	@Override
	public SadModel createModel(String name) {
		SadModel model = new SadModel(name, this);
		put(model);
		return model;
	}
	
	@Override
	public SadModel getModel(String name) {
		return (SadModel) get(SadModel.class, name);
	}
	
	@Override
	public void deleteModel(String name) {
		delete(SadModel.class, name);
	}
	
	@Override
	public SadLevel createLevel(String name) {
		SadLevel level = new SadLevel(name, this);
		put(level);
		return level;
	}
	
	@Override
	public SadLevel getLevel(String name) {
		return (SadLevel) get(SadLevel.class, name);
	}
	
	@Override
	public void deleteLevel(String name) {
		delete(SadLevel.class, name);
	}
	
	@Override
	public SadCamera createCamera(String name) {
		SadCamera camera = new SadCamera(name, this);
		put(camera);
		return camera;
	}
	
	@Override
	public SadCamera getCamera(String name) {
		return (SadCamera) get(SadCamera.class, name);
	}
	
	@Override
	public void deleteCamera(String name) {
		delete(SadCamera.class, name);
	}
	
	@Override
	public SadFrame createFrame(String name, int width, int height) {
		deleteTexture(name);
		SadFrame frame = new SadFrame(name, this, SadGL.createFrameBuffer(), SadGL.createTextureAttachment(width, height),
				SadGL.createDepthBufferAttachment(width, height), width, height);
		put(frame);
		return frame;
	}
	
	@Override
	public SadFrame getFrame(String name) {
		return (SadFrame) get(SadFrame.class, name);
	}
	
	@Override
	public void deleteFrame(String name) {
		delete(SadFrame.class, name);
	}
	
	@Override
	public SadClock createClock(String name) {
		SadClock clock = new SadClock(name);
		put(clock);
		return clock;
	}
	
	@Override
	public SadClock getClock(String name) {
		return (SadClock) get(SadClock.class, name);
	}
	
	@Override
	public void deleteClock(String name) {
		delete(SadClock.class, name);
	}
	
	@Override
	public SadAction createAction(String name, SadActionLogic logic) {
		SadAction action = new SadAction(name, logic);
		put(action);
		return action;
	}
	
	@Override
	public SadAction getAction(String name) {
		return (SadAction) get(SadAction.class, name);
	}
	
	@Override
	public void deleteAction(String name) {
		delete(SadAction.class, name);
	}
}
