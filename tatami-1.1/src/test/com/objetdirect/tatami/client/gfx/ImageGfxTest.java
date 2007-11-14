package com.objetdirect.tatami.client.gfx;

public class ImageGfxTest extends TestGraphicObject {

	private final String url = "browser.png";
	private final int width = 100;
	private final int height = 200;
	
	protected GraphicObject createInstance() {
			return new ImageGfx(url,width,height);
	}

	/**
	 * Tests 
	 *
	 */
	public void testImageGfx() {
		ImageGfx img = (ImageGfx)component;
		assertEquals(url,img.getUrl());
		assertEquals(width,img.getWidth());
		assertEquals(height,img.getHeight());
	}
}
