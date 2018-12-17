package de.ced.sadengine.main;

import static de.ced.sadengine.main.SadThreadHandler.STAMP_COUNT;

public abstract class SadThread extends Thread {
	
	protected final SadThreadHandler threadHandler;
	private SadThreadAction[] actions = new SadThreadAction[STAMP_COUNT];
	
	public SadThread(String name, SadThreadHandler threadHandler) {
		super(name);
		this.threadHandler = threadHandler;
	}
	
	public void setAction(SadThreadAction action, boolean[] upTimes) {
		for (int i = 0; i < STAMP_COUNT; i++) {
			if (!upTimes[i])
				continue;
			
			actions[i] = action;
		}
	}
	
	public boolean done() {
		int stamp = threadHandler.getStamp();
		return actions[stamp] == null || actions[stamp] == actions[normalize(stamp + 1)] || actions[stamp].done;
	}
	
	@Override
	public void run() {
		while (true) {
			int stamp = threadHandler.getStamp();
			SadThreadAction action = actions[stamp];
			if (action == null || action == actions[normalize(stamp - 1)])
				continue;
			
			action.done = false;
			action.run();
			
			synchronized (threadHandler) {
				System.out.println(getName() + ": Notifies " + threadHandler.getName());
				threadHandler.notifyAll();
			}
			
			try {
				synchronized (this) {
					System.out.println(getName() + ": Done");
					wait(5000);
					System.out.println(getName() + ": Waking up");
					//sleep(10);
					System.out.println(getName() + ": Awake");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
		}
		System.out.println(getName() + ": Stopping");
	}
	
	private static int normalize(int stamp) {
		return stamp < STAMP_COUNT ? (stamp >= 0 ? stamp : stamp + STAMP_COUNT) : stamp - STAMP_COUNT;
	}
	
	public abstract void terminate();
}
