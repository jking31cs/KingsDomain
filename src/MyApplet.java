import java.util.Arrays;

import com.jking31cs.trianglehalving.Edge;
import com.jking31cs.trianglehalving.Point;
import com.jking31cs.trianglehalving.Triangle;
import com.jking31cs.trianglehalving.Vector;

import processing.core.PApplet;


public class MyApplet extends PApplet {

	private static final long serialVersionUID = 1L;

	Triangle triangle;
	
	boolean changeP1,changeP2,changeP3;
	
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
	public void mousePressed() {
		Point mousePoint = new Point(mouseX, mouseY);
		if (abs(mousePoint.distTo(triangle.p1)) <= 3) {
			changeP1 = true;
			System.out.println("Changing P1 for reals");
		}
		if (abs(mousePoint.distTo(triangle.p2)) <= 3) {
			changeP2 = true;
		}
		if (abs(mousePoint.distTo(triangle.p3)) <= 3) {
			changeP3 = true;
		}
	}
	
	@Override
	public void mouseDragged() {
		System.out.println("Mouse is dragging.");
		if (changeP1) {
			System.out.println("Changing P1");
			triangle = new Triangle(new Point(mouseX, mouseY), triangle.p2, triangle.p3);
		}
		if (changeP2) {
			System.out.println("Changing P2");
			triangle = new Triangle(triangle.p1, new Point(mouseX, mouseY), triangle.p3);
		}
		if (changeP3) {
			System.out.println("Changing P3");
			triangle = new Triangle(triangle.p1, triangle.p2, new Point(mouseX, mouseY));
		}
	}
	
	@Override
	public void mouseReleased() {
		changeP1=changeP2=changeP3=false;
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
		
		if (!changeP1 && !changeP2 && !changeP3) {
			System.out.println("doing logic.");
			logic();
		}
		
		
	}
	
	private void logic() {
		stroke(0,0,255);
		float minCut = Float.MAX_VALUE;
		for (Point p : Arrays.asList(triangle.p1, triangle.p2, triangle.p3)) {
			Edge startEdge;
			Edge endEdge;

			if (p.equals(triangle.p1)) {
				startEdge = triangle.e3;
				endEdge = triangle.e1;
			} else if (p.equals(triangle.p2)) {
				startEdge = triangle.e1;
				endEdge = triangle.e2;
			} else {
				startEdge = triangle.e2;
				endEdge = triangle.e3;
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
					if (abs(tempEdge.p1.distTo(tempEdge.p2)) <= .001) {
						break;
					}
				}
				if (abs(tempEdge.p1.distTo(tempEdge.p2)) <= .001) {
					break;
				}
				Triangle halfTriangle = new Triangle(startCutPoint, p, endCutPoint);
				if (abs(halfTriangle.area()*2f - triangle.area()) <= .05f) {
					smallCutFound = true;
					minCut = Math.min(minCut, startCutPoint.distTo(endCutPoint));
					line(startCutPoint.x, startCutPoint.y, endCutPoint.x, endCutPoint.y);
				} else if (halfTriangle.area()*2f > triangle.area()) {
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
		System.out.println("Shortest Cut Length: " + minCut);
		
	}
	
	public static void main(String[] args) {
		PApplet.main("MyApplet");
	}

}
