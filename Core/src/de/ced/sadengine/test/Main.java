package de.ced.sadengine.test;

import de.ced.sadengine.objects.SadEngine;
import de.ced.sadengine.objects.Sadness;

public class Main extends SadEngine {
	
	private int x = 0;
	
	private Main() {
		start();
	}
	
	@Override
	public void setup(Sadness sadness) {
		/*
		SadContent c = sadness.getContent();
		SadWindow window = sadness.getWindow();
		c.createCamera("camera");
		window.setCamera("camera");
		c.createLevel("level");
		c.getCamera("camera").setLevel("level");
		c.getCamera("camera").getPosition().set(0, 0, -5);
		
		c.createFrame("frame", 1280, 720);
		c.createCamera("camera2");
		c.getFrame("frame").setCamera("camera2");
		c.getFrame("frame").getColor().set(0.4f, 0.4f, 0.4f, 1f);
		c.createLevel("level2");
		c.getCamera("camera2").setLevel("level2");
		c.getCamera("camera").setLookingAtPosition(false);
		c.getCamera("camera").setDistanceToPosition(10);
		c.getCamera("camera2").getPosition().set(0, 0, -5);
		
		
		c.createTexture("CubeT", new File("./Core/res/textures/Wood.jpg"));
		c.createMesh("CubeM", new File("./Core/res/models/Rect.obj"));
		c.createMesh("mesh", new File("./Core/res/models/dragon.obj"));
		c.createModel("CubeMo").setMesh("CubeM").getScale().setLength(2f).set(4f, 2f, 2f);
		c.createEntity("Cube").setModel("CubeMo").getPosition().z(3);
		
		c.createModel("PortalM").setMesh("CubeM").setTexture("frame");
		c.createEntity("Portal").setModel("PortalM");
		c.getEntity("Portal").getRotation().z(180);
		c.getEntity("Portal").getModel().setRenderBack(true);
		c.getEntity("Portal").getScale().set(16, 9, 0).setLength(12);
		
		c.createModel("TwoStepsModel").setTexture("CubeT").setMesh("CubeM");
		c.createEntity("TwoSteps").setModel("TwoStepsModel").getPosition().set(0, -1, -2);
		c.getLevel("level").addEntity("TwoSteps");
		
		c.getLevel("level2").addEntity("Portal");
		c.getLevel("level2").addEntity("Cube");
		
		c.getLevel("level").addEntity("Portal");
		*/
	}
	
	@Override
	public void update(Sadness sadness) {
		/*
		SadContent c = sadness.getContent();
		SadInput i = sadness.getInput();
		
		c.getEntity("Cube").getRotation().addY(1f);
		c.getEntity("TwoSteps").getRotation().addY(1f);
		
		//c.getEntity("Portal").getRotation().add(1);
		
		//c.getCamera("camera2").getRotation().set(c.getEntity("Portal").getRotation()).negate();
		
		if (i.isJustReleased(KEY_O))
			c.getCamera("camera").setOrtho(!c.getCamera("camera").isOrtho());
		
		if (i.isJustReleased(KEY_T))
			c.getCamera("camera").setLookingAtPosition(!c.getCamera("camera").isLookingAtPosition());
		
		SadVector r = c.getEntity("Portal").getRotation();
		float vP = (i.isPressed(KEY_KP_0) ? 30f : 10f) * c.getInterval();
		
		if (i.isPressed(KEY_KP_1))
			r.addX(-vP);
		if (i.isPressed(KEY_KP_2))
			r.addY(-vP);
		if (i.isPressed(KEY_KP_3))
			r.addZ(-vP);
		if (i.isPressed(KEY_KP_4))
			r.addX(vP);
		if (i.isPressed(KEY_KP_5))
			r.addY(vP);
		if (i.isPressed(KEY_KP_6))
			r.addZ(vP);
		
		SadCamera camera = c.getCamera("camera");
		SadVector pC = camera.getPosition();
		SadVector dC = camera.getYawDirection().mul(0.1f);
		SadVector dCN = new SadVector(-dC.z(), dC.y(), dC.x());
		
		float vC = 15f * c.getInterval();
		
		if (i.isPressed(KEY_D))
			pC.add(dCN);
		if (i.isPressed(KEY_A))
			pC.add(dCN.negate());
		if (i.isPressed(KEY_S))
			pC.add(dC.clone().negate());
		if (i.isPressed(KEY_W))
			pC.add(dC);
		
		SadVector rC = camera.getRotation();
		float vrC = 40f * c.getInterval();
		
		if (i.isPressed(KEY_LEFT))
			rC.addY(vrC);
		if (i.isPressed(KEY_RIGHT))
			rC.addY(-vrC);
		if (i.isPressed(KEY_UP))
			rC.addX(vrC);
		if (i.isPressed(KEY_DOWN))
			rC.addX(-vrC);
		
		SadCamera camera2 = c.getCamera("camera2");
		SadEntity portal = c.getEntity("Portal");
		//camera2.getRotation().set(camera.getRotation());
		//camera2.setDistanceToPosition(camera.getPosition().clone().negate().add(portal.getPosition()).getLength());
		
		
		if (x++ >= 100) {
			x = 0;
			//System.out.println(camera2.getPosition());
			System.out.println(camera.getRotation());
			System.out.println(camera.getDistanceToPosition());
		}
		*/
	}
	
	public static void main(String[] args) {
		new Main();
	}
}
