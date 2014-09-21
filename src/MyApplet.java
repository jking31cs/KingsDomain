import com.jking31cs.trianglehalving.Edge;
import com.jking31cs.trianglehalving.Point;
import com.jking31cs.trianglehalving.Triangle;

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
		if (abs(mousePoint.distTo(triangle.p1)) <= 5) {
			changeP1 = true;
		}
		if (abs(mousePoint.distTo(triangle.p2)) <= 5) {
			changeP2 = true;
		}
		if (abs(mousePoint.distTo(triangle.p3)) <= 5) {
			changeP3 = true;
		}
	}
	
	@Override
	public void mouseDragged() {
		if (changeP1) {
			triangle = new Triangle(new Point(mouseX, mouseY), triangle.p2, triangle.p3);
		}
		if (changeP2) {
			triangle = new Triangle(triangle.p1, new Point(mouseX, mouseY), triangle.p3);
		}
		if (changeP3) {
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
		
		//Points
		stroke(255,0,0);
		fill(255);
		ellipse(triangle.p1.x,triangle.p1.y,10,10);
		ellipse(triangle.p2.x,triangle.p2.y,10,10);
		ellipse(triangle.p3.x,triangle.p3.y,10,10);
		
		//Labels on vertices
		fill(0);
		this.textSize(12);
		this.text("P1", triangle.p1.x + 10, triangle.p1.y -10);
		this.text("P2", triangle.p2.x + 10, triangle.p2.y -10);
		this.text("P3", triangle.p3.x + 10, triangle.p3.y -10);
		
		//Centroid
		fill(255,255,0);
		Point centroid = triangle.centroid();
		ellipse(centroid.x, centroid.y, 5,5);

		//Edges
		stroke(0);
		line(triangle.e1.p1.x,triangle.e1.p1.y,triangle.e1.p2.x,triangle.e1.p2.y);
		line(triangle.e2.p1.x,triangle.e2.p1.y,triangle.e2.p2.x,triangle.e2.p2.y);
		line(triangle.e3.p1.x,triangle.e3.p1.y,triangle.e3.p2.x,triangle.e3.p2.y);
		
		//Smallest Cut
		stroke(0,0,255);
		Edge cut = triangle.shortestCut();
		if (cut != null) line(cut.p1.x, cut.p1.y, cut.p2.x, cut.p2.y);
		
		//Perpendicular Lines 
//		stroke(0,255,0);
//		Vector perp1 = triangle.e1.asVec().rotate(PI/2).normalize();
//		Point pp1 = triangle.e1.midPoint().add(perp1.mul(500f));
//		line(triangle.e1.midPoint().x, triangle.e1.midPoint().y, pp1.x, pp1.y);
//		
//		Vector perp2 = triangle.e2.asVec().rotate(PI/2).normalize();
//		Point pp2 = triangle.e2.midPoint().add(perp2.mul(500f));
//		line(triangle.e2.midPoint().x, triangle.e2.midPoint().y, pp2.x, pp2.y); 
//		
//		Vector perp3 = triangle.e3.asVec().rotate(PI/2).normalize();
//		Point pp3 = triangle.e3.midPoint().add(perp3.mul(500f));
//		line(triangle.e3.midPoint().x, triangle.e3.midPoint().y, pp3.x, pp3.y); 
		
		
		
		
	}
	
	public static void main(String[] args) {
		PApplet.main("MyApplet");
	}

}
