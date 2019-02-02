package de.ced.sadengine.test;

import de.ced.sadengine.input.SadInput;
import de.ced.sadengine.main.SadContent;
import de.ced.sadengine.main.Sadness;
import de.ced.sadengine.objects.SadEntity;
import de.ced.sadengine.objects.SadMesh;
import de.ced.sadengine.objects.SadModel;
import de.ced.sadengine.objects.action.SadActionLogic;
import de.ced.sadengine.utils.SadVector3;

import java.io.File;

import static de.ced.sadengine.utils.SadValue.*;

public class CubeX implements SadActionLogic {
	
	@Override
	public void setup(Sadness sadness) {
		SadContent content = sadness.getContent();
		System.out.println("LUUUUUUUL");
		SadMesh mesh = content.createMesh("CubeX", new File("./Core/res/models/Cube.obj"));
		SadModel model = content.createModel("CubeX").setMesh("CubeX");
		SadEntity entity = content.createEntity("CubeX").setModel("CubeX");
		entity.getPosition().set(0, 0.4f, 5);
		content.getLevel("Level").addEntity(entity.getName());
		
		content.createClock("CubeX").setGoal(1000 * MILLI * SECOND).removeGoal();
	}
	
	@Override
	public void update(Sadness sadness) {
		SadContent content = sadness.getContent();
		SadInput input = sadness.getInput();
		
		SadEntity entity = content.getEntity("CubeX");
		System.out.println("lol");
		float speed = 3f;
		SadVector3 pos = entity.getPosition();
		if (input.isPressed(KEY_LEFT)) {
			pos.x(pos.x() + content.getInterval() * speed);
		}
		if (input.isPressed(KEY_RIGHT)) {
			pos.x(pos.x() - content.getInterval() * speed);
		}
		if (input.isPressed(KEY_DOWN)) {
			pos.z(pos.z() - content.getInterval() * speed);
		}
		if (input.isPressed(KEY_UP)) {
			pos.z(pos.z() + content.getInterval() * speed);
		}
		if (input.isPressed(KEY_SLASH)) {
			pos.y(pos.y() - content.getInterval() * speed);
		}
		if (input.isPressed(KEY_RIGHT_SHIFT)) {
			pos.y(pos.y() + content.getInterval() * speed);
		}
		
		/*if (content.getClock("CubeX").hasReachedGoal())
			content.getAction("CubeX").setRunning(false);*/
	}
}
