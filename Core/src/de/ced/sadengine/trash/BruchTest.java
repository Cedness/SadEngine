package de.ced.sadengine.trash;

public class BruchTest {

	public BruchTest() {
		Bruch bx = new Bruch(7, 10);
		Bruch by = new Bruch(1, 10);
		Bruch bz = Bruchrechner.addieren(bx, by);
		System.out.println(bz.toString());
	}

	public static void main(String[] args) {
		new BruchTest();
	}
}
