package com.objetdirect.tatami.client.charting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;

public class Plot {
	
	
	final public static String PLOT_TYPE_LINES = "Lines";
	final public static String PLOT_TYPE_AREAS = "Areas";
	final public static String PLOT_TYPE_MARKERS_LINES = "Markers";
	final public static String PLOT_TYPE_SCATTER = "MarkersOnly";
	
	final private static String KEY_PLOT_TYPE = "type";
	final private static String KEY_PLOT_NAME = "name";
	
	private Map<String,String> options;
	
	public Plot(String type){
		options = new HashMap<String, String>();
		options.put(KEY_PLOT_TYPE,type);
	}
	

	public String getName() {
		return null;
	}

	public Map<String,String> getOptions() {
		return null;
	}

	
}
