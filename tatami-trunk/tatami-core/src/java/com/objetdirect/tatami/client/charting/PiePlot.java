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

public class PiePlot<T> extends Plot<T>{

	final public static String PLOT_TYPE_PIE = "Pie";
	
	public PiePlot(){
		super(PiePlot.PLOT_TYPE_PIE);
	}
	
	/**
	 * @param offset : the label position from the edge of the plot
	 * Negative values will place the label outside of the plot, whereas positive values will place in inside
	 */
	public void setLabelOffset(int offset){
		options.put("labelOffset", offset);
	}
	
	/**
	 * @param precision : the precision for percentiles 
	 */
	public void setPrecision(int precision){
		options.put("precision", precision);
	}
	
	/**
	 * @param font : font to be applied on the various labels
	 */
	public void setFont(String font){
		options.put("font", font);
	}
	
	/**
	 * @param fontColor : default font color. Can be overriden by each PiePiece
	 */
	public void setFontColor(String fontColor){
		options.put("fontColor", fontColor);
	}
	
	/**
	 * @param radius : the plot radius. Use it if you want to fix the plot size independently from your
	 * chart size. 
	 */
	public void setRadius(int radius){
		options.put("radius", radius);
	}
	
}
