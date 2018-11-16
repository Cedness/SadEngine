package de.ced.sadengine.objects;

import de.ced.sadengine.main.SadContent;
import de.ced.sadengine.utils.SadVector3;

import java.util.ArrayList;
import java.util.List;

/**
 * Screen space in the window
 */
public class SadFrame extends SadDrawable {
	
	//private final SadInput input;
	
	private String camera = null;
	private String parent = null; //TODO
	private ArrayList<String> frames = new ArrayList<>();
	//false: low = back  high = front
	private boolean orderInverted = false;
	
	public SadFrame(String name, SadContent content) {
		super(name, content);
		//this.input = input;
	}
	
	
	//Camera
	
	public SadCamera getCamera() {
		return content.getCamera(camera);
	}
	
	public SadFrame setCamera(String name) {
		camera = name;
		return this;
	}
	
	
	//CursorVector
	
	public SadVector3 getCursorVector() {
		SadCamera camera = getCamera();
		//if (camera == null)
		return null;
		
	}
	
	
	//Frames
	
	public void addFrame(String name) {
		if (hasFrame(name))
			return;
		frames.add(name);
	}
	
	public void removeFrame(String name) {
		if (!hasFrame(name))
			return;
		frames.remove(name);
	}
	
	public SadFrame getFrame(String name) {
		if (!hasFrame(name))
			return null;
		return content.getFrame(name);
	}
	
	public boolean hasFrame(String name) {
		return frames.contains(name);
	}
	
	public List<String> getFrames() {
		return frames;
	}
	
	public void clearAllFrames() {
		frames = new ArrayList<>();
	}
	
	
	//Frame order
	
	public int getRank(String name) {
		if (!hasFrame(name))
			return -1;
		return frames.indexOf(name);
	}
	
	public void swapRanks(String name1, String name2) {
		if (!hasFrame(name1) || !hasFrame(name2))
			return;
		int indexOfName2 = frames.indexOf(name2);
		frames.set(frames.indexOf(name1), name2);
		frames.set(indexOfName2, name1);
	}
	
	public void setRank(String name, int rank) {
		if (!hasFrame(name))
			return;
		int min = 0;
		int max = frames.size();
		frames.remove(name);
		frames.add(rank < max ? (rank > min ? rank : min) : max - 1, name);
	}
	
	public int size() {
		return frames.size();
	}
	
	public boolean isOrderInverted() {
		return orderInverted;
	}
	
	public void setOrderInverted(boolean orderInverted) {
		this.orderInverted = orderInverted;
	}
}
