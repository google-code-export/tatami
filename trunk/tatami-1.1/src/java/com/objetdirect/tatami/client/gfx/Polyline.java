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

public class Polyline extends GraphicObject {

	private Point[] points;
	
	
	public Polyline(Point[] points) {
		super();
		this.points = points; 
	}
	
	public Point[] getPoints() {
		return this.points;
	}
	
	
	private JavaScriptObject createArray(Point[] points) {
		JavaScriptObject jsArray = JavaScriptObject.createArray();
		for ( int i=0; i < points.length; i++) {
			jsArray = put(jsArray,points[i].getGFXPoint(),i);
		}
		return jsArray;
	}
	
	
	private native JavaScriptObject put(JavaScriptObject array, JavaScriptObject point, int index)/*-{
	    array[index] = point;
	    return array;
	}-*/;
	
	
	protected JavaScriptObject createGfx(JavaScriptObject surface) {
		final JavaScriptObject arrayPoints = createArray(points);
		return createPolyline(surface,arrayPoints);
	}
	
	private native JavaScriptObject createPolyline(JavaScriptObject surface,JavaScriptObject points) /*-{
	    return surface.createPolyline({points:points});
	}-*/;
	
	

}
