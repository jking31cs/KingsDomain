import com.jking31cs.trianglehalving.ConvexShape;
import com.jking31cs.trianglehalving.Edge;
import com.jking31cs.trianglehalving.Point;

import processing.core.PApplet;


public class SixPointExample extends PApplet {
	private static final long serialVersionUID = 1L;

	ConvexShape mainShape;
	
	boolean changeP1,changeP2,changeP3,changeP4,changeP5,changeP6;
	
	@Override
	public void setup() {
		size(600,600);
		Point p1 = new Point(284.5583f, 357.6292f);
		Point p2 = new Point(300,300);
		Point p3 = new Point(350, 250);
		Point p4 = new Point(400,400);
		Point p5 = new Point(342.3708f,415.4417f);
		Point p6 = new Point(325, 400);
		mainShape = new ConvexShape(p1,p2,p3,p4,p5,p6);
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
		if (abs(mousePoint.distTo(mainShape.points.get(0))) <= 5) {
			changeP1 = true;
		}
		if (abs(mousePoint.distTo(mainShape.points.get(1))) <= 5) {
			changeP2 = true;
		}
		if (abs(mousePoint.distTo(mainShape.points.get(2))) <= 5) {
			changeP3 = true;
		}
		if (abs(mousePoint.distTo(mainShape.points.get(3))) <= 5) {
			changeP4 = true;
		}
		if (abs(mousePoint.distTo(mainShape.points.get(4))) <= 5) {
			changeP5 = true;
		}
		if (abs(mousePoint.distTo(mainShape.points.get(5))) <= 5) {
			changeP6 = true;
		}
	}
	
	@Override
	public void mouseDragged() {
		if (changeP1) {
			mainShape = new ConvexShape(new Point(mouseX, mouseY), mainShape.points.get(1), 
					mainShape.points.get(2), mainShape.points.get(3),
					mainShape.points.get(4), mainShape.points.get(5));
		}
		if (changeP2) {
			mainShape = new ConvexShape(mainShape.points.get(0), new Point(mouseX, mouseY), 
					mainShape.points.get(2), mainShape.points.get(3),
					mainShape.points.get(4), mainShape.points.get(5));
		}
		if (changeP3) {
			mainShape = new ConvexShape(mainShape.points.get(0), mainShape.points.get(1), 
					new Point(mouseX, mouseY), mainShape.points.get(3),
					mainShape.points.get(4), mainShape.points.get(5));
		}
		if (changeP4) {
			mainShape = new ConvexShape(mainShape.points.get(0), mainShape.points.get(1),
					mainShape.points.get(2), new Point(mouseX, mouseY),
					mainShape.points.get(4), mainShape.points.get(5));
		}
		if (changeP5) {
			mainShape = new ConvexShape(mainShape.points.get(0), mainShape.points.get(1),
					mainShape.points.get(2), mainShape.points.get(3),
					new Point(mouseX, mouseY), mainShape.points.get(5));
		}
		if (changeP6) {
			mainShape = new ConvexShape(mainShape.points.get(0), mainShape.points.get(1),
					mainShape.points.get(2), mainShape.points.get(3),
					mainShape.points.get(4), new Point(mouseX, mouseY));
		}
		
	}
	
	@Override
	public void mouseReleased() {
		changeP1=changeP2=changeP3=changeP4=changeP5=changeP6=false;
	}
	
	@Override
	public void draw() {
		background(255);
		
		//Points
		stroke(255,0,0);
		fill(255);
		ellipse(mainShape.points.get(0).x,mainShape.points.get(0).y,10,10);
		ellipse(mainShape.points.get(1).x,mainShape.points.get(1).y,10,10);
		ellipse(mainShape.points.get(2).x,mainShape.points.get(2).y,10,10);
		ellipse(mainShape.points.get(3).x,mainShape.points.get(3).y,10,10);
		ellipse(mainShape.points.get(4).x,mainShape.points.get(4).y,10,10);
		ellipse(mainShape.points.get(5).x,mainShape.points.get(5).y,10,10);
		
		//Labels on vertices
		fill(0);
		this.textSize(12);
		this.text("P1", mainShape.points.get(0).x + 10, mainShape.points.get(0).y -10);
		this.text("P2", mainShape.points.get(1).x + 10, mainShape.points.get(1).y -10);
		this.text("P3", mainShape.points.get(2).x + 10, mainShape.points.get(2).y -10);
		this.text("P4", mainShape.points.get(3).x + 10, mainShape.points.get(3).y -10);
		this.text("P5", mainShape.points.get(4).x + 10, mainShape.points.get(4).y -10);
		this.text("P6", mainShape.points.get(5).x + 10, mainShape.points.get(5).y -10);

		//Edges
		stroke(0);
		line(mainShape.edges.get(0).p1.x,mainShape.edges.get(0).p1.y,mainShape.edges.get(0).p2.x,mainShape.edges.get(0).p2.y);
		line(mainShape.edges.get(1).p1.x,mainShape.edges.get(1).p1.y,mainShape.edges.get(1).p2.x,mainShape.edges.get(1).p2.y);
		line(mainShape.edges.get(2).p1.x,mainShape.edges.get(2).p1.y,mainShape.edges.get(2).p2.x,mainShape.edges.get(2).p2.y);
		line(mainShape.edges.get(3).p1.x,mainShape.edges.get(3).p1.y,mainShape.edges.get(3).p2.x,mainShape.edges.get(3).p2.y);
		line(mainShape.edges.get(4).p1.x,mainShape.edges.get(4).p1.y,mainShape.edges.get(4).p2.x,mainShape.edges.get(4).p2.y);
		line(mainShape.edges.get(5).p1.x,mainShape.edges.get(5).p1.y,mainShape.edges.get(5).p2.x,mainShape.edges.get(5).p2.y);
		
		//Smallest Cut
		stroke(0,0,255);
		Edge cut1 = mainShape.minCut(mainShape.area() / 4f);
		if (cut1 != null) {
			line(cut1.p1.x, cut1.p1.y, cut1.p2.x, cut1.p2.y);
			ConvexShape inner1 = mainShape.shapeFromCut(cut1, true);
			Edge cut2 = inner1.minCut(mainShape.area() / 4f);
			if (cut2 != null) {
				line(cut2.p1.x, cut2.p1.y, cut2.p2.x, cut2.p2.y);
				Edge cut3 = inner1.shapeFromCut(cut2, true).minCut(mainShape.area() / 4f);
				if (cut3 != null) line(cut3.p1.x, cut3.p1.y, cut3.p2.x, cut3.p2.y);	
			}
		}
		
	}
	
	public static void main(String[] args) {
		PApplet.main("SixPointExample");
	}
}
