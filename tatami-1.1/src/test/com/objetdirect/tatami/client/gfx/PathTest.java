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
	
}
