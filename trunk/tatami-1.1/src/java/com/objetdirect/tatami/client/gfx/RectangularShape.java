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



public abstract class RectangularShape extends GraphicObject {

	
	/**
	 * The width of the rectangle
	 */
	private double width;
	/**
	 * The height of the rectangle
	 */
	private double height;
	
	
	
	/**
	 * Creates a rectangle specifying the width and the height
	 * @param width width of the rectangle
	 * @param height height of the rectangle
	 */
	public RectangularShape(double width, double height) {
		super();
		this.width = width;
		this.height = height;
	
		
	}
	
    /**
     * Creates a GFX <code>Rect</code> from a <code>Rectangle</code>
     * @param rect the <code>Rectangle</code> to determinate the position, width and height
     */	
	public RectangularShape(Rectangle rect) {
		this(rect.getWidth(),rect.getHeight());
	}
	
	/**
	 * Returns the width of the rectangle
	 * @return width of the rectangle
	 */
    public double getWidth() {
	  return this.width;
    }

    /**
     * Returns the height of the rectangle
     * @return the height of the rectangle
     */
    public double getHeight() {
	  return this.height;
    }
	
    /**
	 * Sets the width of the image 
	 * @param width
	 */
	public void setWidth(int width) {
		final float  factor = width/(float)getWidth();
		this.width = width;
		//do a scale in x coordinate
		if ( getShape() != null) {
		  scale(factor,1);
		}
	}
	
	/**
	 * Sets the Height of the image
	 * @param height the height of the image
	 */
	public void setHeight(int height) {
		final float  factor = height/(float)getHeight();
		this.height = height;
		//do a scale in y coordinate
		if ( getShape() != null) {
		  scale(1,factor);
		}
	}

	
}
