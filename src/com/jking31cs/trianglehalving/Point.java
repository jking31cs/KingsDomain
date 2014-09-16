package com.jking31cs.trianglehalving;

/**
 * Simple class to hold a point in 2D space.
 * @author jking31
 */
public class Point {
	public final Float x;
	public final Float y;
	
	public Point(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector asVec() {
		return new Vector(x,y);
	}
}
