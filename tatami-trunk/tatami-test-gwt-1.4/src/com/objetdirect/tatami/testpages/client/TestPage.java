package com.objetdirect.tatami.testpages.client;

import com.google.gwt.user.client.ui.Widget;

public abstract class TestPage {

	public final String TESTPAGE_ID;
	
	public final String TESTPAGE_LABEL;
	
	protected TestPage(String testPageId , String testPageLabel){
		this.TESTPAGE_ID = testPageId;
		this.TESTPAGE_LABEL = testPageLabel;
	}
	
	public abstract Widget getTestPage();
	
}
