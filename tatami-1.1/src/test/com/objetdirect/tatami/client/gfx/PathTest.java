package com.objetdirect.tatami.client.gfx;

public class PathTest extends TestGraphicObject {

	protected void initGraphics() {
		super.initGraphics();
        toPath().moveTo(20,20);
        toPath().lineTo(95,20);
        toPath().lineTo(102,56);
        toPath().lineTo(13,56);
        toPath().lineTo(20,20);
	}
	
	protected GraphicObject createInstance() {
			return new Path();
	}

	/**
	 * Cast the component to a <code>Path</code>
	 * @return a <code>Path</code>
	 */
	public Path toPath() {
		return (Path)component;
	}
	
	public void testAbsoluteMode() {
		initGraphics();
		toPath().setAbsoluteMode(false);
		assertFalse(toPath().getAbsoluteMode());
	}
	
	/**
	 * Tests if the arcTo method is well executed 
	 **/
	public void testArcTo() {
		initGraphics();
		toPath().arcTo(20, 30, 35, true, true, 20,35);
	}
	
	/**
	 * Tests if the curve methods are well executed 
	 **/
	public void testCurve() {
		initGraphics();
		toPath().curveTo(20, 30, 35, 40, 20,35);
     	toPath().qCurveTo(50,30,40,35);
    	toPath().qSmoothCurveTo(45,45);
		toPath().smoothCurveTo(70,15,60,65);
	}

	/**
	 * Tests if the closePath method is welle executed
	 *
	 */
	public void testClosePath() {
		initGraphics();
		toPath().closePath();
	}
   /**
    * Tests if the vLineTo and hLineTo is well executed 
    *
    */
    public void testVHLine() {
    	initGraphics();
    	toPath().vLineTo(50);
    	toPath().hLineTo(-56);
    }
}//end of class
