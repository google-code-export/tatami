package com.objetdirect.tatami.test;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.objetdirect.tatami.unit.TatamiTestCase;
import com.objetdirect.tatami.unit.wrappers.TreeWrapper;

public class TestDNDEvents extends TatamiTestCase {

	private TreeWrapper tree;
	private HtmlElement leftpanel;
	private HtmlElement rightpanel;
	
	
	@Override
	protected String getPageName() {
		return "http://localhost:8888/com.objetdirect.tatami.testpages.TestMainPage/TestMainPage.html";
	}

	@Override
	protected String getTestPageId() {
		return "com.objetdirect.tatami.testpages.client.TestDnDEventsPage";
	}

	public void setUp() throws Exception{
		super.setUp();
		testGwt.waitForBackgroundTasksToComplete(5000);
	}
	
	public void testDragAndDrop(){
		testGwt.dragElementTo(page.getHtmlElementById("widget3"), page.getHtmlElementById("widget2"), false);
		assertEquals("TRUE",page.getHtmlElementById("onDnDStartHasBeenCalled").getTextContent());
		assertEquals("TRUE",page.getHtmlElementById("onDnDCancelHasBeenCalled").getTextContent());
		assertEquals("TRUE",page.getHtmlElementById("onDnDCheckSourceHasBeenCalled").getTextContent());
		assertEquals("TRUE",page.getHtmlElementById("onDnDCheckTargetHasBeenCalled").getTextContent());
		assertEquals("TRUE",page.getHtmlElementById("onDnDDragOverHasBeenCalled").getTextContent());
		assertEquals("TRUE",page.getHtmlElementById("onDnDElementsAcceptedHasBeenCalled").getTextContent());
		assertEquals("TRUE",page.getHtmlElementById("onDnDDropHtml").getTextContent());
		assertEquals("TRUE",page.getHtmlElementById("onDnDStartHasBeenCalled2").getTextContent());
		assertEquals("TRUE",page.getHtmlElementById("onDnDCancelHasBeenCalled2").getTextContent());
		assertEquals("false",page.getHtmlElementById("onDnDCheckSourceHasBeenCalled2").getTextContent());
		assertEquals("false",page.getHtmlElementById("onDnDCheckTargetHasBeenCalled2").getTextContent());
		assertEquals("false",page.getHtmlElementById("onDnDDragOverHasBeenCalled2").getTextContent());
		assertEquals("false",page.getHtmlElementById("onDnDElementsAcceptedHasBeenCalled2").getTextContent());
		assertEquals("false",page.getHtmlElementById("onDnDDropHtml2").getTextContent());
	}
	
}
