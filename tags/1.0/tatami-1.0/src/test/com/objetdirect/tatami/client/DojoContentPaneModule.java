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

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;

public class DojoContentPaneModule implements EntryPoint {

	private DojoContentPane pane;
	private Image image;
	
	/**
	 * Loads the module to test components
	 */
	public void onModuleLoad() {
		RootPanel.get().add(getPane());

	}
	
	/**
	 * Returns the instance of the DojoContentPane.
	 * @return an instance of DojoContentPane, a new instance if created if it was not existed
	 */
	public DojoContentPane getPane() {
		if ( pane == null) {
			this.pane = new DojoContentPane("pane");
            this.pane.add(getImage());
		}
		return this.pane;
	}
	
	/**
	 * Returns the instance of an Image
	 * @return an instance of Image, a new instance if created if it was not existed
	 */
	public Image getImage() {
		if ( image == null ) {
			this.image = new Image("browser.png");
		}
		return this.image;
	}

}
