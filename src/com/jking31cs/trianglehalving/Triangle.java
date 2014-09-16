package com.jking31cs.trianglehalving;

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

}
