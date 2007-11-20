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
 * Authors: Henri Darmet, Vianney Grassaud
 * Initial developer(s):
 * Contributor(s):
 */
package com.objetdirect.tatami.client.gfx;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * A circle graphic component.<br>
 * Represents a circle in a canvas. 
 * The circle is defined by the center position and its radius. Note that
 * the center position is defined when the circle is added to a canvas.<br> 
 * Example : <br>
 * <p>
 * <code>
 * GraphicalCanvas canvas = new GraphicalCanvas();<br>
 * GraphicalObject circle = new Circle(30);<br>
 * //sets the center to the point (50,50) <br>
 * canvas.add(circle,50,50); 
 * </code> 
 * </p>
 *TODO add the setWidth and setHeight methods
 */
public class Circle extends GraphicObject {
	
	/**
	 * The radius of the circle
	 */
	private int radius;
	
	/**
	 * Creates a circle given a specific radius
	 * @param radius
	 */
	public Circle(int radius) {
		
		setRadius(radius);
	}
	
	/**
	 * Sets the radius
	 * @param radius the radius
	 */
	private void setRadius(int radius) {
		this.radius = radius;
	}
	
	/**
	 * Returns the radius of the circle
	 * @return the radius of the circle
	 	 */
	public int getRadius() {
		return this.radius;
	}
	
	
	/**
	 * Creates the DOJO circle object
	 * @return the DOJO circle object
	 */
	protected JavaScriptObject createGfx(JavaScriptObject surface) {
		return createGfx(surface, radius);
	}
	
	
	
	/**
	 * Creates the DOJO circle object
	 * @param surface the Surface 
	 * @param radius the radius
	 * @return the circle JavaScriptObject
	 */
	protected native JavaScriptObject createGfx(JavaScriptObject surface,  int radius) /*-{
		return surface.createCircle(
		   {
		    cx:0,
		    cy:0,
			r:  radius
		   }
		  );
	}-*/;
}
