/*
 * Tatami: 
 * Copyright (C) 2007 Objet Direct
 * Copyright (C) 2007 France Telecom
 * Contact: tatami@googlegroups.com
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
 * USA
 *
 * Authors:  Ronan Dunklau
 * Initial developer(s): Ronan Dunklau
 * Contributor(s):
 */
package com.objetdirect.tatami.testpages.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.data.Item;
import com.objetdirect.tatami.client.grid.Grid;
import com.objetdirect.tatami.client.grid.RowStyler;

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
			return "myRow";
		}

		public String getRowCSSStyles(int rowIndex, boolean selected,   boolean
				mouseover, boolean odd) {
			Item corresponding = grid.getItemFromRow(rowIndex);
			if(corresponding == null){
				return "";
			}
			return "";
		}

	} 

}



