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
 * Authors: Ronan Dunklau
 * Initial developer(s): Ronan Dunklau
 * Contributor(s): 
 */
package com.objetdirect.tatami.client.charting;

import com.google.gwt.core.client.JavaScriptObject;
import com.objetdirect.tatami.client.ConvertibleToJSObject;

/**
 * A point object, designed to be used with Plot2D
 * 
 * @author rdunklau
 *
 */
public class Point implements ConvertibleToJSObject,HasTooltip{

	private double x;
	private double y;
	private String tooltip;
	
	
	public Point(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public Point(double x, double y , String tooltip) {
		this(x,y);
		this.tooltip = tooltip;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public String getTooltip() {
		return tooltip != null ? tooltip : y +"";
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}
	
	public JavaScriptObject toJSObject() {
		return toJSPoint(x, y , getTooltip());
	}
	
	private native JavaScriptObject toJSPoint(double x, double y,String tooltip)/*-{
		return {x: x,y:y,tooltip:tooltip};
	}-*/;
	
	
	
	
	
}
