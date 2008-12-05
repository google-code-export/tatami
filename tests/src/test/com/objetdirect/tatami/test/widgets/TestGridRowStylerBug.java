package com.objetdirect.tatami.test.widgets;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.objetdirect.tatami.testpages.client.widgets.TestGridRowStylerBugPage;
import com.objetdirect.tatami.unit.TatamiTestCase;
import com.objetdirect.tatami.unit.wrappers.GridWrapper;

public class TestGridRowStylerBug extends AbstractTestWidgets{

	GridWrapper grid;
	
	
	protected String getTestPageId(){
		return TestGridRowStylerBugPage.class.getName();
	}
	
	public void setUp() throws Exception{
		super.setUp();
		testGwt.waitForBackgroundTasksToComplete(5000);
		grid =  new GridWrapper(page , "GridContainer");
	}
	
	public void testRowStyler() throws Exception{
		HtmlElement elem = grid.getView(1);
		assertEquals("dojoxGridRow myRow", elem.getHtmlElementsByTagName("div").get(0).getHtmlElementsByTagName("div").get(0).getHtmlElementsByTagName("div").get(0).getHtmlElementsByTagName("div").get(0).getAttribute("class"));
	}


}
