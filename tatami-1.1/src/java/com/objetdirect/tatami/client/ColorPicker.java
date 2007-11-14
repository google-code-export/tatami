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
 * Authors: Vianney Grassaud
 * Initial developer(s):
 * Contributor(s):
 */
package com.objetdirect.tatami.client;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * provides an interactive HSV ColorPicker similar to
 * PhotoShop's color selction tool. Will eventually
 * mixin FormWidget and be used as a suplement or a
 * 'more interactive' replacement for ColorChooser
 * 
 * @author Vianney
 *
 */
public class ColorPicker extends AbstractDojoFocus {

	/**
	 *show/update HSV input nodes
	 */
	private boolean showHsv = true;
	/**
	 * show/update RGB input nodes
	 */
	private boolean showRgb = true;
	/**
	 * show/update Hex value field
	 */
	private boolean showHex = true;
	/**
	 * toggle to use slideTo (true) or just place the cursor (false) on click
	 */
	private boolean animatePoint = true;
	
	/**
	 * Creates a Color Picker 
	 * @param showHsv  show/update HSV input nodes
	 * @param showRgb show/update RGB input nodes
	 * @param showHex show/update Hex value field
	 * @param animatePoint toggle to use slideTo (true) or just place the cursor (false) on click
	 */
	public ColorPicker(boolean showHsv,boolean showRgb,boolean showHex,boolean animatePoint){
		this.showHex = showHex;
		this.showRgb = showRgb;
		this.animatePoint = animatePoint;
		this.showHsv = showHsv;
	}

	/**
	 * Creates a standard ColorPicker.
	 */
	public ColorPicker() {
		this(true,true,true,true);
	}
	
	/**
	 * Create the DOJO ColorPicker
	 */
	public void createDojoWidget() {
		this.dojoWidget = createDojoColorPicker(showHsv,showRgb,showHex,animatePoint);
	}
	
	/**
	 * Creates the DOJO Color Picker 
	 * @param showHsv  show/update HSV input nodes
	 * @param showRgb show/update RGB input nodes
	 * @param showHex show/update Hex value field
	 * @param animatePoint toggle to use slideTo (true) or just place the cursor (false) on click
	 * @return the DOJO widget
	 */
	private native JavaScriptObject createDojoColorPicker(boolean showHsv,boolean showRgb,boolean showHex,boolean animatePoint) /*-{
	   var widget =  new $wnd.dojox.widget.ColorPicker({
	      showHsv : showHsv,
	      showRgb : showRgb,
	      showHex : showHex,
	      animatePoint : animatePoint});
	    return widget;
	}-*/;

	/**
	 * Calls the startup method when the GWT widget is attached
	 */
	public void onLoad() {
		DojoController.startup(this);
	}
	
	/**
	 * Returns the name of the DOJO widget 
	 * @return "dojox.widget.ColorPicker"
	 */ 
	public String getDojoName() {
		return "dojox.widget.ColorPicker";
	}

}
