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
 * Authors: Vianney Grassaud
 * Initial developer(s):
 * Contributor(s):
 */
package com.objetdirect.tatami.client.gfx;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.ui.Image;

/**
 * Represents a GFX image. 
 * A gfx is a <code>GraphicalObject</code> but some properties are not use : 
 * the color of the fill, the stroke and the pattern. 

 */
public class ImageGfx extends RectangularShape {

	
	
	/** The url of the image */
	private String url;
	
	/**
	 * Creates a GFX image. 
	 * @param url the url of the image
	 * @param width the width of the image
	 * @param height the height of the image
	 */
	public ImageGfx(String url,double width, double height) {
		super(width,height);
		this.url = url;
		
		
	}
	
	/**
	 * Creates a GFX image from a GWT Image
	 * @param image a GWT image
	 */
	public ImageGfx(Image image) {
		this(image.getUrl(),image.getWidth(),image.getHeight());
	}
	
	/**
	 * Returns the url of the image
	 * @return the url of the image
	 */
	public String getUrl() {
		return this.url;
	}
	
	
	/**
	 * Creates the gfx image
	 * @param surface the canvas
	 */
	protected JavaScriptObject createGfx(JavaScriptObject surface) {
      	return createImage(surface,getUrl(),getWidth(),getHeight());
	}

    /**
     * Creates the gfx Image
     * @param surface the canvas to create the image
     * @param url the url to use of the image 
     * @param width the width of the image
     * @param height the height of the image
     * @return the DOJO GFX image
     */
	native private JavaScriptObject createImage(JavaScriptObject surface,String url, double width,double height) /*-{
	     return  surface.createImage({  src : url,width:width,height:height});
	}-*/; 
	
	
	
	

	
}//end of class
