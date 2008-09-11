package com.objetdirect.tatami.testpages.client;

import java.util.Date;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.grid.Cell;
import com.objetdirect.tatami.client.grid.Grid;
import com.objetdirect.tatami.client.grid.formatters.BaseFormatter;
import com.objetdirect.tatami.client.grid.formatters.BooleanFormatter;
import com.objetdirect.tatami.client.grid.formatters.DateFormatter;

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
