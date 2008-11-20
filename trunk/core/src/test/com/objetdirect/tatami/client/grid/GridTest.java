package com.objetdirect.tatami.client.grid;

import com.google.gwt.user.client.ui.RootPanel;
import com.objetdirect.tatami.client.DefaultTatamiTest;

public class GridTest extends DefaultTatamiTest {

	public void testGridCreation(){
		Grid grid = new Grid();
		grid.addColumn("Column1");
		grid.addColumn("Column2");
		grid.addColumn("Column3");
		
		Object[] row1 = { "Hello" , "John" , "Doe"};
		Object[] row2 = { "Goodbye" , "John" , "Doe"};
		grid.addRow(row1);
		grid.addRow(row2);
	
		assertEquals(2, grid.getRowCount());
		assertEquals(3, grid.getColumnCount());
		
	}
	
	public void testGridAttach(){
		Grid grid = new Grid();
		grid.addColumn("Column1");
		grid.addColumn("Column2");
		grid.addColumn("Column3");
		
		Object[] row1 = { "Hello" , "John" , "Doe"};
		Object[] row2 = { "Goodbye" , "John" , "Doe"};
		Object[] row3 = { "Still Here !" , "John" , "Doe"};
		grid.addRow(row1);
		grid.addRow(row2);
		RootPanel.get().add(grid);
		grid.updateGrid();
		grid.addRow(row3);
		assertEquals(3, grid.getRowCount());
	}
	
	
}
