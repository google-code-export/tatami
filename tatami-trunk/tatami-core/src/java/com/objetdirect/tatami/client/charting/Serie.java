package com.objetdirect.tatami.client.charting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Serie<T> {
	
	private String name;
	
	private List<T> data;
	private Map<String,Object> options;
	private Map<String, Object> strokeOptions = new HashMap<String, Object>();

	final public static String MARKER_TYPE_CIRCLE=		"m-3,0 c0,-4 6,-4 6,0 m-6,0 c0,4 6,4 6,0";
	final public static String MARKER_TYPE_SQUARE=		"m-3,-3 l0,6 6,0 0,-6 z";
	final public static String MARKER_TYPE_DIAMOND=	"m0,-3 l3,3 -3,3 -3,-3 z";
	final public static String MARKER_TYPE_CROSS=		"m0,-3 l0,6 m-3,-3 l6,0";
	final public static String MARKER_TYPE_X=		"m-3,-3 l6,6 m0,-6 l-6,6";
	final public static String MARKER_TYPE_TRIANGLE=	"m-3,3 l3,-6 3,6 z";
	final public static String MARKER_TYPE_TRIANGLE_INVERTED= "m-3,-3 l3,6 3,-6 z";

	public Serie(){
		options = new HashMap<String, Object>();
		this.data = new ArrayList<T>();
	}
	
	/**
	 * A serie contains data, stored as a List.
	 * This data can be : a Number, a Point @see {@link Point}, a Bubble @see {@link Bubble}
	 * or a PiePiece @see {@link PiePiece}, depending on the chart type
	 * 
	 * @param data : the data to plot on the chart
	 */
	public Serie(List<T> data){
		options = new HashMap<String, Object>();
		this.data = data;
	}
	
	/**
	 * Similar to @see {@link #Serie(List)}, but initialize the 
	 * serie with a user defined name.
	 * 
	 * @param name
	 * @param data
	 */
	public Serie(List<T> data, String name ){
		this(data);
		setName(name);
	}
	
	public Map<String,Object> getOptions() {
		return options;
	}
	

	public List<T> getData() {
		return data;
	}
	
	public void setData(List<T> data) {
		this.data = data;
	}
	
	public void addData(T o){
		this.data.add(o);
	}
	
	
	public void addData(int index , T o){
		this.data.add(index,o);
	}
	
	public void removeData(T o){
		this.data.remove(o);
	}
	
	public void removeData(int index){
		this.data.remove(index);
	}
	

	/**
	 * @param color : the strokes color
	 */
	public void setStrokeColor(String color){
		strokeOptions.put("color",color);
		options.put("stroke",strokeOptions);
	}
	
	/**
	 * @param width : sets the stroke width
	 */
	public void setStrokeWidth(int width){
		strokeOptions.put("width",width);
		options.put("stroke",strokeOptions);
	}
	
	/**
	 * @param color : sets the color which fills the area under the lines
	 */
	public void setFillColor(String color){
		options.put("fill", color);
	}
	
	/**
	 * Forces the plot to display its axes from this min 
	 * @param min : min value displayed
	 */
	public void setMinDisplayed(double min){
		options.put("min", min);
	}
	
	/**
	 * Forces the plot to display its axes up to  this max 
	 * @param max : max value displayed
	 */
	public void setMaxDisplayed(double max){
		options.put("max", max);
	}
	
	/**
	 * @param marker : the marker type, as an SVG path.
	 * Predefined types are available as Serie.MARKER_TYPE_*
	 */
	public void setMarkerType(String marker){
		options.put("marker",marker);
	}
	
	/**
	 * @return the unique, displayed name of this serie
	 */
	public String getName() {
		return name != null ? name : toString();
	}

	/**
	 * @param name the unique, displayed name of this serie
	 */
	public void setName(String name) {
		this.name = name;
	}

	
}
