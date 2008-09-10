/*
 * Tatami: 
 * Copyright (C) 2007 Objet Direct
 * Copyright (C) 2007 France Telecom
 * Contact: tatami@googlegroups.com
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
 * USA
 *
 * Authors:  Vianney Grassaud
 * Initial developer(s): Vianney Grassaud
 * Contributor(s):
 */
package com.objetdirect.tatami.client.gfx;

import com.objetdirect.tatami.client.DefaultTatamiTest;

public class PatternTest extends DefaultTatamiTest {

	private Pattern pattern;
	
	public void gwtSetUp() {
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
	
	public void gwtTearDown() {
		pattern = null;
	}
	
}//end of class
