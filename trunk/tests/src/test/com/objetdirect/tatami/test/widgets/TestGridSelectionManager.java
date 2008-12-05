package com.objetdirect.tatami.test.widgets;


import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.objetdirect.tatami.client.grid.GridSelectionManager;
import com.objetdirect.tatami.testpages.client.widgets.TestGridSelectionManagerPage;
import com.objetdirect.tatami.testpages.client.widgets.TestSimpleGridPage;
import com.objetdirect.tatami.unit.TatamiTestCase;
import com.objetdirect.tatami.unit.TestGWT;
import com.objetdirect.tatami.unit.wrappers.GridWrapper;

public class TestGridSelectionManager extends AbstractTestWidgets{

	GridWrapper grid;
	
	HtmlElement lastClickedCell;
	HtmlElement lastClickedColumnField;
	HtmlElement lastChangedValue;
	HtmlElement lastRowSelection;
	
	
	HtmlElement selectFirstAndLastRows;
	HtmlElement deselectAllRows;
	HtmlElement deselectFirstRow;
	HtmlElement removeSelectedRows;
	HtmlSelect selectMode;

	protected String getTestPageId() {
		return TestGridSelectionManagerPage.class.getName();
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		testGwt.waitForBackgroundTasksToComplete(5000);
		grid =  new GridWrapper(page , "GridContainer");
		
		lastClickedCell = (HtmlElement) page.getElementById("LastClickedCell");
		lastClickedColumnField = (HtmlElement) page.getElementById("LastClickedColumnField");
		lastChangedValue = (HtmlElement) page.getElementById("LastDataChange");
		lastRowSelection = (HtmlElement) page.getElementById("SelectedRows");
		selectFirstAndLastRows = (HtmlElement) page.getElementById("selectFirstAndLastRows");
		deselectAllRows = (HtmlElement) page.getElementById("deselectAllRows");
		deselectFirstRow = (HtmlElement) page.getElementById("deselectFirstRow");
		removeSelectedRows = (HtmlElement) page.getElementById("RemoveSelectedRowsButton");
		selectMode = (HtmlSelect) page.getElementById("ListSelectionModes");
	}
	
	public void testSelectionProgrammatic() throws Exception{
		testGwt.mouseClick(selectFirstAndLastRows);
		assertEquals("0-2",lastRowSelection.asText());
		testGwt.mouseClick(deselectAllRows);
		assertEquals("",lastRowSelection.asText());
		testGwt.mouseClick(selectFirstAndLastRows);
		assertEquals("0-2",lastRowSelection.asText());
		testGwt.mouseClick(deselectFirstRow);
		assertEquals("2",lastRowSelection.asText());
	}
	
	public void testSelectionInMultipleMode() throws Exception{
		selectMode.setSelectedAttribute(GridSelectionManager.SELECTION_MODE_MULTIPLE, true);
		grid.clickOnCell(0,0);
		assertEquals("0", lastRowSelection.getTextContent());
		grid.clickOnCell(1,0);
		assertEquals("0-1", lastRowSelection.getTextContent());
		grid.clickOnCell(0,0);
		assertEquals("1", lastRowSelection.getTextContent());
	}
	
	public void testSelectionInSingleMode() throws Exception{
		selectMode.setSelectedAttribute(GridSelectionManager.SELECTION_MODE_SINGLE, true);
		grid.clickOnCell(0,0);
		assertEquals("0", lastRowSelection.getTextContent());
		grid.clickOnCell(1,0);
		assertEquals("1", lastRowSelection.getTextContent());
		grid.clickOnCell(0,0);
		assertEquals("0", lastRowSelection.getTextContent());
	}
	
	public void testSelectionInNoneMode() throws Exception{
		selectMode.setSelectedAttribute(GridSelectionManager.SELECTION_MODE_NONE, true);
		grid.clickOnCell(0,0);
		assertEquals("", lastRowSelection.getTextContent());
		grid.clickOnCell(1,0);
		assertEquals("", lastRowSelection.getTextContent());
		grid.clickOnCell(0,0);
		assertEquals("", lastRowSelection.getTextContent());
	}
	
	public void testSelectionInExtendedMode(){
		selectMode.setSelectedAttribute(GridSelectionManager.SELECTION_MODE_EXTENDED, true);
		grid.clickOnCell(0,0);
		assertEquals("0", lastRowSelection.getTextContent());
		grid.clickOnCell(0,0);
		testGwt.mouseClick(grid.getCell(2,0), TestGWT.BUTTON_LEFT, false, true, false);
		assertEquals("0-1-2", lastRowSelection.getTextContent());
		grid.clickOnCell(1,0);
		assertEquals("1", lastRowSelection.getTextContent());
		testGwt.mouseClick(grid.getCell(0,0), TestGWT.BUTTON_LEFT, false, false, true);
		assertEquals("0-1", lastRowSelection.getTextContent());
		testGwt.mouseClick(removeSelectedRows, TestGWT.BUTTON_LEFT, false, false, false);
		assertEquals(1 , grid.getRowCount());
		assertEquals("C", grid.getCell(0,0).getTextContent());
	}
	
	
}
