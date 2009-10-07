package com.objetdirect.tatami.test.widgets;


import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.objetdirect.tatami.testpages.client.widgets.TestChangeDataStorePage;
import com.objetdirect.tatami.unit.wrappers.GridWrapper;

public class TestChangeDataStore extends AbstractTestWidgets{

	GridWrapper grid;
	
	HtmlElement changeDataStoreButton;

	protected String getTestPageId() {
		return TestChangeDataStorePage.class.getName();
	}
	
	protected void setUp() throws Exception {
		super.setUp();
	//	testGwt.waitForBackgroundTasksToComplete(5000);
		grid =  new GridWrapper(page , "Grid");
		changeDataStoreButton = (HtmlElement) page.getElementById("ChangeDataStoreButton");
	}
	
	
	public void testChangeDatastore(){
		assertEquals(grid.getCell(0,0).asText(),"Item1 From Store1");
		testGwt.mouseClick(changeDataStoreButton);
		assertEquals(grid.getCell(0,0).asText(),"Item1 From Store2");
	}
}