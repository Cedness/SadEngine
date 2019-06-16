package de.ced.sadengine.graphics.control;

import de.ced.sadengine.objects.input.SadInput;
import de.ced.sadengine.objects.SadCamera;
import de.ced.sadengine.trash.SadContent;

public abstract class SadCameraControl {
	
	protected SadCamera camera = null;
	
	public SadCameraControl() {
	}
	
	public SadCameraControl(SadCamera camera) {
		this.camera = camera;
	}
	
	public SadCamera getCamera() {
		return camera;
	}
	
	public void setCamera(SadCamera camera) {
		this.camera = camera;
	}
	
	public abstract void move(SadContent content, SadInput input);
}
