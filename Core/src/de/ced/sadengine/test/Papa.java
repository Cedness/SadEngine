package de.ced.sadengine.test;

import de.ced.sadengine.objects.*;

public class Papa extends SadEngine {
	
	public static void main(String[] args) {
		new Papa();
	}
	
	private Papa() {
		super(0);
		setName("Dennis");
		setFullscreen(true);
		setWidth(1920);
		setHeight(1080);
		start();
	}
	
	@Override
	public void setup() {
		SadCamera camera = new SadCamera();
		setCamera(camera);
		SadLevel level = new SadLevel();
		camera.setLevel(level);
		SadMesh mesh = new SadMesh("./Core/res/models/Rect.obj");
		SadEntity entity = new SadEntity();
		SadModel model = new SadModel();
		entity.setModel(model);
		model.setMesh(mesh);
		SadTexture texture = new SadTexture("./Core/res/textures/images/mauer.gif");
		model.setTexture(texture);
		model.getColor().set(0.1f);
		camera.getPosition().z(-5f);
		level.addEntity(entity);
		getColor().set(1f, 0.7f, 1f);
		entity.getRotation().y(-40f);
		entity.getScale().set(1f);
		entity.setVelocityEnabled(true);
		//entity.getVelocity().getScale().set(0.1f);
		getMover().add(entity);
		entity.getVelocity().setVelocityEnabled(true);
		//entity.getVelocity().getVelocity().getScale().set(0.1f);
		getMover().add(entity.getVelocity());
		model.setRenderBack(true);
		entity.getVelocity().getRotation().y(10.5f);
		getInput().getCursor().setHidden(true);
	}
	
	@Override
	public void update() {
		//System.out.println(getWidth() + " " + getHeight());
	}
}
