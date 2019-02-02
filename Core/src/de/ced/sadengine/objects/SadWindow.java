package de.ced.sadengine.objects;

import de.ced.sadengine.main.SadContent;
import de.ced.sadengine.main.SadGlWindow;

public class SadWindow extends SadFrame {
	
	public SadWindow(SadContent content) {
		super("mainFrame", content, 0, 0, 0, 0, 0);
	}
	
	public void setup(SadGlWindow glWindow) {
		width = glWindow.getWidth();
		height = glWindow.getHeight();
	}
}
