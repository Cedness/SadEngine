package de.ced.sadengine.objects.time;

import java.util.ArrayList;
import java.util.List;

public class SadClockwork {
	
	private List<SadClock> clocks = new ArrayList<>();
	
	public void add(SadClock clock) {
		if (clocks.contains(clock))
			return;
		clocks.add(clock);
	}
	
	public void remove(SadClock clock) {
		clocks.remove(clock);
	}
	
	public void removeAll() {
		clocks = new ArrayList<>();
	}
	
	public void increaseClocks(float interval) {
		for (SadClock clock : clocks) {
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
