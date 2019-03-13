package de.ced.sadengine.objects;

import de.ced.sadengine.utils.SadVector;

import static org.lwjgl.opengl.GL30.glDeleteFramebuffers;
import static org.lwjgl.opengl.GL30.glDeleteRenderbuffers;

public class SadFrame extends SadTexture implements SadFrameI {
	
	private final int fboID;
	private final int depthID;
	private boolean rendered = false;
	private int geniousParadoxPreventer = 0;
	
	private SadCamera camera;
	
	private SadVector color = new SadVector(4);
	
	@SuppressWarnings({"unused", "WeakerAccess"})
	public SadFrame(int width, int height) {
		super(new int[]{SadGL.createTextureAttachment(width, height), width, height, 3});
		this.fboID = SadGL.createFrameBuffer();
		this.depthID = SadGL.createDepthBufferAttachment(width, height);
	}
	
	SadFrame() {
		super(new int[]{0, 1280, 720, 3});
		fboID = 0;
		depthID = 0;
	}
	
	
	//Camera
	
	@Override
	public SadCamera getCamera() {
		return camera;
	}
	
	@Override
	public SadFrame setCamera(SadCamera camera) {
		this.camera = camera;
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
	void release() {
		super.release();
		glDeleteFramebuffers(fboID);
		glDeleteRenderbuffers(depthID);
	}
}
