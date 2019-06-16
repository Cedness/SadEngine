package de.ced.sadengine.utils;

import de.ced.sadengine.objects.SadCamera;

import static de.ced.sadengine.utils.SadValue.*;

public class SadGLMatrix {
	
	private float m00;
	private float m01;
	private float m02;
	private float m03;
	private float m10;
	private float m11;
	private float m12;
	private float m13;
	private float m20;
	private float m21;
	private float m22;
	private float m23;
	private float m30;
	private float m31;
	private float m32;
	private float m33;
	
	public SadGLMatrix identity() {
		m00 = 1f;
		m01 = 0f;
		m02 = 0f;
		m03 = 0f;
		m10 = 0f;
		m11 = 1f;
		m12 = 0f;
		m13 = 0f;
		m20 = 0f;
		m21 = 0f;
		m22 = 1f;
		m23 = 0f;
		m30 = 0f;
		m31 = 0f;
		m32 = 0f;
		m33 = 1f;
		return this;
	}
	
	public void mul(SadGLMatrix mat) {
		SadGLMatrix old = clone();
		m00 = old.m00 * mat.m00 + old.m01 * mat.m10 + old.m02 * mat.m20 + old.m03 * mat.m30;
		m01 = old.m00 * mat.m01 + old.m01 * mat.m11 + old.m02 * mat.m21 + old.m03 * mat.m31;
		m02 = old.m00 * mat.m02 + old.m01 * mat.m12 + old.m02 * mat.m22 + old.m03 * mat.m32;
		m03 = old.m00 * mat.m03 + old.m01 * mat.m13 + old.m02 * mat.m23 + old.m03 * mat.m33;
		m10 = old.m10 * mat.m00 + old.m11 * mat.m10 + old.m12 * mat.m20 + old.m13 * mat.m30;
		m11 = old.m10 * mat.m01 + old.m11 * mat.m11 + old.m12 * mat.m21 + old.m13 * mat.m31;
		m12 = old.m10 * mat.m02 + old.m11 * mat.m12 + old.m12 * mat.m22 + old.m13 * mat.m32;
		m13 = old.m10 * mat.m03 + old.m11 * mat.m13 + old.m12 * mat.m23 + old.m13 * mat.m33;
		m20 = old.m20 * mat.m00 + old.m21 * mat.m10 + old.m22 * mat.m20 + old.m23 * mat.m30;
		m21 = old.m20 * mat.m01 + old.m21 * mat.m11 + old.m22 * mat.m21 + old.m23 * mat.m31;
		m22 = old.m20 * mat.m02 + old.m21 * mat.m12 + old.m22 * mat.m22 + old.m23 * mat.m32;
		m23 = old.m20 * mat.m03 + old.m21 * mat.m13 + old.m22 * mat.m23 + old.m23 * mat.m33;
		m30 = old.m30 * mat.m00 + old.m31 * mat.m10 + old.m32 * mat.m20 + old.m33 * mat.m30;
		m31 = old.m30 * mat.m01 + old.m31 * mat.m11 + old.m32 * mat.m21 + old.m33 * mat.m31;
		m32 = old.m30 * mat.m02 + old.m31 * mat.m12 + old.m32 * mat.m22 + old.m33 * mat.m32;
		m33 = old.m30 * mat.m03 + old.m31 * mat.m13 + old.m32 * mat.m23 + old.m33 * mat.m33;
	}
	
	public float[] toArray() {
		return new float[]{
				m00, m01, m02, m03,
				m10, m11, m12, m13,
				m20, m21, m22, m23,
				m30, m31, m32, m33
		};
	}
	
	public SadGLMatrix clone() {
		SadGLMatrix clone = new SadGLMatrix();
		clone.m00 = m00;
		clone.m01 = m01;
		clone.m02 = m02;
		clone.m03 = m03;
		clone.m10 = m10;
		clone.m11 = m11;
		clone.m12 = m12;
		clone.m13 = m13;
		clone.m20 = m20;
		clone.m21 = m21;
		clone.m22 = m22;
		clone.m23 = m23;
		clone.m30 = m30;
		clone.m31 = m31;
		clone.m32 = m32;
		clone.m33 = m33;
		return clone;
	}
	
	public String toString() {
		return
				m00 + "/" + m01 + "/" + m02 + "/" + m03 + "\n" +
						m10 + "/" + m11 + "/" + m12 + "/" + m13 + "\n" +
						m20 + "/" + m21 + "/" + m22 + "/" + m23 + "\n" +
						m30 + "/" + m31 + "/" + m32 + "/" + m33 + "\n";
	}
	
	public void transformationMatrix(SadVector p, SadVector r, SadVector z) {
		float rx = toRadians(r.x());
		float ry = toRadians(r.y());
		float rz = toRadians(r.z());
		float cx = cos(rx);
		float sx = sin(rx);
		float cy = cos(ry);
		float sy = sin(ry);
		float cz = cos(rz);
		float sz = sin(rz);
		
		m00 = z.x() * cy * cz;
		m01 = z.x() * cy * -sz;
		m02 = z.x() * sy;
		m10 = z.y() * (-sx * -sy * cz + cx * sz);
		m11 = z.y() * (-sx * -sy * -sz + cx * cz);
		m12 = z.y() * -sx * cy;
		m20 = z.z() * (cx * -sy * cz + sx * sz);
		m21 = z.z() * (cx * -sy * sz + sx * cz);
		m22 = z.z() * cx * cy;
		m03 = p.x();
		m13 = p.y();
		m23 = p.z();
	}
	
	public void viewMatrix(SadCamera camera, SadVector l, SadVector u, SadVector f) {
		//Rotation: x-Pitch y-Yaw z-Roll
		
		SadVector p = camera.getPosition();
		
		f.set(camera.getDirection());
		u.set(camera.getUpWorld());
		l.set(f.clone());
		l.crossProduct(u);
		u.set(f.clone());
		u.crossProduct(l).invert();
		
		float roll = camera.getRotation().z();
		if (roll != 0) {
			roll = toRadians(roll);
			u.mul(cos(roll));
			l.mul(sin(roll));
			u.add(l);
			
			l = f.clone();
			l.crossProduct(u);
		}
		
		m00 = l.x();
		m10 = l.y();
		m20 = l.z();
		m01 = u.x();
		m11 = u.y();
		m21 = u.z();
		m02 = f.x();
		m12 = f.y();
		m22 = f.z();
		
		m30 = -l.x() * p.x() - l.y() * p.y() - l.z() * p.z();
		m31 = -u.x() * p.x() - u.y() * p.y() - u.z() * p.z();
		m32 = -f.x() * p.x() - f.y() * p.y() - f.z() * p.z();
	}
	
	public void projectionMatrix(SadCamera camera, float aspectRatio) {
		float n = camera.getNear();
		float f = camera.getFar();
		float t = tan(toRadians(camera.getFov() * 0.5f)) * n;
		if (camera.isHorizontalFov())
			t /= aspectRatio;
		float r = t * aspectRatio;
		
		if (!camera.isOrtho()) {
			m00 = n / r;
			m20 = 0;
			m30 = 0;
			m11 = n / t;
			m21 = 0;
			m31 = 0;
			m22 = (-f - n) / (f - n);
			m32 = (-2 * f * n) / (f - n);
			m23 = -1;
			m33 = 0;
		} else {
			m00 = 1 / r;
			m20 = 0;
			m30 = 0;
			m11 = 1 / t;
			m21 = 0;
			m31 = 0;
			m22 = (-2) / (f - n);
			m32 = -(f + n) / (f - n);
			m23 = 0;
			m33 = 1;
		}
	}
}
