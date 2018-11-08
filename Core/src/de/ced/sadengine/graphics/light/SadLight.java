package de.ced.sadengine.graphics.light;

import de.ced.sadengine.utils.SadVector3;

@Deprecated
public class SadLight {
	
	private SadVector3 color;
	private SadVector3 position;
	
	public SadLight(SadVector3 color, SadVector3 position) {
		this.color = color;
		this.position = position;
	}
	
	public SadVector3 getColor() {
		return color;
	}
	
	public void setColor(SadVector3 color) {
		this.color = color;
	}
	
	public SadVector3 getPosition() {
		return position;
	}
	
	public void setPosition(SadVector3 position) {
		this.position = position;
	}
}
