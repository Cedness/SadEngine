package de.ced.sadengine.test;

import de.ced.sadengine.objects.*;

import static de.ced.sadengine.utils.SadValue.KEY_F4;
import static de.ced.sadengine.utils.SadValue.KEY_MENU;

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
		SadOBJMesh rect = new SadOBJMesh("Core/res/models/Cube.obj");
		SadEntity entity = new SadEntity();
		SadModel model = new SadModel();
		entity.setModel(model);
		model.setMesh(rect);
		SadTexture texture = new SadTexture("Core/res/textures/TwoStepsFromHell.jpg");
		model.setTexture(texture);
		model.getColor().set(0f, 1f, 1f);
		camera.getPosition().z(-5f);
		camera.getRotation().set(0f, 180f, 190f);
		camera.setVelocityEnabled(true);
		level.addEntity(entity);
		getColor().set(1f, 0.7f, 1f);
		entity.getRotation().y(-40f);
		//entity.getScale().set(4f);
		entity.setVelocityEnabled(true);
		//entity.getVelocity().getScale().set(0.1f);
		getMover().add(entity);
		entity.getVelocity().setVelocityEnabled(true);
		entity.getVelocity().getVelocity().getScale().set(0.03f);
		getMover().add(entity.getVelocity());
		model.setRenderBack(true);
		entity.getVelocity().getRotation().y(10.5f);
		//getInput().getCursor().setHidden(true);
		
		camera.getVelocity().setVelocityEnabled(true);
		camera.getVelocity().getVelocity().setVelocityEnabled(true);
		camera.getVelocity().getVelocity().getVelocity().getRotation().z(0.2f);
		getMover().add(camera);
		getMover().add(camera.getVelocity());
		getMover().add(camera.getVelocity().getVelocity());
		//getActionHandler().add(new SadAction(new CameraControl(getCamera(), getInput(), getInterval())));
		
		
	}
	
	@Override
	public void update() {
		//System.out.println(getWidth() + " " + getHeight());
		if (getInput().isPressed(KEY_MENU) && getInput().isPressed(KEY_F4))
			stop();
	}
}
