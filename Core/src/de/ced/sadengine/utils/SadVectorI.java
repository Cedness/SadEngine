package de.ced.sadengine.utils;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public interface SadVectorI {
	
	SadVector identify();
	
	float getLength();
	
	float getSquaredLength();
	
	SadVector setLength(float length);
	
	SadVector addLength(float length);
	
	SadVector mulLength(float length);
	
	SadVector normalize();
	
	float dotProduct(SadVector vector);
	
	float dotProduct(float... values);
	
	SadVector crossProduct(float... values);
	
	SadVector crossProduct(SadVector vector);
	
	float checkIndex(float v);
	
	int getDimension();
	
	SadVector setDimension(int dimension);
	
	float x();
	
	float y();
	
	float z();
	
	float a();
	
	float get(int i);
	
	SadVector x(float x);
	
	SadVector y(float y);
	
	SadVector z(float z);
	
	SadVector a(float a);
	
	SadVector set(int i, float value);
	
	SadVector set(float... values);
	
	SadVector set(float scalar);
	
	SadVector set(SadVector vector);
	
	SadVector mulX(float x);
	
	SadVector mulY(float y);
	
	SadVector mulZ(float z);
	
	SadVector mulA(float a);
	
	SadVector mul(int i, float value);
	
	SadVector mul(float... values);
	
	SadVector mul(float scalar);
	
	SadVector mul(SadVector vector);
	
	SadVector invert();
	
	SadVector addX(float x);
	
	SadVector addY(float y);
	
	SadVector addZ(float z);
	
	SadVector addA(float a);
	
	SadVector add(int i, float value);
	
	SadVector add(float... values);
	
	SadVector add(float scalar);
	
	SadVector add(SadVector vector);
	
	SadVector rot(int i, float radAngle);
	
	boolean dimensionEquals(SadVector vector);
	
	boolean dimensionEquals(float[] values);
	
	boolean contentEquals(SadVector vector);
	
	boolean contentEquals(float[] values);
	
	SadVector sync(SadVector vector);
	
	SadVector unSync();
	
	SadVector clone();
	
	@Override
	String toString();
}
