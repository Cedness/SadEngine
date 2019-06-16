package de.ced.sadengine.test;

import de.ced.sadengine.objects.*;
import de.ced.sadengine.utils.SadVector;

import java.io.File;

import static de.ced.sadengine.utils.SadValue.*;

public class Main extends SadEngine {
	
	private int x = 0;
	private SadFrame window, frame;
	private SadCamera camera, camera2;
	private SadLevel level, level2;
	private SadEntity portalEntity, dragonEntity, twoEntity;
	
	@Override
	public void setup() {
		camera = new SadCamera();
		setCamera(camera);
		level = new SadLevel();
		camera.setLevel(level);
		camera.getPosition().set(0, 0, -5);
		
		frame = new SadFrame(1280, 720);
		camera2 = new SadCamera();
		frame.setCamera(camera2);
		frame.getColor().set(0.4f, 0.4f, 0.4f, 1f);
		level2 = new SadLevel();
		camera2.setLevel(level2);
		camera.setLookingAtPosition(false);
		camera.setDistanceToPosition(10);
		camera2.getPosition().set(0, 0, -5);
		
		
		SadTexture wood = new SadTexture(new File("./Core/res/textures/Wood.jpg"));
		SadOBJMesh rect = new SadOBJMesh(new File("./Core/res/models/Rect.obj"));
		SadOBJMesh dragon = new SadOBJMesh(new File("./Core/res/models/dragon.obj"));
		SadModel dragonModel = new SadModel();
		dragonModel.setMesh(dragon).getScale().setLength(2f).set(4f, 2f, 2f);
		dragonEntity = new SadEntity();
		dragonEntity.setModel(dragonModel).getPosition().z(3f);
		
		SadModel portalModel = new SadModel().setMesh(rect).setTexture(frame).setRenderBack(true);
		portalEntity = new SadEntity().setModel(portalModel);
		portalEntity.getRotation().z(180f);
		portalEntity.getScale().set(16, 9, 0).setLength(12);
		
		SadModel twoModel = new SadModel().setTexture(wood).setMesh(rect);
		twoEntity = new SadEntity().setModel(twoModel);
		twoEntity.getPosition().set(0f, -1f, -2f);
		level.addEntity(twoEntity);
		
		level2.addEntity(portalEntity);
		level2.addEntity(dragonEntity);
		
		level.addEntity(portalEntity);
	}
	
	@Override
	public void update() {
		dragonEntity.getRotation().addY(1f);
		twoEntity.getRotation().addY(1f);
		
		//c.getEntity("Portal").getRotation().add(1);
		
		//c.getCamera("camera2").getRotation().set(c.getEntity("Portal").getRotation()).invert();
		
		if (getInput().isJustReleased(KEY_O))
			camera.setOrtho(!camera.isOrtho());
		
		if (getInput().isJustReleased(KEY_T))
			camera.setLookingAtPosition(!camera.isLookingAtPosition());
		
		SadVector r = portalEntity.getRotation();
		float vP = (getInput().isPressed(KEY_KP_0) ? 30f : 10f) * getInterval();
		
		if (getInput().isPressed(KEY_KP_1))
			r.addX(-vP);
		if (getInput().isPressed(KEY_KP_2))
			r.addY(-vP);
		if (getInput().isPressed(KEY_KP_3))
			r.addZ(-vP);
		if (getInput().isPressed(KEY_KP_4))
			r.addX(vP);
		if (getInput().isPressed(KEY_KP_5))
			r.addY(vP);
		if (getInput().isPressed(KEY_KP_6))
			r.addZ(vP);
		
		SadVector pC = camera.getPosition();
		SadVector dC = camera.getYawDirection().mul(0.1f);
		SadVector dCN = new SadVector(-dC.z(), dC.y(), dC.x());
		
		float vC = 15f * getInterval();
		
		if (getInput().isPressed(KEY_D))
			pC.add(dCN);
		if (getInput().isPressed(KEY_A))
			pC.add(dCN.invert());
		if (getInput().isPressed(KEY_S))
			pC.add(dC.clone().invert());
		if (getInput().isPressed(KEY_W))
			pC.add(dC);
		
		SadVector rC = camera.getRotation();
		float vrC = 40f * getInterval();
		
		if (getInput().isPressed(KEY_LEFT))
			rC.addY(vrC);
		if (getInput().isPressed(KEY_RIGHT))
			rC.addY(-vrC);
		if (getInput().isPressed(KEY_UP))
			rC.addX(vrC);
		if (getInput().isPressed(KEY_DOWN))
			rC.addX(-vrC);
		
		//camera2.getRotation().set(camera.getRotation());
		//camera2.setDistanceToPosition(camera.getPosition().clone().invert().add(portalEntity.getPosition()).getLength());
		
		
		if (x++ >= 100) {
			x = 0;
			//System.out.println(camera2.getPosition());
			System.out.println(camera.getRotation());
			System.out.println(camera.getDistanceToPosition());
		}
	}
	
	public static void main(String[] args) {
		new Main();
	}
}
