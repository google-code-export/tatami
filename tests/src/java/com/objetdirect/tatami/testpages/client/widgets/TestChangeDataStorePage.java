package com.objetdirect.tatami.testpages.client.widgets;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.data.DefaultDataStore;
import com.objetdirect.tatami.client.data.Item;
import com.objetdirect.tatami.client.grid.Cell;
import com.objetdirect.tatami.client.grid.Grid;
import com.objetdirect.tatami.testpages.client.TestPage;

public class TestChangeDataStorePage extends TestPage{

	public TestChangeDataStorePage(){
		super(TestChangeDataStorePage.class.getName(),"Test Change DataStore");
	}

	@Override
	public Widget getTestPage() {
		FlowPanel panel = new FlowPanel();
		final DefaultDataStore store1 = new DefaultDataStore();
		Item item1 = new Item();
		item1.setId("Item1 From Store1");
		store1.add(item1);
		
		final DefaultDataStore store2 = new DefaultDataStore();
		Item item2 = new Item();
		item2.setId("Item1 From Store2");
		store2.add(item2);
		
		final Grid grid = new Grid();
		grid.addCell(new Cell(Item.idAttribute,"Id"));
		grid.setStore(store1);
		
		Button button = new Button("Change datastore", new ClickListener(){

			public void onClick(Widget arg0) {
				if(grid.getStore() == store1){
					grid.setStore(store2);
				}else{
					grid.setStore(store1);
				}
			}
			
		});
		DOM.setElementAttribute(button.getElement(), "id", "ChangeDataStoreButton");
		DOM.setElementAttribute(grid.getElement(), "id", "Grid");
		panel.add(grid);
		panel.add(button);
		return panel;
	}
	
	
	
}
