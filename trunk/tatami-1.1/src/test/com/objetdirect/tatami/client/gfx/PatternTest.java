package com.objetdirect.tatami.client.gfx;

import com.objetdirect.tatami.client.DefaultTatamiTest;

public class PatternTest extends DefaultTatamiTest {

	private Pattern pattern;
	
	public void setUp() {
		pattern = new Pattern("/tatami/gfx/icon.gif",-5,15,50,150);
	}
	
	/** Tests the location of the <code>Pattern</code>
	 * */
	 
	public void testLocation() {
		assertEquals(-5,pattern.getX(),0.001);
		assertEquals(15,pattern.getY(),0.001);
	}
	
	/**
	 * Tests the getWidth() and getHeight() method
	 *
	 */
	public void testShape() {
		assertEquals(50,pattern.getWidth(),0.001);
		assertEquals(150,pattern.getHeight(),0.001);
	}
	
	/**
	 * Tests the getUrl() method
	 *
	 */
	public void testUrl() {
		assertEquals("/tatami/gfx/icon.gif",pattern.getUrl());
	}
	/**
	 * Tests if the JavaScript Pattern is well created
	 *
	 */
	public void testJSPattern() {
		assertNotNull(pattern.getGFXPattern());
	}
	
	public void tearDown() {
		pattern = null;
	}
	
}//end of class
