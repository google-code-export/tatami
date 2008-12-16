package com.objetdirect.tatami.test.widgets;


import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.objetdirect.tatami.testpages.client.widgets.TestAddingSameItemToGridPage;
import com.objetdirect.tatami.testpages.client.widgets.TestFillingEmptyGridPage;
import com.objetdirect.tatami.unit.wrappers.GridWrapper;

public class TestAddingSameItemToGrid extends AbstractTestWidgets{

	GridWrapper grid;
	HtmlElement addRowButton;

	protected String getTestPageId() {
		return TestAddingSameItemToGridPage.class.getName();
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		testGwt.waitForBackgroundTasksToComplete(5000);
		grid =  new GridWrapper(page , "GridContainer");
		addRowButton = (HtmlElement) page.getElementById("AddRowButton");
	}
	
	public void testAddItemWithSameId() throws Exception {
		assertEquals("OLD Item",grid.getCell(0,1).asText());
		testGwt.mouseClick(addRowButton);
		assertEquals("New Item",grid.getCell(0,1).asText());
	}
	
}
