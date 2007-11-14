package com.objetdirect.tatami.client.gfx;

public class EllipseTest extends TestGraphicObject {

	
	protected GraphicObject createInstance() {
			return new Ellipse(70,100);
	}

	/**
	 * Tests the radius x and radius y
	 *
	 */
	public void testRadius() {
		Ellipse e = (Ellipse)component;
		assertEquals(70,e.getRadiusX());
		assertEquals(70,e.getRadiusY());
	}
}
