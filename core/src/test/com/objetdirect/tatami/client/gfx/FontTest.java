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

public class FontTest extends DefaultTatamiTest {

	private Font font;
	

	/**
	 * Creates a point
	 */
	protected void gwtSetUp() throws Exception {
		font = new Font(Font.DEFAULT_FONT);
		super.gwtSetUp();
	}
	
	/**
	 * Tests the family property
	 *
	 */
	
	public void testFamily() {
		font.setFamily("Arial");
		assertEquals("The family should be Arial","Arial",font.getFamily());
	}
	
	
	/**
	 * Tests the contructor
	 *
	 */
	public void testFontContructor() {
		assertEquals("Arial",font.getFamily());
		assertEquals(10,font.getSize());
		assertEquals(Font.NORMAL,font.getStyle());
		assertEquals(Font.NORMAL,font.getVariant());
		assertEquals(Font.NORMAL,font.getWeight());
	}
	
	/**
	 * Tests the size property
	 *
	 */
	public void testSize() {
		font.setSize(15);
		assertEquals(15,font.getSize());
	}
	
	/**
	 * Tests the weight property
	 *
	 */
	public void testAllWeight() {
		String[] tab = { Font.BOLD,Font.BOLDER,Font.NORMAL,Font.LIGHTER};
		for (int i = 0 ; i<tab.length ; i++) {
			testWeight(tab[i]);
		}
	}
	
	/**
	 * Tests a weight property
	 * @param weight a value of a weight property to test
	 *
	 */
	private void testWeight(String weight) {
		font.setWeight(weight);
		assertEquals(weight,font.getWeight());
		
	}
	
	/**
	 * Tests the style property
	 *
	 */
	
	public void testStyle() {
		font.setStyle(Font.NORMAL);
		assertEquals(Font.NORMAL,font.getStyle());
		font.setStyle(Font.ITALIC);
		assertEquals(Font.ITALIC,font.getStyle());
		font.setStyle(Font.OBLIQUE);
		assertEquals(Font.OBLIQUE,font.getStyle());
	}
	
	
	/**
	 * Tests the variant property
	 *
	 */
	public void testVariant() {
		font.setVariant(Font.NORMAL);
		assertEquals(Font.NORMAL,font.getVariant());
		font.setVariant(Font.SMALL_CAPS);
		assertEquals(Font.SMALL_CAPS,font.getVariant());
	}
	
	/**
	 * Destroys the point
	 */
	public void gwtTearDown() throws Exception{
		font = null;
		super.gwtTearDown();
	}
	
	/**
	 * Tests if the JavaScipt Font object is well
	 * created
	 *
	 */
	public void testJSFont() {
		assertNotNull(font.createFont());
	}

	
}
