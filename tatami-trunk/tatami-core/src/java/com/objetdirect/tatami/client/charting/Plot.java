package com.objetdirect.tatami.client.charting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.objetdirect.tatami.client.charting.effects.Effect;

public class Plot<T>{
	
	
	
	public static final String PLOT_TYPE_BUBBLE = "Bubble";
	final public static String PLOT_TYPE_GRID = "Grid";
	
	final private static String KEY_PLOT_TYPE = "type";
	
	
	protected Map<String,Object> options;
	private List<Serie<T>> series ; 
	private List<Effect> effects;
	private Axis hAxis ;
	private Axis vAxis ;
	private String name;
	
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
		series = new ArrayList<Serie<T>>();
		effects = new ArrayList<Effect>();
	}

	/**
	 * @return the series to draw in this plot
	 */
	public List<Serie<T>> getSeries() {
		return series;
	}

	/**
	 * @param series the series to draw in this plot
	 */ 
	public void setSeries(List<Serie<T>> series) {
		this.series = series;
	}

	/**
	 * @return the plot id used by dojo to identify axis,plots and more.
	 */
	public String getName() {
		if(this.name == null){
			return toString();
		}else{
			return this.name;
		}
	}

	protected void setName(String name) {
		this.name = name;
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
		series.add(serie);
	}
	
	/**
	 * @param serie : a serie to be drawn on this plot
	 */
	public void removeSerie(Serie<T> serie){
		series.remove(serie);
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
	
	
	public void setGridHMajorLines(boolean show){
		options.put("hMajorLines",show);
	}
	
	public void setGridHMinorLines(boolean show){
		options.put("hMinorLines",show);
	}
	
	public void setGridVMajorLines(boolean show){
		options.put("vMajorLines",show);
	}
	
	public void setGridVMinorLines(boolean show){
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
