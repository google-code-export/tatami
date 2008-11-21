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
package com.objetdirect.tatami.testpages.client.widgets;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.grid.Grid;
import com.objetdirect.tatami.testpages.client.TestPage;

public class TestFillingEmtyGridPage extends TestPage{

	protected TestFillingEmtyGridPage() {
		super(TestFillingEmtyGridPage.class.getCanonicalName(), "Test Empty Grid");
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
