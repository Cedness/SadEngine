package de.ced.sadengine.objects;

import de.ced.sadengine.objects.input.SadInput;
import de.ced.sadengine.objects.light.SadLight;
import de.ced.sadengine.shader.SadShader;
import de.ced.sadengine.utils.SadVector;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class SadRenderer {
	
	private SadWindow window;
	private SadContent content;
	@SuppressWarnings({"FieldCanBeLocal", "unused"})
	private SadInput input;
	
	private SadGlWindow glWindow;
	private SadShader shader;
	
	@SuppressWarnings("deprecation")
	private SadLight light = new SadLight(new SadVector(1f, 1f, 1f), new SadVector(0f, 5f, 0f));
	
	void setup(SadWindow window, SadContent content, Saddings settings, SadInput input) {
		this.window = window;
		this.content = content;
		this.input = input;
		
		glWindow = new SadGlWindow();
		
		glWindow.setup(settings, input);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		window.setup(glWindow);
		
		shader = new SadShader();
	}
	
	void updateWindow() {
		glWindow.update();
	}
	
	void render() {
		renderFrame(window);
	}
	
	private void renderFrame(SadFrame frame) {
		SadCamera camera = frame.getCamera();
		if (camera == null)
			return;
		
		SadLevel level = camera.getLevel();
		if (level == null)
			return;
		
		int geniousParadoxPreventer = frame.getGeniousParadoxPreventer();
		frame.setGeniousParadoxPreventer(geniousParadoxPreventer + 1);
		
		if (geniousParadoxPreventer == 0) {
			SadGL.bindFrameBuffer(frame);
			SadGL.clear(frame);
			frame.setRendered(false);
		}
		
		if (geniousParadoxPreventer <= 3) {
			for (String modelName : level.getIndex().keySet()) {
				renderModelFrames(content.getModel(modelName));
			}
		}
		
		boolean renderOnlyFrames = frame.isRendered();
		
		SadGL.bindFrameBuffer(frame);
		lastTexture = null;
		shader.enable(true);
		camera.update(frame.getWidth(), frame.getHeight());
		shader.uploadProjectionMatrix(camera.getProjectionMatrix());
		shader.uploadViewMatrix(camera.getViewMatrix());
		//noinspection deprecation
		shader.uploadLight(light);
		HashMap<String, ArrayList<String>> index = level.getIndex();
		for (String modelName : index.keySet()) {
			parentMatrices = new ArrayList<>();
			parentTextures = new ArrayList<>();
			renderModel(content.getModel(modelName), index.get(modelName), renderOnlyFrames);
		}
		shader.enable(false);
		
		frame.setRendered(true);
		
		frame.setGeniousParadoxPreventer(geniousParadoxPreventer);
	}
	
	private void renderModelFrames(SadModel model) {
		if (model == null)
			return;
		SadTexture texture = model.getTexture();
		if (texture instanceof SadFrame)
			renderFrame((SadFrame) texture);
		for (String modelName : model.getModels()) {
			renderModelFrames(model.getModel(modelName));
		}
	}
	
	private SadTexture lastTexture = null;
	private Matrix4f modelMatrix = new Matrix4f();
	private Matrix4f entityMatrix = new Matrix4f();
	private List<Matrix4f> parentMatrices;
	private List<SadTexture> parentTextures;
	
	private void renderModel(SadModel model, List<String> entityNames, boolean renderOnlyFrames) {
		if (model == null)
			return;
		
		parentMatrices.add(model.writeToMatrix());
		parentTextures.add(model.getTexture());
		
		renderMesh(model, entityNames, renderOnlyFrames);
		
		for (String subModel : model.getModels()) {
			renderModel(model.getModel(subModel), entityNames, renderOnlyFrames);
		}
		
		parentMatrices.remove(parentMatrices.size() - 1);
		parentTextures.remove(model.getTexture());
	}
	
	private void renderMesh(SadModel model, List<String> entityNames, boolean renderOnlyFrames) {
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
			
			if (renderOnlyFrames && !(currentTexture instanceof SadFrame))
				return;
			
			textureFound = true;
			
			if (lastTexture != currentTexture) {
				shader.uploadTexture(currentTexture, 0);
				lastTexture = currentTexture;
			}
		}
		
		boolean renderBack = model.isRenderBack();
		if (renderBack)
			SadGL.enableBackRendering();
		mesh.loadVao(true);
		
		for (String entityName : entityNames) {
			renderEntity(content.getEntity(entityName), mesh);
		}
		
		mesh.loadVao(false);
		if (renderBack)
			SadGL.disableBackRendering();
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
		//noinspection ConstantConditions
		glfwSetErrorCallback(null).free();
	}
	/*
	//cursor = input.getCursor().getNormalizedPosition();
	
	private void renderFrame(SadFrameX frame) {
		renderCamera(frame);
		
		renderSubframes(frame);
	}
	
	private void renderCamera(SadFrameX frame) {
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
		
		//frameMatrix.identity();
		//frameMatrix.translate(frame.getPosition().x(), frame.getPosition().y(), 0);
		//frameMatrix.rotateZ((float) Math.toRadians(frame.getRotation().z()));
		//frameMatrix.scale(frame.getScale().x(), frame.getScale().y(), 1);
		
		camera.end(glWindow.getWidth(),glWindow.getHeight());
	
		//shader.uploadFrameOffset(new SadVector3(frame.getPosition().x(), frame.getPosition().y(), frame.getScale().x()));
		
		//shader.uploadFrameMatrix(frameMatrix);
			shader.uploadProjectionMatrix(camera.getProjectionMatrix());
			shader.uploadViewMatrix(camera.getViewMatrix());
			shader.uploadLight(light);
			
			//Vector3f vector = new Vector3f();
			//Matrix4f invProj = new Matrix4f(camera.getProjectionMatrix()).invert();
			//Matrix4f invView = new Matrix4f(camera.getViewMatrix()).invert();
			//vector.mulProject(invProj);
			//vector.mulPosition(invView);
		
		HashMap<String, ArrayList<String>> index = level.getIndex();
			
			for(
		String modelName :index.keySet())
		
		{
			parentMatrices = new ArrayList<>();
			parentTextures = new ArrayList<>();
			renderModel(content.getModel(modelName), index.get(modelName));
		}
			
			shader.enable(false);
		
	}
	
	private void renderSubframes(SadFrameX frame) {
		List<String> subframes = frame.getFrames();
		for (int i = 0; i < frame.size(); i++) {
			renderFrame(content.getFrame(subframes.get(frame.isOrderInverted() ? frame.size() - 1 - i : i)));
		}
	}
	 */
}
