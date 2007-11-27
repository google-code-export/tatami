package com.objetdirect.tatami.client.gfx;

public class PolyLineTest extends TestGraphicObject {

	private final Point[] arrow = {
			new Point(-2, 15),new Point(2, 15),new Point(2, -105),
			new Point(6, -105),new Point(0, -116),new Point(-6, -105),
			new Point(-2, -105),new Point(-2, 15)	}; 
	
	
	protected GraphicObject createInstance() {
		return new Polyline(arrow);
	}

	
	public Polyline toPolyline()  {
		return (Polyline)component;
	}
	
	public void testgetPoints() {
		initGraphics();
		assertEquals(arrow,toPolyline().getPoints());
	}
}
