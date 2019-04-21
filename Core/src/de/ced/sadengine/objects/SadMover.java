package de.ced.sadengine.objects;

import java.util.ArrayList;
import java.util.List;

public class SadMover {
	
	private List<SadPositionable> positionables = new ArrayList<>();
	private List<SadLevel> levels = new ArrayList<>();
	private List<SadPositionable> done = new ArrayList<>();
	private float interval;
	
	SadMover() {
	}
	
	public void add(SadPositionable positionable) {
		if (positionables.contains(positionable))
			return;
		positionables.add(positionable);
	}
	
	public void remove(SadPositionable positionable) {
		positionables.remove(positionable);
	}
	
	public boolean contains(SadPositionable positionable) {
		return positionables.contains(positionable);
	}
	
	public void add(SadLevel level) {
		if (levels.contains(level))
			return;
		levels.add(level);
	}
	
	public void remove(SadLevel level) {
		levels.remove(level);
	}
	
	public boolean contains(SadLevel level) {
		return levels.contains(level);
	}
	
	public void clear() {
		positionables = new ArrayList<>();
		levels = new ArrayList<>();
	}
	
	void invoke(float interval) {
		this.interval = interval;
		done = new ArrayList<>();
		for (SadPositionable positionable : positionables) {
			movePositionable(positionable);
		}
		for (SadLevel level : levels) {
			moveLevel(level);
		}
	}
	
	private void moveLevel(SadLevel level) {
		for (SadEntity entity : level.getEntities()) {
			checkMovePositionable(entity);
		}
		for (SadCamera camera : level.getCameras()) {
			checkMovePositionable(camera);
		}
	}
	
	private void checkMovePositionable(SadPositionable positionable) {
		if (done.contains(positionable))
			return;
		movePositionable(positionable);
	}
	
	private void movePositionable(SadPositionable positionable) {
		SadPositionable velocity = positionable.getVelocity();
		if (velocity == null)
			return;
		movePositionable(velocity);
		positionable.getPosition().add(velocity.getPosition().clone().mul(interval));
		positionable.getRotation().add(velocity.getRotation().clone().mul(interval));
		positionable.getScale().add(velocity.getScale().clone().mul(interval));
		done.add(positionable);
	}
	
	/*void invoke_OLD(float interval) {
		this.interval = interval;
		displayedPositionables = new ArrayList<>();
		enabledPositionables = new ArrayList<>();
		done = new ArrayList<>();
		
		iterateFrame(window);
		
		for (SadObject object : content.getCameras()) {
			iterateOffscreenCamera((SadCamera) object);
		}
		for (SadObject object : content.getLevels()) {
			iterateOffscreenLevel((SadLevel) object);
		}
		
		for (SadObject object : content.getTransformers()) {
			move((SadTransformer) object);
		}
		
	}
	
	private void iterateOffscreenLevel(SadLevel level) {
		if (level == null || done.contains(level))
			return;
		done.add(level);
		
		if (level.getMovement() != ENABLED)
			return;
		
		for (SadModel model : level.getIndex().keySet()) {
			enabledPositionables.addAll(level.getIndex().get(model));
			iterateModel(model, true);
		}
	}
	
	private void iterateOffscreenCamera(SadCamera camera) {
		if (camera == null || done.contains(camera))
			return;
		done.add(camera);
		
		SadLevel level = camera.getLevel();
		if (level == null)
			return;
		
		if (level.getMovement() != ENABLED)
			return;
		
		enabledPositionables.add(camera);
		
		if (done.contains(level))
			return;
		done.add(level);
		
		for (SadModel model : level.getIndex().keySet()) {
			enabledPositionables.addAll(level.getIndex().get(model));
			iterateModel(model, true);
		}
	}
	
	private void iterateFrame(SadFrame frame) {
		if (frame == null || done.contains(frame))
			return;
		done.add(frame);
		
		SadCamera camera = frame.getCamera();
		if (camera == null || done.contains(camera))
			return;
		done.add(camera);
		
		SadLevel level = camera.getLevel();
		if (level == null)
			return;
		
		SadMovement movement = level.getMovement();
		if (movement == DISABLED)
			return;
		boolean enabled = movement == ENABLED;
		
		if (enabled) enabledPositionables.add(camera);
		else displayedPositionables.add(camera);
		
		if (done.contains(level))
			return;
		done.add(level);
		
		for (SadModel model : level.getIndex().keySet()) {
			if (enabled) enabledPositionables.addAll(level.getIndex().get(model));
			else displayedPositionables.addAll(level.getIndex().get(model));
			iterateModel(model, enabled);
		}
	}
	
	private void iterateModel(SadModel model, boolean enabled) {
		if (model == null || done.contains(model))
			return;
		done.add(model);
		if (enabled) enabledPositionables.add(model);
		else displayedPositionables.add(model);
		model.getModels().forEach(subModel -> iterateModel(subModel, enabled));
		
		SadTexture texture = model.getTexture();
		if (texture instanceof SadFrame)
			iterateFrame((SadFrame) texture);
	}
	
	private void move(SadTransformer transformer) {
		SadPositionable positionable = transformer.getPositionable();
		if (positionable == null)
			return;
		boolean displayed = displayedPositionables.contains(positionable);
		boolean enabled = enabledPositionables.contains(positionable);
		SadMovement movement = transformer.getMovement();
		if (movement == DISABLED || !(displayed || enabled || movement == ENABLED))
			return;
		positionable.getPosition().add(transformer.getPosition().clone().mul(interval));
		positionable.getRotation().add(transformer.getRotation().clone().mul(interval));
		positionable.getScale().mul(transformer.getScale().clone());
	}*/
}
