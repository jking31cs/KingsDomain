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
	
	/**
	 * Taken from the wikipedia page on line-line intersection.
	 * @param e
	 * @return null if parallel, else the intersection point.
	 */
	public Point intersectionPoint(Edge e) {
		float xTop = (p1.asVec().vectorProduct(p2.asVec())*(e.p1.x - e.p2.x)) - 
				((p1.x-p2.x)*e.p1.asVec().vectorProduct(e.p2.asVec()));
		float bot = ((p1.x -p2.x)*(e.p1.y-e.p2.y)) - ((p1.y-p2.y)*(e.p1.x-e.p2.x));
		float yTop = (p1.asVec().vectorProduct(p2.asVec())*(e.p1.y - e.p2.y)) - 
				((p1.y-p2.y)*e.p1.asVec().vectorProduct(e.p2.asVec()));
		
		if (bot == 0) {
			return null;
		}
		return new Point(xTop/bot, yTop/bot);
	}
	
	/**
	 * Finds the midpoint of the edge.
	 * @return
	 */
	public Point midPoint() {
		Vector v = p1.asVec().add(p2.asVec()).mul(.5f);
		return new Point(v.x,v.y);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((p1 == null) ? 0 : p1.hashCode());
		result = prime * result + ((p2 == null) ? 0 : p2.hashCode());
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
		Edge other = (Edge) obj;
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
		return true;
	}

	@Override
	public String toString() {
		return "Edge [p1=" + p1 + ", p2=" + p2 + "]";
	}
	
	
}
