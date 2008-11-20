package com.objetdirect.tatami.client.charting;

import com.google.gwt.core.client.JavaScriptObject;
import com.objetdirect.tatami.client.ConvertibleToJSObject;

public class AxisLabel implements ConvertibleToJSObject{
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
