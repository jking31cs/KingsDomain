

import com.jking31cs.trianglehalving.ConvexShape;
import com.jking31cs.trianglehalving.Edge;
import com.jking31cs.trianglehalving.Point;
import com.jking31cs.trianglehalving.Vector;

import processing.core.PApplet;


public class TriangleExample extends PApplet {

	ConvexShape triangle;
	boolean changeP1,changeP2,changeP3;
	
	@Override
	public void mousePressed() {
		Point mousePoint = new Point(mouseX, mouseY);
		if (abs(mousePoint.distTo(triangle.points.get(0))) <= 5) {
			changeP1 = true;
		}
		if (abs(mousePoint.distTo(triangle.points.get(1))) <= 5) {
			changeP2 = true;
		}
		if (abs(mousePoint.distTo(triangle.points.get(2))) <= 5) {
			changeP3 = true;
		}
	}
	
	@Override
	public void mouseDragged() {
		if (changeP1) {
			triangle = new ConvexShape(new Point(mouseX, mouseY), triangle.points.get(1), triangle.points.get(2));
		}
		if (changeP2) {
			triangle = new ConvexShape(triangle.points.get(0), new Point(mouseX, mouseY), triangle.points.get(2));
		}
		if (changeP3) {
			triangle = new ConvexShape(triangle.points.get(0), triangle.points.get(1), new Point(mouseX, mouseY));
		}
	}
	
	@Override
	public void mouseReleased() {
		changeP1=changeP2=changeP3=false;
	}
	
	@Override
	public void setup() {
		size(600,600);
		Point p1 = new Point(300,300);
		Point p2 = new Point(400, 400);
		Edge e = new Edge(p1, p2);
		Vector r = e.asVec().rotate(PI/3).normalize();
		Point p3 = p1.add(r.mul(e.length()));
		triangle = new ConvexShape(p1,p2,p3);
	}
	
	@Override
	public void draw() {
		background(255);
		stroke(255,0,0);
		fill(255);
		drawPoint(triangle.points.get(0));
		drawPoint(triangle.points.get(1));
		drawPoint(triangle.points.get(2));
		
		stroke(0);
		drawLine(triangle.points.get(0),triangle.points.get(1));
		drawLine(triangle.points.get(1),triangle.points.get(2));
		drawLine(triangle.points.get(2),triangle.points.get(0));
		
		float targetArea = triangle.area() / 4f;
		
		Edge cut1 = triangle.minCut(targetArea);
		stroke(0,0,255);
		if (cut1 != null) {
			drawLine(cut1);
			ConvexShape inner1 = triangle.shapeFromCut(cut1, true);
			Edge cut2 = inner1.minCut(targetArea);
			if (cut2 != null) {
				drawLine(cut2);
				ConvexShape inner2 = inner1.shapeFromCut(cut2, true);
				Edge cut3 = inner2.minCut(targetArea);
				if (cut3 != null) drawLine(cut3);
			}
			
		}
		
		
//		stroke(0,255,0);
//		Vector v = triangle.points.get(0).asVec().add(triangle.points.get(1).asVec()).add(triangle.points.get(2).asVec()).mul(1/3f);
//		Point centroid = new Point(v.x, v.y);
//		drawLine(new Edge(triangle.points.get(0),triangle.points.get(1)).midPoint(),centroid);
//		drawLine(new Edge(triangle.points.get(1),triangle.points.get(2)).midPoint(),centroid);
//		drawLine(new Edge(triangle.points.get(2),triangle.points.get(0)).midPoint(),centroid);
	}
	
	private void drawPoint(Point p) { ellipse(p.x,p.y,10,10); }
	
	private void drawLine(Edge e) { drawLine(e.p1, e.p2); }
	private void drawLine(Point p1, Point p2) { line(p1.x, p1.y, p2.x, p2.y); }
	
	public static void main(String[] args) {
		PApplet.main("TriangleExample");
	}
}
