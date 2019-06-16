package de.ced.sadengine.objects.light;

import de.ced.sadengine.utils.SadVector;

@Deprecated
public class SadLight {
	
	private SadVector color;
	private SadVector position;
	
	public SadLight(SadVector color, SadVector position) {
		this.color = color;
		this.position = position;
	}
	
	public SadVector getColor() {
		return color;
	}
	
	public void setColor(SadVector color) {
		this.color = color;
	}
	
	public SadVector getPosition() {
		return position;
	}
	
	public void setPosition(SadVector position) {
		this.position = position;
	}
}
