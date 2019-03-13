package de.ced.sadengine.test;

import de.ced.sadengine.objects.*;
import de.ced.sadengine.objects.input.SadInput;

import java.io.File;

public class Papa extends SadEngine {
	
	public static void main(String[] args) {
		new Papa();
	}
	
	@Override
	public void setup(Sadness sadness) {
		SadContent c = sadness.getContent();
		SadFrame w = sadness.getWindow();
		SadInput i = sadness.getInput();
		
		SadCamera camera = new SadCamera();
		w.setCamera(camera);
		SadLevel level = new SadLevel();
		camera.setLevel(level);
		SadMesh mesh = new SadMesh(new File("./Core/res/models/Rect.obj"));
		SadTexture texture = new SadTexture(new File("./Core/res/textures/Wood.jpg"));
		SadEntity entity = new SadEntity();
		SadModel model = new SadModel();
		entity.setModel(model);
		model.setMesh(mesh);
		model.setTexture(texture);
		camera.getPosition().z(-5);
		level.addEntity(entity);
		w.getColor().set(0.5f);
	}
	
	@Override
	public void update(Sadness sadness) {
	
	}
}
