package de.ced.sadengine.test;

import de.ced.sadengine.main.SadMainLogic;

import java.io.File;

public class Main implements SadMainLogic {
	
	public static void main(String[] args) {
		File file = new File("");
		System.out.println(file.getAbsolutePath());
	}
}
