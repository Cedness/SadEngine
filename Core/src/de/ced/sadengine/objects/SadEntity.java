package de.ced.sadengine.objects;

import java.util.ArrayList;
import java.util.List;

public class SadEntity extends SadPositionable {
	
	private List<SadLevel> levels = new ArrayList<>();
	
	private boolean enabled = true;
	
	private boolean visible = true;
	private SadModel model;
	private SadBody body;
	
	List<SadLevel> getLevels() {
		return levels;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public SadEntity setEnabled(boolean enabled) {
		this.enabled = enabled;
		return this;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public SadEntity setVisible(boolean visible) {
		this.visible = visible;
		return this;
	}
	
	public SadModel getModel() {
		return model;
	}
	
	public SadEntity setModel(SadModel model) {
		for (SadLevel level : levels) {
			level.removeEntity(this);
		}
		
		this.model = model;
		
		for (SadLevel level : levels) {
			level.addEntity(this);
		}
		
		return this;
	}
	
	public SadBody getBody() {
		return body;
	}
	
	public SadEntity setBody(SadBody body) {
		this.body = body;
		return this;
	}
	
	public SadEntity updateBody() {
		
		return this;
	}
}
