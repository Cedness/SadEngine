package de.ced.sadengine.objects;

import de.ced.sadengine.objects.input.SadInput;

@SuppressWarnings({"unused", "FieldCanBeLocal", "WeakerAccess"})
abstract class SadProcessor {
	
	final SadFrame window;
	final SadContent content;
	final SadInput input;
	final SadEngine engine;
	
	SadProcessor(SadFrame window, SadContent content, SadInput input, SadEngine engine) {
		this.window = window;
		this.content = content;
		this.input = input;
		this.engine = engine;
	}
	
	abstract void invoke();
}
