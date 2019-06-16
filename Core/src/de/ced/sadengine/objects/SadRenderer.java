package de.ced.sadengine.objects;

import de.ced.sadengine.objects.input.SadInput;
import de.ced.sadengine.objects.light.SadLight;
import de.ced.sadengine.utils.SadGLMatrix;
import de.ced.sadengine.utils.SadRotationVector;
import de.ced.sadengine.utils.SadVector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

public class SadRenderer {
	
	private final SadFrame window;
	
	private SadWindow glWindow;
	private SadShader shader;
	
	@SuppressWarnings("deprecation")
	private SadLight light = new SadLight(new SadVector(1f, 1f, 1f), new SadVector(0f, 5f, 0f));
	
	private SadTexture currentTexture = null;
	private SadTexture lastTexture = null;
	private SadGLMatrix matrix = new SadGLMatrix().identity();
	private List<SadModel> parentModels;
	private List<SadTexture> parentTextures;
	
	private SadVector position = new SadVector(3);
	private SadVector rotation = new SadRotationVector(3);
	private SadVector scale = new SadVector(3);
	
	private SadVector entityPosition = new SadVector(3);
	private SadVector entityRotation = new SadRotationVector(3);
	private SadVector entityScale = new SadVector(3);
	
	SadRenderer(SadEngine window, SadInput input) {
		this.window = window;
		
		glWindow = new SadWindow();
		glWindow.setup(window, input);
		
		shader = new SadShader();
	}
	
	void invoke() {
		renderFrame(window);
		glWindow.update();
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
			bindFrameBuffer(frame);
			clear(frame);
			frame.setRendered(false);
		}
		
		if (geniousParadoxPreventer <= 3) {
			for (SadModel model : level.getIndex().keySet()) {
				renderModelFrames(model);
			}
		}
		
		boolean renderOnlyFrames = frame.isRendered();
		
		bindFrameBuffer(frame);
		lastTexture = null;
		shader.enable(true);
		camera.update(frame.getWidth(), frame.getHeight());
		shader.uploadProjectionMatrix(camera.getProjectionMatrix());
		shader.uploadViewMatrix(camera.getViewMatrix());
		//noinspection deprecation
		shader.uploadLight(light);
		HashMap<SadModel, ArrayList<SadEntity>> index = level.getIndex();
		for (SadModel model : index.keySet()) {
			parentModels = new ArrayList<>();
			parentTextures = new ArrayList<>();
			renderModel(model, index.get(model), renderOnlyFrames);
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
		for (SadModel sadModel : model.getModels()) {
			renderModelFrames(sadModel);
		}
	}
	
	private void renderModel(SadModel model, List<SadEntity> entities, boolean renderOnlyFrames) {
		if (model == null)
			return;
		
		parentModels.add(model);
		parentTextures.add(model.getTexture());
		
		renderMesh(model, entities, renderOnlyFrames);
		
		for (SadModel subModel : model.getModels()) {
			renderModel(subModel, entities, renderOnlyFrames);
		}
		
		parentModels.remove(parentModels.size() - 1);
		parentTextures.remove(model.getTexture());
	}
	
	private void renderMesh(SadModel model, List<SadEntity> entities, boolean renderOnlyFrames) {
		SadOBJMesh mesh = model.getMesh();
		if (mesh == null)
			return;
		
		
		boolean textureFound = false;
		
		position.set(0);
		rotation.set(0);
		scale.set(1);
		
		for (int i = parentModels.size() - 1; i >= 0; i--) {
			SadModel model1 = parentModels.get(i);
			position.mul(model1.getScale()).add(model1.getPosition());
			rotation.add(model1.getRotation());
			scale.mul(model1.getScale());
			
			if (textureFound)
				continue;
			
			currentTexture = parentTextures.get(i);
			if (currentTexture == null)
				continue;
			
			if (renderOnlyFrames && !(currentTexture instanceof SadFrame))
				return;
			
			textureFound = true;
		}
		
		if (lastTexture != currentTexture) {
			shader.uploadTexture(currentTexture, 0);
			lastTexture = currentTexture;
		}
		
		shader.uploadColor(model.getColor());
		
		boolean renderBack = model.isRenderBack() || lastTexture != null && lastTexture.isRenderBack();
		if (renderBack)
			enableBackRendering();
		mesh.loadVao();
		
		for (SadEntity entity : entities) {
			renderEntity(entity, mesh);
		}
		
		mesh.unloadVao();
		if (renderBack)
			disableBackRendering();
	}
	
	private void renderEntity(SadEntity entity, SadOBJMesh mesh) {
		entityPosition.set(position).mul(entity.getScale()).add(entity.getPosition());
		entityRotation.set(rotation).add(entity.getRotation());
		entityScale.set(scale).mul(entity.getScale());
		
		matrix.transformationMatrix(entityPosition, entityRotation, entityScale);
		shader.uploadTransformationMatrix(matrix);
		
		mesh.draw();
	}
	
	void release() {
		shader.release();
		
		glfwSetWindowShouldClose(glWindow.getWindowID(), true);
		glfwTerminate();
		//noinspection ConstantConditions
		glfwSetErrorCallback(null).free();
	}
	
	boolean shouldClose() {
		return glWindow.shouldClose();
	}
	
	void stop() {
		glWindow.close();
	}
	
	private static void bindFrameBuffer(SadFrame frame) {
		glBindTexture(GL_TEXTURE_2D, 0);
		glBindFramebuffer(GL_FRAMEBUFFER, frame.getFboID());
		glBindRenderbuffer(GL_RENDERBUFFER, frame.getDepthID());
		glViewport(0, 0, frame.getWidth(), frame.getHeight());
	}
	
	@SuppressWarnings("unused")
	private static void unbindFrameBuffer(SadWindow glWindow) {
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
		glBindRenderbuffer(GL_RENDERBUFFER, 0);
		glViewport(0, 0, glWindow.getWidth(), glWindow.getHeight());
	}
	
	private static void clear(SadFrame frame) {
		bindFrameBuffer(frame);
		SadVector c = frame.getColor();
		glClearColor(c.get(0), c.get(1), c.get(2), c.get(3));
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	private static void enableBackRendering() {
		glDisable(GL_CULL_FACE);
	}
	
	private static void disableBackRendering() {
		glEnable(GL_CULL_FACE);
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
			shader.uploadViewMatrix(camera.getViewMatrix_OLD());
			shader.uploadLight(light);
			
			//Vector3f vector = new Vector3f();
			//Matrix4f invProj = new Matrix4f(camera.getProjectionMatrix()).invert();
			//Matrix4f invView = new Matrix4f(camera.getViewMatrix_OLD()).invert();
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
