package com.objetdirect.tatami.test.widgets;

import com.objetdirect.tatami.testpages.client.widgets.TestDNDEventsPage;

public class TestDNDEvents extends AbstractTestWidgets {

	
	

	@Override
	protected String getTestPageId() {
		return TestDNDEventsPage.class.getName();
	}

	public void setUp() throws Exception{
		super.setUp();
		testGwt.waitForBackgroundTasksToComplete(20000);
	}
	
	public void testDragAndDrop(){
		testGwt.waitForBackgroundTasksToComplete(1000);
		testGwt.toggleJSAlertLogging(true);
		testGwt.dragElementTo(page.getHtmlElementById("widget4"), page.getHtmlElementById("TargetOnlyPanel"), false);
		testGwt.waitForBackgroundTasksToComplete(1000);
		assertEquals("TRUE",page.getHtmlElementById("onDnDStartHasBeenCalled").getTextContent());
		assertEquals("TRUE",page.getHtmlElementById("onDnDCancelHasBeenCalled").getTextContent());
		assertEquals("TRUE",page.getHtmlElementById("onDnDCheckSourceHasBeenCalled").getTextContent());
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
		
		System.out.println(page.getHtmlElementById("onDnDStartHasBeenCalled").getTextContent());
		System.out.println(page.getHtmlElementById("onDnDCancelHasBeenCalled").getTextContent());
		System.out.println(page.getHtmlElementById("onDnDCheckSourceHasBeenCalled").getTextContent());
		System.out.println(page.getHtmlElementById("onDnDCheckTargetHasBeenCalled").getTextContent());
		System.out.println(page.getHtmlElementById("onDnDDragOverHasBeenCalled").getTextContent());
		System.out.println(page.getHtmlElementById("onDnDElementsAcceptedHasBeenCalled").getTextContent());
		System.out.println(page.getHtmlElementById("onDnDDropHtml").getTextContent());
		System.out.println(page.getHtmlElementById("onDnDStartHasBeenCalled2").getTextContent());
		System.out.println(page.getHtmlElementById("onDnDCancelHasBeenCalled2").getTextContent());
		System.out.println(page.getHtmlElementById("onDnDCheckSourceHasBeenCalled2").getTextContent());
		System.out.println(page.getHtmlElementById("onDnDCheckTargetHasBeenCalled2").getTextContent());
		System.out.println(page.getHtmlElementById("onDnDDragOverHasBeenCalled2").getTextContent());
		System.out.println(page.getHtmlElementById("onDnDElementsAcceptedHasBeenCalled2").getTextContent());
		System.out.println(page.getHtmlElementById("onDnDDropHtml2").getTextContent());
		
	}
	
}
