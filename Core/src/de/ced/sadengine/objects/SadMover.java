package de.ced.sadengine.objects;

import de.ced.sadengine.objects.input.SadInput;

import java.util.ArrayList;

import static de.ced.sadengine.objects.SadMovement.DISABLED;
import static de.ced.sadengine.objects.SadMovement.ENABLED;

class SadMover extends SadProcessor {
	
	private ArrayList<SadPositionable> displayedPositionables;
	private ArrayList<SadPositionable> enabledPositionables;
	private ArrayList<SadObject> done;
	private float secInterval;
	
	SadMover(SadFrame window, SadContent content, SadInput input, SadEngine engine) {
		super(window, content, input, engine);
	}
	
	@Override
	void invoke() {
		this.secInterval = content.getInterval();
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
		positionable.getPosition().add(transformer.getPosition().clone().mul(secInterval));
		positionable.getRotation().add(transformer.getRotation().clone().mul(secInterval));
		positionable.getScale().mul(transformer.getScale().clone());
	}
}
