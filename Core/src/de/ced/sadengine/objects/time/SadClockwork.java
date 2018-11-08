package de.ced.sadengine.objects.time;

import de.ced.sadengine.objects.SadObject;

import java.util.HashMap;

public class SadClockwork {
	
	private final HashMap<String, SadObject> clocks;
	
	public SadClockwork(HashMap<String, SadObject> clocks) {
		this.clocks = clocks;
	}
	
	public void increaseClocks(float interval) {
		for (SadObject obj : clocks.values()) {
			SadClock clock = (SadClock) obj;
			if (!clock.isRunning())
				continue;
			float incr = clock.getIncrPerSec() * interval;
			if (clock.isStopAtReach()) {
				if (clock.getTime() >= clock.getGoal())
					return;
				if (clock.getTime() + incr > clock.getGoal()) {
					clock.setTime(clock.getGoal());
					return;
				}
			}
			clock.addTime(incr);
		}
	}
}
