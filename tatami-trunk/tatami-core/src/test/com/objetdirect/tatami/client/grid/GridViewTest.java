package com.objetdirect.tatami.client.grid;

import com.google.gwt.core.client.JavaScriptObject;
import com.objetdirect.tatami.client.DefaultTatamiTest;

public class GridViewTest extends DefaultTatamiTest {

	public void testAttributes(){
		GridView view = new GridView();
		view.setScrollable(false);
		view.setWidth("200px");
		assertEquals(false, view.isScrollable());
		assertEquals("200px", view.getWidth());
		assertTrue(assertJSViewAttributes(view.toJSObject()));
	}
	
	private native boolean assertJSViewAttributes(JavaScriptObject view)/*-{
		if(view.noscroll != true) return false;
		if(view.width != "200px") return false;
		return true;
	}-*/;
	
	public void testCellManipulation(){
		GridView view = new GridView();
		Cell cell1 = new Cell("grou");
		Cell cell2 = new Cell("grougrou");
		Cell cell3 = new Cell("grougrougrou");
		Cell cell4 = new Cell("grougrougrougrou");
		
		view.addCellToLastRow(cell1);
		view.addCellToLastRow(cell2);
		view.addCellToLastRow(cell3);
		
		assertEquals(cell1 , view.getCell(0));
		assertEquals(cell2 , view.getCell(1));
		
		view.addCellToLastRow(cell4 , 2);
		assertEquals(cell4 , view.getCell(2));
		assertEquals(cell3 , view.getCell(3));
		
		assertEquals(4, view.getNbCells());
		view.clear();
		assertEquals(0, view.getNbCells());
		cell1.setRowSpan(new Integer(2));
		cell2.setColSpan(new Integer(2));
		view.addCellToRow(cell1, 0);
		view.addCellToRow(cell2, 0);
		view.addCellToRow(cell3, 1);
		view.addCellToRow(cell4, 1);
		assertEquals(4, view.getNbCells());
		assertEquals(cell1, view.getCell(0));
		assertEquals(cell2, view.getCell(1));
		assertEquals(cell3, view.getCell(2));
		assertEquals(cell4, view.getCell(3));
		assertEquals(cell1, view.getCellFromRow(0, 0));
		assertEquals(cell3, view.getCellFromRow(1, 0));
		
		view.removeCell(cell1);
		assertEquals(cell2, view.getCellFromRow(0 , 0));
		assertEquals(cell4, view.getCell(2));
		view.removeCell(1);
		assertEquals(2, view.getNbCells());
		assertEquals(cell4, view.getCell(1));
		
		view.addCellToRow(cell1, 1 , 0);
		assertEquals(cell1, view.getCellFromRow(1,0));
		assertEquals(cell4, view.getCellFromRow(1 , 1));
		assertEquals(cell1, view.getCell(1));
		
		view.removeCell(1, 0);
		assertEquals(cell2, view.getCellFromRow(0 , 0));
		assertEquals(cell4, view.getCellFromRow(1 , 0));
	}
	
	public void testExportGridViewToJS(){
		GridView view = new GridView();
		Cell cell1 = new Cell("grou");
		Cell cell2 = new Cell("grougrou");
		Cell cell3 = new Cell("grougrougrou");
		Cell cell4 = new Cell("grougrougrougrou");
		
		view.addCellToLastRow(cell1);
		view.addCellToLastRow(cell2);
		view.addCellToLastRow(cell3);
		view.addCellToRow(cell4, 1);
		
		assertTrue(isConform(view.toJSObject()));
	}
	
	private native boolean isConform(JavaScriptObject view)/*-{
		if(typeof view != 'object') return false;
		if(view.cells.length != 2 ) return false;
		var firstRow = view.cells[0];
		if(firstRow.length != 3) return false;
		if(firstRow[0].name != "grou") return false;
		if(firstRow[1].name != "grougrou") return false;
		if(firstRow[2].name != "grougrougrou") return false;
		var secondRow = view.cells[1];
		if(secondRow.length != 1) return false;
		if(secondRow[0].name != "grougrougrougrou") return false;
		return true;
	}-*/;
	
	
	
	
}
