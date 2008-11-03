package com.objetdirect.tatami.client.charting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.objetdirect.tatami.client.charting.effects.Effect;

public class Plot2D extends Plot{

	final public static String PLOT_TYPE_LINES = "Lines";
	final public static String PLOT_TYPE_AREAS = "Areas";
	
	final public static String PLOT_TYPE_MARKERS_LINES = "Markers";
	
	final public static String PLOT_TYPE_SCATTER = "Scatter";
	
	final public static String PLOT_TYPE_LINES_STACKED = "StackedLines";
	final public static String PLOT_TYPE_AREAS_STACKED = "StackedAreas";
	
	public Plot2D() {
		super(PLOT_TYPE_LINES);
	}

	public Plot2D(String type) {
		super(type);
	}
	
	/**
	 * @param tension : the "tension" to be applied.
	 * It allows you to add some curve to the lines 
	 * 
	 * Default : 1
	 * If :
	 * 	-tension > 1 : lines are "curved"
	 *	-tension = "X" : best curve
	 *	-tension = "S" : smooth
	 * 	-tension < 1 : edges are "sharpened"
	 */
	public void setTension(String tension){
		options.put("tension" , tension);
	}
	
	/**
	 * Creates a shadow
	 * 
	 * @param width : the shadow's width
	 * @param dx : shadow's x position from the line
	 * @param dy : shadow's y position from the line
	 */
	public void setShadow(int width, int dx, int dy){
		Map<String,Integer> shadow = new HashMap<String, Integer>();
		shadow.put("dx",new Integer(dx));
		shadow.put("dy",new Integer(dy));
		shadow.put("dw",new Integer(width));
		options.put("shadows", shadow);
	}
	
	/**
	 * @param show : Set wether to show the markers or not.
	 * Overrides the default "PLOT_TYPE" behavior.
	 */
	public void setShowMarkers(boolean show){
		options.put("markers",show);
	}
	
	/**
	 * @param show : Set wether to show the markers or not.
	 * Overrides the default "PLOT_TYPE" behavior.
	 */
	public void setShowLines(boolean show){
		options.put("lines",show);
	}


}
