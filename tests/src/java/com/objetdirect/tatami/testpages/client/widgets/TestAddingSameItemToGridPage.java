package com.objetdirect.tatami.testpages.client.widgets;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.data.DefaultDataStore;
import com.objetdirect.tatami.client.data.Item;
import com.objetdirect.tatami.client.grid.Grid;
import com.objetdirect.tatami.testpages.client.TestPage;

public class TestAddingSameItemToGridPage extends TestPage{

	protected TestAddingSameItemToGridPage() {
		super(TestAddingSameItemToGridPage.class.getName(), "Test adding items with same Id");
	}

	@Override
	public Widget getTestPage() {
		final FlowPanel panel = new FlowPanel();
		final DefaultDataStore store = new DefaultDataStore();
		final Item item1 =  new Item("1","OLD Item");
		Grid grid = new Grid();
		grid.addColumn("ID",Item.idAttribute);
		grid.addColumn("Name",Item.labelAttribute);
		store.add(item1);
		grid.setStore(store);
		panel.add(grid);
		Button addRow = new Button("Add Item with ID 1",new ClickListener(){
			public void onClick(Widget sender) {
				store.add(new Item("1","New Item"));
			}
		});
		panel.add(addRow);
		DOM.setElementAttribute(grid.getElement(),"id", "GridContainer");
		DOM.setElementAttribute(addRow.getElement(),"id", "AddRowButton");
		return panel;
	}

}
