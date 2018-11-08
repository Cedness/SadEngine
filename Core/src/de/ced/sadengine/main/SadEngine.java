package de.ced.sadengine.main;

import de.ced.sadengine.api.Saddings;
import de.ced.sadengine.input.SadInput;
import de.ced.sadengine.objects.SadRenderer;
import de.ced.sadengine.objects.SadWindow;

import static de.ced.sadengine.utils.SadValue.KEY_ESCAPE;

public class SadEngine {
	
	private final SadLoop loop;
	
	private final SadMainLogic logic;
	private final Saddings settings;
	private final Sadness sadness;
	private final SadWindow window;
	private final SadContent content;
	private final SadInput input;
	private final SadRenderer renderer;
	
	public SadEngine(SadMainLogic logic, Saddings settings, SadLoop loop) {
		this.logic = logic;
		this.settings = settings;
		
		sadness = new Sadness();
		content = sadness.getContent();
		window = sadness.getWindow();
		input = sadness.getInput();
		
		renderer = new SadRenderer();
		this.loop = loop;
	}
	
	public void setup() {
		renderer.setup(window, content, settings, input);
		input.update();
		logic.setup(sadness);
		content.getActionHandler().setup();
		input.update();
	}
	
	public void render() {
		renderer.render();
	}
	
	public void finishRender() {
		renderer.updateWindow();
	}
	
	public void update() {
		float secInterval = 1.0f / (float) settings.getUps();
		
		content.update(secInterval);
		logic.update(sadness);
		content.getActionHandler().update();
		logic.updateFinally(sadness);
		input.update();
		
		if (input.isPressed(KEY_ESCAPE))
			loop.stop();
	}
	
	public void finishUpdate() {
	
	}
	
	public void terminate() {
		logic.terminate(sadness);
		content.getActionHandler().terminate();
		logic.preTerminate(sadness);
		
		renderer.release();
		
		System.out.println("SadEngine3D");
		System.out.println("(ɔ) 2018 by Ced");
	}
}
