
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
 * 
 * Represents an Ellipse object. 
 * 
 *
 */
public class Ellipse extends GraphicObject {

	private int radiusX;
    private int radiusY;

	/**
	 * Creates an ellipse with the specifics radius
	 * @param radiusX radius for the X axis
	 * @param radiusY radius for the Y axis
	 */
    public Ellipse(int radiusX,int radiusY) {
       super();
       this.radiusX = radiusX;
       this.radiusY = radiusY;
    	
    }
    
    /**
     * Returns the radius of the X axis
     * @return the radius of the X axis
     */
    public int getRadiusX() {
    	return this.radiusX;
    }
    
    /**
     * Returns the radius of the Y axis
     * @return the radius of the Y axis
     */
    public int getRadiusY() {
    	return this.radiusY;
    }
    
    /**
     * Creates a GFX Ellipse
     * @param surface the canvas
     */
    protected JavaScriptObject createGfx(JavaScriptObject surface) {
			return createEllipse(surface,getRadiusX(),getRadiusY());
	}
    
    /**
     * Creates a GFX Ellipse
     * @param surface the canvas
     * @param centerX the x coordinate of center of the Ellipse
     * @param centerY the y coordinate of center of the Ellipse
     * @param radiusX the radius for the X
     * @param radiusY the radius for the Y 
     * @return the DOJO GFX ellipse
     */
    native private JavaScriptObject createEllipse(JavaScriptObject surface, int radiusX,int radiusY)/*-{
       return surface.createEllipse({cx:0,cy:0,rx:radiusX,ry:radiusY});        
    }-*/;
	
   

 
}
