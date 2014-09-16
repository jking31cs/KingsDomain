package com.jking31cs.trianglehalving;

import static processing.core.PApplet.*;
/**
 * Simple Triangle class.
 * @author jking31
 */
public class Triangle {
	public final Point p1;
	public final Point p2;
	public final Point p3;
	public final Edge e1;
	public final Edge e2;
	public final Edge e3;
	
	public Triangle(Point p1, Point p2, Point p3) {
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.e1 = new Edge(p1,p2);
		this.e2 = new Edge(p2,p3);
		this.e3 = new Edge(p3,p1);
	}

	/**
	 * Using Heron's Formula.
	 * @return
	 */
	public float area() {
		float s = (e1.length() + e2.length() + e3.length()) * .5f;
		return sqrt(s*(s-e1.length())*(s-e2.length())*(s-e3.length()));
	}
}
