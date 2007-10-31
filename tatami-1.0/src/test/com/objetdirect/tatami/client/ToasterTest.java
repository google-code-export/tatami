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



public class ToasterTest extends DefaultTatamiTest {

	private String testMessage = "This is a test";

	/**
	 * Tests if the toaster is not null. And display all predifined message (warning, error, plain)
	 * @param position the position for the toaster
	 * @return true if all predifined message are displayed
	 */
	private boolean applyPredefinedMessages(String position) {
		final ToasterModule module = new ToasterModule(position);
		Toaster toaster = module.getToaster();
		assertNotNull(toaster);

		return module.showPreDefinedMessage(testMessage);

	}

	/**
	 * Tests all predefined messages with all position
	 *@see #applyPredefinedMessages(String)
	 */
	public void testAllPredefined() {
		for (int i = 0; i < ToasterModule.positions.length; i++) {
			assertTrue(applyPredefinedMessages(ToasterModule.positions[i]));
		}
	}

	/**
	 * Shows message on a specify position
	 * @param position the position for the message
	 * @param type the type of the message to show
	 * @return true if the message is displayed
	 */
	private boolean applyPosition(String position, String type) {
		ToasterModule module = new ToasterModule(position);
		module.onModuleLoad();
		return module.showMessage(testMessage, type, 1500);

	}

	
	/**
	 * Tests  fatal type message with all positions
	 *
	 */
	public void testAllFatalPosition() {
		for (int i = 0; i < ToasterModule.positions.length; i++) {
			assertTrue(applyPosition(ToasterModule.positions[i],Toaster.FATAL_MESSAGE));
		}
	}
	
	/**
	 * Tests  plain  type message with all positions
	 *
	 */
	public void testAllPlainPosition() {
		for (int i = 0; i < ToasterModule.positions.length; i++) {
			assertTrue(applyPosition(ToasterModule.positions[i],Toaster.PLAIN_MESSAGE));
		}
	}

}// end of class
