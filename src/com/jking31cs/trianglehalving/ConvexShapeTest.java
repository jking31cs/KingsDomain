package com.jking31cs.trianglehalving;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConvexShapeTest {

	@Test
	public void testArea() {
		ConvexShape shape = new ConvexShape(
			new Point(2,2),
			new Point(5,2),
			new Point(5,5)
		);
		assertEquals(4.5f, shape.area()*1f, .05f);
	}
	
	@Test
	public void testEdgePairs() {
		ConvexShape shape = new ConvexShape(
			new Point(2,2),
			new Point(5,2),
			new Point(5,5)
		);
		
		assertEquals(3, shape.edgePairs().size(), 0);
	}

}
