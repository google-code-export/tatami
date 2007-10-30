/*
 * Tatami: 
 * Copyright (C) 2007 Objet Direct
 * Copyright (C) 2007 France Telecom
 * Contact: tatami@objectweb.org
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
 * Authors: Vianney Grassaud
 * Initial developer(s):
 * Contributor(s):
 */
package com.objetdirect.tatami.client;

import com.google.gwt.user.client.ui.Label;

public class DojoContentPaneTest extends DefaultTatamiTest {

	
	/**
	 * Tests the the adding of several widgets and removing
	 * on the DojoContentPane component 
	 *
	 */
	public void testAddWidget() {
		DojoContentPaneModule module = new DojoContentPaneModule();
		module.onModuleLoad();
		DojoContentPane pane = module.getPane();
		int count = pane.getWidgetCount();
		Label label = new Label("test");
		pane.add(label);
		assertTrue(count < pane.getWidgetCount());
		assertSame(pane.getWidget(0),module.getImage());
		assertSame(pane.getWidget(1),label);
		pane.remove(label);
		assertEquals(count,pane.getWidgetCount());
		
		
	}
	
	

}//end of class
