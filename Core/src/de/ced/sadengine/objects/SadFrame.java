package de.ced.sadengine.objects;

import de.ced.sadengine.utils.SadVector;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.GL_DEPTH_COMPONENT32;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL32.glFramebufferTexture;

public class SadFrame extends SadTexture implements SadFrameI {
	
	private final int fboID;
	private final int depthID;
	private boolean rendered = false;
	private int geniousParadoxPreventer = 0;
	
	private SadCamera camera;
	
	private SadVector color = new SadVector(4);
	
	@SuppressWarnings("unused")
	public SadFrame(int width, int height) {
		super(new int[]{createTextureAttachment(width, height), width, height, 3});
		this.fboID = createFrameBuffer();
		this.depthID = createDepthBufferAttachment(width, height);
	}
	
	SadFrame() {
		super(new int[]{0, 0, 0, 3});
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
	
	//FrameBuffer
	
	private static int createFrameBuffer() {
		int frameBuffer = glGenFramebuffers();
		glBindFramebuffer(GL_FRAMEBUFFER, frameBuffer);
		glDrawBuffer(GL_COLOR_ATTACHMENT0);
		return frameBuffer;
	}
	
	private static int createTextureAttachment(int width, int height) {
		int texture = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, texture);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, (ByteBuffer) null);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glFramebufferTexture(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, texture, 0);
		return texture;
	}
	
	@SuppressWarnings("unused")
	private static int createDepthTextureAttachment(int width, int height) {
		int depthTexture = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, depthTexture);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT32, width, height, 0, GL_DEPTH_COMPONENT, GL_FLOAT, (ByteBuffer) null);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glFramebufferTexture(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, depthTexture, 0);
		return depthTexture;
	}
	
	private static int createDepthBufferAttachment(int width, int height) {
		int depthBuffer = glGenRenderbuffers();
		glBindRenderbuffer(GL_RENDERBUFFER, depthBuffer);
		glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT, width, height);
		glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, depthBuffer);
		return depthBuffer;
	}
}
