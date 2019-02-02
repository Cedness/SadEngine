package de.ced.sadengine.objects;

import de.ced.sadengine.main.SadContent;

import static org.lwjgl.opengl.GL30.glDeleteFramebuffers;
import static org.lwjgl.opengl.GL30.glDeleteRenderbuffers;

public class SadFrame extends SadTexture {
	
	private final SadContent content;
	private final int fboID;
	private final int depthID;
	protected int width;
	protected int height;
	private boolean rendered = false;
	private int geniousParadoxPreventer = 0;
	
	private String camera;
	
	public SadFrame(String name, SadContent content, int fboID, int textureID, int depthID, int width, int height) {
		super(name, textureID);
		this.content = content;
		this.fboID = fboID;
		this.depthID = depthID;
		this.width = width;
		this.height = height;
	}
	
	
	//Camera
	
	public SadCamera getCamera() {
		return content.getCamera(camera);
	}
	
	public SadFrame setCamera(String name) {
		camera = name;
		return this;
	}
	
	
	public int getFboID() {
		return fboID;
	}
	
	public int getDepthID() {
		return depthID;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public boolean isRendered() {
		return rendered;
	}
	
	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}
	
	public int getGeniousParadoxPreventer() {
		return geniousParadoxPreventer;
	}
	
	public void setGeniousParadoxPreventer(int geniousParadoxPreventer) {
		this.geniousParadoxPreventer = geniousParadoxPreventer;
	}
	
	@Override
	public void release() {
		super.release();
		glDeleteFramebuffers(fboID);
		glDeleteRenderbuffers(depthID);
	}
}
