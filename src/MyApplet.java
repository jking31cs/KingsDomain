import com.jking31cs.trianglehalving.Point;
import com.jking31cs.trianglehalving.Triangle;

import processing.core.PApplet;


public class MyApplet extends PApplet {

	private static final long serialVersionUID = 1L;

	Triangle triangle;
	
	@Override
	public void setup() {
		size(600,600);
		com.jking31cs.trianglehalving.Point p1 = new Point(300,300);
		com.jking31cs.trianglehalving.Point p2 = new Point(300,400);
		com.jking31cs.trianglehalving.Point p3 = new Point(400,400);
		
		triangle = new Triangle(p1,p2,p3);
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
		
	}
	
	public static void main(String[] args) {
		PApplet.main("MyApplet");
	}

}
