

import com.jking31cs.trianglehalving.ConvexShape;
import com.jking31cs.trianglehalving.Edge;
import com.jking31cs.trianglehalving.Point;
import com.jking31cs.trianglehalving.Vector;

import processing.core.PApplet;


public class TriangleExample extends PApplet {

	ConvexShape triangle;
	
	@Override
	public void setup() {
		size(600,600);
		background(255);
		Point p1 = new Point(300,300);
		Point p2 = new Point(400, 400);
		Edge e = new Edge(p1, p2);
		Vector r = e.asVec().rotate(PI/3).normalize();
		Point p3 = p1.add(r.mul(e.length()));
		
		triangle = new ConvexShape(p1,p2,p3);
		
		stroke(255,0,0);
		fill(255);
		drawPoint(p1);
		drawPoint(p2);
		drawPoint(p3);
		
		stroke(0);
		drawLine(p1,p2);
		drawLine(p2,p3);
		drawLine(p3,p1);
		
		float targetArea = triangle.area() / 3f;
		
		Edge cut1 = triangle.minCut(targetArea);
		stroke(0,0,255);
		if (cut1 != null) drawLine(cut1);
		
		Edge cut2 = triangle.shapeFromCut(cut1, true).minCut(targetArea);
		if (cut2 != null) drawLine(cut2);
		
		stroke(0,255,0);
		Vector v = p1.asVec().add(p2.asVec()).add(p3.asVec()).mul(1/3f);
		Point centroid = new Point(v.x, v.y);
		drawLine(new Edge(p1,p2).midPoint(),centroid);
		drawLine(new Edge(p2,p3).midPoint(),centroid);
		drawLine(new Edge(p3,p1).midPoint(),centroid);
	}
	
	private void drawPoint(Point p) { ellipse(p.x,p.y,10,10); }
	
	private void drawLine(Edge e) { drawLine(e.p1, e.p2); }
	private void drawLine(Point p1, Point p2) { line(p1.x, p1.y, p2.x, p2.y); }
	
	public static void main(String[] args) {
		PApplet.main("TriangleExample");
	}
}
