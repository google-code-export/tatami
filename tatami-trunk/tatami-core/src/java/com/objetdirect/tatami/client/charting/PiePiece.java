package com.objetdirect.tatami.client.charting;

import com.google.gwt.core.client.JavaScriptObject;
import com.objetdirect.tatami.client.ConvertibleToJSObject;

public class PiePiece implements ConvertibleToJSObject{



	private double value;
	

	private String label;

	private String color;
	
	private String fontColor;
	
	private String tooltip;
	
	

	public PiePiece(double value) {
		this.value = value;
	}

	public PiePiece(double value , String label) {
		this.label = label;
		this.value = value;
	}


	public PiePiece( double value , String label,String color) {
		this.color = color;
		this.label = label;
		this.value = value;
	}
	

	public PiePiece(double value , String label,String color, String fontColor) {
		this(value, label, color);
		this.fontColor = fontColor;
	}
	
	public PiePiece(double value , String label,String color, String fontColor, String tooltip) {
		this(value,label,color,fontColor);
		this.tooltip = tooltip;
	}
	
	public String getFontColor() {
		return fontColor;
	}

	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public JavaScriptObject toJSObject() {
		return getJSPiePiece(value, label, color , fontColor,tooltip);
	}
	
	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}
	
	private native JavaScriptObject getJSPiePiece(double value, String label , String color , String fontColor, String tooltip)/*-{
		var pie = {y: value};
		if(label!=null){
			pie.text = label;
		}
		if(color!=null){
			pie.color = color;
		}
		if(fontColor != null){
			pie.fontColor = fontColor;
		}
		if(tooltip != null){
			pie.tooltip = tooltip;
		}
		return pie;
	}-*/;
	
}
