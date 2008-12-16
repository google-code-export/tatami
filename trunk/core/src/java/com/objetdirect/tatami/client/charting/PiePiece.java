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
 * A data object used to represent pie pieces in a pie plot
 * 
 * @author rdunklau
 *
 */
public class PiePiece implements ConvertibleToJSObject,HasTooltip{


	private double value;

	private String label;

	private String color;
	
	private String fontColor;
	
	private String tooltip;
	
	

	/**
	 * @param value: this pie's value
	 */
	public PiePiece(double value) {
		this.value = value;
	}

	/**
	 * @param value : this pie's value
	 * @param label : this pie's label
	 */
	public PiePiece(double value , String label) {
		this(value);
		this.label = label;
	}


	/**
	 * @param value
	 * @param label
	 * @param color
	 */
	public PiePiece( double value , String label,String color) {
		this(value,label);
		this.color = color;
	}
	

	/**
	 * @param value
	 * @param label
	 * @param color
	 * @param fontColor
	 */
	public PiePiece(double value , String label,String color, String fontColor) {
		this(value, label, color);
		this.fontColor = fontColor;
	}
	
	/**
	 * @param value
	 * @param label
	 * @param color
	 * @param fontColor
	 * @param tooltip
	 */
	public PiePiece(double value , String label,String color, String fontColor, String tooltip) {
		this(value,label,color,fontColor);
		this.tooltip = tooltip;
	}
	
	/**
	 * @return: the font color used for this pie piece label
	 */
	public String getFontColor() {
		return fontColor;
	}

	/**
	 * @param fontColor: the font color used for this pie piece label
	 */
	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}

	/**
	 * @return: returns the filling color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param color: the filling color
	 */
	public void setColor(String color) {
		this.color = color;
	}
	
	/**
	 * @return: this pie piece value
	 */
	public double getValue() {
		return value;
	}

	/**
	 * @param value: this pie piece value
	 */
	public void setValue(double value) {
		this.value = value;
	}

	/**
	 * @return: this pie piece label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label: this pie piece label
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	
	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.ConvertibleToJSObject#toJSObject()
	 */
	public JavaScriptObject toJSObject() {
		return getJSPiePiece(value, label, color , fontColor,tooltip);
	}
	
	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.charting.HasTooltip#getTooltip()
	 */
	public String getTooltip() {
		return tooltip;
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.charting.HasTooltip#setTooltip(java.lang.String)
	 */
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}
	
	/**
	 * Convert the java object to a javascript object
	 * 
	 * @param value
	 * @param label
	 * @param color
	 * @param fontColor
	 * @param tooltip
	 * @return
	 */
	private native JavaScriptObject getJSPiePiece(double value, String label , String color , String fontColor, String tooltip)/*-{
		var pie = {y: value};
		if(label!=null){
			pie.text = label;
		}
		if(color!=null){
			pie.color = color;
		}
		if(fontColor != null){
			pie.fontColor = fontColor;
		}
		if(tooltip != null){
			pie.tooltip = tooltip;
		}
		return pie;
	}-*/;
	
}
