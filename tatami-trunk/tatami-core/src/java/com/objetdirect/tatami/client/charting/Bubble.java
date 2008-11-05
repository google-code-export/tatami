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

public class Bubble implements ConvertibleToJSObject{

	private double x;
	private double y;
	private double size;
	private String tooltip;
	
	public double getX() {
		return x;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public double getY() {
		return y;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public double getSize() {
		return size;
	}
	
	public void setSize(double size) {
		this.size = size;
	}
	
	public Bubble(double x, double y ,double size) {
		super();
		this.size = size;
		this.x = x;
		this.y = y;
	}
	
	public Bubble(double x, double y ,double size,String tooltip) {
		this(x,y,size);
		this.tooltip = tooltip;
	}

	public JavaScriptObject toJSObject() {
		return getJSBubble(x, y, size,getTooltip());
	}
	
	private native JavaScriptObject getJSBubble(double x, double y, double size, String tooltip)/*-{
		return {x: x,y:y,size:size,tooltip:tooltip};
	}-*/;
	
	public String getTooltip() {
		return tooltip != null ? tooltip : y +"";
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}
	
}
