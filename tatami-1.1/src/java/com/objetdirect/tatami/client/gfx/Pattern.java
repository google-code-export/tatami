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
import com.google.gwt.user.client.ui.Image;
/**
 * 
 * A pattern is an image which is used to fill 
 * a <code>GraphicalOject</code>. The pattern is applied until the 
 * <code>GraphicalOject</code> is fully filled.<br>
 * Example :<br>
 * <p><code>
 *      GraphicalObject circle = new Circle(50);<br>
 *      Pattern pattern = new Pattern("icon.gif",0,0,10,10);<br>
 *      circle.applyPattern(pattern);
 *   </code>
 * </p> 
 * The <b>pattern is prioritary</b> on the backgroud color. To unuse the pattern, you 
 * need to apply the <code>DEFAULT_PATTERN</code> on the <code>GraphicalObject</code>
 */
public class Pattern {

	/**
	 * The default Pattern, (an empty pattern)
	 */
	static public final Pattern DEFAULT_PATTERN = new Pattern(" ",0,0,0,0);
	
	/**
	 * Url of the image
	 */
	private String url ="";
	
	/** the position of the pattern*/
	private Point position;
	
	
    /**
     * The with of the image
     */
	private double width = 10;
	/**
	 * the height of the image
	 */
	private double height = 10;
	
	/**
	 * Creates a <code>Pattern</code> for a <code>GraphicalObject</code>
	 * @param url the url of image to use
	 * @param xCoord the x coordinate
	 * @param yCoord the y coordinate
	 * @param width the width of the image
	 * @param height the height of the image
	 */
	public Pattern(String url,double xCoord,double yCoord, double width, double height) {
		this.url = url;
		position = new Point(xCoord,yCoord);
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Creates a <code>Pattern</code> for a GraphicalObject from
	 * a gfx image
	 * @param x the x coordinate for the pattern
	 * @param y the y coordinate for the pattern
	 */
	public Pattern(ImageGfx image, double xCoord, double yCoord) {
		this(image.getUrl(),xCoord,yCoord,image.getWidth(),image.getHeight());
	}
	
	/**
	 * Creates a <code>Pattern</code> for a <code>GraphicalObject</code> from
	 * a GWT image 
	 * @param image the GWT image
	 * @param xCoord the x coordinate for the pattern
	 * @param yCoord the y coordinate for the pattern
	 */
	public Pattern(Image image, double xCoord, double yCoord) {
		this(image.getUrl(),xCoord,yCoord,image.getWidth(),image.getHeight());
	}
	/**
	 * Returns the GFX DOJO pattern
	 * @return the GFX DOJO pattern.
	 */
	public JavaScriptObject getGFXPattern() {
		return createPattern(getUrl(),getX(),getY(),getWidth(),getHeight());
	}
	
	/**
	 * Creates the DOJO GFX Pattern
	 * @param url the url of the image
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param width the width of the image
	 * @param height the height of the image
	 * @return the DOJO GFX pattern
	 */
	private native JavaScriptObject createPattern(String url,double x, double y, double width, double height)/*-{
	    return {type:"pattern", src:url,x:x,y:y,width:width,height:height};
	}-*/;
	
	/**
	 * Returns the url of the image
	 * @return the url of the image
	 */
	public String getUrl() {
		return this.url;
	}

	/**
	 * Returns the x coordinate of the first image
	 * @return the x coordinate of the first image
	 */
	public double getX() {
		return this.position.getX();
	}

	/**
	 * Returns the y coordinate of the first image
	 * @return the y coordinate of the first image
	 */
	public double getY() {
		return this.position.getY();
	}
	
	/**
	 * Returns the width of the image
	 * @return the width of the image
	 */
	public double getWidth() {
		return this.width;
	}
	/**
	 * Returns the height of the image
	 * @return the height of the image
	 */
	public double getHeight() {
		return this.height;
	}
}//end of class
