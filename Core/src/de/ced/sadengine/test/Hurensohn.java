package de.ced.sadengine.test;

import de.ced.sadengine.api.SadEngine3D;
import de.ced.sadengine.input.SadInput;
import de.ced.sadengine.main.SadContent;
import de.ced.sadengine.main.SadMainLogic;
import de.ced.sadengine.main.Sadness;
import de.ced.sadengine.objects.SadEntity;
import de.ced.sadengine.objects.SadWindow;

import java.io.File;

import static de.ced.sadengine.utils.SadValue.*;

public class Hurensohn implements SadMainLogic {
	
	public void setup(Sadness sadness) {
		SadContent content = sadness.getContent();
		SadWindow window = sadness.getWindow();
		
		content.createLevel("Level");
		content.createCamera("Camera").setLevel("Level");
		window.setCamera("Camera");
		
		content.createMesh("CowMesh", new File("Cow"));
		content.createModel("CowModel").setMesh("CowMesh");
		content.createEntity("Cow").setModel("CowModel");
		
		content.getLevel("Level").addEntity("Cow");
		
		content.getEntity("Cow").getPosition().x(9f);
		
		content.getCamera("Camera").getPosition().set(0, 0, -10);
		//camera.getRotation().x -= 30f;
	}
	
	public void update(Sadness sadness) {
		SadContent content = sadness.getContent();
		SadInput input = sadness.getInput();
		
		SadEntity cow = content.getEntity("Cow");
		if (input.isPressed(KEY_LEFT)) {
			cow.getRotation().add(0, -content.getInterval() * 30f, 0);
		}
		
		if (input.isPressed(KEY_RIGHT)) {
			cow.getRotation().add(0, content.getInterval() * 30f, 0);
		}
		
		if (input.isPressed(KEY_UP)) {
			cow.getScale().mul(1.01f);
		}
		
		if (input.isPressed(KEY_DOWN)) {
			cow.getScale().mul(0.99f);
		}
	}
	
	public void terminate(Sadness sadness) {
	
	}
	
	public static void main(String[] args) {
		SadEngine3D.start(new Hurensohn());
	}
}