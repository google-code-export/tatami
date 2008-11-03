package com.objetdirect.tatami.client.charting;

import com.google.gwt.core.client.JavaScriptObject;
import com.objetdirect.tatami.client.ConvertibleToJSObject;

public class Point implements ConvertibleToJSObject{

	private double x;
	private double y;
	private String tooltip;
	
	
	public Point(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public Point(double x, double y , String tooltip) {
		this(x,y);
		this.tooltip = tooltip;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public String getTooltip() {
		return tooltip != null ? tooltip : y +"";
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}
	
	public JavaScriptObject toJSObject() {
		return toJSPoint(x, y , getTooltip());
	}
	
	private native JavaScriptObject toJSPoint(double x, double y,String tooltip)/*-{
		return {x: x,y:y,tooltip:tooltip};
	}-*/;
	
	
	
	
	
}
