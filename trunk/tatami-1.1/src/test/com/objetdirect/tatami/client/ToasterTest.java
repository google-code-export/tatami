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

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;



public class ToasterTest extends DefaultTatamiTest {

	private Toaster toaster;
	private final static String[] positions = {Toaster.BOTTOM_RIGHT_UP, Toaster.BOTTOM_RIGHT_LEFT,Toaster.BOTTOM_LEFT_UP,Toaster.BOTTOM_LEFT_RIGHT,Toaster.TOP_RIGHT_DOWN,
            Toaster.TOP_RIGHT_LEFT,Toaster.TOP_LEFT_DOWN,Toaster.TOP_LEFT_RIGHT};

	private static final String messageTopic = "message";

	private String testMessage = "This is a test";

	/**
	 * Tests if the toaster is not null. And display all predifined message (warning, error, plain)
	 * @param position the position for the toaster
	 * @return true if all predifined message are displayed
	 */
	private boolean applyPredefinedMessages(String position) {
		toaster = getToaster(position);
		assertNotNull(toaster);
		return showPreDefinedMessage(testMessage);

	}

	
	
	/**
	 * Tests all predefined messages with all position
	 *@see #applyPredefinedMessages(String)
	 */
	public void testAllPredefined() {
		for (int i = 0; i < positions.length; i++) {
			assertTrue(applyPredefinedMessages(positions[i]));
		}
	}

	/**
	 * Shows message on a specify position
	 * @param position the position for the message
	 * @param type the type of the message to show
	 * @return true if the message is displayed
	 */
	private boolean applyPosition(String position, String type) {
		toaster = getToaster(position);
		return showMessage(testMessage, type, 1500);

	}

	
	/**
	 * Tests  fatal type message with all positions
	 *
	 */
	public void testAllFatalPosition() {
		for (int i = 0; i < positions.length; i++) {
			assertTrue(applyPosition(positions[i],Toaster.FATAL_MESSAGE));
		}
	}
	
	/**
	 * Tests  plain  type message with all positions
	 *
	 */
	public void testAllPlainPosition() {
		for (int i = 0; i < positions.length; i++) {
			assertTrue(applyPosition(positions[i],Toaster.PLAIN_MESSAGE));
		}
	}

	
	public void tearDown() {
		this.toaster =null;
	}
	
	
	/**
	 * Shows all predefined message of a Toaster
	 * @param message the message to write
	 * @return true if all predefined message are displayed
	 */
	private boolean showPreDefinedMessage(String message) {
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
	private boolean showMessage(String message,String type, int delay) {
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
	 * Returns the instance of the Toaster.
	 * @param position position for the toaster
	 * @return an instance of Toaster, a new instance if created if it was not existed
	 */
	private Toaster getToaster(String position) {
		if (toaster == null) {
			toaster = new Toaster(messageTopic,position);
			RootPanel.get().add(toaster);
		}
		return this.toaster;
	}
}// end of class
