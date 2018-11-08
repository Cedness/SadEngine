package de.ced.sadengine.objects;

import org.joml.Vector2f;
import org.joml.Vector3f;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SadOBJLoader {
	
	public static SadMesh loadObj(String name, File file) throws FileNotFoundException, NumberFormatException {
		InputStream stream = new FileInputStream(file);
		if (stream == null)
			System.out.println("lol");
		InputStreamReader streamReader = new InputStreamReader(stream);
		BufferedReader reader = new BufferedReader(streamReader);
		
		List<Integer> indices = new ArrayList<>();
		List<Vector3f> positions = new ArrayList<>();
		List<Vector2f> textureCoordinates = new ArrayList<>();
		List<Vector3f> normals = new ArrayList<>();
		
		String line = null;
		String[] parts;
		try {
			while (reader.ready()) {
				line = reader.readLine();
				parts = line.split(" ");
				
				if (line.startsWith("v ")) {
					positions.add(new Vector3f(
							Float.parseFloat(parts[1]),
							Float.parseFloat(parts[2]),
							Float.parseFloat(parts[3])
					));
				} else if (line.startsWith("vt ")) {
					textureCoordinates.add(new Vector2f(
							Float.parseFloat(parts[1]),
							Float.parseFloat(parts[2])
					));
				} else if (line.startsWith("vn ")) {
					normals.add(new Vector3f(
							Float.parseFloat(parts[1]),
							Float.parseFloat(parts[2]),
							Float.parseFloat(parts[3])
					));
				}
			}
			
			
			boolean isTextured = textureCoordinates.size() > 0;
			boolean hasNormals = normals.size() > 0;
			
			
			float[] positionsArray = new float[positions.size() * 3];
			float[] textureCoordinatesArray = isTextured ? new float[positions.size() * 2] : null;
			float[] normalsArray = new float[positions.size() * 3];
			
			streamReader = new InputStreamReader(new FileInputStream(file));
			reader = new BufferedReader(streamReader);
			
			while (reader.ready()) {
				line = reader.readLine();
				if (!line.startsWith("f"))
					continue;
				
				parts = line.split(" ");
				
				for (int i = 1; i < parts.length; i++) {
					String[] part = parts[i].split("/");
					
					int index = Integer.parseInt(part[0]) - 1;
					indices.add(index);
					
					if (isTextured) {
						Vector2f textureCoordinate = textureCoordinates.get(Integer.parseInt(part[1]) - 1);
						textureCoordinatesArray[index * 2] = textureCoordinate.x;
						textureCoordinatesArray[index * 2 + 1] = textureCoordinate.y;
					}
					
					if (hasNormals) {
						Vector3f normal = normals.get(Integer.parseInt(part[2]) - 1);
						normalsArray[index * 3] = normal.x;
						normalsArray[index * 3 + 1] = normal.y;
						normalsArray[index * 3 + 2] = normal.z;
					}
				}
			}
			
			reader.close();
			
			int[] indicesArray = new int[indices.size()];
			
			for (int i = 0; i < positions.size(); i++) {
				positionsArray[i * 3] = positions.get(i).x;
				positionsArray[i * 3 + 1] = positions.get(i).y;
				positionsArray[i * 3 + 2] = positions.get(i).z;
			}
			
			for (int i = 0; i < indices.size(); i++) {
				indicesArray[i] = indices.get(i);
			}
			
			return isTextured ? hasNormals ?
					new SadMesh(name, indicesArray, positionsArray, textureCoordinatesArray, normalsArray) :
					new SadMesh(name, indicesArray, positionsArray, textureCoordinatesArray) :
					new SadMesh(name, indicesArray, positionsArray);
			
		} catch (IOException | NumberFormatException ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
