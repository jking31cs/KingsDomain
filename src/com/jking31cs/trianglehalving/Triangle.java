package com.jking31cs.trianglehalving;

import static processing.core.PApplet.*;

import java.util.Arrays;
/**
 * Simple Triangle class.
 * @author jking31
 * @Deprecated  Use ConvexShape instead.
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

	/**
	 * Finds centroid via average.
	 * @return
	 */
	public Point centroid() {
		Vector v = p1.asVec().add(p2.asVec()).add(p3.asVec()).mul(1/3f);
		return new Point(v.x, v.y);
	}

	/**
	 * Finds shortest possible cut via ball-morph.
	 * @return
	 */
	public Edge shortestCut() {
		float minCut = Float.MAX_VALUE;
		Point startMinCut = null;
		Point endMinCut = null;
		for (Point p : Arrays.asList(p1, p2, p3)) {
			Edge startEdge;
			Edge endEdge;

			if (p.equals(p1)) {
				startEdge = e3;
				endEdge = e1;
			} else if (p.equals(p2)) {
				startEdge = e1;
				endEdge = e2;
			} else {
				startEdge = e2;
				endEdge = e3;
			}
			
			Point startCutPoint = startEdge.midPoint();
			Edge edgeToLookAt = startEdge;
			Vector r1Vec = startEdge.asVec().rotate(PI/2).normalize();
			boolean smallCutFound = false;
			while (!smallCutFound) {
				boolean match = false;
				Edge tempEdge = endEdge;
				Point endCutPoint = endEdge.midPoint();
				Vector rVec = endEdge.asVec().rotate(PI/2).normalize();
				Point center = null;
				while (!match) {
					center = new Edge(endCutPoint, endCutPoint.add(rVec))
							.intersectionPoint(new Edge(startCutPoint, startCutPoint.add(r1Vec)));
					if (abs(center.distTo(startCutPoint) - center.distTo(endCutPoint)) <= .5f) {
						match = true;
					} else if (center.distTo(startCutPoint) > center.distTo(endCutPoint)) {
						tempEdge = new Edge(tempEdge.p1, endCutPoint);
						endCutPoint = tempEdge.midPoint();
					} else {
						tempEdge = new Edge(endCutPoint, tempEdge.p2);
						endCutPoint = tempEdge.midPoint();
					}
					if (abs(tempEdge.p1.distTo(tempEdge.p2)) <= .001) {
						break;
					}
				}
				if (abs(tempEdge.p1.distTo(tempEdge.p2)) <= .001) {
					break;
				}
				Triangle halfTriangle = new Triangle(startCutPoint, p, endCutPoint);
				if (abs(halfTriangle.area()*2f - area()) <= 5f) {
					smallCutFound = true;
					minCut = Math.min(minCut, startCutPoint.distTo(endCutPoint));
					startMinCut = startCutPoint;
					endMinCut = endCutPoint;
					
				} else if (halfTriangle.area()*2f > area()) {
					edgeToLookAt = new Edge(startCutPoint, edgeToLookAt.p2);
					startCutPoint = edgeToLookAt.midPoint();
				} else {
					edgeToLookAt = new Edge(edgeToLookAt.p1, startCutPoint);
					startCutPoint = edgeToLookAt.midPoint();
				}
				if (abs(edgeToLookAt.p1.distTo(edgeToLookAt.p2)) <= .001) {
					break;
				}
			}
		}	
		if (startMinCut != null && endMinCut != null) {
			return new Edge(startMinCut, endMinCut);
		}
		return null;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((e1 == null) ? 0 : e1.hashCode());
		result = prime * result + ((e2 == null) ? 0 : e2.hashCode());
		result = prime * result + ((e3 == null) ? 0 : e3.hashCode());
		result = prime * result + ((p1 == null) ? 0 : p1.hashCode());
		result = prime * result + ((p2 == null) ? 0 : p2.hashCode());
		result = prime * result + ((p3 == null) ? 0 : p3.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Triangle other = (Triangle) obj;
		if (e1 == null) {
			if (other.e1 != null)
				return false;
		} else if (!e1.equals(other.e1))
			return false;
		if (e2 == null) {
			if (other.e2 != null)
				return false;
		} else if (!e2.equals(other.e2))
			return false;
		if (e3 == null) {
			if (other.e3 != null)
				return false;
		} else if (!e3.equals(other.e3))
			return false;
		if (p1 == null) {
			if (other.p1 != null)
				return false;
		} else if (!p1.equals(other.p1))
			return false;
		if (p2 == null) {
			if (other.p2 != null)
				return false;
		} else if (!p2.equals(other.p2))
			return false;
		if (p3 == null) {
			if (other.p3 != null)
				return false;
		} else if (!p3.equals(other.p3))
			return false;
		return true;
	}
	
	
}
