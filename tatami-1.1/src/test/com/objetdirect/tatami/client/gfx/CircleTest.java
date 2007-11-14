package com.objetdirect.tatami.client.gfx;


public class CircleTest extends TestGraphicObject {

	
	protected GraphicObject createInstance() {
		return new Circle(50);
	}

	/** Tests the radius */
	public void testRadius() {
		Circle c = (Circle)component;
		assertEquals(50,c.getRadius());
	}

}
