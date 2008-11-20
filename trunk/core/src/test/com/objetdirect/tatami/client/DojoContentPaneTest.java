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
 * Authors:  Vianney Grassaud
 * Initial developer(s): Vianney Grassaud
 * Contributor(s):
 */
package com.objetdirect.tatami.client;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class DojoContentPaneTest extends DefaultTatamiTest {

	private DojoContentPane pane;
	private Image image;
	private final String ID = "pane";
	/**
	 * Tests the the adding of several widgets and removing
	 * on the DojoContentPane component 
	 *
	 */
	public void testAddWidget() {
		pane = getPane();
		int count = pane.getWidgetCount();
		Label label = new Label("test");
		pane.add(label);
		assertTrue(count < pane.getWidgetCount());
		assertSame(pane.getWidget(0),getImage());
		assertSame(pane.getWidget(1),label);
		pane.remove(label);
		assertEquals(count,pane.getWidgetCount());
		assertSame(pane.getId(),ID);
	}

	
	/**
	 * Test the getID() method
	 *
	 */
	public void testID() {
		pane = getPane();
		assertSame(pane.getId(),ID);
	}
	
	/**
	 * 
	 */
	@Override
	public void gwtTearDown() throws Exception{
		this.image = null;
		this.pane = null;
		super.gwtTearDown();
	}
	
	/**
	 * Returns the instance of the DojoContentPane.
	 * @return an instance of DojoContentPane, a new instance if created if it was not existed
	 */
	private DojoContentPane getPane() {
		RootPanel.get().clear();
		if ( pane == null) {
			this.pane = new DojoContentPane(ID);
            this.pane.add(getImage());
            
            RootPanel.get().add(pane);
		}
		return this.pane;
	}
	
	/**
	 * Returns the instance of an Image
	 * @return an instance of Image, a new instance if created if it was not existed
	 */
	private Image getImage() {
		if ( image == null ) {
			this.image = new Image("browser.png");
		}
		return this.image;
	}

}//end of class
