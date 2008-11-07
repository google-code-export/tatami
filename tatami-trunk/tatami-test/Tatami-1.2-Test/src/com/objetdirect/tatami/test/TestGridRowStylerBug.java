package com.objetdirect.tatami.test;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.objetdirect.tatami.unit.TatamiTestCase;
import com.objetdirect.tatami.unit.wrappers.GridWrapper;

public class TestGridRowStylerBug extends TatamiTestCase{

	GridWrapper grid;
	
	protected String getPageName() {
		return "http://localhost:8888/com.objetdirect.tatami.testpages.TestMainPage/TestMainPage.html";
	}
	
	protected String getTestPageId(){
		return "com.objetdirect.tatami.testpages.client.TestGridRowStylerBugPage";
	}
	
	public void setUp() throws Exception{
		super.setUp();
		testGwt.waitForBackgroundTasksToComplete(5000);
		grid =  new GridWrapper(page , "GridContainer");
	}
	
	public void testRowStyler() throws Exception{
		HtmlElement elem = grid.getView(0);
		assertEquals("dojoxGridRow myRow", elem.getHtmlElementsByTagName("div").get(0).getHtmlElementsByTagName("div").get(0).getHtmlElementsByTagName("div").get(0).getHtmlElementsByTagName("div").get(0).getAttribute("class"));
	}


}
