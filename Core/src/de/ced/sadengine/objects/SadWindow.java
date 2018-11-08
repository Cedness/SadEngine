package de.ced.sadengine.objects;

import de.ced.sadengine.main.SadContent;
import de.ced.sadengine.utils.SadVector3;

public class SadWindow extends SadFrame {
	
	public SadWindow(SadContent content) {
		super("baseFrame", content);
	}
	
	@Override
	@Deprecated
	public SadVector3 getPosition() {
		position.set(0, 0, 0);
		return super.getPosition();
	}
	
	@Override
	@Deprecated
	public SadVector3 getRotation() {
		rotation.set(0, 0, 0);
		return super.getRotation();
	}
	
	@Override
	@Deprecated
	public SadVector3 getScale() {
		scale.set(1, 1, 1);
		return super.getScale();
	}
}
