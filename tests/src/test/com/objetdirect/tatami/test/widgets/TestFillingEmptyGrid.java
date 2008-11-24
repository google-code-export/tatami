package com.objetdirect.tatami.test.widgets;


import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.objetdirect.tatami.testpages.client.widgets.TestFillingEmptyGridPage;
import com.objetdirect.tatami.unit.wrappers.GridWrapper;

public class TestFillingEmptyGrid extends AbstractTestWidgets{

	GridWrapper grid;
	HtmlElement addRowButton;

	protected String getTestPageId() {
		return TestFillingEmptyGridPage.class.getName();
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		testGwt.waitForBackgroundTasksToComplete(5000);
		grid =  new GridWrapper(page , "GridContainer");
		addRowButton = (HtmlElement) page.getElementById("AddRowButton");
	}
	
	public void testFillingGrid() throws Exception {
		testGwt.mouseClick(addRowButton);
		testGwt.waitForBackgroundTasksToComplete(300);
		assertEquals("1",grid.getCell(0,0).getTextContent());
		testGwt.mouseClick(addRowButton);
		testGwt.waitForBackgroundTasksToComplete(300);
		assertEquals("4",grid.getCell(1,0).getTextContent());
	}
	
}
