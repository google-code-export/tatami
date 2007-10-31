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
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.RootPanel;

public class ClockModule implements EntryPoint {

	private Clock clock;

	/**
	 * Loads the module to test components
	 */
	public void onModuleLoad() {
		RootPanel.get().add(getClock());

	}

	/**
	 * Returns the root element of this module
	 * @return the root DOM element of the module
	 */
	public Element getRootElement() {
		return RootPanel.get().getElement();
	}
	
	/**
	 * Returns the instance of the Clock.
	 * @return an instance of Clock, a new instance if created if it was not existed
	 */
	public Clock getClock() {
		if (this.clock == null) {
			clock = new Clock();
		}
		return clock;
	}
}//end of class
