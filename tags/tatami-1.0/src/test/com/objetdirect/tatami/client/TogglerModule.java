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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class TogglerModule implements EntryPoint {

	/**
	 * 
	 */
	private  Toggler toggler ;
	/**
	 * 
	 */
	private DojoContentPane pane;
	
	public static String id = "labelID";
	
	/**
	 * Loads the module to test components
	 */
	public void onModuleLoad() {
         RootPanel.get().add(getPane(),5,5);
         RootPanel.get().add(getToggler(),5,25);
	}
	
	
	/**
	 * Returns the absolute left position of the Toggler.
	 * @return the left position
	 */
	public int getTogglerX() {
		return this.toggler.getAbsoluteLeft();
	}
	
	/**
	 * Returns the absolute top position of the Toggler.
	 * @return the top position
	 */
	public int getTogglerY() {
		return this.toggler.getAbsoluteTop();
	}
	
	
	/**
	 * Returns the instance of the Toggler.
	 * @return an instance of Toggler, a new instance if created if it was not existed
	 */
	public Toggler getToggler() {
		if ( this.toggler == null) {
			toggler = new Toggler(id);
			toggler.add(new Image("browser.png"));
		}
		return this.toggler;
	}

	/**
	 * Returns the instance of the DojoContentPane.
	 * @return an instance of DojoContentPane, a new instance if created if it was not existed
	 */
	public DojoContentPane getPane() {
		if ( pane == null) {
			this.pane = new DojoContentPane(id);
			pane.add(new Label("Toggler module"));
		}
		return pane;
	}
	
	
	
}//end of class
