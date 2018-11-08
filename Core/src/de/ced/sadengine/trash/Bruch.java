package de.ced.sadengine.trash;

public class Bruch {

	private int zaehler = 1;
	private int nenner = 1;

	public Bruch() {
	}

	public Bruch(int zaehler, int nenner) {
		setZaehler(zaehler);
		setNenner(nenner);
	}

	public int getZaehler() {
		return zaehler;
	}

	public void setZaehler(int zaehler) {
		this.zaehler = zaehler;
	}

	public int getNenner() {
		return nenner;
	}

	public void kehrwert() {
		int temp = zaehler;
		zaehler = nenner;
		nenner = temp;
	}

	public void kuerzen() {
		int ggt = ggt(zaehler, nenner);
		zaehler /= ggt;
		nenner /= ggt;
	}

	public static int ggt(int z1, int z2) {
		int temp;

		if (z2 > z1) {
			temp = z2;
			z2 = z1;
			z1 = temp;
		}

		do {
			temp = z1 % z2;
			z1 = z2;
			z2 = temp;
		} while (z2 > 0);

		return z1;
	}

	public static int kgv(int z1, int z2) {
		return z1 * z2 / ggt(z1, z2);
	}

	public static int ggtx(int z1, int z2) {
		for (int i = z1; i <= z1 * z2; i += z1) {
			for (int j = z2; j <= z1 * z2; j += z2) {
				if (i == j)
					return z1 * z2 / i;
			}
		}
		return 1;
	}

	public static int kgvx(int z1, int z2) {
		for (int i = z1; i <= z1 * z2; i += z1) {
			for (int j = z2; j <= z1 * z2; j += z2) {
				if (i == j)
					return i;
			}
		}
		return 1;
	}

	public void setNenner(int nenner) {
		this.nenner = nenner == 0 ? 1 : nenner;
	}

	@Override
	public String toString() {
		return getZaehler() + "/" + getNenner();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Bruch))
			return false;
		Bruch b1 = clone();
		Bruch b2 = ((Bruch) obj).clone();
		b1.kuerzen();
		b2.kuerzen();
		return b1.getZaehler() == b2.getZaehler() && b1.getNenner() == b2.getNenner();
	}

	@Override
	public int hashCode() {
		Bruch clone = clone();
		clone.kuerzen();
		return (int) (clone.getZaehler() * 2367.236725638 + clone.getNenner() * 86754654635.12358);
	}

	public Bruch clone() {
		return new Bruch(zaehler, nenner);
	}
}
