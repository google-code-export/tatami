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
 * Tests the <code>Color</color> class
 * @author Vianney
 *
 */
public class ColorTest extends DefaultTatamiTest {

  private Color color;	

  /** creates a color */
  public void setUp() {
	color = new Color(127,255,233,127);  
  }

  
  public void tearDown() {
	  color = null;
  }

  /** Tests  color */
  public void testColor() {
	  assertEquals(127,color.getRed());
	  assertEquals(255,color.getGreen());
	  assertEquals(233,color.getBlue());
	  assertEquals(127,color.getAlpha());
 
      color.setRed(255);
      assertEquals(255,color.getRed());
      color.setGreen(255);
      assertEquals(255,color.getGreen());
      color.setBlue(255);
      assertEquals(255,color.getBlue());
      color.setAlpha(255);
      assertEquals(255,color.getAlpha());
  }
  
  /**
   * Tests the reverse method
   *
   */
  public void testReverse() {
	  Color reverse = color.reverse();
	  assertEquals(255-127,reverse.getRed());
	  assertEquals(0,reverse.getGreen());
	  assertEquals(255-233,reverse.getBlue());
	  assertEquals(127,reverse.getAlpha());
   }
  
  /**
   * Tests the toHex() method
   *
   */
  public void testToHex() {
	  assertNotNull(color.toHex());
	  assertEquals("#7fffe9",color.toHex());
  }
  
  /**
   * Tests the toCss() method 
   *
   */
  public void testToCss() {
	 
	  assertNotNull(color.toCss(true));
	  assertEquals("rgba(127, 255, 233, 0.49803921580314636)",color.toCss(true));
	  assertNotNull(color.toCss(false));
	  assertEquals("rgb(127, 255, 233)",color.toCss(false));
  }
 

}//end of class
