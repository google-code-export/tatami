package com.objetdirect.tatami.test;

import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.objetdirect.tatami.unit.TatamiTestCase;
import com.objetdirect.tatami.unit.wrappers.GridWrapper;

public class TestLayoutGrid extends TatamiTestCase{
	
	private GridWrapper grid;
	
	protected String getPageName() {
		return "http://localhost:8888/com.objetdirect.tatami.testpages.TestMainPage/TestMainPage.html";
	}

	protected String getTestPageId() {
		return "com.objetdirect.tatami.testpages.client.TestLayoutGridPage";
	}

	protected void setUp() throws Exception {
		super.setUp();
		testGwt.waitForBackgroundTasksToComplete(5000);
		grid =  new GridWrapper(page , "GridContainer");
	}
	
	public void testLayoutIsCorrectlyRendered(){
		assertEquals("▲Name",grid.getCellHeader(0).getTextContent());
		assertEquals("Phone Number",grid.getCellHeader(1).getTextContent());
		assertEquals("Salary",grid.getCellHeader(2).getTextContent());
		assertEquals("Birthdate",grid.getCellHeader(3).getTextContent());
		assertEquals("Mark",grid.getCellHeader(4).getTextContent());
		assertEquals("Send e-mail",grid.getCellHeader(5).getTextContent());
		assertEquals("Description",grid.getCellHeader(6).getTextContent());
		
		assertEquals("Jane Doe",grid.getCell(0,0).getTextContent());
		assertEquals("9876543210",grid.getCell(0,1).getTextContent());
		assertEquals("EUR1,200.00",grid.getCell(0,2).getTextContent());
		assertEquals("11/12/78",grid.getCell(0,3).getTextContent());
		assertEquals("8",grid.getCell(0,4).getTextContent());
		assertEquals("send mail",((HtmlImage)grid.getCell(0,5).getFirstChild()).getAltAttribute());
		assertEquals("I m Jane Doe and this is my description",grid.getCell(0,6).getTextContent());
	}

}
