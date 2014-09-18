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
