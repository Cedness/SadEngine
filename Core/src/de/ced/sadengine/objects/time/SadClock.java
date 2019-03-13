package de.ced.sadengine.objects.time;

import de.ced.sadengine.objects.SadRunnable;

public class SadClock extends SadRunnable {
	
	private float incrPerSec = 1f;
	private Float goal = null;
	private boolean stopAtReach = false;
	
	private float time = 0f;
	
	@Override
	public SadClock setRunning(boolean running) {
		super.setRunning(running);
		return this;
	}
	
	public float getIncrPerSec() {
		return incrPerSec;
	}
	
	public SadClock setIncrPerSec(float incrPerSec) {
		this.incrPerSec = incrPerSec;
		return this;
	}
	
	public boolean hasGoal() {
		return goal != null;
	}
	
	public Float getGoal() {
		return goal;
	}
	
	public SadClock setGoal(float goal) {
		this.goal = goal;
		return this;
	}
	
	public SadClock removeGoal() {
		this.goal = null;
		return this;
	}
	
	@Deprecated
	public boolean isAtGoal() {
		if (!hasGoal())
			return false;
		return time == goal;
	}
	
	public boolean hasReachedGoal() {
		if (!hasGoal())
			return false;
		return time >= goal;
	}
	
	public boolean isStopAtReach() {
		return stopAtReach;
	}
	
	public SadClock setStopAtReach(boolean stopAtReach) {
		this.stopAtReach = stopAtReach;
		return this;
	}
	
	public float getTime() {
		return time;
	}
	
	public SadClock setTime(float time) {
		this.time = time;
		return this;
	}
	
	public SadClock addTime(float time) {
		this.time += time;
		return this;
	}
	
	public SadClock mulTime(float time) {
		this.time *= time;
		return this;
	}
}
