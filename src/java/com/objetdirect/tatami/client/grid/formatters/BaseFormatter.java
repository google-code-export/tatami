package com.objetdirect.tatami.client.grid.formatters;

import com.google.gwt.core.client.JavaScriptObject;

public abstract class BaseFormatter implements Formatter{
	
	public JavaScriptObject getFormatter() {
		return getJSFormatter();
	}
	
	protected native JavaScriptObject getJSFormatter()/*-{
		var f = function(toFormat){
			return this.formatter.gwtFormatter.@com.objetdirect.tatami.client.grid.formatters.BaseFormatter::format(Ljava/lang/String;)(toFormat +"");
		};
		f.gwtFormatter = this;
		return f;
	}-*/;
	
	public abstract String format(String toFormat);

}
