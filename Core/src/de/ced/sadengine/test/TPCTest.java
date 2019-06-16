package de.ced.sadengine.test;

import de.ced.sadengine.objects.SadEngine;

public class TPCTest extends SadEngine {
	
	public TPCTest() {
		start();
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
		camera.setLevel(level.getName());
		camera.getPosition().set(0f, 0f, -10f);
		SadEntity entity = c.createEntity("entity");
		SadOBJMesh rect = c.createMesh("rect", new File("./Core/res/models/Rect.obj"));
		SadTexture wood = c.createTexture("wood", new File("./Core/res/textures/Wood.jpg"));
		SadModel model = c.createModel("model");
		model.setMesh(rect.getName()).setTexture(wood.getName());
		entity.setModel(model.getName());
		*/
	}
	
	public static void main(String[] args) {
		new TPCTest();
	}
}
