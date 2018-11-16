package de.ced.sadengine.test;

import de.ced.sadengine.api.SadEngine3D;
import de.ced.sadengine.input.SadInput;
import de.ced.sadengine.main.SadContent;
import de.ced.sadengine.main.SadMainLogic;
import de.ced.sadengine.main.Sadness;
import de.ced.sadengine.objects.SadCamera;
import de.ced.sadengine.objects.SadEntity;
import de.ced.sadengine.utils.SadVector3;

import java.io.File;

import static de.ced.sadengine.utils.SadValue.*;

public class Cube implements SadMainLogic {
	
	public static void main(String[] args) {
		SadEngine3D.start(new Cube());
	}
	
	@Override
	public void setup(Sadness sadness) {
		SadContent content = sadness.getContent();
		
		
		content.createTexture("Cube", new File("./Core/res/textures/Wood.jpg"));
		
		content.createMesh("Cube", new File("./Core/res/models/Rect.obj"));
		content.createModel("Cube").setMesh("Cube").setTexture("Cube");
		content.createEntity("Cube").setModel("Cube");
		
		content.createLevel("Level").addEntity("Cube");
		SadCamera camera = content.createCamera("Camera").setLevel("Level");
		
		camera.getPosition().set(0, 0, -2.8f);
		
		//sadness.getWindow().setCamera("Camera");
		
		
		float scale = 0.5f;
		content.createFrame("Frame").setCamera("Camera").getScale().mul(scale);
		content.createFrame("FrameL").setCamera("Camera").getScale().mul(scale);
		content.createFrame("FrameR").setCamera("Camera").getScale().mul(scale);
		float offset = 4f;
		content.getFrame("FrameL").getRotation().z(-90);
		content.getFrame("FrameL").getPosition().x(-offset);
		content.getFrame("FrameR").getRotation().z(90);
		content.getFrame("FrameR").getPosition().x(offset);
		
		camera.setOrtho(true);
		camera.getScale().set(scale);
		content.getFrame("Frame").getPosition().x(0);
		sadness.getWindow().addFrame("Frame");
		//sadness.getWindow().addFrame("FrameL");
		//sadness.getWindow().addFrame("FrameR");
		
		
		sadness.getInput().getCursor().setHidden(true);
		sadness.getInput().getCursor().setLocked(true);
		sadness.getInput().getCursor().getPosition().set(0, 0, 0);
		
		content.createAction("CubeX", new CubeX());
	}
	
	@Override
	public void update(Sadness sadness) {
		SadContent content = sadness.getContent();
		SadEntity cube = content.getEntity("Cube");
		SadVector3 cubeRot = cube.getRotation();
		SadInput input = sadness.getInput();
		float interval = content.getInterval();
		float speed = 100f;
		
		//System.out.println(sadness.getContent().getPart("CubePart").getTexture().getName());
		
		if (input.isPressed(KEY_A)) {
			cubeRot.z(cubeRot.z() - interval * speed);
		}
		
		if (input.isPressed(KEY_D)) {
			cubeRot.z(cubeRot.z() + interval * speed);
		}
		
		if (input.isPressed(KEY_W)) {
			cubeRot.x(cubeRot.x() + interval * speed);
		}
		
		if (input.isPressed(KEY_S)) {
			cubeRot.x(cubeRot.x() - interval * speed);
		}
		
		cubeRot.x(input.getCursor().getPosition().y() / 4f);
		cube.getPosition().x(input.getCursor().getPosition().x() / -100f);
		
		if (input.isPressed(KEY_Q)) {
			cube.getScale().mul(0.99f);
		}
		
		if (input.isPressed(KEY_E)) {
			cube.getScale().mul(1.01f);
		}
		
		SadCamera camera = content.getCamera("Camera");
		if (input.isJustReleased(KEY_O)) {
			camera.setOrtho(!camera.isOrtho());
		}
		
		if (input.isPressed(KEY_KP_ADD)) {
			camera.setFov(camera.getFov() + 1);
		}
		
		if (input.isPressed(KEY_KP_SUBTRACT)) {
			camera.setFov(camera.getFov() - 1);
		}
		
		
		//System.out.println(content.getCamera("Camera").getCursorVector());
	}
}
