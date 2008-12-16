package com.objetdirect.tatami.test.widgets;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.objetdirect.tatami.testpages.client.widgets.TestSpinnerPage;
import com.objetdirect.tatami.unit.TestGWT;
import com.objetdirect.tatami.unit.wrappers.NumberSpinnerWrapper;


public class TestSpinner extends AbstractTestWidgets{

	NumberSpinnerWrapper spinner ;
	HtmlElement value ;
	HtmlElement toggleChangesButton;
	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		spinner =  new NumberSpinnerWrapper(page , "TheSpinner");
		value = (HtmlElement) page.getElementById("SpinnerValueBOX");
		toggleChangesButton = (HtmlElement) page.getElementById("toggleIntermediateChangeButton");
	}

	public void testTypeMatic() throws Exception{
		testGwt.mouseDown(spinner.getUpArrow(), TestGWT.BUTTON_LEFT, false, false, false);
		Thread.sleep(1567);
		testGwt.mouseUp(spinner.getUpArrow(), TestGWT.BUTTON_LEFT, false, false, false);
		page.setFocusedElement(value);
		assertEquals("7", value.getTextContent());
		testGwt.mouseDown(spinner.getDownArrow(), TestGWT.BUTTON_LEFT, false, false, false);
		Thread.sleep(1567);
		testGwt.mouseUp(spinner.getDownArrow(), TestGWT.BUTTON_LEFT, false, false, false);
		page.setFocusedElement(value);
		assertEquals("0", value.getTextContent());
	}
	
	public void testKeyboardInput() throws Exception{
		page.setFocusedElement(spinner.getInput());
		testGwt.typeOnFocusedElement("10" , true);
		page.setFocusedElement(value);
		assertEquals("10", value.getTextContent());
	}
	
	public void testIntermediateChanges() throws Exception{
		testGwt.mouseClick(toggleChangesButton.getHtmlElementsByTagName("button").get(0), TestGWT.BUTTON_LEFT, false, false, false);
		testGwt.waitForBackgroundTasksToComplete(1000);
		testGwt.mouseDown(spinner.getUpArrow(), TestGWT.BUTTON_LEFT, false, false, false);
		Thread.sleep(813);
		testGwt.mouseUp(spinner.getUpArrow(), TestGWT.BUTTON_LEFT, false, false, false);
		testGwt.waitForBackgroundTasksToComplete(1000);
		assertEquals("3", value.getTextContent());
		testGwt.mouseClick(toggleChangesButton.getHtmlElementsByTagName("button").get(0), TestGWT.BUTTON_LEFT, false, false, false);
		testGwt.mouseDown(spinner.getUpArrow(), TestGWT.BUTTON_LEFT, false, false, false);
		Thread.sleep(813);
		testGwt.mouseUp(spinner.getUpArrow(), TestGWT.BUTTON_LEFT, false, false, false);
		assertEquals("3", value.getTextContent());
		page.setFocusedElement(value);
		assertEquals("6", value.getTextContent());
	}
	
	public void testMinAndMax() throws Exception{
		for(int i = 0 ; i < 10 ; i++){
			testGwt.mouseDown(spinner.getUpArrow(), TestGWT.BUTTON_LEFT, false, false, false);
			testGwt.mouseUp(spinner.getUpArrow(), TestGWT.BUTTON_LEFT, false, false, false);
		}
		page.setFocusedElement(value);
		for(int i = 0 ; i < 25 ; i++){
			testGwt.mouseDown(spinner.getUpArrow(), TestGWT.BUTTON_LEFT, false, false, false);
			testGwt.mouseUp(spinner.getUpArrow(), TestGWT.BUTTON_LEFT, false, false, false);
		}
		page.setFocusedElement(value);
		testGwt.waitForBackgroundTasksToComplete(100);
		assertEquals("10", value.getTextContent());
		assertEquals("10", spinner.getValue());
		for(int i = 0 ; i < 45 ; i++){
			testGwt.mouseDown(spinner.getDownArrow(), TestGWT.BUTTON_LEFT, false, false, false);
			testGwt.mouseUp(spinner.getDownArrow(), TestGWT.BUTTON_LEFT, false, false, false);
		}
		page.setFocusedElement(value);
		for(int i = 0 ; i < 200 ; i++){
			testGwt.mouseDown(spinner.getDownArrow(), TestGWT.BUTTON_LEFT, false, false, false);
			testGwt.mouseUp(spinner.getDownArrow(), TestGWT.BUTTON_LEFT, false, false, false);
		}
		page.setFocusedElement(value);
		testGwt.waitForBackgroundTasksToComplete(100);
		assertEquals("-10", value.getTextContent());
		assertEquals("-10", spinner.getValue());
	}

	
	protected String getTestPageId(){
		return TestSpinnerPage.class.getName();
	}
}
