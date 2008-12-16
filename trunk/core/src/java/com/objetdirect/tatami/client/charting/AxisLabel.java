package com.objetdirect.tatami.client.charting;

import com.google.gwt.core.client.JavaScriptObject;
import com.objetdirect.tatami.client.ConvertibleToJSObject;

/**
 * Represents an Axis label
 * A label has a text, and the value to which it is attached
 * 
 * @author rdunklau
 *
 */
public class AxisLabel implements ConvertibleToJSObject{
	String text;
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

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
