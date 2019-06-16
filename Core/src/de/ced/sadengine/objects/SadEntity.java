package de.ced.sadengine.objects;

import java.util.ArrayList;
import java.util.List;

public class SadEntity extends SadPositionable implements SadEntityI {
	
	private List<SadLevel> levels = new ArrayList<>();
	
	private boolean enabled = true;
	
	private boolean visible = true;
	private SadModel model;
	private SadBody body;
	
	List<SadLevel> getLevels() {
		return levels;
	}
	
	@Override
	public boolean isEnabled() {
		return enabled;
	}
	
	@Override
	public SadEntity setEnabled(boolean enabled) {
		this.enabled = enabled;
		return this;
	}
	
	@Override
	public boolean isVisible() {
		return visible;
	}
	
	@Override
	public SadEntity setVisible(boolean visible) {
		this.visible = visible;
		return this;
	}
	
	@Override
	public SadModel getModel() {
		return model;
	}
	
	@Override
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
	
	@Override
	public SadBody getBody() {
		return body;
	}
	
	@Override
	public SadEntity setBody(SadBody body) {
		this.body = body;
		return this;
	}
	
	public SadEntity updateBody() {
		
		return this;
	}
}
