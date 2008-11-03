package com.objetdirect.tatami.client.charting;

import com.google.gwt.core.client.JavaScriptObject;
import com.objetdirect.tatami.client.ConvertibleToJSObject;

public class Bubble implements ConvertibleToJSObject{

	private double x;
	private double y;
	private double size;
	private String tooltip;
	
	public double getX() {
		return x;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public double getY() {
		return y;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public double getSize() {
		return size;
	}
	
	public void setSize(double size) {
		this.size = size;
	}
	
	public Bubble(double x, double y ,double size) {
		super();
		this.size = size;
		this.x = x;
		this.y = y;
	}
	
	public Bubble(double x, double y ,double size,String tooltip) {
		this(x,y,size);
		this.tooltip = tooltip;
	}

	public JavaScriptObject toJSObject() {
		return getJSBubble(x, y, size,getTooltip());
	}
	
	private native JavaScriptObject getJSBubble(double x, double y, double size, String tooltip)/*-{
		return {x: x,y:y,size:size,tooltip:tooltip};
	}-*/;
	
	public String getTooltip() {
		return tooltip != null ? tooltip : y +"";
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}
	
}
