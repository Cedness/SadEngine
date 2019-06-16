package de.ced.sadengine.objects;

import de.ced.sadengine.utils.SadVector;

import static de.ced.sadengine.utils.SadValue.toRadians;

class SadBody extends SadObject {
	
	private float radius = 0;
	private SadVector v1;
	private SadVector v2;
	
	private void iterateMesh(SadOBJMesh mesh, SadVector pos, SadVector rot, SadVector scale) {
		float[] positions = mesh.getPositions();
		SadVector currentPosition = new SadVector(3);
		for (int i = 0; i < positions.length; i += 3) {
			currentPosition.set(positions[i], positions[i + 1], positions[i + 2]);
			for (int j = 0; j < 3; j++) {
				currentPosition.rot(j, toRadians(rot.get(j)));
			}
			currentPosition.mul(scale);
			currentPosition.add(pos);
		}
	}
	
	
	private void subModels(SadModel model) {
		for (SadModel subModel : model.getModels()) {
			if (subModel == null)
				continue;
			SadOBJMesh mesh = subModel.getMesh();
			if (mesh == null)
				continue;
			
			
		}
	}
}
