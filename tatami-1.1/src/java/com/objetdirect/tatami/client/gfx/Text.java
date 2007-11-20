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
 * Represents some text in a graphical environment.
 * The text can have a specific font and a decoration. 
 * @author Vianney
 
 *TODO  the decoration attribute doesn't seem to work even in pur JavaScript
 */
public class Text extends GraphicObject {

	/** Produces no text decoration.*/
	public static final String NONE = "none";
   
	/** Each line of text has a line above it. */	
	public static final String OVERLINE = "overline";
	
	/** Each line of text is underlined. */
	public static final String UNDERLINE = "underline";
	
	/** Each line of text has a line through the middle. */
	public static final String LINE_THROUGH = "line-through";
	
	
	/**
	 * The text to show
	 */
	 private String text;
	
	/**
	 * Decoraction for the text
	 */
	final private String decoration ;
	
	/**
	 * The font for the text
	 */
	private Font font = Font.DEFAULT_FONT;
	
	/**
	 * Creates a GFX text 
	 * @param text the text to write
	 * @param xPosition the x coordinate
	 * @param yPosition the y coordinate
	 */
	public Text(String text) {
		this(text,NONE);
    }
	
	/**
	 * Creates a GFX text
	 * @param text the text to display
	 * @param decoration the decoration to use for the text
	 */
	public Text(String text,String decoration) {
		super();
		this.text = text;
		this.decoration = decoration;
	}
	
	/**
	 * Returns the used decoration for the text.
	 * @return NONE | OVERLINE | UNDERLINE | LINE_THROUGH
	 */
	public String getDecoration() {
		return this.decoration;
	}
	
	/**
	 * Creates the shape of the text
	 * @param surface the DOJO canvas
	 */
	protected JavaScriptObject createGfx(JavaScriptObject surface) {
		return createGfx(surface,text,decoration);
	}

	/**
	 * Creates the DOJO GFX Text
	 * @param surface the DOJO canvas
	 * @param text the text to write
	 * @param xPosition  the x coordinate
	 * @param yPosition  the y coordinate
	 * @return the DOJO GFX text
	 */
	native private JavaScriptObject createGfx(JavaScriptObject surface, String text,String decoration) /*-{
	   return  surface.createText({  text : text,decoration:decoration});
	}-*/; 
	
	
	
	/**
	 * Returns the font size in pixel
	 * @param size the size in pt of the font
	 * @return the size of the font in pixel
	 */
	native private double getFontSize(String size) /*-{
	  return $wnd.dojox.gfx.normalizedLength(size);
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
	 * Returns approximative bounds of this text.
	 * The DOJO GFX text return a <code>null Rectangle</code> for the bounds. So 
	 * the method is overriden to return an aproximative <code>Rectangle</code>.
	 * The bounds are calculated from the font size.
	 * The rectangle is approximative because, font ascent and font descent
	 * are not known. 
	 * @return a <code>Rectangle</code> 
	 */
	public Rectangle getBounds() {
		final int fontSize = (int)getFontSize(font.getSize()+"pt");
		final double width = text.length() * fontSize * 0.75 ;
		return new Rectangle(getX(),getY(),width,fontSize);

	}
	
	
	/**
	 * Returns the width of this <code>Text</code> object 
	 * @return the width of this <code>Text</code> object
	 */
	public double getWidth() {
		return getBounds().getWidth();
	}
	
	/**
	 * Returns the height of this <code>Text</code> object 
	 * @return the height of this <code>Text</code> object
	 */
	public double getHeight() {
		return getBounds().getHeight();
	}
	
    /**
     * Creates the text in the canvas and set the font
     * 
     */
	protected void show(GraphicCanvas canvas) {
		super.show(canvas);
		this.setFont(getFont());
	}

}//end of class
