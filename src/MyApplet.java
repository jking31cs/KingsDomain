import com.jking31cs.trianglehalving.Edge;
import com.jking31cs.trianglehalving.Point;
import com.jking31cs.trianglehalving.Triangle;
import com.jking31cs.trianglehalving.Vector;

import processing.core.PApplet;


public class MyApplet extends PApplet {

	private static final long serialVersionUID = 1L;

	Triangle triangle;
	
	@Override
	public void setup() {
		size(600,600);
		Point p1 = new Point(300,300);
		Point p2 = new Point(400,450);
		Point p3 = new Point(250,375);
		
		triangle = new Triangle(p1,p2,p3);
	}
	
	@Override
	public void keyPressed() {
		if (this.key == 'q') {
			this.exit();
		}
	}
	
	@Override
	public void draw() {
		background(255);
		
		stroke(255,0,0);
		fill(255);
		ellipse(triangle.p1.x,triangle.p1.y,10,10);
		ellipse(triangle.p2.x,triangle.p2.y,10,10);
		ellipse(triangle.p3.x,triangle.p3.y,10,10);
		
		stroke(0);
		line(triangle.e1.p1.x,triangle.e1.p1.y,triangle.e1.p2.x,triangle.e1.p2.y);
		line(triangle.e2.p1.x,triangle.e2.p1.y,triangle.e2.p2.x,triangle.e2.p2.y);
		line(triangle.e3.p1.x,triangle.e3.p1.y,triangle.e3.p2.x,triangle.e3.p2.y);
		
		stroke(0,0,255);
		Point startCutPoint = triangle.e1.midPoint();
		Edge edgeToLookAt = triangle.e1;
		Vector r1Vec = triangle.e1.asVec().rotate(PI/2).normalize();
		boolean smallCutFound = false;
		while (!smallCutFound) {
			boolean match = false;
			Edge tempEdge = triangle.e2;
			Point endCutPoint = triangle.e2.midPoint();
			Vector rVec = triangle.e2.asVec().rotate(PI/2).normalize();
			while (!match) {
				Point center = new Edge(endCutPoint, endCutPoint.add(rVec))
						.intersectionPoint(new Edge(startCutPoint, startCutPoint.add(r1Vec)));
				if (abs(center.distTo(startCutPoint) - center.distTo(endCutPoint)) <= .05f) {
					match = true;
				} else if (center.distTo(startCutPoint) > center.distTo(endCutPoint)) {
					tempEdge = new Edge(tempEdge.p1, endCutPoint);
					endCutPoint = tempEdge.midPoint();
				} else {
					tempEdge = new Edge(endCutPoint, tempEdge.p2);
					endCutPoint = tempEdge.midPoint();
				}
			}
			Triangle halfTriangle = new Triangle(startCutPoint, triangle.p2, endCutPoint);
			if (abs(halfTriangle.area()*2f - triangle.area()) <= .05f) {
				smallCutFound = true;
				line(startCutPoint.x, startCutPoint.y, endCutPoint.x, endCutPoint.y);
			} else if (halfTriangle.area()*2f > triangle.area()) {
				edgeToLookAt = new Edge(startCutPoint, edgeToLookAt.p2);
				startCutPoint = edgeToLookAt.midPoint();
			} else {
				edgeToLookAt = new Edge(edgeToLookAt.p1, startCutPoint);
				startCutPoint = edgeToLookAt.midPoint();
			}
		}
		
		
		
	}
	
	public static void main(String[] args) {
		PApplet.main("MyApplet");
	}

}
