package de.ced.sadengine.objects;

import de.ced.sadengine.api.Saddings;
import de.ced.sadengine.graphics.light.SadLight;
import de.ced.sadengine.input.SadInput;
import de.ced.sadengine.main.SadContent;
import de.ced.sadengine.main.SadGlWindow;
import de.ced.sadengine.shader.SadShader;
import de.ced.sadengine.utils.SadVector3;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class SadRenderer {
	
	private SadWindow window;
	private SadContent content;
	private SadInput input;
	
	private SadGlWindow glWindow;
	private SadShader shader;
	
	private SadTexture lastTexture = null;
	private Matrix4f modelMatrix = new Matrix4f();
	private Matrix4f entityMatrix = new Matrix4f();
	private SadLight light = new SadLight(new SadVector3(1, 1, 1), new SadVector3(0, 5, 0));
	
	private SadVector3 cursor;
	
	public void setup(SadWindow window, SadContent content, Saddings settings, SadInput input) {
		this.window = window;
		this.content = content;
		this.input = input;
		
		glWindow = new SadGlWindow();
		
		glWindow.setup(settings, input);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		shader = new SadShader();
	}
	
	public void updateWindow() {
		glWindow.update();
	}
	
	/**
	 * Renders everything inside of window
	 */
	public void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		cursor = input.getCursor().getNormalizedPosition();
		
		renderFrame(window);
	}
	
	/**
	 * Renders a frame
	 *
	 * @param frame the frame to be rendered
	 */
	private void renderFrame(SadFrame frame) {
		renderCamera(frame);
		
		renderSubframes(frame);
	}
	
	private void renderCamera(SadFrame frame) {
		//This frame
		SadCamera camera = frame.getCamera();
		if (camera == null)
			return;
		
		SadVector3 vector = camera.getCursorVector();
		//System.out.println("Cursor                            " + cursor);
		float fov = camera.getFov();
		float aspectRatio = (float) glWindow.getWidth() / (float) glWindow.getHeight();
		//System.out.println(Math.toRadians(fov) * cursor.x() * aspectRatio);
		vector.set(
				(float) Math.tan(Math.toRadians(fov) * cursor.x() * aspectRatio),
				(float) Math.tan(Math.toRadians(fov) * cursor.y()),
				1);
		
		SadLevel level = camera.getLevel();
		if (level == null)
			return;
		glClear(GL_DEPTH_BUFFER_BIT);
		
		shader.enable(true);
		
		/*
		frameMatrix.identity();
		frameMatrix.translate(frame.getPosition().x(), frame.getPosition().y(), 0);
		frameMatrix.rotateZ((float) Math.toRadians(frame.getRotation().z()));
		frameMatrix.scale(frame.getScale().x(), frame.getScale().y(), 1);
		*/
		
		camera.update(glWindow.getWidth(), glWindow.getHeight());
		
		//shader.uploadFrameOffset(new SadVector3(frame.getPosition().x(), frame.getPosition().y(), frame.getScale().x()));
		
		//shader.uploadFrameMatrix(frameMatrix);
		shader.uploadProjectionMatrix(camera.getProjectionMatrix());
		shader.uploadViewMatrix(camera.getViewMatrix());
		shader.uploadLight(light);
		
		/*
		Vector3f vector = new Vector3f();
		Matrix4f invProj = new Matrix4f(camera.getProjectionMatrix()).invert();
		Matrix4f invView = new Matrix4f(camera.getViewMatrix()).invert();
		vector.mulProject(invProj);
		vector.mulPosition(invView);
		*/
		
		HashMap<String, ArrayList<String>> index = level.getIndex();
		
		for (String modelName : index.keySet()) {
			parentMatrices = new ArrayList<>();
			parentTextures = new ArrayList<>();
			renderModel(content.getModel(modelName), index.get(modelName));
		}
		
		shader.enable(false);
		
	}
	
	private void renderSubframes(SadFrame frame) {
		//Subframes
		List<String> subframes = frame.getFrames();
		for (int i = 0; i < frame.size(); i++) {
			renderFrame(content.getFrame(subframes.get(frame.isOrderInverted() ? frame.size() - 1 - i : i)));
		}
	}
	
	private List<Matrix4f> parentMatrices;
	private List<SadTexture> parentTextures;
	
	private void renderModel(SadModel model, List<String> entityNames) {
		
		if (model == null)
			return;
		
		parentMatrices.add(model.writeToMatrix(new Matrix4f()));
		parentTextures.add(model.getTexture());
		
		renderMesh(model, entityNames);
		
		for (String subModel : model.getModels()) {
			renderModel(model.getModel(subModel), entityNames);
		}
		
		parentMatrices.remove(parentMatrices.size() - 1);
		parentTextures.remove(model.getTexture());
	}
	
	private void renderMesh(SadModel model, List<String> entityNames) {
		SadMesh mesh = model.getMesh();
		if (mesh == null)
			return;
		
		modelMatrix.identity();
		
		boolean textureFound = false;
		for (int i = parentMatrices.size() - 1; i >= 0; i--) {
			modelMatrix.mul(parentMatrices.get(i));
			if (textureFound)
				continue;
			
			SadTexture currentTexture = parentTextures.get(i);
			if (currentTexture == null)
				continue;
			
			textureFound = true;
			
			if (lastTexture != currentTexture) {
				shader.uploadTexture(currentTexture, 0);
				lastTexture = currentTexture;
			}
		}
		
		mesh.loadVao(true);
		
		for (String entityName : entityNames) {
			renderEntity(content.getEntity(entityName), mesh);
		}
		
		mesh.loadVao(false);
	}
	
	private void renderEntity(SadEntity entity, SadMesh mesh) {
		entityMatrix.set(modelMatrix);
		entity.writeToMatrix(entityMatrix);
		shader.uploadTransformationMatrix(entityMatrix);
		
		mesh.draw();
	}
	
	public void release() {
		shader.release();
		
		glfwSetWindowShouldClose(glWindow.getGlWindow(), true);
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}
}
