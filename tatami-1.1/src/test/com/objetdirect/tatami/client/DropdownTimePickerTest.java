
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

import java.util.Date;

import com.google.gwt.user.client.ui.RootPanel;

public class DropdownTimePickerTest extends TestDropdownContainer {

	
	
	/**
	 * Returns a new instance of DropdownTimePicker
	 * @return a new instance of DropdownTimePicker
	 */
	protected DropdownContainer createInstance(Date min, Date max) {
		DropdownContainer container = null;
		if ( min == null || max == null) {
			container =new DropdownTimePicker(); 
		} else {
			container = new DropdownTimePicker(min,max,DropdownTimePicker.DEFAULT_PATTERN);
		}
		return container;
	}


	/**
	 * Returns a time in a string representation
	 * @return "21:25"
	 */
	protected String getText() {
		return "21:25";
	}
	
	
	public void testTimePattern() {
		DropdownTimePicker container  =new DropdownTimePicker();
		RootPanel.get().add(container);
		assertEquals(container.getTimePattern(),DropdownTimePicker.DEFAULT_PATTERN);
	}
	
	/**
	 * Tests the setTime and getTime methods
	 *
	 */
	public void testSetTime() {
		DropdownTimePicker container  =new DropdownTimePicker();
		container.setTime(new Date());
		assertNotNull(container.getTime());
	}

}//end of class