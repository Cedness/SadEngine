package de.ced.sadengine.sekokan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SekoLoader {
	
	public static int[][] file(int level) {
		BufferedReader b;
		String zeile;
		String[] splitZeile;
		int file[][] = new int[20][20];
		int x = 0, y = 0;
		
		int leerzeilen = level - 1;
		level = (level - 1) * 20;
		
		try {
			b = new BufferedReader(new InputStreamReader(SekoLoader.class.getResourceAsStream("sokoban.txt")));
			
			while ((zeile = b.readLine()) != null && x++ < level + leerzeilen) ;
			
			while ((zeile = b.readLine()) != null && x++ <= level + 20 + leerzeilen) {
				splitZeile = zeile.split(" ");
				for (int i = 0; i < 20; i++) {
					file[y][i] = Integer.parseInt(splitZeile[i]);
				}
				y++;
			}
			
			b.close();
			
		} catch (IOException e) {
			System.out.println("Fehler beim lesen der Datei");
		}
		
		return file;
	}
}
