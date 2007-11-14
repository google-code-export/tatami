package com.objetdirect.tatami.client.gfx;

public class LineTest extends TestGraphicObject {

	private final Point pa = new Point(10,10);
	private final Point pb = new Point(40,102);
	
	protected GraphicObject createInstance() {
	   	return new Line(pa,pb);
	
	}

	
}
