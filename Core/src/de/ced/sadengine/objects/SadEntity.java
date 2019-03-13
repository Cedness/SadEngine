package de.ced.sadengine.objects;

import java.util.ArrayList;
import java.util.List;

public class SadEntity extends SadPositionable implements SadEntityI {
	
	private List<SadLevel> levels = new ArrayList<>();
	
	private SadModel model;
	private SadBody body;
	
	List<SadLevel> getLevels() {
		return levels;
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
