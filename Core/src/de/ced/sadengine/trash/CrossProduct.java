package de.ced.sadengine.trash;

import de.ced.sadengine.utils.SadVector;

import static de.ced.sadengine.utils.SadValue.pow;

public class CrossProduct {
	
	private CrossProduct() {
		float left;
		float right;
		float bottom;
		float top;
		float near;
		float far;
		
		SadVector pointA = new SadVector(0f, 0f, 0f);
		SadVector pointB = new SadVector(1f, 0f, 0f);
		SadVector pointC = new SadVector(1f, 1f, 0f);
		SadVector pointD = new SadVector(0f, 1f, 0f);
		SadVector viewerPosition = new SadVector(2f, 0.5f, 3f);
		float ar = pointB.clone().add(viewerPosition).getLength();
		float br = pointA.clone().add(viewerPosition).getLength();
		float cr = pointA.clone().add(pointB).getLength();
		float ap = pointD.clone().add(viewerPosition).getLength();
		float bp = pointA.clone().add(viewerPosition).getLength();
		float cp = pointA.clone().add(pointD).getLength();
		System.out.println(ar);
		System.out.println(br);
		System.out.println(cr);
		System.out.println(ap);
		System.out.println(bp);
		System.out.println(cp);
		float cosBetaR = pow(br) - pow(cr) - pow(ar) / (-2f * cr * ar);
		/*while (cosBetaR > 2 * PI) {
			cosBetaR -= (2 * PI);
		}*/
		float r = br * cosBetaR;
		float cosBetaP = pow(bp) - pow(cp) - pow(ap) / (-2f * cp * ap);
		float p = bp * cosBetaP;
		
		System.out.println(cosBetaR);
		System.out.println(p);
		
		SadVector pointR = pointA.clone().invert().add(pointB).setLength(r).add(pointA);
		
		SadVector pointQ = pointA.clone().invert().add(pointD).setLength(p).add(pointA);
		
		SadVector zero = pointR.clone().add(pointQ);
		
		near = zero.clone().invert().add(viewerPosition).getLength();
		
		left = -r;
		right = left + cr;
		bottom = -p;
		top = bottom + cp;
		
		System.out.println(near);
		System.out.println(left);
		System.out.println(right);
		System.out.println(bottom);
		System.out.println(top);
	}
	
	public static void main(String[] args) {
		new CrossProduct();
	}
}
