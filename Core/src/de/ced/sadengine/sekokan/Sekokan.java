package de.ced.sadengine.sekokan;

public class Sekokan {
	
	private Sekokengine engine;
	private final int[][][] referenceFields = new int[114][20][20];
	
	private int[][] currentfield = new int[20][20];
	private int currentLevel = 0;
	private int xPlayer, yPlayer;
	private int xTarget, yTarget;
	private int xBehindTarget, yBehindTarget;
	private int player;
	private int target;
	private int behindTarget;
	
	public static void main(String[] args) {
		new Sekokan();
	}
	
	private Sekokan() {
		for (int i = 0; i < referenceFields.length; i++) {
			referenceFields[i] = SekoLoader.file(i + 1);
		}
		engine = new Sekokengine(this, referenceFields);
	}
	
	void setup() {
		loadLevel(0);
	}
	
	int getLevel() {
		return currentLevel;
	}
	
	void setLevel(int level) {
		if (currentLevel == level)
			return;
		loadLevel(level < 0 ? 0 : (level >= 114 ? 113 : level));
	}
	
	private void loadLevel(int level) {
		for (int x = 0; x < currentfield.length; x++) {
			for (int y = 0; y < currentfield[0].length; y++) {
				currentfield[x][y] = referenceFields[level][x][y];
				if (isPlayer(currentfield[x][y])) {
					xPlayer = x;
					yPlayer = y;
					System.out.println(x + " " + y);
				}
			}
		}
		currentLevel = level;
		engine.load(level);
	}
	
	void move(int x, int y) {
		xTarget = xPlayer + x;
		yTarget = yPlayer + y;
		xBehindTarget = xTarget + x;
		yBehindTarget = yTarget + y;
		player = currentfield[xPlayer][yPlayer];
		target = currentfield[xTarget][yTarget];
		behindTarget = currentfield[xBehindTarget][yBehindTarget];
		
		if (isWall(target)) {
			return;
		}
		
		if (isBox(target)) {
			if (isWall(behindTarget) || isBox(behindTarget)) {
				return;
			}
			currentfield[xBehindTarget][yBehindTarget] = isFinish(behindTarget) ? 6 : 2;
		}
		
		currentfield[xTarget][yTarget] = isFinish(target) ? 5 : 3;
		currentfield[xPlayer][yPlayer] = isFinish(player) ? 4 : 7;
		System.out.println("mv " + xPlayer + "/" + yPlayer + " " + xTarget + "/" + yTarget + " " + xBehindTarget + "/" + yBehindTarget);
		engine.move(xBehindTarget, yBehindTarget, xTarget, yTarget);
		engine.move(xTarget, yTarget, xPlayer, yPlayer);
		xPlayer = xTarget;
		yPlayer = yTarget;
	}
	
	public static boolean isBox(int type) {
		return type == 2 || type == 6;
	}
	
	public static boolean isWall(int type) {
		return type == 1;
	}
	
	public static boolean isPlayer(int type) {
		return type == 3 || type == 5;
	}
	
	public static boolean isFinish(int type) {
		return type == 4 || type == 5 || type == 6;
	}
	
	public static boolean isMoveable(int type) {
		return type == 2 || type == 3 || type == 5 || type == 6;
	}
}

/*	1 - Mauer
 *  2 - Kiste
 *  3 - Spieler
 *  4 - Ziel
 *  5 - Spieler ueber Ziel
 *  6 - Kiste ueber Ziel
 *  7 - Leeres Feld
 */
