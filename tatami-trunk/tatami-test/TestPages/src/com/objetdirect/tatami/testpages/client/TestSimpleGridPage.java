package com.objetdirect.tatami.testpages.client;

import java.util.Date;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.data.Item;
import com.objetdirect.tatami.client.grid.Cell;
import com.objetdirect.tatami.client.grid.Grid;
import com.objetdirect.tatami.client.grid.GridDataStore;
import com.objetdirect.tatami.client.grid.GridListener;
import com.objetdirect.tatami.client.grid.GridView;
import com.objetdirect.tatami.client.grid.editor.TextEditor;
import com.objetdirect.tatami.client.grid.formatters.DateFormatter;

public class TestSimpleGridPage extends TestPage{

	protected TestSimpleGridPage() {
		super("com.objetdirect.tatami.testpages.client.TestSimpleGridPage", "Test Grid");
		
	}
	Grid grid;
	Panel panel;
	
	public Widget getTestPage() {
		grid = new Grid();
		panel = new VerticalPanel();
		panel.add(grid);
		grid.addColumn("First Column","1" , new TextEditor());
		grid.addColumn("Second Column","2");
		grid.addColumn("Third Column","3");
		Object[] row1 = {"A" , "b" , "3"};
		Object[] row2 = {"B" , "c" , "1"};
		Object[] row3 = {"C" , "a" , "2"};
		grid.addRow(row1);
		grid.addRow(row3);
		grid.addRow(row2);
		grid.setUserSortable(true);
		grid.setAutoHeight(false);
		grid.setAutoWidth(false);
		grid.setSortIndex(1,false);
		DOM.setElementAttribute(grid.getElement(), "id", "GridContainer");
		panel.add(grid);
		Button sortBySecondColumnButton = new Button("Sort by second column" , new ClickListener() {
			public void onClick(Widget sender) {
				grid.setSortIndex(1 , true);
			}
		});
		DOM.setElementAttribute(sortBySecondColumnButton.getElement(),"id","SetSecondColumnOrderButton");
		panel.add(sortBySecondColumnButton);
		
		Button clearGridButton = new Button("Clear Grid" , new ClickListener() {
			public void onClick(Widget sender) {
				grid.clearGrid();
			}
		});
		DOM.setElementAttribute(clearGridButton.getElement(),"id","ClearGridButton");
		panel.add(clearGridButton);
		
		Button addRowButton = new Button("Add a row" , new ClickListener(){
			public void onClick(Widget sender) {
				Object[] newRow = { "D" , "d" , "4"};
				grid.addRow(newRow);
			}
		});
		DOM.setElementAttribute(addRowButton.getElement(),"id","AddRowButton");
		panel.add(addRowButton);
		
		Button removeSelectedRowsButton = new Button("Remove selected rows button" , new ClickListener(){
			public void onClick(Widget sender) {
				grid.removeSelectedRows();
			}
		});
		DOM.setElementAttribute(removeSelectedRowsButton.getElement(),"id","RemoveSelectedRowsButton");
		panel.add(removeSelectedRowsButton);
		
		Button removeRowNumber1 =  new Button("Remove row at index 1" , new ClickListener(){
			public void onClick(Widget sender) {
				grid.removeRow(1);
			}
		});
		DOM.setElementAttribute(removeRowNumber1.getElement(),"id","RemoveRowNumber1Button");
		panel.add(removeRowNumber1);
		
		Button addColumnButton =  new Button("Add Column" , new ClickListener(){
			public void onClick(Widget sender) {
				grid.insertColumn("Fourth Column (identical to the Second One)" ,"2" ,2);
				grid.updateView();
			}
		});
		DOM.setElementAttribute(addColumnButton.getElement(),"id","AddColumnButton");
		panel.add(addColumnButton);
		
		Button removeColumnButton =  new Button("Remove Column at index 2" , new ClickListener(){
			public void onClick(Widget sender) {
				grid.removeColumn(2);
				grid.updateView();
			}
		});
		DOM.setElementAttribute(removeColumnButton.getElement(),"id","RemoveColumnButton");
		panel.add(removeColumnButton);
		
		
		final HTML lastClickedCell = new HTML();
		DOM.setElementAttribute(lastClickedCell.getElement(), "id", "LastClickedCell");
		panel.add(lastClickedCell);
		final HTML lastClickedColumnField = new HTML();
		DOM.setElementAttribute(lastClickedColumnField.getElement(), "id", "LastClickedColumnField");
		panel.add(lastClickedColumnField);
		final HTML lastDataChange = new HTML();
		DOM.setElementAttribute(lastDataChange.getElement(), "id", "LastDataChange");
		panel.add(lastDataChange);
		final HTML selectedRows = new HTML();
		DOM.setElementAttribute(selectedRows.getElement(), "id", "SelectedRows");
		panel.add(selectedRows);
		
		
		grid.addGridListener(new GridListener(){
			public void onCellClick(Grid grid, int rowIndex, int colIndex,
					String colField) {
				lastClickedCell.setHTML(rowIndex + ":" + colIndex);
				lastClickedColumnField.setHTML(colField);
			}
			public void onDataChange(Grid grid, Item itemWhichChanged,
					String attributeName, Object oldValue, Object newValue) {
				lastDataChange.setHTML(newValue.toString());
			}
			public void onSelectionChanged(Grid grid) {
				int[] indexes = grid.getSelectedIndexes();
				String text = "";
				for (int i = 0; i < indexes.length; i++) {
					if(i!=0){
						text += "-";
					}
					text+= indexes[i];
				}
				selectedRows.setHTML(text);
			}
			public void onCellDblClick(Grid grid, int rowIndex, int colIndex,
					String colField) {
				
			}
			
		});
		return panel;
	}

	
}
