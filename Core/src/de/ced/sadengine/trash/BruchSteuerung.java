package de.ced.sadengine.trash;

public class BruchSteuerung {

	public BruchSteuerung() {
		Bruch b1 = new Bruch(7, 8);
		Bruch b2 = new Bruch(7, 8);
		Bruch b;
		boolean ergebnis;
		//Überpruefung auf Gleichheit
		ergebnis = Bruchrechner.istGleich(b1, b2);
		System.out.println("Methode istGleich");
		System.out.println("Bruch1: " + b1.toString());
		System.out.println("Bruch2: " + b2.toString());
		System.out.println(ergebnis + "\n");
		b1.setZaehler(7);
		b1.setNenner(8);
		b2.setZaehler(3);
		b2.setNenner(4);
		//Überpruefung auf Gleichheit
		ergebnis = Bruchrechner.istGleich(b1, b2);
		System.out.println("Methode istGleich");
		System.out.println("Bruch1: " + b1.toString());
		System.out.println("Bruch2: " + b2.toString());
		System.out.println(ergebnis + "\n");
		//zu Bruch b1 dazuaddieren
		Bruchrechner.add(b1, b2);
		System.out.println("Methode add");
		System.out.println("Bruch1: " + b1.toString());
		System.out.println("Bruch2: " + b2.toString());
		System.out.println(b1.toString() + "\n");
		//zwei Brüche addieren
		b = Bruchrechner.addieren(b1, b2);
		System.out.println("Methode addieren");
		System.out.println("Bruch1: " + b1.toString());
		System.out.println("Bruch2: " + b2.toString());
		System.out.println(b.toString() + "\n");
		//zwei Brüche subtrahieren, Ergebnis nach b1
		Bruchrechner.sub(b1, b2);
		System.out.println("Methode sub");
		System.out.println("Bruch1: " + b1.toString());
		System.out.println("Bruch2: " + b2.toString());
		System.out.println(b1.toString() + "\n");
		//zwei Brüche subtrahieren
		b = Bruchrechner.subtrahieren(b1, b2);
		System.out.println("Methode subtrahieren");
		System.out.println("Bruch1: " + b1.toString());
		System.out.println("Bruch2: " + b2.toString());
		System.out.println(b.toString() + "\n");
		//zwei Brüche multiplizieren, Ergebnis nach b1
		Bruchrechner.mul(b1, b2);
		System.out.println("Methode mul");
		System.out.println("Bruch1: " + b1.toString());
		System.out.println("Bruch2: " + b2.toString());
		System.out.println(b1.toString() + "\n");
		//zwei Brüche multiplizieren
		b = Bruchrechner.multiplizieren(b1, b2);
		System.out.println("Methode multiplizieren");
		System.out.println("Bruch1: " + b1.toString());
		System.out.println("Bruch2: " + b2.toString());
		System.out.println(b.toString() + "\n");
		//zwei Brüche dividieren, Ergebnis nach b1
		Bruchrechner.div(b1, b2);
		System.out.println("Methode div");
		System.out.println("Bruch1: " + b1.toString());
		System.out.println("Bruch2: " + b2.toString());
		System.out.println(b1.toString() + "\n");
		//zwei Brüche dividieren
		b = Bruchrechner.dividieren(b1, b2);
		System.out.println("Methode dividieren");
		System.out.println("Bruch1: " + b1.toString());
		System.out.println("Bruch2: " + b2.toString());
		System.out.println(b.toString() + "\n");
	}

	public static void main(String[] args) {
		BruchSteuerung b = new BruchSteuerung();
	}
}
