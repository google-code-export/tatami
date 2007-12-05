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
package com.objetdirect.tatami.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.DOM;


/**
 *  
 * Class of widgets that permit to choose a color (represented by 
 * a string with an hexadecimal format : #RRGGBB) from a selector of colors DOJO widget. 
 * <p>
 * This component is an encapsulation GWT of Dojo components. Warning the widget exist under 3 faces : 
 * <ul>
 * <li>a GWT widget (this!)</li>
 * <li>a Dojo widget (which play the role of 'this' for GWT)</li>
 * <li>a structur of DOM elements, the only exploitable presentation by the navigator.</li>
 * </ul>
 * </p>
 * 
 
 */
public class ColorChooser extends AbstractDojoFocus {
 
    /** 
     *   a palette with 12 color : 3x4
     
     **/
	static final public String TWELVE_COLORS = "3x4";  	
    
	/** 
	 *a palette with 70 colors : 7x10.
 
	 * 
	 **/
	static final public String SEVENTY_COLORS = "7x10";

    /**
	 * the selected color, default is black. 
	
	 */
	private String color = "#000000";
	
	
	/** size for the palette.
	
	 **/
	private String size = SEVENTY_COLORS;
	
	
	/**
	 * Creates a selector of color specifying the size
	 * @param size the size for the palette :  <code>TWELVE_COLORS, SEVENTY_COLORS</code>
	 */
	public ColorChooser(String size){
		super();
		DOM.setElementProperty(getElement(),"id","colorP");
		this.size = size;
	}
	
	/**
	 * Creates a selector with 70 colors.
	 */
	public ColorChooser(){
		this(SEVENTY_COLORS);
	}
	
	
	/**
	 * English : Creates a DOJO palette of colors with the given size by the constructor. 
	 */
	public void createDojoWidget() {
		this.dojoWidget = createColorPalette(size);
	}
	
    /**
     * Returns the name of the DOJO widget.
     * @return "ColorPalette".
     */
	public String getDojoName() {
		return "dijit.ColorPalette";
	}
	
	/**
	 * 
	 * Creates a DOJO widget of type selector of colors (ColorPalette).
	 * @param size the type of palette : <code>TWELVE_COLORS, SEVENTY_COLORS</code>
	 * @return the DOJO widget
	 */
	private native JavaScriptObject createColorPalette(String size)
	/*-{
		var widget = new $wnd.dijit.ColorPalette(
			{
				palette: size
			}
		);
		return widget;
	}-*/;
	
	/**
	 * Arms a callback on the call of method <code>onColorSelect</code> from the DOJO widget to 
     * this one give the hand to the method <code>onValueChanged</code> of the GWT widget.
	 * @param dojoWidget DOJO widget fulfiling the selector of colors.
	 */
	 private  native void setEventCallback(JavaScriptObject dojoWidget)	
	/*-{
		dojoWidget.onChange = function(color) {
	    dojoWidget.gwtWidget.@com.objetdirect.tatami.client.ColorChooser::onValueChanged(Ljava/lang/String;)(color);
		};
	}-*/;
	

	 /**
	  * Arms a callback when a user clicks on a color from the palette. 
	  * 
	  */
	 public void onLoad() {
			setEventCallback(getDojoWidget());
	 }
	 
	 
	
    /**
     * Returns the selected color. 
     * @return the color with a hexadecimal representation format. 
     */
	public String getColor() {
		return color;
	}
	
	/**
	 * Sets the selected color. The GWT widget notify a value changement if the color is different. 
	 * @param the new color to select with a hexadecimal representation format.
	 */
	public void setColor(String color) {
		if (this.color==null && color!=null || 
			this.color!=null && color==null ||
			this.color!=null && !this.color.equals(color)) 
		{
			this.color = color;
		    if (changeListeners != null) {
		        changeListeners.fireChange(this);
		    }
		}
	}
	
	/**
	 * Callback method called by the DOJO widget when a color is selected by the a user. 
	 * By default this method changes only the color of the GWT widget.
     * @param color the color selected.
	 * @see #setColor(String)
	 */
	public void onValueChanged(String color) {
		setColor(color);
	}
	

		
}
