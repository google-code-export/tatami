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
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;

public class ToasterModule implements EntryPoint {

	private Toaster toaster;
	public final static String[] positions = {Toaster.BOTTOM_RIGHT_UP, Toaster.BOTTOM_RIGHT_LEFT,Toaster.BOTTOM_LEFT_UP,Toaster.BOTTOM_LEFT_RIGHT,Toaster.TOP_RIGHT_DOWN,
            Toaster.TOP_RIGHT_LEFT,Toaster.TOP_LEFT_DOWN,Toaster.TOP_LEFT_RIGHT};

	public static final String messageTopic = "message";

	private String position;

	/**
	 * Creates the module
	 * @param position position for the toaster
	 */
	public ToasterModule(String position) {

		this.position = position;
	}

	/**
	 * Shows all predefined message of a Toaster
	 * @param message the message to write
	 * @return true if all predefined message are displayed
	 */
	public boolean showPreDefinedMessage(String message) {
		boolean success = false;
		try {
			Toaster.publishError(messageTopic, message);
			Toaster.publishMessage(messageTopic, message);
			Toaster.publishWarning(messageTopic, message);
			success = true;
		} catch (Exception e) {
			GWT.log("error ", e);
			success = false;
		}
		return success;
	}

	/**
	 * Shows a message specifying the type and the delay. 
	 * @param message the message to write
	 * @param type the type for the message  <code>PLAIN_MESSAGE, 
	 *        WARNING_MESSAGE, ERROR_MESSAGE et FATAL_MESSAGE</code>
	 * @param delay 
	 * @return returns true if the message is displayed
	 */
	public boolean showMessage(String message,String type, int delay) {
		boolean success = false;
		try {
           Toaster.publish(messageTopic,message, type, delay);
           success = true;
		} catch (Exception e) {
			GWT.log("error ", e);
			success = false;
		}
		return success;
	}

	/**
	 * Loads the module to test components
	 */
	public void onModuleLoad() {
		RootPanel.get().add(getToaster());

	}

	/**
	 * Returns the instance of the Toaster.
	 * @return an instance of Toaster, a new instance if created if it was not existed
	 */
	public Toaster getToaster() {
		if (toaster == null) {
			this.toaster = new Toaster(messageTopic, this.position);
		}
		return this.toaster;
	}

}
