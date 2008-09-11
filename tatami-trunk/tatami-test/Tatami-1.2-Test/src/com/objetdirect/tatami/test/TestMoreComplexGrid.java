package com.objetdirect.tatami.test;

import junit.framework.Test;

import com.gargoylesoftware.htmlunit.AlertHandler;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.html.HtmlCheckBoxInput;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.objetdirect.tatami.unit.TatamiTestCase;
import com.objetdirect.tatami.unit.TestGWT;
import com.objetdirect.tatami.unit.wrappers.GridWrapper;
import com.objetdirect.tatami.unit.wrappers.NumberSpinnerWrapper;
import com.sun.org.apache.bcel.internal.generic.SALOAD;

public class TestMoreComplexGrid extends TatamiTestCase {

	private GridWrapper grid;
	private HtmlSelect filterListBox;
	private HtmlElement	filterTextBox;
	private HtmlElement filterButton;
	private GridWrapper filterGrid;
	private HtmlElement nextPageButton;
	private HtmlElement previousPageButton;
	
	private HtmlElement addEmployeeButton;
	private HtmlElement removeSelectedEmployessButton;
	private HtmlInput lastNameTextBox;
	private HtmlElement firstNameTextBox;
	private HtmlElement dateTextBox;
	private NumberSpinnerWrapper salarySpinner;
	private HtmlSelect positionElement;
	private HtmlElement subscriberElement;
	
	protected String getPageName() {
		return "http://localhost:8888/com.objetdirect.tatami.testpages.TestMainPage/TestMainPage.html";
	}

	protected String getTestPageId() {
		return "com.objetdirect.tatami.testpages.client.TestMoreComplexGridPage";
	}

	protected void setUp() throws Exception {
		super.setUp();
		testGwt.waitForBackgroundTasksToComplete(3000);
		grid =  new GridWrapper(page , "GridContainer");
		filterGrid =  new GridWrapper(page , "FilterGridContainer");
		filterListBox = (HtmlSelect) page.getHtmlElementById("FilterListBox");
		filterTextBox =  page.getHtmlElementById("FilterTextBox");
		nextPageButton = page.getHtmlElementById("dojox_form_TatamiButton_1");
		previousPageButton = page.getHtmlElementById("dojox_form_TatamiButton_0");
		addEmployeeButton = page.getHtmlElementById("dojox_form_TatamiButton_2");
		removeSelectedEmployessButton = page.getHtmlElementById("dojox_form_TatamiButton_3");
		filterButton = page.getHtmlElementById("dojox_form_TatamiButton_4");
		lastNameTextBox = (HtmlInput) page.getHtmlElementById("LastNameTextBox");
		firstNameTextBox = (HtmlInput) page.getHtmlElementById("FirstNameTextBox");
		dateTextBox = page.getHtmlElementById("dijit_form_DateTextBox_0");
		salarySpinner = new NumberSpinnerWrapper(page.getHtmlElementById("SalarySpinner"));
		positionElement = (HtmlSelect) page.getHtmlElementById("PositionComboBox");
		subscriberElement = (HtmlElement) page.getHtmlElementById("SubscriberCheckBox");
	}
	
	public void testNextPagePreviousPage(){	
		grid.clickOnCellHeader(0);
		assertEquals("1", grid.getCell(0, 0).getTextContent());
		testGwt.mouseClick(nextPageButton, TestGWT.BUTTON_LEFT, false, false, false);
		assertEquals("51", grid.getCell(0, 0).getTextContent());
		testGwt.mouseClick(nextPageButton, TestGWT.BUTTON_LEFT, false, false, false);
		assertEquals("101", grid.getCell(0, 0).getTextContent());
		testGwt.mouseClick(nextPageButton, TestGWT.BUTTON_LEFT, false, false, false);
		assertEquals("151", grid.getCell(0, 0).getTextContent());
		testGwt.mouseClick(previousPageButton, TestGWT.BUTTON_LEFT, false, false, false);
		assertEquals("101", grid.getCell(0, 0).getTextContent());
		testGwt.mouseClick(previousPageButton, TestGWT.BUTTON_LEFT, false, false, false);
		assertEquals("51", grid.getCell(0, 0).getTextContent());
		testGwt.mouseClick(previousPageButton, TestGWT.BUTTON_LEFT, false, false, false);
		assertEquals("1", grid.getCell(0, 0).getTextContent());
		testGwt.mouseClick(previousPageButton, TestGWT.BUTTON_LEFT, false, false, false);
		assertEquals("1", grid.getCell(0, 0).getTextContent());
	}

	public void testGridEditViaForm(){
		grid.clickOnCell(2, 0);
		page.setFocusedElement(lastNameTextBox);
		testGwt.typeOnFocusedElement("Mouse" , true);
		page.tabToNextElement();
		testGwt.typeOnFocusedElement("Mickey" , true);
		page.tabToNextElement();
		testGwt.typeOnElement(dateTextBox,  "04/09/1982" , true);
		page.setFocusedElement(salarySpinner.getInput());
		testGwt.typeOnFocusedElement("100000000" , true);
		page.setFocusedElement(positionElement);
		positionElement.setSelectedAttribute("DHR", true);
		testGwt.waitForBackgroundTasksToComplete(1000);
		assertEquals("3" , grid.getCell(2, 0).getTextContent());
		assertEquals("Mickey" , grid.getCell(2, 1).getTextContent());
		assertEquals("Mouse" , grid.getCell(2, 2).getTextContent());
		assertEquals("4/9/82" , grid.getCell(2, 3).getTextContent());
		assertEquals("100000000" , grid.getCell(2, 4).getTextContent());
		assertEquals("DHR" , grid.getCell(2, 5).getTextContent());
	}
	
	public void testAddRemoveEmployeeBehavior(){
		grid.clickOnCell(3, 4);
		testGwt.mouseClick(addEmployeeButton , TestGWT.BUTTON_LEFT, false, false, false);
		grid.clickOnCell(3, 4);
		testGwt.waitForBackgroundTasksToComplete(1500);
		assertEquals("172", grid.getCell(3,0).getTextContent());
		assertEquals("(Insert First Name)", grid.getCell(3,1).getTextContent());
		assertEquals("(Insert Last Name)", grid.getCell(3,2).getTextContent());
		assertEquals("1/1/01", grid.getCell(3,3).getTextContent());
		assertEquals("0", grid.getCell(3,4).getTextContent());
		assertEquals("(Insert Function)", grid.getCell(3,5).getTextContent());
		assertEquals("", grid.getCell(3,6).getHtmlElementsByTagName("input").get(0).getAttribute("checked"));
		assertEquals("(Insert Last Name)", lastNameTextBox.getAttribute("value"));
		assertEquals("(Insert First Name)", firstNameTextBox.getAttribute("value"));
		assertEquals("1/1/1901", dateTextBox.getAttribute("value"));
		assertEquals("0", salarySpinner.getInput().getAttribute("value"));
		
		grid.clickOnCell(3, 1);
		testGwt.mouseClick(removeSelectedEmployessButton , TestGWT.BUTTON_LEFT, false, false, false);
		testGwt.waitForBackgroundTasksToComplete(1500);
		assertEquals("4", grid.getCell(3,0).getTextContent());
		assertEquals("Jean", grid.getCell(3,1).getTextContent());
		assertEquals("Poe", grid.getCell(3,2).getTextContent());
		assertEquals("6/22/88", grid.getCell(3,3).getTextContent());
		assertEquals("4000", grid.getCell(3,4).getTextContent());
		assertEquals("Developper", grid.getCell(3,5).getTextContent());
		assertEquals("on", grid.getCell(3,6).getHtmlElementsByTagName("input").get(0).getAttribute("value"));
		
	}
	
	public void testFilter(){
		String[] firstNames = {"Jean" , "John" , "John" };
		String[] lastNames = {"Poe" , "Poe" , "Turing" };
		filterListBox.setSelectedAttribute("lastName", true);
		testGwt.typeOnElement(filterTextBox, "Poe",true);
		testGwt.mouseClick(filterButton , TestGWT.BUTTON_LEFT, false, false, false);
		testGwt.waitForBackgroundTasksToComplete(1500);
		int count = grid.getRowCount();
		for(int i = 0; i < count ; i++){
			assertEquals("Poe", grid.getCell(i, 2).getTextContent());
			assertTrue(grid.getCell(i, 1).getTextContent().equals("Jean") || grid.getCell(i, 1).getTextContent().equals("John"));
		}
		filterListBox.setSelectedAttribute("firstName", true);
		testGwt.typeOnElement(filterTextBox, "John",true);
		testGwt.mouseClick(filterButton , TestGWT.BUTTON_LEFT, false, false, false);
		testGwt.waitForBackgroundTasksToComplete(1000);
		count = grid.getRowCount();
		for(int i = 0; i < count ; i++){
			assertEquals("Poe", grid.getCell(i, 2).getTextContent());
			assertEquals("John", grid.getCell(i, 1).getTextContent());
		}
		filterGrid.clickOnCell(0,2);
		testGwt.waitForBackgroundTasksToComplete(1500);
		count = grid.getRowCount();
		for(int i = 0; i < count ; i++){
			assertEquals("John", grid.getCell(i, 1).getTextContent());
			assertTrue(grid.getCell(i, 2).getTextContent().equals("Poe") || grid.getCell(i,2).getTextContent().equals("Turing"));
		}
		filterGrid.clickOnCell(0,2);
		testGwt.waitForBackgroundTasksToComplete(3000);
		count = grid.getRowCount();
		for(int i = 0; i < count ; i++){
			assertEquals(firstNames[i%3], grid.getCell(i, 1).getTextContent());
			assertEquals(lastNames[i%3], grid.getCell(i, 2).getTextContent());
		}
	}
	
	
	
}
