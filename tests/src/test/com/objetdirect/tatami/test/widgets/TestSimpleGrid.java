package com.objetdirect.tatami.test.widgets;


import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.objetdirect.tatami.testpages.client.widgets.TestSimpleGridPage;
import com.objetdirect.tatami.unit.TestGWT;
import com.objetdirect.tatami.unit.wrappers.GridWrapper;

public class TestSimpleGrid extends AbstractTestWidgets{

	GridWrapper grid;
	
	HtmlElement lastClickedCell;
	HtmlElement lastClickedColumnField;
	HtmlElement lastChangedValue;
	HtmlElement lastRowSelection;
	
	
	HtmlElement sortBySecondColumnButton;
	HtmlElement addRowButton;
	HtmlElement removeRow1Button;
	HtmlElement removeSelectedRows;
	HtmlElement addColumnButton;
	HtmlElement removeColumnButton;
	HtmlElement clearGridButton;
	

	protected String getTestPageId() {
		return TestSimpleGridPage.class.getName();
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		testGwt.waitForBackgroundTasksToComplete(5000);
		grid =  new GridWrapper(page , "GridContainer");
		
		lastClickedCell = (HtmlElement) page.getElementById("LastClickedCell");
		lastClickedColumnField = (HtmlElement) page.getElementById("LastClickedColumnField");
		lastChangedValue = (HtmlElement) page.getElementById("LastDataChange");
		lastRowSelection = (HtmlElement) page.getElementById("SelectedRows");
		
		sortBySecondColumnButton = (HtmlElement) page.getElementById("SetSecondColumnOrderButton");
		addRowButton = (HtmlElement) page.getElementById("AddRowButton");
		removeRow1Button = (HtmlElement) page.getElementById("RemoveRowNumber1Button");
		removeSelectedRows = (HtmlElement) page.getElementById("RemoveSelectedRowsButton");
		addColumnButton = (HtmlElement) page.getElementById("AddColumnButton");
		removeColumnButton = (HtmlElement) page.getElementById("RemoveColumnButton");
		clearGridButton = (HtmlElement) page.getElementById("ClearGridButton");
	}
	
	
	public void testGridInit(){
		String firstCellContent = grid.getCell(0,0).getTextContent();
		String firstHeaderCellContent = grid.getCellHeader(0).getTextContent();
		assertEquals("B", firstCellContent);
		assertEquals("First Column", firstHeaderCellContent);
		grid.clickOnCell(1,1);
		testGwt.waitForBackgroundTasksToComplete(400);
		assertEquals("1:1",lastClickedCell.getTextContent());
		grid.clickOnCellHeader(0);
		testGwt.waitForBackgroundTasksToComplete(100);
		firstCellContent = grid.getCell(0,0).getTextContent();
		assertEquals("A", firstCellContent);
		grid.clickOnCellHeader(0);
		firstCellContent = grid.getCell(0,0).getTextContent();
		assertEquals("C", firstCellContent);
	}
	
	public void testSort(){
		grid.clickOnCellHeader(0);
		String secondCellContent = grid.getCell(1,0).getTextContent();
		String firstCellContent = grid.getCell(0,0).getTextContent();
		assertEquals("A", firstCellContent);
		assertEquals("B", secondCellContent);
		grid.clickOnCellHeader(0);
		firstCellContent = grid.getCell(0,0).getTextContent();
		assertEquals("C", firstCellContent);
		testGwt.mouseClick(sortBySecondColumnButton, TestGWT.BUTTON_LEFT, false, false, false);
		firstCellContent = grid.getCell(0,1).getTextContent();
		assertEquals("a", firstCellContent);
		
	}
	
	public void testEditCell(){
		grid.dblClickOnCell(1, 0);
		testGwt.typeOnFocusedElement("I was A\n" , true);
		grid.clickOnCell(1,1);
		testGwt.waitForBackgroundTasksToComplete(1000);
		assertEquals("I was A" , grid.getCell(1,0).getTextContent());
		assertEquals("I was A" , lastChangedValue.getTextContent());
	}
	
	public void testAddAndRemoveRow(){
		testGwt.mouseClick(addRowButton, TestGWT.BUTTON_LEFT, false, false, false);
		assertEquals(4, grid.getRowCount());
		assertEquals("D", grid.getCell(3, 0).getTextContent());
		testGwt.mouseClick(removeRow1Button, TestGWT.BUTTON_LEFT, false, false, false);
		assertEquals(3, grid.getRowCount());
		HtmlElement cell1 = grid.getCell(2, 0);
		assertEquals("D", cell1.getTextContent());
		assertEquals("B", grid.getCell(0, 0).getTextContent());
	}
	
	
	public void testAddRemoveColumns(){
		testGwt.mouseClick(addColumnButton, TestGWT.BUTTON_LEFT, false, false, false);
		assertEquals("c" , grid.getCell(0,2).getTextContent());
		testGwt.mouseClick(removeColumnButton, TestGWT.BUTTON_LEFT, false, false, false);
		assertEquals("1" , grid.getCell(0,2).getTextContent());
	}
	
	public void testClearGrid(){
		assertEquals(3,grid.getRowCount());
		testGwt.mouseClick(clearGridButton, TestGWT.BUTTON_LEFT, false, false, false);
		assertEquals(0,grid.getRowCount());
	}
	
	
}
