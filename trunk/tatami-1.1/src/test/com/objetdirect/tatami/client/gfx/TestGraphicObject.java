package com.objetdirect.tatami.client.gfx;

import com.google.gwt.user.client.ui.RootPanel;
import com.objetdirect.tatami.client.DefaultTatamiTest;

abstract public class TestGraphicObject extends DefaultTatamiTest {

  protected GraphicObject component;
  protected GraphicCanvas canvas;
  private final Point position = new Point(200,200);
  
  protected abstract GraphicObject createInstance();
	 
  protected void initGraphics() {
	  canvas = new GraphicCanvas();
	  RootPanel.get().add(canvas);
	  canvas.setSize("600px", "500px");
	  component = createInstance();
	  canvas.add(component,(int)position.getX(),(int)position.getY());
  }
  
  
  public void tearDown() {
	 
	  canvas = null;
	  component = null;
  }
  
  /** Tests the translating transformation */
  public void testTranslate() {
	  initGraphics();
	  double x = component.getX();
	  double y = component.getY();
	  component.translate(-5, 10);
	  assertEquals((x-5),component.getX(),0.001);
	  assertEquals((y+10),component.getY(),0.001);
	  
  }
  
  /**
   * Tests the scaling transformation
   *
   */
  public void testScale() {
	  initGraphics();
	  Rectangle bounds = component.getBounds();
	  final Point before = component.getLocation(); 
	  component.scale(1.5f);
	  assertFalse(bounds.isEmpty());
	  assertEquals(before,component.getLocation());
	  //assertFalse(bounds.equals(component.getBounds()));
  }
  
  /**
   * Tests the rotating transformation.
   * The test tests only if the rotation is executed without
   * throwing an Error or an exception.   
   *
   */
  public void testRotate() {
	  initGraphics();
	  Point cBefore = component.getCenter();
	  component.rotate(90);
	  assertEquals(cBefore,component.getCenter());
	  
  }
  
  /** Tests the method to set the location of the 
   * <code>GraphicObject</code>
   **/
  public void testPosition() {
	  initGraphics();
	  assertEquals(position.getX(),component.getX(),0.001);
	  assertEquals(position.getY(),component.getY(),0.001);
	  Point pt = new Point(89,102);
	  //component.setLocation(pt);
	  assertEquals(pt,component.getLocation());
	  //component.setLocation(pt.getX(), pt.getY());
	  assertEquals(pt.getX(),component.getX(),0.001);
	  assertEquals(pt.getY(),component.getY(),0.001);
  }
  
 
  /**
   * Tests if the bounds is never empty
   *
   */
  public void testBounds() {
	  initGraphics();
	  Rectangle bounds = component.getBounds();
	  assertFalse(bounds.isEmpty());
  }
  
  /**
   * Tests the methods to set the fill of 
   * the <code>GraphicObject</code> 
   */
  public void testFill() {
	  initGraphics();
	  component.setFillColor(Color.RED);
	  assertEquals(Color.RED,component.getFillColor());
  }
  
  /**
   * Tests the method to set the stroke of a <code>GraphicObject</code>
   *
   */
  public void testStroke() {
	  initGraphics();
	  component.setStroke(Color.BLUE, 4);
	  assertEquals(Color.BLUE,component.getStrokeColor());
	  assertEquals(4,component.getStrokeWidth());
	  component.setStrokeColor(Color.YELLOW);
	  assertEquals(Color.YELLOW,component.getStrokeColor());
	  assertEquals(4,component.getStrokeWidth());
	  component.setStrokeWidth(2);
	  assertEquals(Color.YELLOW,component.getStrokeColor());
	  assertEquals(2,component.getStrokeWidth());
  }
  
  /**
   * Tests if the pattern is apply correctly.
   *
   */
  public void testApplyPattern() {
	  initGraphics();
	  Pattern p = component.getPattern();
	  assertNull(p);
	  component.applyPattern(Pattern.DEFAULT_PATTERN);
	  assertEquals(Pattern.DEFAULT_PATTERN,component.getPattern());
  }
  
  /**
   * Tests only if the method can be invoked without throwing errors 
   * or exceptions
   *
   */
  public void testMoveBackAndFront() {
	  initGraphics();
	  component.moveToBack();
	  component.moveToFront();
  }

}//end of class
