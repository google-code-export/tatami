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

import java.util.Date;

import com.google.gwt.user.client.ui.RootPanel;

public class TimePickerTest extends TestBasePicker {

	/**
	 * Returns a new instance of TimePicker
	 * @param min
	 * @param max
	 * @return a new instance of TimePicker
	 */
	protected BasePicker createInstance(Date min,Date max) {
		BasePicker picker = null;
		if ( min ==null || max == null) {
			picker = new TimePicker();
		} else {
			picker = new TimePicker(min,max,new TimePickerConstraints());
		}
		return picker;
	}

	/**
	 * Tests the setTime and getTime methods
	 *
	 */
	public void testSetTime() {
		TimePicker  picker = new TimePicker();
		RootPanel.get().add(picker);
		Date date = new Date();
		picker.setDate(date);
		assertNotNull(picker.getTime());
		
	}


	/**
	 * Tests the methods about the TimePickerConstraints
	 *
	 */
    public void testTimePattern() {
    	TimePickerConstraints constraints= new TimePickerConstraints();
     	constraints.clickableIncrement = TimePickerConstraints.EVERY_HALF_HOUR;
     	TimePicker timePicker = new TimePicker(constraints);
        RootPanel.get().add(timePicker);
     	
        assertEquals(constraints.clickableIncrement,timePicker.getClickableIncrement());
        assertEquals(constraints.timePattern,timePicker.getTimePattern());
        assertEquals(constraints.visibleIncrement,timePicker.getVisibleIncrement());
        assertEquals(constraints.visibleRange,timePicker.getVisibleRange());
    }

}//end of class
