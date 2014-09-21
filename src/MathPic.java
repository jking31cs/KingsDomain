import com.jking31cs.trianglehalving.Edge;
import com.jking31cs.trianglehalving.Point;
import com.jking31cs.trianglehalving.Vector;

import processing.core.PApplet;

/**
 * Applet to show off shortest cut proof.
 * @author jking31
 *
 */
public class MathPic extends PApplet {
	
	Edge e1;
	Edge e2;
	
	@Override
	public void setup() {
		e1 = new Edge(new Point(300, 300), new Point(400,450));
		e2 = new Edge(new Point(400,450), new Point(250,500));
		size(600,600);
	}
	
	@Override
	public void draw() {
		background(255);
		
		stroke(0);
		line(e1.p1.x, e1.p1.y, e1.p2.x, e1.p2.y);
		line(e2.p1.x, e2.p1.y, e2.p2.x, e2.p2.y);
		
		Vector perp1 = e1.asVec().rotate(PI/2).normalize();
		Vector perp2 = e2.asVec().rotate(PI/2).normalize();
		
		Point startPoint = e1.midPoint();
		Point endPoint = null;
		Point center = null;
		Edge obsEdge = e2;
		while (endPoint == null) {
			Point tempEnd = obsEdge.midPoint();
			Edge startEdge = new Edge(startPoint, startPoint.add(perp1));
			Edge endEdge = new Edge(tempEnd, tempEnd.add(perp2));
			
			center = startEdge.intersectionPoint(endEdge);
			if (abs(center.distTo(startPoint) - center.distTo(tempEnd)) <= .05f) {
				endPoint = tempEnd;
			} else if (abs(center.distTo(startPoint)) > abs(center.distTo(tempEnd))) {
				obsEdge = new Edge(obsEdge.p1, tempEnd);
			} else {
				obsEdge = new Edge(tempEnd, obsEdge.p2);
			}
		}

		stroke(255,0,0);
		fill(255);
		ellipse(center.x, center.y, 2*center.distTo(startPoint),2*center.distTo(startPoint));
		
		stroke(0,255,0);
		line(startPoint.x, startPoint.y, center.x, center.y);
		line(endPoint.x, endPoint.y, center.x, center.y);
		
		stroke(0,0,255);
		line(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
		
		
		
	}

	public static void main(String[] args) {
		PApplet.main("MathPic");
	}
}
