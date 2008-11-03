package com.objetdirect.tatami.client.charting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.objetdirect.tatami.client.ConvertibleToJSObject;
import com.objetdirect.tatami.client.JSHelper;


/**
 * @author rdunklau
 *
 */
public class Axis {


	final public static int VERTICAL = 1;
	
	final public static int HORIZONTAL = 0;
	
	final public static int LEFT = 0;
	
	final public static int RIGHT = 2;
	
	final public static int BOTTOM = 0;
	
	final public static int TOP = 2;
	
	final public static String FIX_TYPE_NONE = "none";
	final public static String FIX_TYPE_MINOR = "minor";
	final public static String FIX_TYPE_MAJOR = "major";
	final public static String FIX_TYPE_MICRO = "micro";
	

	private String fixUpper = "none";	// align the upper on ticks: "major", "minor", "micro", "none"
	private String fixLower=    "none";	// align the lower on ticks= "major"; "minor"; "micro"; "none"
	private Boolean natural=     false;		// all tick marks should be made on natural numbers
	private Boolean includeZero= false;		// 0 should be included
	private Boolean fixed=       true;		// all labels are fixed numbers
	private Boolean majorLabels= true;		// draw major labels
	private Boolean minorTicks=  true;		// draw minor ticks
	private Boolean majorTicks=  true;		// draw minor ticks
	private Boolean minorLabels= true;		// draw minor labels
	private Boolean microTicks=  false;		// draw micro ticks
	private Boolean htmlLabels=  true;		// use HTML to draw labels

	

	
	private String name ;
	
	private Map<String,Object> options;
	private Map<String,Object> majorTickOptions;
	private Map<String,Object> minorTickOptions;
	private Map<String,Object> microTickOptions;

	
	
	private List<AxisLabel> labels;
	
	private final static String verticalOption = "vertical";
	private final static String leftOption = "leftBottom";
	
	private class AxisLabel implements ConvertibleToJSObject{
		String text;
		double value;
		
		public AxisLabel(String text, double value) {
			super();
			this.text = text;
			this.value = value;
		}

		public JavaScriptObject toJSObject() {
			return toJS(text, value);
		}
		
		private native JavaScriptObject toJS(String text, double value)/*-{
			return {text: text, value:value};
		}-*/;
	}
	
	public Axis(){
		options = new HashMap<String,Object>();
		setAlignement(HORIZONTAL | BOTTOM);
	}
	
	public Axis(int alignement){
		this();
		setAlignement(alignement);
	}
	
	public void setAlignement(int alignement){
		if((alignement - RIGHT) >= 0 ){
			options.put(leftOption,Boolean.FALSE);
		}else{
			options.put(leftOption,Boolean.TRUE);
		}
		if((alignement - VERTICAL)%2 == 0 ){
			options.put(verticalOption,Boolean.TRUE);
		}else{
			options.put(verticalOption,Boolean.FALSE);
		}
	}
	
	
	public Map<String,?> getOptions() {
		return options;
	}

	public String getId() {
		if(name != null){
			return name;
		}
		return this.toString();
	}

	/**
	 * @return a default horizontal axis instance
	 */
	public static Axis simpleXAxis() {
		return new Axis(BOTTOM | HORIZONTAL);
	}

	
	/**
	 * @return a default vertical axis instance
	 */
	public static Axis simpleYAxis() {
		return new Axis(LEFT | VERTICAL);
	}

	public String getFixUpper() {
		return fixUpper;
	}

	/**
	 * @param fixUpper : Forces the upper value on the axis to be aligned with the corresponding tick
	 * Possible value are :
	 * -none
	 * -minor
	 * -major
	 * -micro
	 */
	public void setFixUpper(String fixUpper) {
		this.fixUpper = fixUpper;
		options.put("fixUpper", fixUpper);
	}

	public String getFixLower() {
		return fixLower;
	}

	/**
	 * @param fixLower : Forces the lower value on the axis to be aligned with the corresponding tick
	 * Possible value are :
	 * -none
	 * -minor
	 * -major
	 * -micro
	 */
	public void setFixLower(String fixLower) {
		this.fixLower = fixLower;
		options.put("fixLower", fixLower);
	}

	public Boolean getNatural() {
		return natural;
	}

	/**
	 * @param natural : forces all ticks to be on natural numbers
	 */
	public void setNatural(Boolean natural) {
		this.natural = natural;
		options.put("natural", natural);
	}

	public Boolean getIncludeZero() {
		return includeZero;
	}

	/**
	 * @param includeZero : forces the axis to include the zero
	 */
	public void setIncludeZero(Boolean includeZero) {
		this.includeZero = includeZero;
		options.put("includeZero", includeZero);
	}

	public Boolean getFixed() {
		return fixed;
	}

	/**
	 * @param fixed : fixes the precision on the labels
	 */
	public void setFixed(Boolean fixed) {
		this.fixed = fixed;
		options.put("fixed", fixed);
	}

	public Boolean getMajorLabels() {
		return majorLabels;
	}

	/**
	 * @param majorLabels : whether the major ticks should be labeled
	 */
	public void setMajorLabels(Boolean majorLabels) {
		this.majorLabels = majorLabels;
		options.put("majorLabels", majorLabels);
	}

	public Boolean getMinorTicks() {
		return minorTicks;
	}

	/**
	 * @param minorTicks : whether the minor ticks should be displayed
	 */
	public void setMinorTicks(Boolean minorTicks) {
		this.minorTicks = minorTicks;
		options.put("minorTicks", minorTicks);
	}

	public Boolean getMinorLabels() {
		return minorLabels;
	}

	/**
	 * @param minorLabels : whether the minor ticks should be labeled
	 */
	public void setMinorLabels(Boolean minorLabels) {
		this.minorLabels = minorLabels;
		options.put("minorLabels", minorLabels);
	}

	public Boolean getMicroTicks() {
		return microTicks;
	}

	/**
	 * @param microTicks : whether the micro ticks should be displayed
	 */
	public void setMicroTicks(Boolean microTicks) {
		this.microTicks = microTicks;
		options.put("microTicks", microTicks);
	}

	public Boolean getMajorTicks() {
		return majorTicks;
	}

	/**
	 * @param majorTicks : whether the major ticks should be displayed
	 */
	public void setMajorTicks(Boolean majorTicks) {
		this.majorTicks = majorTicks;
		options.put("majorTicks", microTicks);
	}

	public Boolean getHtmlLabels() {
		return htmlLabels;
	}

	/**
	 * @param htmlLabels 
	 */
	public void setHtmlLabels(Boolean htmlLabels) {
		this.htmlLabels = htmlLabels;
		options.put("htmlLabels", htmlLabels);
	}

	/**
	 * @param options : options to be passed to the Chart. Prefer using the 
	 * various setters.
	 */
	public void setOptions(Map<String, Object> options) {
		this.options = options;
	}
	
	
	/**
	 * @param min : min value for the axis
	 */
	public void setMin(double min){
		options.put("min", min);
	}
	
	/**
	 * @param max : max value for the axis
	 */
	public void setMax(double max){
		options.put("max", max);
	}
	
	protected void setName(String name){
		this.name = name;
	}
	
	/**
	 * @param color : minor ticks color
	 */
	public void setMinorTicksColor(String color){
		if(minorTickOptions == null){
			minorTickOptions = new HashMap<String, Object>();
		}
		minorTickOptions.put("color",color);
		options.put("minorTick", minorTickOptions);
	}
	/**
	 * @param color : major ticks color
	 */
	public void setMajorTicksColor(String color){
		if(majorTickOptions == null){
			majorTickOptions = new HashMap<String, Object>();
		}
		majorTickOptions.put("color",color);
		options.put("majorTick", majorTickOptions);
	}
	
	/**
	 * @param color : micro ticks color
	 */
	public void setMicroTicksColor(String color){
		if(microTickOptions == null){
			microTickOptions = new HashMap<String, Object>();
		}
		microTickOptions.put("color",color);
		options.put("microTick", microTickOptions);
	}
	
	/**
	 * @param length : minor ticks length
	 */
	public void setMinorTicksLength(double length){
		if(minorTickOptions == null){
			minorTickOptions = new HashMap<String, Object>();
		}
		minorTickOptions.put("length",length);
		options.put("minorTick", minorTickOptions);
	}
	
	/**
	 * @param length : major ticks length
	 */
	public void setMajorTicksLength(double length){
		if(majorTickOptions == null){
			majorTickOptions = new HashMap<String, Object>();
		}
		majorTickOptions.put("length",length);
		options.put("majorTick", majorTickOptions);
	}
	
	
	/**
	 * @param length : micro ticks length
	 */
	public void setMicroTicksLength(double length){
		if(microTickOptions == null){
			microTickOptions = new HashMap<String, Object>();
		}
		microTickOptions.put("length",length);
		options.put("microTick", microTickOptions);
	}
	
	/**
	 * @param step : step between the major ticks
	 */
	public void setMajorTickStep(double step) {
		options.put("majorTickStep",step);
	}
	
	
	/**
	 * @param step : step between the minor ticks
	 */
	public void setMinorTickStep(double step) {
		options.put("minorTickStep",step);
	}
	
	/**
	 * @param step : step between the micro ticks
	 */
	public void setMicroTickStep(double step) {
		options.put("microTickStep",step);
	}
	
	/**
	 * @param color : axis labels font color 
	 */
	public void setFontColor(String color){
		options.put("fontColor", color);
	}
	/**
	 * @param font : axis labels font (ex: "normal normal bold 14pt Tahoma")  
	 */
	public void setFont(String font){
		options.put("font", font);
	}
	
	/**
	 * @param forValue : the value for which we define a label
	 * @param text : the label itself
	 */
	public void addLabel(double forValue, String text){
		if(labels == null){
			labels = new ArrayList<AxisLabel>();
		}
		labels.add(new AxisLabel(text,forValue));
		options.put("labels",JSHelper.convertObjectToJSObject(labels));
	}
	
	
	/**
	 * Add labels for the 
	 * @param labels
	 */
	public void addLabels(String[] labels){
		
	}
	
	
}
