package de.ced.sadengine.objects;

import de.ced.sadengine.main.SadContent;
import de.ced.sadengine.utils.SadVector;

public class SadWindow extends SadFrame {
	
	public SadWindow(SadContent content) {
		super("baseFrame", content);
	}
	
	@Override
	@Deprecated
	public SadVector getPosition() {
		position.set(0, 0, 0);
		return super.getPosition();
	}
	
	@Override
	@Deprecated
	public SadVector getRotation() {
		rotation.set(0, 0, 0);
		return super.getRotation();
	}
	
	@Override
	@Deprecated
	public SadVector getScale() {
		scale.set(1, 1, 1);
		return super.getScale();
	}
}
