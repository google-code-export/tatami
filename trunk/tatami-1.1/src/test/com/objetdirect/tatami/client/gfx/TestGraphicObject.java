package com.objetdirect.tatami.client.gfx;

import com.google.gwt.user.client.ui.RootPanel;
import com.objetdirect.tatami.client.DefaultTatamiTest;


/**
 * This abstract class permits to test some common method and properties 
 * of a <code>GraphicObject</code>. These tests even if they succeed don't guarented 
 * if the <code>GraphicObject</code> works correctly in a browser. 
 * But they are useful to assume some method and avoid some exception which can be 
 * thrown during the runtime. 
 * 
 * @author Vianney
 *
 */
abstract public class TestGraphicObject extends DefaultTatamiTest {

  /**
   * The <code>GraphicObject</code> to test
   */
  protected GraphicObject component;
  /** the canvas containing the code>GraphicObject</code> to test*/
  protected GraphicCanvas canvas;
  
  private final Point position = new Point(200,200);
  
  protected abstract GraphicObject createInstance();

  /**
   * Init the GFX graphic environment
   *
   */
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
  
  
 
  /**
   * Tests if the bounds is never empty
   *
   */
  public void testBounds() {
	  initGraphics();
	  Rectangle bounds = component.getBounds();
	  //assertFalse(bounds.isEmpty());
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

  /**
   * Tests the getParent() and getGroup() method
   *
   */
  public void testParentAndGroup() {
	  initGraphics();
	  assertNull("No group has been set",component.getGroup());
	  assertSame(canvas,component.getParent());
  }
}//end of class
