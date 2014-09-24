package com.jking31cs.trianglehalving;

import static processing.core.PApplet.abs;
import static processing.core.PConstants.PI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * This holds a list of points which represents the convex shape.  There's no verifying that this is convex, so any
 * concave shapes will just fail miserably in shortest path detection.  Also, all points must be given in counter-clockwise
 * order for calculations to work.  
 * @author jking31
 */
public class ConvexShape {
	
	public final List<Point> points;
	public final List<Edge>  edges;
	
	public ConvexShape(Point... points) {
		this(Arrays.asList(points));
	}
	
	public ConvexShape(List<Point> points) {
		this.points = points;
		edges = new ArrayList<>();
		Iterator<Point> pointIt = this.points.iterator();
		Point prev = pointIt.next();
		while (pointIt.hasNext()) {
			Point cur = pointIt.next();
			edges.add(new Edge(prev, cur));
			prev = cur;
		}
		edges.add(new Edge(prev, this.points.get(0))); //Loop back edge to beginning.
	}
	
	/**
	 * This gets the area using a determinate.
	 * @return
	 */
	public Float area() {
		List<Point> detPoints = new ArrayList<>(points);
		detPoints.add(points.get(0));
		float d1 = 0;
		float d2 = 0;
		Iterator<Point> it = detPoints.iterator();
		Point prev = it.next();
		while (it.hasNext()) {
			Point cur = it.next();
			d1 += prev.x*cur.y;
			d2 += cur.x*prev.y;
			prev = cur;
		}
		return .5f*(d1-d2);
	}
	
	/**
	 * Figures out the min cut via the ball morph algorithm.  The idea is to take
	 * all pairs of edges, find the min cut such that the area is correct, then take the
	 * minimum of all possible cuts.
	 * @return
	 */
	public Edge minCut(float targetArea) {
		float minCut = Float.MAX_VALUE;
		Point startMinCut = null, 
				endMinCut = null;
		
		Set<Pair<Edge>> setOfPairs = edgePairs();
		for (Pair<Edge> pair : setOfPairs) {
			Edge startEdge = pair.o1;
			Edge endEdge = pair.o2;
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
				//Weird Situation where rVec and r1Vec are parallel;
				if (abs(rVec.vectorProduct(r1Vec)) <= 0.001f) {
					endCutPoint = new Edge(startCutPoint, startCutPoint.add(r1Vec)).intersectionPoint(tempEdge);
					center = new Edge(startCutPoint, endCutPoint).midPoint();
					match = true;
				} else {
					while (!match) {
						center = new Edge(endCutPoint, endCutPoint.add(rVec))
								.intersectionPoint(new Edge(startCutPoint, startCutPoint.add(r1Vec)));
						
						if (center == null) {
							break;
						}
						if (abs(center.distTo(startCutPoint) - center.distTo(endCutPoint)) <= .05f) {
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
				}
				
				if (!match) {
					edgeToLookAt = new Edge(startCutPoint, edgeToLookAt.p2);
					startCutPoint = edgeToLookAt.midPoint();
					if (abs(edgeToLookAt.p1.distTo(edgeToLookAt.p2)) <= .001) {
						break;
					}
					continue;
				}
				
				ConvexShape newShape = shapeFromCut(new Edge(startCutPoint, endCutPoint), false);
				
				if (abs(newShape.area() - targetArea) <= 10f) {
					smallCutFound = true;
					minCut = Math.min(minCut, startCutPoint.distTo(endCutPoint));
					startMinCut = startCutPoint;
					endMinCut = endCutPoint;
				} else if (newShape.area() > targetArea) {
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
		if (startMinCut == null || endMinCut == null) return null;
		return new Edge(startMinCut, endMinCut);
	}
	
	public ConvexShape shapeFromCut(Edge cut, boolean opposite) {
		Edge startEdge = null, 
			 endEdge = null;
		for (Edge e : edges) {
			Vector v1 = e.asVec().normalize();
			Vector v2 = new Edge(cut.p1, e.p2).asVec().normalize();
			Vector v3 = new Edge(e.p1, cut.p2).asVec().normalize();
			if (abs(v1.vectorProduct(v2)) < .005f) {
				if (opposite) {
					endEdge = e;
				} else {
					startEdge = e;					
				}
			}
			if (abs(v1.vectorProduct(v3)) < .005f) {
				if (opposite) {
					startEdge = e;
				} else {
					endEdge = e;					
				}
			}
		}
		if (startEdge == null || endEdge == null) {
			return null;
		}
		int beginIndex = edges.indexOf(startEdge);
		int endIndex = edges.indexOf(endEdge);
		List<Point> newShapePoints = new ArrayList<>();
		if (opposite) {
			newShapePoints.add(cut.p2);
		} else {
			newShapePoints.add(cut.p1);			
		}
		int i = beginIndex + 1;
		while (i % edges.size() != endIndex) {
			Edge e = edges.get(i % edges.size());
			newShapePoints.add(e.p1);
			newShapePoints.add(e.p2);
			i++;			
		}
		if (abs(startEdge.p2.distTo(endEdge.p1)) <=.05f) newShapePoints.add(endEdge.p1);
		if (opposite) {
			newShapePoints.add(cut.p1);
		} else {
			newShapePoints.add(cut.p2);
		}
		
		return new ConvexShape(newShapePoints);
	}

	/**
	 * Creates a set of pair of edges.
	 * @return
	 */
	public Set<Pair<Edge>> edgePairs() {
		Set<Pair<Edge>> setOfPairs = new HashSet<>();
		for (Edge e1 : edges) {
			for (Edge e2 : edges) {
				if (e1.equals(e2)) continue;
				setOfPairs.add(new Pair<Edge>(e1, e2));
			}
		}
		return setOfPairs;
	}
}
