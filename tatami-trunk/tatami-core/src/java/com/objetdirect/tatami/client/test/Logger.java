package com.objetdirect.tatami.client.test;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class Logger {
	
	private static FlowPanel loggerWindow;
	
	public static FlowPanel getLoggerWindow(){
		if(loggerWindow == null){
			loggerWindow = new FlowPanel();
			loggerWindow.setSize("100%","20em");
			loggerWindow.addStyleName("__tatamiDebugLogger");
		}
		return loggerWindow;
	}
	
	public static void log(String message){
		getLoggerWindow().add(new HTML("<p>"+message+"</p>"));
	}
	
}
