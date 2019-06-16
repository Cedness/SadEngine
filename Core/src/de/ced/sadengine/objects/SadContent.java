package de.ced.sadengine.objects;

/**
 * Contains all the stuff processed by the engine.
 * Objects in here won't be processed directly, they first have to be added to the SadWindi instance.
 */
public class SadContent {
	
	/*
	private float interval;
	private final HashMap<Class<? extends SadObject>, HashMap<String, SadObject>> contents = new HashMap<>();
	
	private final SadActionHandler actionHandler;
	private final SadClockwork clockwork;
	
	SadContent(Sadness sadness) {
		contents.put(SadEntity.class, new HashMap<>());
		contents.put(SadBody.class, new HashMap<>());
		contents.put(SadModel.class, new HashMap<>());
		contents.put(SadOBJMesh.class, new HashMap<>());
		contents.put(SadTexture.class, new HashMap<>());
		contents.put(SadLevel.class, new HashMap<>());
		contents.put(SadCamera.class, new HashMap<>());
		contents.put(SadFrame.class, new HashMap<>());
		contents.put(SadTransformer.class, new HashMap<>());
		contents.put(SadClock.class, new HashMap<>());
		contents.put(SadAction.class, new HashMap<>());
		
		actionHandler = new SadActionHandler(contents.get(SadAction.class), sadness);
		clockwork = new SadClockwork(contents.get(SadClock.class));
	}
	
	void update(float secInterval) {
		interval = secInterval;
		
		clockwork.increaseClocks(interval);
	}
	
	//Basic metods
	
	private void put(SadObject object) {
		String name = object.getName();
		if (name == null || name.isEmpty())
			return;
		for (HashMap<String, SadObject> map : contents.values()) {
			map.remove(name);
		}
		contents.get(object.getClass()).put(name, object);
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
	
	//Internal methods
	
	SadActionHandler getActionHandler() {
		return actionHandler;
	}
	
	Collection<SadObject> getLevels() {
		return contents.get(SadLevel.class).values();
	}
	
	Collection<SadObject> getCameras() {
		return contents.get(SadCamera.class).values();
	}
	
	Collection<SadObject> getTransformers() {
		return contents.get(SadTransformer.class).values();
	}
	
	//Individual methods
	
	//@Override
	public float getInterval() {
		return interval;
	}
	
	@Override
	public SadPositionable getPositionable(String name) {
		for (Class<? extends SadObject> clazz : contents.keySet()) {
			if (SadPositionable.class.isAssignableFrom(clazz)) {
				SadObject object = contents.get(clazz).getOrDefault(name, null);
				if (object != null)
					return (SadPositionable) object;
			}
		}
		return null;
	}
	
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
	public SadOBJMesh createMesh(String name, File file) {
		SadOBJMesh mesh = SadGL.loadMesh(name, file);
		if (mesh == null)
			return null;
		put(mesh);
		return mesh;
	}
	
	@Override
	public SadOBJMesh getMesh(String name) {
		return (SadOBJMesh) get(SadOBJMesh.class, name);
	}
	
	@Override
	public void deleteMesh(String name) {
		delete(SadOBJMesh.class, name);
	}
	
	@Override
	public SadTexture createTexture(String name, File file) {
		deleteFrame(name);
		SadTexture texture = SadGL.loadTexture(name, file);
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
	
	@Deprecated
	@Override
	public SadFont createFont(String name, File atlas, File data) {
		SadFont font = new SadFont(name, atlas, data);
		put(font);
		return font;
	}
	
	@Deprecated
	@Override
	public SadFont getFont(String name) {
		return (SadFont) get(SadFont.class, name);
	}
	
	@Deprecated
	@Override
	public void deleteFont(String name) {
		delete(SadFont.class, name);
	}
	
	@Override
	public SadBody createHitbox(String name) {
		SadBody hitbox = new SadBody(name);
		put(hitbox);
		return hitbox;
	}
	
	@Override
	public SadBody getBody(String name) {
		return (SadBody) get(SadBody.class, name);
	}
	
	@Override
	public void deleteHitbox(String name) {
		delete(SadBody.class, name);
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
	public SadFrame createFrame(String name, int getWidth, int getHeight) {
		deleteTexture(name);
		SadFrame frame = new SadFrame(name, this, SadGL.createFrameBuffer(), SadGL.createTextureAttachment(getWidth, getHeight),
				SadGL.createDepthBufferAttachment(getWidth, getHeight), getWidth, getHeight);
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
	public SadTransformer createTransformer(String name) {
		SadTransformer transformer = new SadTransformer(name, this);
		put(transformer);
		return transformer;
	}
	
	@Override
	public SadTransformer getTransformer(String name) {
		return (SadTransformer) get(SadTransformer.class, name);
	}
	
	@Override
	public void deleteTransformer(String name) {
		delete(SadTransformer.class, name);
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
	*/
}
