package com.objetdirect.tatami.client.gfx;

import com.objetdirect.tatami.client.DefaultTatamiTest;

/** 
 * tests the Point class Point class is a POJO class 
 **/ 
public class PointTest extends DefaultTatamiTest {

	private Point point;
	
	/**
	 * Creates a point
	 */
	public void setUp() {
		point = new Point(15,45);
	}
	
	/**
	 * Destroys the point
	 */
	public void tearDown() {
		point = null;
	}
	
	/**
	 * Tests the distance between two points
	 *
	 */
	public void testDistance() {
		double dist = point.distance(point);
		Point pt = new Point(20,45);
		assertEquals(0,dist,0.001);
		double dist2 = Point.distance(point.getX(),point.getY(),pt.getX(),pt.getY());
		assertEquals(5,dist2,0.001);
	}
	
	/**
	 * Tests the translation of a point
	 *
	 */
	public void testTranslate() {
		point.translate(5,-5);
		assertEquals(20,point.getX(),0.001);
		assertEquals(40,point.getY(),0.001);
	}
	
	
	public void testEquals() {
		Point p2 = new Point(point);
		assertEquals(point,p2);
	}
	
	
}
