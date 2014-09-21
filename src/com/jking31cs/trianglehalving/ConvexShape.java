package com.jking31cs.trianglehalving;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

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
		this.points = Arrays.asList(points);
		edges = new ArrayList<>();
		Iterator<Point> pointIt = this.points.iterator();
		Point prev = pointIt.next();
		while (pointIt.hasNext()) {
			Point cur = pointIt.next();
			edges.add(new Edge(prev, cur));
			prev = cur;
		}
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
}
