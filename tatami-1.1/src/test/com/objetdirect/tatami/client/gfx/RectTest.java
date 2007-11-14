package com.objetdirect.tatami.client.gfx;

public class RectTest extends TestGraphicObject {

	private final int width = 100;
	private final int height = 30;
	
	protected GraphicObject createInstance() {
		return new Rect(100,30);
	}
	
	/**
	 * 
	 *
	 */
	public void testRect() {
		Rect rect = (Rect)component;
		assertTrue(width == rect.getWidth());
		assertTrue(height == rect.getHeight());
	}

}//end of class
