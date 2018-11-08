package de.ced.sadengine.trash;

import org.joml.Vector3f;

public class Position extends Vector3f {

	public void increase(float x, float y, float z) {
		this.x += x;
		this.y += y;
		this.z += z;
	}
}
