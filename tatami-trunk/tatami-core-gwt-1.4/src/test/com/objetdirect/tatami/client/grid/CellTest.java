package com.objetdirect.tatami.client.grid;

import com.google.gwt.core.client.JavaScriptObject;
import com.objetdirect.tatami.client.DefaultTatamiTest;

public class CellTest extends DefaultTatamiTest{

	public void testCellCreation(){
		Cell cell = initCell();
		assertEquals("Phone Number", cell.getName());
		assertEquals("phonenumber", cell.getField());
		assertEquals("cssCellClass", cell.getCellClasses());
		assertEquals("cssCellStyle", cell.getCellStyles());
		assertEquals("columnClass", cell.getClasses());
		assertEquals(2, cell.getColSpan().intValue());
		assertEquals(3, cell.getRowSpan().intValue());
		assertEquals("ahahah", cell.getDefaultValue());
		assertEquals(true, cell.getIsNotResizable().booleanValue());
		assertEquals("columnStyle", cell.getStyles());
		assertEquals("200px", cell.getWidth());
	}
	
	public void testCellToJSObject(){
		Cell cell = initCell();
		assertTrue(asserttJSCellIsConform(cell.toJSObject()));
	}
	
	
	private native boolean asserttJSCellIsConform(JavaScriptObject cell)/*-{
		if(cell.name != "Phone Number") return false;
		if(cell.field != "phonenumber") return false;
		if(cell.cellClasses != "cssCellClass") return false;
		if(cell.cellStyles != "cssCellStyle") return false;
		if(cell.classes != "columnClass") return false;
		if(cell.colSpan != 2) return false;
		if(cell.rowSpan != 3) return false;
		if(cell.value != "ahahah") return false;
		if(cell.noresize != true) return false;
		if(cell.styles != "columnStyle") return false;
		if(cell.width != "200px") return false;
		return true
	}-*/;
	
	private Cell initCell(){
		Cell cell = new Cell("Phone Number");
		cell.setField("phonenumber");
		cell.setCellClasses("cssCellClass");
		cell.setCellStyles("cssCellStyle");
		cell.setClasses("columnClass");
		cell.setColSpan(new Integer(2));
		cell.setDefaultValue("ahahah");
		cell.setIsNotResizable(Boolean.TRUE);
		cell.setRowSpan(new Integer(3));
		cell.setStyles("columnStyle");
		cell.setWidth("200px");
		return cell;
	}
	
	
}
