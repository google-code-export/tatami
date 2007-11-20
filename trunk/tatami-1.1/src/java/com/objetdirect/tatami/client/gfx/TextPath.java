/*
 * Tatami: 
 * Copyright (C) 2007 Objet Direct
 * Copyright (C) 2007 France Telecom
 * Contact: tatami@objectweb.org
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

import com.google.gwt.core.client.JavaScriptObject;

/**
 * 
 * TODO : fix the bug : decoration doesn't work even in pure JavaSript
 *
 */
public class TextPath extends Path {

	/**
	 * The text 
	 */
	private String text ="";
	/** the decoration to use */
	private String decoration = Text.NONE;
	/**
	 * The font to use
	 */
	private Font font = Font.DEFAULT_FONT;
	
	/**
	 * Create a <code>TextPath</code> with the given text
	 * @param text the text to join with the path
	 * @param decoration the decoration to use for the text NONE | OVERLINE | UNDERLINE | LINE_THROUGH
	 */
	public TextPath(String text,String decoration) {
		super();
		this.text = text;
		this.decoration = decoration;
	}
	
	/**
	 * Creates a <code>TextPath</code> with a given text to join to the path
	 * @param text the text to join to the path
	 */
	public TextPath(String text) {
		this(text,Text.NONE);
	}

	/**
	 * Creates a <code>TextPath</code> from a <code>Text</code> gfx component
	 * @param text a <code>Text</code> gfx component
	 */
	public TextPath(Text text) {
		this(text.getText(),text.getDecoration());
	}
	
	/**
	 * Create the DOJO gfx textPath
	 * @param surface the canvas
	 * @param text the text to write
	 * @param the decoration to use
	 * @return the DOJO GFX textPath
	 */
	protected JavaScriptObject createGfx(JavaScriptObject surface) {
		return createTextPath(surface,text,decoration);
	}

	/**
	 * Returns the decoration used for the text
	 * @return <code>Text.NONE | Text.OVERLINE | Text.UNDERLINE | Text.LINE_THROUGH </code>
	 */
	public String getDecoration() {
		return this.decoration;
	}
	
	
	/**
	 * Creates the DOJO GFX textPath
	 * @param surface the canvas
	 * @param text the text to write
	 * @param the decoration to use
	 * @return the DOJO GFX textPath
	 */
	private native JavaScriptObject createTextPath(JavaScriptObject surface,String text,String decoration)/*-{
         var param = { text:text,decoration: decoration};
         return surface.createTextPath(param);	    
	}-*/;
	
	
	
	/**
	 * Sets the font of the text
	 * @param font the font to use
	 */
	public void setFont(Font font) {
		this.font = font;
		if ( getShape() != null) {
			setFont(getShape(),font.createFont());
		}
	}
	
	
	/**
	 * Returns the Font used for the text
	 * @return the font used for the text
	 */
	public Font getFont() {
		return this.font;
	}
	/**
	 * Sets the font for the text
	 * @param shape the shape of the text
	 * @param font the font to use
	 */
	private native void setFont(JavaScriptObject shape,JavaScriptObject font)/*-{
	   shape.setFont(font);
	}-*/;

	/**
	 * Returns the text to write 
	 * @return the text to write
	 */
	public String getText() {
		return this.text;
	}
	 /**
     * Creates the textPath in the canvas and set the font
     * 
     */
	protected void show(GraphicCanvas canvas) {
		super.show(canvas);
		this.setFont(getFont());
	}
}
