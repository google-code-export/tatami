package com.objetdirect.tatami.client.grid.formatters;

import com.google.gwt.core.client.JavaScriptObject;

public class BooleanFormatter extends  BaseFormatter{

	public String format(String toFormat) {
		if(toFormat.compareTo("true") == 0){
			return "<input type='checkbox' checked='true' disabled='true'></input>";
		}else{
			return "<input type='checkbox' disabled='true'></input>";
		}
	}


}