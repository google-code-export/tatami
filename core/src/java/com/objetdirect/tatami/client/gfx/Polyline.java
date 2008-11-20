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
 * The Polyline component permits to create complex 
 * shape from an array of <code>Point</code>. 
 * 
 * @author Vianney
 *
 */
public class Polyline extends GraphicObject {

	/** The array of <code>Point</code> which represent this <code>PolyLine</code>*/
	private Point[] points;
	

	/**
	 * Creates a new <code>Polyline</code>.
	 * @param points an array of <code>Points</code> 
	 **/
	public Polyline(Point[] points) {
		super();
		this.points = points; 
	}
	
	/**
	 * Returns the points which describe this <code>Polyline</code>
	 * @return an array of <code>Point</code>
	 */
	public Point[] getPoints() {
		return this.points;
	}
	
	
	/**
	 * Creates a JavaScript array of JavaScrtip Point, from a Java array of <code>Point</code>.
	 * @param points the Java <code>Points</code>
	 * @return a JavaScrtip array
	 */
	private JavaScriptObject createArray(Point[] points) {
		JavaScriptObject jsArray = JavaScriptObject.createArray();
		for ( int i=0; i < points.length; i++) {
			jsArray = put(jsArray,points[i].getGFXPoint(),i);
		}
		return jsArray;
	}
	
	/**
	 * Put a JavaScript point in an array. 
	 * @param array the array of JavaScript Point
	 * @param point the point to put in the array
	 * @param index the index to insert the point
	 * @return the JavaScript array
	 */
	private native JavaScriptObject put(JavaScriptObject array, JavaScriptObject point, int index)/*-{
	    array[index] = point;
	    return array;
	}-*/;
	
	
	/**
	 * Creates the GFX Polyline
	 */
	protected JavaScriptObject createGfx(JavaScriptObject surface) {
		final JavaScriptObject arrayPoints = createArray(points);
		return createPolyline(surface,arrayPoints);
	}
	

	/**
	 * Creates the GFX Polyline
	 */
	
	private native JavaScriptObject createPolyline(JavaScriptObject surface,JavaScriptObject points) /*-{
	    return surface.createPolyline({points:points});
	}-*/;
	
	

}
