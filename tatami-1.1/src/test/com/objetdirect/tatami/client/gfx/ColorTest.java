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
