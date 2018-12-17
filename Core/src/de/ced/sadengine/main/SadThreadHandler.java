package de.ced.sadengine.main;

import java.util.ArrayList;

public class SadThreadHandler extends Thread {
	
	public static final int STAMP_COUNT = 4;
	
	private int stamp = 0;
	private ArrayList<SadThread> threads = new ArrayList<>();
	
	public SadThreadHandler() {
		super("ThreadHandler");
		System.out.println(getName() + ": Starting");
	}
	
	public int getStamp() {
		return stamp;
	}
	
	public void addThread(SadThread thread) {
		threads.add(thread);
	}
	
	@Override
	public void run() {
		System.out.println(getName() + ": Starting threads...");
		for (SadThread thread : threads) {
			thread.start();
		}
		System.out.println(getName() + ": Beginning Main Loop");
		while (true) {
			check:
			do {
				try {
					synchronized (this) {
						System.out.println(getName() + ": Done");
						wait(5000);
						System.out.println(getName() + ": Waking up");
						//sleep(10);
						System.out.println(getName() + ": Awake");
					}
				} catch (InterruptedException e) {
					System.out.println(getName() + ": Stopping all threads...");
					for (SadThread thread : threads) {
						thread.interrupt();
						thread.terminate();
					}
					System.out.println(getName() + ": Stopping");
					break;
				}
				
				for (SadThread thread : threads) {
					if (!thread.done())
						continue check;
				}
			} while (false);
			
			stamp = normalize(stamp + 1);
			
			for (SadThread thread : threads) {
				synchronized (thread) {
					System.out.println(getName() + ": Notifies " + thread.getName());
					thread.notifyAll();
				}
			}
		}
	}
	
	private static int normalize(int stamp) {
		return stamp < STAMP_COUNT ? stamp : stamp - STAMP_COUNT;
	}
}
