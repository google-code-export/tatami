/*
 * Tatami: 
 * Copyright (C) 2007 Objet Direct
 * Copyright (C) 2007 France Telecom
 * Contact: tatami@googlegroups.com
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
 * USA
 *
 * Authors:  Vianney Grassaud
 * Initial developer(s): Vianney Grassaud
 * Contributor(s):
 */
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
	public void gwtSetUp() {
		point = new Point(15,45);
	}
	
	/**
	 * Destroys the point
	 */
	public void gwtTearDown() {
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
