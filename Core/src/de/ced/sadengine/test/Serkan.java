package de.ced.sadengine.test;

import de.ced.sadengine.objects.SadEngine;

public class Serkan extends SadEngine {
	
	private Serkan() {
		start();
	}
	
	public static void main(String[] args) {
		new Serkan();
	}
	
	@Override
	public void setup() {
		/*
		SadContent c = sadness.getContent();
		SadWindi w = sadness.getWindow();
		SadInput i = sadness.getInput();
		
		SadCamera camera = c.createCamera("camera");
		SadLevel level = c.createLevel("level");
		
		w.setCamera(camera.getName());
		
		w.getColor().set(0.5f, 0.5f, 0.5f);
		
		camera.setLevel(level.getName());
		
		SadOBJMesh mesh = c.createMesh("mesh", new File("./Core/res/models/Rect.obj"));
		SadTexture texture = c.createTexture("texture", new File("./Core/res/textures/TwoStepsFromHell.jpg"));
		SadModel model = c.createModel("model");
		model.setMesh(mesh.getName());
		model.getColor().set(1f, 0f, 0f, 1f);
		model.setTexture(texture.getName());
		model.setRenderBack(true);
		model.getPosition().set(2, 0, 0);
		SadModel model2 = c.createModel("model2");
		model2.addModel(model.getName());
		SadEntity entity = c.createEntity("entity");
		entity.setModel(model2.getName());
		entity.getPosition().set(-3, 1, 0);
		level.addEntity(entity.getName());
		
		SadTransformer transformer = c.createTransformer("transformer");
		transformer.setPositionable(entity.getName());
		transformer.getScale().set(1.01f);
		
		camera.getPosition().z(-5);
		
		transformer.setMovement(SadMovement.DISABLED);
		*/
	}
	
	@Override
	public void update() {
		
		//System.out.println(c.getEntity("entity").getRotation());
	}
}
