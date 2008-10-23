package com.objetdirect.tatami.testpages.client;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.grid.Grid;

public class TestFillingEmtyGridPage extends TestPage{

	protected TestFillingEmtyGridPage() {
		super("com.objetdirect.tatami.testpages.client.TestFillingEmtyGridPage", "Test Empty Grid");
	}

	public Widget getTestPage() {
		Panel flow = new FlowPanel();
		final Grid grid = new Grid();
		grid.addColumn("1");
		grid.addColumn("2");
		grid.addColumn("3");
		flow.add(grid);
		Object[] row = {"1","2","3"};
		flow.add(new Button("Add row", new ClickListener(){

			public void onClick(Widget sender) {
				Object[] row = {"1","2","3"};
				grid.addRow(row);
			}
		}));
		return flow;
	}

}
