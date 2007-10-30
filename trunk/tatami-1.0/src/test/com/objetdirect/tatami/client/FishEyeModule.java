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
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootPanel;
import com.objetdirect.tatami.client.test.TestUtil;

public class FishEyeModule implements EntryPoint {

	private FishEye fe;

	private boolean clicked = false;

	public static final String defaultIcon = "browser.png";

	/**
	 * Loads the module to test components
	 */
	public void onModuleLoad() {
		FishEye f = getFishEye();
		f.add(defaultIcon, "titre", new Command() {
			public void execute() {
			    System.out.println("je clique");
				clicked = true;
			}
		});
		RootPanel.get().add(f, 10, 10);
		
		
	}

	/**
	 * Returns the instance of the FishEye.
	 * @return an instance of FishEye, a new instance if created if it was not existed
	 */
	public FishEye getFishEye() {
		if (fe == null) {
			fe = new FishEye();
		}
		return this.fe;
	}

	 /**
	 * Permits to test is a mouse click was thrown.
	 * @return true if a click was done false otherwise.
	 */
	public boolean isClicked() {
		return this.clicked;
	}

	/**
	 * Sets if a click mouse event was done or not.
	 * @param b
	 */
	public void setClicked(boolean b) {
		this.clicked = b;
	}
}
