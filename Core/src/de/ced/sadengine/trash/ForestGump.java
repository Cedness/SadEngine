package de.ced.sadengine.trash;

import de.ced.sadengine.main.SadContent;
import de.ced.sadengine.main.SadMainLogic;
import de.ced.sadengine.main.Sadness;

public class ForestGump implements SadMainLogic {
	
	public ForestGump() {
		Forest forest = new Forest(40, 10);
		forest.letTreesDie(4, true);
		forest.letTreesDie(5, true);
		forest.letTreesDie(6, false);
		forest.letTreesDie(7, false);
		forest.letTreesDie(8, false);
		forest.letTreesDie(9, false);
		forest.letTreesDie(10, false);
		System.out.println(forest.toString());
	}
	
	@Override
	public void setup(Sadness sadness) {
		SadContent content = sadness.getContent();
		/*
		content.createTexture("Baum", new File("./Core/res/textures/Baum.jpg"));
		content.createTexture("Cube", new File("./Core/res/textures/Wood.jpg"));
		content.createMesh("Cube", new File("./Core/res/models/Rect.obj"));
		content.createModel("Baum").setMesh("Cube").setTexture("Baum");
		
		SadLevel level = content.createLevel("Level");
		
		SadCamera camera = content.createCamera("Camera");
		camera.getPosition().z(-5);
		camera.getScale().set(0.3f);
		camera.setOrtho(true);
		camera.setLevel("Level");
		content.createFrame("Frame", width, height).setCamera("Camera");
		sadness.getWindow().addFrame("Frame");
		
		Forest forest = new Forest(10, 8);
		
		for (int i = 0; i < forest.getWidth(); i++) {
			for (int j = 0; j < forest.getHeight(); j++) {
				SadEntity entity = content.createEntity("Baum" + i + j).setModel("Baum");
				entity.getPosition().set(i - 4.5f, j - 4.5f, 0);
				level.addEntity(entity.getName());
			}
		}*/
	}
	
	@Override
	public void update(Sadness sadness) {
		
	}
	
	public static void main(String[] args) {
		new ForestGump();
	}
}
