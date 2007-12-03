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

public class DropdownDatePickerTest extends TestDropdownContainer {

	/**
	 * Returns a new instance of DropdownDatePicker
	 * @return  a new instance of DropdownDatePicker
	 */
	protected DropdownContainer createInstance(Date min, Date max) {
		DropdownContainer container = null;
		if ( min == null || max == null) {
			container =new DropdownDatePicker(); 
		} else {
			container = new DropdownDatePicker(min,max);
		}
		return container;
	}
	
	/**
	 * Returns the current date in a string representation
	 * @return the current date in a string representation
	 */
	protected String getText() {
		return "04/04/2081";
	}
}
