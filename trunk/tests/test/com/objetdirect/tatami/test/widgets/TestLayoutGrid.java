package com.objetdirect.tatami.test.widgets;

import com.gargoylesoftware.htmlunit.html.HtmlCheckBoxInput;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.objetdirect.tatami.testpages.client.widgets.TestLayoutGridPage;
import com.objetdirect.tatami.unit.TatamiTestCase;
import com.objetdirect.tatami.unit.wrappers.GridWrapper;
import com.objetdirect.tatami.unit.wrappers.NumberSpinnerWrapper;

public class TestLayoutGrid extends AbstractTestWidgets{
	
	private GridWrapper grid;
	

	protected String getTestPageId() {
		return TestLayoutGridPage.class.getName();
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
		assertEquals("Is Married ?",grid.getCellHeader(4).getTextContent());
		assertEquals("Appreciation",grid.getCellHeader(5).getTextContent());
		assertEquals("Send e-mail",grid.getCellHeader(6).getTextContent());
		assertEquals("Description",grid.getCellHeader(7).getTextContent());
		assertEquals("Jane Doe",grid.getCell(0,0).getTextContent());
		assertEquals("9876543210",grid.getCell(0,1).getTextContent());
		assertEquals("€1,200.00",grid.getCell(0,2).getTextContent());
		assertEquals("11/12/78",grid.getCell(0,3).getTextContent());
		assertEquals("false",grid.getCell(0,4).getTextContent());
		assertEquals("Awesome",grid.getCell(0,5).getTextContent());
		assertEquals("send mail",((HtmlImage)grid.getCell(0,6).getFirstChild().getFirstChild()).getAltAttribute());
		assertEquals("I m Jane Doe and this is my description",grid.getCell(0,7).getTextContent());
	}
	
	
	public void testTextEditor() throws Exception{
		grid.dblClickOnCell(0, 7);
		testGwt.typeOnFocusedElement("0000", true);
		grid.clickOnCell(0, 1);
		assertEquals("0000",grid.getCell(0,7).getTextContent());
	}
	
	public void testSpinnerEditor() throws Exception{
		grid.dblClickOnCell(0,2);
		testGwt.waitForBackgroundTasksToComplete(1000);
		NumberSpinnerWrapper wrapper = new NumberSpinnerWrapper((HtmlElement) grid.getCell(0,2).getFirstChild());
		for(int i=0 ; i < 5 ; i++){
			testGwt.mouseDown(wrapper.getUpArrow());
			testGwt.mouseUp(wrapper.getUpArrow());
		}
		testGwt.waitForBackgroundTasksToComplete(200);
		grid.clickOnCell(0, 1);
		assertEquals("€1,205.00",grid.getCell(0,2).getTextContent());
	}
	
	public void testDateEditor() throws Exception{
		grid.dblClickOnCell(0,3);
		testGwt.waitForBackgroundTasksToComplete(1000);
		testGwt.typeOnFocusedElement("11/12/1996",true);
		testGwt.waitForBackgroundTasksToComplete(1000);
		grid.clickOnCell(0, 1);
		testGwt.waitForBackgroundTasksToComplete(1000);
		assertEquals("11/12/96",grid.getCell(0,3).getTextContent());
	}
	
	public void testCheckBoxEditor() throws Exception{
		assertEquals("false",grid.getCell(0,4).getTextContent());
		grid.dblClickOnCell(0,4);
		testGwt.waitForBackgroundTasksToComplete(1000);
		HtmlCheckBoxInput input = (HtmlCheckBoxInput) grid.getCell(0,4).getOneHtmlElementByAttribute("input", "type", "checkbox");
		assertEquals(false,input.isDefaultChecked());
		input.click();
		assertEquals(true,input.isChecked());
		grid.clickOnCell(0, 1);
		assertEquals("true",grid.getCell(0,4).getTextContent());
		assertEquals("true",grid.getCell(1,4).getTextContent());
		grid.dblClickOnCell(1,4);
		testGwt.waitForBackgroundTasksToComplete(1000);
		HtmlCheckBoxInput input2 = (HtmlCheckBoxInput) grid.getCell(1,4).getOneHtmlElementByAttribute("input", "type", "checkbox");
		input2.click();
		assertEquals(false,input2.isChecked());
		grid.clickOnCell(0, 1);
		assertEquals("false",grid.getCell(1,4).getTextContent());
	}
	
	public void testComboBoxEditor() throws Exception{
		grid.dblClickOnCell(0,5);
		testGwt.waitForBackgroundTasksToComplete(1000);
		HtmlSelect input = (HtmlSelect) grid.getCell(0, 5).getFirstChild();
		input.setSelectedAttribute(input.getOption(1),true);
		grid.clickOnCell(0, 1);
		assertEquals("Very Good",grid.getCell(0,5).getTextContent());
	}
	
}
