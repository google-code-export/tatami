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

import com.google.gwt.core.client.JavaScriptObject;

/**
 * A Rectangle graphic component.
 * 
 * @author Henri, Vianney
 * 
 *
 */
public class Rect extends RectangularShape {
	
	/**
	 * Creates a rectangle specifying the width and the height
	 * @param width width of the rectangle
	 * @param height height of the rectangle
	 */
	public Rect(double width, double height) {
		super(width,height);
	}
	
    /**
     * Creates a GFX <code>Rect</code> from a <code>Rectangle</code>
     * @param rect the <code>Rectangle</code> to determinate the position, width and height
     */	
	public Rect(Rectangle rect) {
		super(rect);
	}
	
	/**
	 * Creates the DOJO Rectangle object
	 * @param surface the DOJO surface
	 * @return the DOJO rectangle
	 */
	protected JavaScriptObject createGfx(JavaScriptObject surface) {
		return createGfx(surface, getWidth(), getHeight());
	}

	
	
	/**
	 * Creates the DOJO Rectangle object
	 * @param surface the DOJO surface
	 * @param x the x coordinate of the rectangle
	 * @param y the y coordinate of the rectangle
	 * @param width the width of the rectangle
	 * @param height  the height of the rectangle
	 * @return the DOJO rectangle
	 */
	protected native JavaScriptObject createGfx(JavaScriptObject surface,double width, double height) /*-{
		return surface.createRect({
			width:  width,
			height: height
		});
	}-*/;
	


	


}//end of class
