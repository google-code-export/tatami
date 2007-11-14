package com.objetdirect.tatami.client.gfx;

import com.objetdirect.tatami.client.DefaultTatamiTest;

public class PointTest extends DefaultTatamiTest {

	private Point point;
	
	public void setUp() {
		point = new Point(15,45);
	}
	
	public void tearDown() {
		point = null;
	}
	
	public void testDistance() {
		double dist = point.distance(point);
		Point pt = new Point(20,45);
		assertEquals(0,dist,0.001);
		double dist2 = Point.distance(point.getX(),point.getY(),pt.getX(),pt.getY());
		assertEquals(5,dist2,0.001);
	}
	
	public void testTranslate() {
		point.translate(5,-5);
		assertEquals(20,point.getX(),0.001);
		assertEquals(40,point.getY(),0.001);
	}
	
	
	
	
	
}
