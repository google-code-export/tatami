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

import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.grid.Cell;
import com.objetdirect.tatami.client.grid.Grid;
import com.objetdirect.tatami.client.grid.formatters.BaseFormatter;

public class TestPourDoc extends TestPage {
	
	public TestPourDoc(){
		super("com.objetdirect.tatami.testpages.client.TestPourDoc", "Test Pour Doc");
	}
	
	public Widget getTestPage() {
		Grid grid = new Grid();
		Cell cell = grid.addColumn("Year" , "year");
		cell.setClasses("red");
		cell.setHeaderClasses("favorite");
		grid.addColumn("Gross Profit" , "gprofit" , new ColorFormatter());
		Object[] row1 = {new Integer(2003) , new Float(-12738)};
		Object[] row2 = {new Integer(2004) , new Float(-9425)};
		Object[] row3 = {new Integer(2005) , new Float(1188)};
		Object[] row4 = {new Integer(2006) , new Float(-2340)};
		Object[] row5 = {new Integer(2007) , new Float(4322)};
		Object[] row6 = {new Integer(2008) , new Float(7000)};
		grid.addRow(row1);
		grid.addRow(row2);
		grid.addRow(row3);
		grid.addRow(row4);
		grid.addRow(row5);
		grid.addRow(row6);
		return grid;

	}

	private class ColorFormatter extends BaseFormatter{
		public String format(String toFormat) {
			try{
				Float f = new Float(toFormat);
				if(f.floatValue() >= 0){
					return "<span style='color:green;'>"+f.toString()+"</span>";
				}else{
					return "<span style='color:red;'>"+f.toString()+"</span>";
				}
				
			}catch(NumberFormatException e){
				return "NaN";
			}
		}
	}
	
}
