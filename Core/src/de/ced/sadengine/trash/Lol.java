package de.ced.sadengine.trash;

import de.ced.sadengine.objects.SadCamera;
import de.ced.sadengine.utils.SadGLMatrix;
import de.ced.sadengine.utils.SadVector;

public class Lol {
	
	public static void main(String[] args) {
		SadCamera camera = new SadCamera();
		camera.getUpWorld().set(1f, 0f, 0f);
		camera.getRotation().set(0f, 0f, 90f);
		System.out.println(camera.getRotation());
		
		SadGLMatrix matrix = new SadGLMatrix();
		matrix.viewMatrix(camera, new SadVector(3), new SadVector(3), new SadVector(3));
	}
}
