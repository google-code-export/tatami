package com.objetdirect.tatami.testpages.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.data.Item;
import com.objetdirect.tatami.client.grid.Grid;
import com.objetdirect.tatami.client.grid.RowStyler;
import com.objetdirect.tatami.client.grid.formatters.BooleanFormatter;

public class TestGridRowStylerBugPage extends TestPage {

	public Widget getTestPage() {
		Grid grid = new Grid();
		grid.addColumn("name" );
		grid.addColumn("Phone Number" );
		for ( int i = 0; i < 10; i ++) {
			Object[] row2 = {"jhpark" , "000" };
			grid.addRow(row2);
		}
		MyStyler s = new MyStyler(grid);
		grid.setStyler(s);
		grid.setHeight("400");
		grid.setAutoWidth(true);
		grid.setAutoHeight(true);
		grid.setUserSortable(true);
		grid.updateView();  // test
		grid.updateGrid();  // test
		DOM.setElementAttribute(grid.getElement(), "id", "GridContainer");
		return grid;
	}

	public TestGridRowStylerBugPage(){
		super("com.objetdirect.tatami.testpages.client.TestGridRowStylerBugPage", "Test bug grid row styler");
	}

	public class MyStyler implements RowStyler {

		private Grid grid;

		public MyStyler(Grid grid) {
			this.grid = grid;
		}

		public String getRowCSSClasses(int rowIndex, boolean selected,
				boolean mouseover, boolean odd) {
			Item corresponding = grid.getItemFromRow(rowIndex);
			if(corresponding == null){
				return "";
			}
			String name = (String)corresponding.getValues("name"); 
			String name1 = (String) corresponding.getValue("name","name");
			String name2 = (String)grid.getDataAt(rowIndex, 0); 
			return "myRow";
		}

		public String getRowCSSStyles(int rowIndex, boolean selected,   boolean
				mouseover, boolean odd) {
			Item corresponding = grid.getItemFromRow(rowIndex);
			if(corresponding == null){
				return "";
			}
			String name = (String)corresponding.getValues("name"); 
			String name1 = (String) corresponding.getValue("name","name");
			String name2 = (String)grid.getDataAt(rowIndex, 0); 
			return null;
		}

	} 

}



