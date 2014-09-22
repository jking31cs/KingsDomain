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
	public Edge minCut() {
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
				while (!match) {
					center = new Edge(endCutPoint, endCutPoint.add(rVec))
							.intersectionPoint(new Edge(startCutPoint, startCutPoint.add(r1Vec)));
					if (center == null) {
						break;
					}
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
				if (!match) {
					break;
				}
				int beginIndex = edges.indexOf(startEdge);
				int endIndex = edges.indexOf(endEdge);
				List<Point> newShapePoints = new ArrayList<>();
				newShapePoints.add(startCutPoint);
				newShapePoints.add(startEdge.p2);
				for (int i = beginIndex+1; i < endIndex; i++) {
					Edge e = edges.get(i);
					newShapePoints.add(e.p1);
					newShapePoints.add(e.p2);
				}
				if (startEdge.p2 != endEdge.p1) newShapePoints.add(endEdge.p1);
				newShapePoints.add(endCutPoint);
				
				ConvexShape newShape = new ConvexShape(newShapePoints);
				
				if (abs(newShape.area()*2f - area()) <= 5f) {
					smallCutFound = true;
					minCut = Math.min(minCut, startCutPoint.distTo(endCutPoint));
					startMinCut = startCutPoint;
					endMinCut = endCutPoint;
					
				} else if (newShape.area()*2f > area()) {
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
