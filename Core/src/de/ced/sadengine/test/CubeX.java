package de.ced.sadengine.test;

import de.ced.sadengine.main.SadContent;
import de.ced.sadengine.main.Sadness;
import de.ced.sadengine.objects.SadEntity;
import de.ced.sadengine.objects.action.SadActionLogic;

import static de.ced.sadengine.utils.SadValue.MILLI;
import static de.ced.sadengine.utils.SadValue.SECOND;

public class CubeX implements SadActionLogic {
	
	@Override
	public void setup(Sadness sadness) {
		SadContent content = sadness.getContent();
		
		SadEntity entity = content.createEntity("CubeX").setModel("Cube");
		entity.getPosition().y(1);
		content.getLevel("Level").addEntity(entity.getName());
		
		content.createClock("CubeX").setGoal(1000 * MILLI * SECOND).removeGoal();
	}
	
	@Override
	public void update(Sadness sadness) {
		SadContent content = sadness.getContent();
		
		SadEntity entity = content.getEntity("CubeX");
		
		entity.getRotation().addZ(2);
		
		if (content.getClock("CubeX").hasReachedGoal())
			content.getAction("CubeX").setRunning(false);
	}
}
