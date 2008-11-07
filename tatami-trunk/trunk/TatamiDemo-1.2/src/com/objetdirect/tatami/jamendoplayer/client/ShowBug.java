package com.objetdirect.tatami.jamendoplayer.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;

public class ShowBug implements EntryPoint{

	public void onModuleLoad() {
		SimplePanel test = new SimplePanel();
		test.getElement().setAttribute("id", "DIVTEST");
		RootPanel.get().add(test);
	}
	
	
}
