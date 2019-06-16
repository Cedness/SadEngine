package de.ced.sadengine.trash;

public class Bruchrechner {

	public static boolean istGleich(Bruch bx, Bruch by) {
		return bx.equals(by);
	}

	public static void add(Bruch bx, Bruch by) {
		calc(bx, by, true);
	}

	public static void sub(Bruch bx, Bruch by) {
		calc(bx, by, false);
	}

	private static void calc(Bruch bx, Bruch by, boolean add) {
		int kgv = Bruch.kgv(bx.getNenner(), by.getNenner());
		bx.setZaehler(bx.getZaehler() * (kgv / bx.getNenner()) + (add ? 1 : -1) * by.getZaehler() * (kgv / by.getNenner()));
		bx.setNenner(kgv);
		bx.kuerzen();
	}

	private static Bruch calculate(Bruch bx, Bruch by, boolean addieren) {
		Bruch res = bx.clone();
		calc(res, by, addieren);
		return res;
	}

	public static Bruch addieren(Bruch bx, Bruch by) {
		return calculate(bx, by, true);
	}

	public static Bruch subtrahieren(Bruch bx, Bruch by) {
		return calculate(bx, by, false);
	}

	public static void mul(Bruch bx, Bruch by) {
		bx.setZaehler(bx.getZaehler() * by.getZaehler());
		bx.setNenner(bx.getNenner() * by.getNenner());
		bx.kuerzen();
	}

	public static void div(Bruch bx, Bruch by) {
		Bruch temp = by.clone();
		temp.kehrwert();
		mul(bx, temp);
	}

	public static Bruch multiplizieren(Bruch bx, Bruch by) {
		Bruch res = bx.clone();
		mul(res, by);
		return res;
	}

	public static Bruch dividieren(Bruch bx, Bruch by) {
		Bruch res = bx.clone();
		div(res, by);
		return res;
	}
}
