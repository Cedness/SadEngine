package de.ced.sadengine.objects;

import de.ced.sadengine.utils.SadVector;

import static org.lwjgl.opengl.GL30.glDeleteFramebuffers;
import static org.lwjgl.opengl.GL30.glDeleteRenderbuffers;

public class SadFrame extends SadTexture implements SadFrameI {
	
	private final SadContent content;
	private final int fboID;
	private final int depthID;
	protected int width;
	protected int height;
	private boolean rendered = false;
	private int geniousParadoxPreventer = 0;
	
	private String camera;
	
	private SadVector color = new SadVector(4);
	
	SadFrame(String name, SadContent content, int fboID, int textureID, int depthID, int width, int height) {
		super(name, textureID);
		this.content = content;
		this.fboID = fboID;
		this.depthID = depthID;
		this.width = width;
		this.height = height;
	}
	
	
	//Camera
	
	@Override
	public SadCamera getCamera() {
		return content.getCamera(camera);
	}
	
	@Override
	public SadFrame setCamera(String name) {
		camera = name;
		return this;
	}
	
	@Override
	public SadVector getColor() {
		return color;
	}
	
	int getFboID() {
		return fboID;
	}
	
	int getDepthID() {
		return depthID;
	}
	
	@Override
	public int getWidth() {
		return width;
	}
	
	@Override
	public int getHeight() {
		return height;
	}
	
	boolean isRendered() {
		return rendered;
	}
	
	void setRendered(boolean rendered) {
		this.rendered = rendered;
	}
	
	@Override
	public int getGeniousParadoxPreventer() {
		return geniousParadoxPreventer;
	}
	
	@Override
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
