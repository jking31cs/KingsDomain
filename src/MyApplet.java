import com.jking31cs.trianglehalving.ConvexShape;
import com.jking31cs.trianglehalving.Edge;
import com.jking31cs.trianglehalving.Point;
import com.jking31cs.trianglehalving.Vector;

import processing.core.PApplet;


public class MyApplet extends PApplet {

	private static final long serialVersionUID = 1L;

	ConvexShape triangle;
	
	boolean changeP1,changeP2,changeP3,changeP4;
	
	@Override
	public void setup() {
		size(600,600);
		Point p1 = new Point(284.5583f, 357.6292f);
		Point p2 = new Point(300,300);
		Point p3 = new Point(400,400);
		Point p4 = new Point(342.3708f,415.4417f);
		
		triangle = new ConvexShape(p1,p2,p3,p4);
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
		if (abs(mousePoint.distTo(triangle.points.get(0))) <= 5) {
			changeP1 = true;
		}
		if (abs(mousePoint.distTo(triangle.points.get(1))) <= 5) {
			changeP2 = true;
		}
		if (abs(mousePoint.distTo(triangle.points.get(2))) <= 5) {
			changeP3 = true;
		}
		if (abs(mousePoint.distTo(triangle.points.get(3))) <= 5) {
			changeP4 = true;
		}
	}
	
	@Override
	public void mouseDragged() {
		if (changeP1) {
			triangle = new ConvexShape(new Point(mouseX, mouseY), triangle.points.get(1), triangle.points.get(2), triangle.points.get(3));
		}
		if (changeP2) {
			triangle = new ConvexShape(triangle.points.get(0), new Point(mouseX, mouseY), triangle.points.get(2), triangle.points.get(3));
		}
		if (changeP3) {
			triangle = new ConvexShape(triangle.points.get(0), triangle.points.get(1), new Point(mouseX, mouseY), triangle.points.get(3));
		}
		if (changeP4) {
			triangle = new ConvexShape(triangle.points.get(0), triangle.points.get(1), triangle.points.get(2), new Point(mouseX, mouseY));
		}
	}
	
	@Override
	public void mouseReleased() {
		changeP1=changeP2=changeP3=changeP4=false;
	}
	
	@Override
	public void draw() {
		background(255);
		
		//Points
		stroke(255,0,0);
		fill(255);
		ellipse(triangle.points.get(0).x,triangle.points.get(0).y,10,10);
		ellipse(triangle.points.get(1).x,triangle.points.get(1).y,10,10);
		ellipse(triangle.points.get(2).x,triangle.points.get(2).y,10,10);
		ellipse(triangle.points.get(3).x,triangle.points.get(3).y,10,10);
		
		//Labels on vertices
		fill(0);
		this.textSize(12);
		this.text("P1", triangle.points.get(0).x + 10, triangle.points.get(0).y -10);
		this.text("P2", triangle.points.get(1).x + 10, triangle.points.get(1).y -10);
		this.text("P3", triangle.points.get(2).x + 10, triangle.points.get(2).y -10);
		this.text("P4", triangle.points.get(3).x + 10, triangle.points.get(3).y -10);
		
//		//Centroid
//		fill(255,255,0);
//		Point centroid = triangle.centroid();
//		ellipse(centroid.x, centroid.y, 5,5);

		//Edges
		stroke(0);
		line(triangle.edges.get(0).p1.x,triangle.edges.get(0).p1.y,triangle.edges.get(0).p2.x,triangle.edges.get(0).p2.y);
		line(triangle.edges.get(1).p1.x,triangle.edges.get(1).p1.y,triangle.edges.get(1).p2.x,triangle.edges.get(1).p2.y);
		line(triangle.edges.get(2).p1.x,triangle.edges.get(2).p1.y,triangle.edges.get(2).p2.x,triangle.edges.get(2).p2.y);
		line(triangle.edges.get(3).p1.x,triangle.edges.get(3).p1.y,triangle.edges.get(3).p2.x,triangle.edges.get(3).p2.y);
		
		//Smallest Cut
		stroke(0,0,255);
		Edge cut = triangle.minCut(triangle.area() / 2);
		if (cut != null) line(cut.p1.x, cut.p1.y, cut.p2.x, cut.p2.y);
		
		//Perpendicular Lines 
//		stroke(0,255,0);
//		Vector perp1 = triangle.edges.get(0).asVec().rotate(PI/2).normalize();
//		Point pp1 = triangle.edges.get(0).midPoint().add(perp1.mul(500f));
//		line(triangle.edges.get(0).midPoint().x, triangle.edges.get(0).midPoint().y, pp1.x, pp1.y);
//		
//		Vector perp2 = triangle.edges.get(1).asVec().rotate(PI/2).normalize();
//		Point pp2 = triangle.edges.get(1).midPoint().add(perp2.mul(500f));
//		line(triangle.edges.get(1).midPoint().x, triangle.edges.get(1).midPoint().y, pp2.x, pp2.y); 
//		
//		Vector perp3 = triangle.edges.get(2).asVec().rotate(PI/2).normalize();
//		Point pp3 = triangle.edges.get(2).midPoint().add(perp3.mul(500f));
//		line(triangle.edges.get(2).midPoint().x, triangle.edges.get(2).midPoint().y, pp3.x, pp3.y); 
		
		
		
		
	}
	
	public static void main(String[] args) {
		PApplet.main("MyApplet");
	}

}
