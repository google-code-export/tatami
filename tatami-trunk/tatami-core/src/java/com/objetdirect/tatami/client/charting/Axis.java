package com.objetdirect.tatami.client.charting;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;

public class Axis {


	final public static int VERTICAL = 1;
	
	final public static int HORIZONTAL = 0;
	
	final public static int LEFT = 0;
	
	final public static int RIGHT = 2;
	
	final public static int BOTTOM = 0;
	
	final public static int TOP = 2;
	
	private Map<String,String> options;
	
	private final static String verticalOption = "vertical";
	private final static String leftOption = "leftBottom";
	
	public Axis(){
		options = new HashMap<String, String>();
	}
	
	public Axis(int alignement){
		this();
		setAlignement(alignement);
	}
	
	public void setAlignement(int alignement){
		if((alignement - RIGHT) >= 0 ){
			options.put(leftOption,Boolean.toString(false));
		}else{
			options.put(leftOption,Boolean.toString(true));
		}
		if((alignement - VERTICAL)%2 == 0 ){
			options.put(verticalOption,Boolean.toString(true));
		}else{
			options.put(verticalOption,Boolean.toString(false));
		}
	}
	
	
	public Map<String,String> getOptions() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
