package de.ced.sadengine.trash;

public class SadColor {
	
	public float r = 1f;
	public float g = 1f;
	public float b = 1f;
	public float a = 1f;
	
	public SadColor() {
	
	}
	
	public SadColor(float r, float g, float b, float a) {
		set(r, g, b, a);
	}
	
	public void set(float r, float g, float b, float a) {
		set(r, g, b);
		setA(a);
	}
	
	public void set(float r, float g, float b) {
		setR(r);
		setG(g);
		setB(b);
	}
	
	public float getR() {
		return r;
	}
	
	public void setR(float r) {
		this.r = correct(r);
	}
	
	public float getG() {
		return g;
	}
	
	public void setG(float g) {
		this.g = correct(g);
	}
	
	public float getB() {
		return b;
	}
	
	public void setB(float b) {
		this.b = correct(b);
	}
	
	public float getA() {
		return a;
	}
	
	public void setA(float a) {
		this.a = correct(a);
	}
	
	private float correct(float value) {
		return 0f <= value ? value <= 1f ? value : 1f : 0f;
	}
	
	public SadColor clone() {
		return new SadColor(r, g, b, a);
	}
}
