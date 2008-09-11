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

public class TextTest extends TestGraphicObject {

	private final String text = "Some text"; 
	
	
	/**
	 * Creates a <code>Text</code> component
	 */
	protected GraphicObject createInstance() {
		return new Text(text);
	}
	
	/**
	 * Tests the constructor of a <code>Text</code> component
	 *
	 */
	public void testText() {
		initGraphics();
		assertEquals(text,toText().getText());
		assertEquals(Text.NONE,toText().getDecoration());
	}
	
	/**
	 * Casts the <code>GraphicObject</code> component to a 
	 * <code>Text</code> component
	 * @return a <code>Text</code>
	 */
	public Text toText() {
		return (Text)component;
	}
	
	/**
	 * Tests the getDecoration() method
	 *
	 */
	public void testDecoration() {
		initGraphics();
		Text text1 = new Text(text,Text.OVERLINE);
		Text text2 = new Text(text,Text.LINE_THROUGH);
		Text text3 = new Text(text,Text.UNDERLINE);
		
		canvas.add(text1, 50, 30);
		canvas.add(text2, 50, 50);
		canvas.add(text3, 50, 70);
		
		assertEquals(Text.OVERLINE,text1.getDecoration());
		assertEquals(Text.LINE_THROUGH,text2.getDecoration());
		assertEquals(Text.UNDERLINE,text3.getDecoration());
	}
	
	
	/**
	 * Tests the setFont() method
	 *
	 */
	public void testFont() {
		initGraphics();
		Font font = new Font("courier",12,Font.ITALIC,Font.SMALL_CAPS,Font.BOLD);
		toText().setFont(font);
		assertSame(font,toText().getFont());
		
	}

	/**
	 * Tests the getHeight() and the getLineHeight() methods
	 *
	 */
	public void testHeight() {
		initGraphics();
		assertTrue(0!= toText().getHeight());
		assertTrue(0!= toText().getLineHeight());
	}
}
