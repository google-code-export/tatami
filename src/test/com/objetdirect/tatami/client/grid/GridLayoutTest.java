package com.objetdirect.tatami.client.grid;

import com.objetdirect.tatami.client.DefaultTatamiTest;

public class GridLayoutTest extends DefaultTatamiTest{

	public void testLayoutSettings(){
		GridLayout layout = new GridLayout();
		layout.setRowBar(true);
		assertEquals(true, layout.hasRowBar());
		layout.setRowBar(false);
		assertEquals(false, layout.hasRowBar());
	}
	
	
	public void testManipulatingViews(){
		GridLayout layout = new GridLayout();
		GridView view1 = new GridView();
		Cell cell11 = new Cell("grou");
		Cell cell12 = new Cell("grougrou");
		Cell cell13 = new Cell("grougrougrou");
		Cell cell14 = new Cell("grougrougrougrou");
		
		view1.addCellToLastRow(cell11);
		view1.addCellToLastRow(cell12);
		view1.addCellToRow(cell13, 1);
		view1.addCellToRow(cell14, 0 , 1);
		
		layout.addView(view1);
		
		assertEquals(4, layout.getNbCells());
		assertEquals(1, layout.getNbViews());
		assertEquals(view1, layout.getLastView());
		assertEquals(view1, layout.getView(0));
		assertEquals(cell11 , layout.getCellAmongAllViews(0));
		assertEquals(cell14 , layout.getCellAmongAllViews(1));
		assertEquals(cell12 , layout.getCellAmongAllViews(2));
		assertEquals(cell13 , layout.getCellAmongAllViews(3));
		
		
		GridView view2 = new GridView();
		Cell cell21 = new Cell("grou");
		Cell cell22 = new Cell("grougrou");
		Cell cell23 = new Cell("grougrougrou");
		Cell cell24 = new Cell("grougrougrougrou");
		
		view2.addCellToLastRow(cell21);
		view2.addCellToLastRow(cell22);
		view2.addCellToRow(cell23, 1);
		view2.addCellToRow(cell24, 0 , 1);
		
		layout.addView(view2);
		
		assertEquals(8, layout.getNbCells());
		assertEquals(2, layout.getNbViews());
		assertEquals(view2, layout.getLastView());
		assertEquals(view1, layout.getView(0));
		assertEquals(view2, layout.getView(1));
		assertEquals(cell11 , layout.getCellAmongAllViews(0));
		assertEquals(cell14 , layout.getCellAmongAllViews(1));
		assertEquals(cell12 , layout.getCellAmongAllViews(2));
		assertEquals(cell13 , layout.getCellAmongAllViews(3));
		assertEquals(cell21 , layout.getCellAmongAllViews(4));
		assertEquals(cell24 , layout.getCellAmongAllViews(5));
		assertEquals(cell22 , layout.getCellAmongAllViews(6));
		assertEquals(cell23 , layout.getCellAmongAllViews(7));
		
		layout.removeCellAmongAllViews(cell11);
		assertEquals(7, layout.getNbCells());
		layout.addCellToLastRowFromLastView(cell11);
		assertEquals(8, layout.getNbCells());
		assertEquals(cell11 , layout.getCellAmongAllViews(7));
		layout.removeCellAmongAllViews(cell11);
		layout.addCellToLastRowFromLastView(cell11, 0);
		assertEquals(cell11 , layout.getCellAmongAllViews(6));
		layout.removeCellAmongAllViews(cell11);
		
		layout.addCellToLastView(cell11, 0);
		assertEquals(cell11 , layout.getCellAmongAllViews(6));
		layout.removeCellAmongAllViews(cell11);
		
		layout.addCellToLastView(cell11, 0, 0);
		assertEquals(cell11 , layout.getCellAmongAllViews(3));
		layout.removeCellAmongAllViews(cell11);
		
		view1.addCellToRow(cell11, 0 , 0);
		layout.removeView(0);
		
		assertEquals(1, layout.getNbViews());
		assertEquals(4, layout.getNbCells());
		assertEquals(cell21 , layout.getCellAmongAllViews(0));
		assertEquals(cell24 , layout.getCellAmongAllViews(1));
		assertEquals(cell22 , layout.getCellAmongAllViews(2));
		assertEquals(cell23 , layout.getCellAmongAllViews(3));
		
		
		
		layout.addView(0, view1);
		assertEquals(8, layout.getNbCells());
		assertEquals(2, layout.getNbViews());
		assertEquals(view2, layout.getLastView());
		assertEquals(view1, layout.getView(0));
		assertEquals(view2, layout.getView(1));
		assertEquals(cell11 , layout.getCellAmongAllViews(0));
		assertEquals(cell14 , layout.getCellAmongAllViews(1));
		assertEquals(cell12 , layout.getCellAmongAllViews(2));
		assertEquals(cell13 , layout.getCellAmongAllViews(3));
		assertEquals(cell21 , layout.getCellAmongAllViews(4));
		assertEquals(cell24 , layout.getCellAmongAllViews(5));
		assertEquals(cell22 , layout.getCellAmongAllViews(6));
		assertEquals(cell23 , layout.getCellAmongAllViews(7));
		
		
	}
	
}
