package com.jking31cs.trianglehalving;

public class Edge {
	public final Point p1;
	public final Point p2;
	
	public Edge(Point p1, Point p2) {
		this.p1 = p1;
		this.p2 = p2;
	}
	
	public Vector asVec() {
		return new Vector(p2.x-p1.x, p2.y-p1.y);
	}
	
	public float length() {
		return asVec().getMag();
	}
}
