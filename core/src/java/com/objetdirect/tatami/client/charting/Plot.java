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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.objetdirect.tatami.client.charting.effects.Effect;

/**
 * Generic class used for various plot types
 * 
 * @author rdunklau
 *
 * @param <T>: the data for which the plot is designed
 */
public class Plot<T>{
	
	
	public static final String PLOT_TYPE_BUBBLE = "Bubble";
	final protected static String PLOT_TYPE_GRID = "Grid";
	
	final private static String KEY_PLOT_TYPE = "type";
	
	
	protected Map<String,Object> options;
	private Map<String,Serie<T>> series ; 
	private List<Effect> effects;
	private Axis hAxis ;
	private Axis vAxis ;

	
	/**
	 * @param type : One of Plot.PLOT_TYPE_* constants.
	 * Constructs a new plot of the given type.
	 */
	public Plot(String type){
		this();
		options.put(KEY_PLOT_TYPE,type);
	}
	
	public Plot(){
		options = new HashMap<String, Object>();
		series = new HashMap<String,Serie<T>>();
		effects = new ArrayList<Effect>();
	}

	/**
	 * @return the series to draw in this plot
	 */
	public Collection<Serie<T>> getSeries() {
		return series.values();
	}

	/**
	 * @param series the series to draw in this plot
	 */ 
	public void setSeries(List<Serie<T>> series) {
		for(Serie<T> serie : series){
			this.series.put(serie.getName() , serie);
		}
	}

	/**
	 * @return the plot id used by dojo to identify axis,plots and more.
	 */
	public String getName() {
		return toString();
	}

	/**
	 * @return the plot options map
	 */
	public Map<String,Object> getOptions() {
		return options;
	}

	/**
	 * @param serie : a serie to be drawn on this plot
	 */
	public void addSerie(Serie<T> serie){
		series.put(serie.getName(),serie);
	}
	
	/**
	 * @param serie : a serie to be drawn on this plot
	 */
	public void removeSerie(Serie<T> serie){
		series.remove(serie.getName());
	}
	
	/**
	 * @return the horizontal axis, or null if no axis is defined
	 */
	public Axis getXAxis() {
		return hAxis;
	}


	/**
	 * @param axis the horizontal axis, or null if no axis is defined
	 */
	public void setXAxis(Axis axis) {
		hAxis = axis;
	}


	/**
	 * @return the vertical axis, or null if no axis is defined
	 */
	public Axis getYAxis() {
		return vAxis;
	}

	/**
	 * @param axis the vertical axis, or null if no axis is defined
	 */
	public void setYAxis(Axis axis) {
		vAxis = axis;
	}
	
	
	protected void setGridHMajorLines(boolean show){
		options.put("hMajorLines",show);
	}
	
	protected void setGridHMinorLines(boolean show){
		options.put("hMinorLines",show);
	}
	
	protected void setGridVMajorLines(boolean show){
		options.put("vMajorLines",show);
	}
	
	protected void setGridVMinorLines(boolean show){
		options.put("vMinorLines",show);
	}
	
	public String getType(){
		return (String) options.get(KEY_PLOT_TYPE);
	}
	
	public void addEffect(Effect effect){
		effects.add(effect);
	}
	
	public void removeEffect(Effect effect){
		effects.remove(effect);
	}

	public List<Effect> getEffects(){
		return effects;
	}
	
}
