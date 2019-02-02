package de.ced.sadengine.test;

import de.ced.sadengine.main.SadContent;
import de.ced.sadengine.main.SadEngine;
import de.ced.sadengine.main.SadMainLogic;
import de.ced.sadengine.main.Sadness;
import de.ced.sadengine.objects.SadWindow;

import java.io.File;

public class Main implements SadMainLogic {
	
	@Override
	public void setup(Sadness sadness) {
		SadContent content = sadness.getContent();
		SadWindow window = sadness.getWindow();
		content.getClearColor().set(0.5f);
		content.createCamera("camera");
		window.setCamera("camera");
		content.createLevel("level");
		content.getCamera("camera").setLevel("level");
		content.getCamera("camera").getPosition().set(0, 0, -5);
		
		content.createFrame("frame", 800, 600);
		content.createCamera("camera2");
		content.getFrame("frame").setCamera("camera2");
		content.createLevel("level2");
		content.getCamera("camera2").setLevel("level2");
		content.getCamera("camera2").getPosition().set(0, 0, -5);
		
		
		content.createTexture("Cube", new File("./Core/res/textures/Wood.jpg"));
		content.createMesh("Cube", new File("./Core/res/models/Rect.obj"));
		content.createModel("Cube").setMesh("Cube").setTexture("Cube");
		content.createEntity("Cube").setModel("Cube");
		
		content.createModel("Portal").setMesh("Cube").setTexture("frame");
		content.createEntity("Portal").setModel("Portal");
		
		content.getLevel("level2").addEntity("Cube");
		
		content.getLevel("level").addEntity("Portal");
		
	}
	
	@Override
	public void update(Sadness sadness) {
		SadContent content = sadness.getContent();
		
		content.getEntity("Cube").getPosition().addX(0.1f);
	}
	
	public static void main(String[] args) {
		new SadEngine(new Main());
	}
}
