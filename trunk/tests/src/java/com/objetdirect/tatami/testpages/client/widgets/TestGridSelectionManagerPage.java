package com.objetdirect.tatami.testpages.client.widgets;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.data.Item;
import com.objetdirect.tatami.client.grid.Grid;
import com.objetdirect.tatami.client.grid.GridListener;
import com.objetdirect.tatami.client.grid.GridSelectionManager;
import com.objetdirect.tatami.client.grid.editor.TextEditor;
import com.objetdirect.tatami.testpages.client.TestPage;

public class TestGridSelectionManagerPage extends TestPage{
	protected TestGridSelectionManagerPage() {
		super(TestGridSelectionManagerPage.class.getName(), "Test Grid Selection Manager");
		
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
		final GridSelectionManager manager = new GridSelectionManager(grid);
		
		Button selectFirstAndLastRows =  new Button("Select rows 1 and 3" , new ClickListener(){
			public void onClick(Widget sender) {
				manager.addToSelection(0);
				manager.addToSelection(2);
			}
		});
		DOM.setElementAttribute(selectFirstAndLastRows.getElement(),"id","selectFirstAndLastRows");
		panel.add(selectFirstAndLastRows);
		
		Button deselectAllRows =  new Button("Deselect All" , new ClickListener(){
			public void onClick(Widget sender) {
				manager.deselectAll();
			}
		});
		
		DOM.setElementAttribute(deselectAllRows.getElement(),"id","deselectAllRows");
		panel.add(deselectAllRows);
		Button deselectFirstRow =  new Button("Deselect FirstRow" , new ClickListener(){
			public void onClick(Widget sender) {
				manager.deselect(0);
			}
		});
		
		DOM.setElementAttribute(deselectFirstRow.getElement(),"id","deselectFirstRow");
		panel.add(deselectFirstRow);
		
		Button removeSelectedRowsButton = new Button("Remove selected rows button" , new ClickListener(){
			public void onClick(Widget sender) {
				grid.removeSelectedRows();
			}
		});
		DOM.setElementAttribute(removeSelectedRowsButton.getElement(),"id","RemoveSelectedRowsButton");
		panel.add(removeSelectedRowsButton);
		
		ListBox list = new ListBox();
		list.addItem(GridSelectionManager.SELECTION_MODE_EXTENDED);
		list.addItem(GridSelectionManager.SELECTION_MODE_MULTIPLE);
		list.addItem(GridSelectionManager.SELECTION_MODE_SINGLE);
		list.addItem(GridSelectionManager.SELECTION_MODE_NONE);
		list.addChangeListener(new ChangeListener(){
			public void onChange(Widget arg0) {
				manager.setMode(((ListBox)arg0).getItemText(((ListBox)arg0).getSelectedIndex()));
			}
		});
		DOM.setElementAttribute(list.getElement(), "id", "ListSelectionModes");
		panel.add(list);
		
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
