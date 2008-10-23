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

import com.google.gwt.core.client.JavaScriptObject;
import com.objetdirect.tatami.client.DefaultTatamiTest;

public class RectangleTest extends DefaultTatamiTest {

  private Rectangle rect;
  
  public void gwtSetUp() throws Exception{
	  super.gwtSetUp();
	  rect = new Rectangle(50,60,100,200);
  }

  /**
   * Tests the location of the <code>Rectangle</code>
   *
   */
  public void testLocation() {
	  assertEquals(50,rect.getX(),0.001);
	  assertEquals(60,rect.getY(),0.001);
	  Point point = new Point(50,60);
	  assertEquals(point,rect.getLocation());
	  assertEquals(150,rect.getMaxX(),0.001);
	  assertEquals(260,rect.getMaxY(),0.001);
	  assertEquals(50,rect.getMinX(),0.001);
	  assertEquals(60,rect.getMinY(),0.001);
	  
  }
  
  /**
   * Tests the size properties
   *
   */
  public void testSize() {
	  assertEquals(100,rect.getWidth(),0.001);
	  assertEquals(200,rect.getHeight(),0.001);
	  assertFalse(rect.isEmpty());
	  rect.setRect(50, 60, 0, 0);
	  assertTrue(rect.isEmpty());
  }
  
  /**
   * Tests the center position
   *
   */
  public void testCenter() {
	  Point center = new Point(100,160);
	  assertEquals(center,rect.getCenter());
	  assertEquals(100,rect.getCenterX(),0.001);
	  assertEquals(160,rect.getCenterY(),0.001);
  }
  
  /**
   * Tests the creation of a <code>Rectangle</code> from a diagonal
   * coordinate
   *
   */
  public void testDiagonal() {
	  rect.setRectFromDiagonal(0,0,10,60);
	  assertEquals(0,rect.getX(),0.001);
	  assertEquals(0,rect.getY(),0.001);
	  assertEquals(10,rect.getWidth(),0.001);
	  assertEquals(60,rect.getHeight(),0.001);
  }
  
  /**
   * Tests the setRectXXX methods
   *
   */
  public void testRectangle() {
	  rect.setRect(0,0,10,60);
	  assertEquals(0,rect.getX(),0.001);
	  assertEquals(0,rect.getY(),0.001);
	  assertEquals(10,rect.getWidth(),0.001);
	  assertEquals(60,rect.getHeight(),0.001);
	  Rectangle r = new Rectangle(1,-1,45,56);
	  rect.setRect(r);
	  assertEquals(1,rect.getX(),0.001);
	  assertEquals(-1,rect.getY(),0.001);
	  assertEquals(45,rect.getWidth(),0.001);
	  assertEquals(56,rect.getHeight(),0.001);
      rect.setRectFromPoints(createPoints());
      assertEquals(1,rect.getX(),0.001);
	  assertEquals(1,rect.getY(),0.001);
	  assertEquals(50,rect.getWidth(),0.001);
	  assertEquals(70,rect.getHeight(),0.001);
	  rect.setRect(createRect());
	  assertEquals(2,rect.getX(),0.001);
	  assertEquals(-1,rect.getY(),0.001);
	  assertEquals(102,rect.getWidth(),0.001);
	  assertEquals(258,rect.getHeight(),0.001);
  }
  
  /**
   * Creatas an array with 4 DOJO GFX points
   * @return an array with 4 DOJO GFX points
   */
  private native JavaScriptObject createPoints()/*-{
          var array = new Array();
          array[0]= {x:1,y:1};
          array[1]= {x:51,y:1};
          array[2]= {x:51,y:71};
          array[3]= {x:1,y:71};
          return array;
  }-*/;
  
  /** Creates a DOJO GFX Rectangle */
  private native JavaScriptObject createRect() /*-{
         return {x:2,y:-1,width:102,height:258};
  }-*/;
  
  
  public void gwtTearDown() throws Exception{
	  rect = null;
	  super.gwtTearDown();
  }
  
  /**
   * Tests the rotation and the translation
   *
   */
  public void testTransformation() {
	  rect.translate(5, 5);
	  assertEquals(55,rect.getX(),0.001);
	  assertEquals(65,rect.getY(),0.001);
	  rect.rotate(90);
	  /* center : 105,165 */
	  assertEquals(205,rect.getX(),0.001);
	  assertEquals(115,rect.getY(),0.001);
	  
  }
  
  /**
   * Tests the equals method
   *
   */
  public void testEquals() {
	  Rectangle r = new Rectangle(-1,12,56,98.5);
      assertFalse(rect.equals(r));
      r.setRect(50,60, 100, 200);
      assertTrue(rect.equals(r));
      
  }
  
  /**
   * Tests the union 
   *
   */
  public void testUnion() {
	  Rectangle r = new Rectangle(50,0,50,60);
	  Rectangle union = rect.createUnion(r);
	  assertEquals(50,union.getX(),0.001);
	  assertEquals(0,union.getY(),0.001);
	  assertEquals(150,union.getWidth(),0.001);
	  assertEquals(260,union.getHeight(),0.001);
  }
  
  /**
   * Tests the intersection
   *
   */
  public void testIntersection() {
	  Rectangle r = new Rectangle(50,0,50,300);
	  Rectangle intersect = rect.createIntersection(r);
	  assertEquals(50,intersect.getX(),0.001);
	  assertEquals(60,intersect.getY(),0.001);
	  assertEquals(50,intersect.getWidth(),0.001);
	  assertEquals(200,intersect.getHeight(),0.001);
  }
  
}//end of class
