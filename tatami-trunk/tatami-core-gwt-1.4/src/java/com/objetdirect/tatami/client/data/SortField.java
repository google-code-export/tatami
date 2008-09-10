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
 * Authors:  Ronan Dunklau
 * Initial developer(s): Ronan Dunklau
 * Contributor(s):
 */

package com.objetdirect.tatami.client.data;

import com.google.gwt.core.client.JavaScriptObject;
import com.objetdirect.tatami.client.ConvertibleToJSObject;

/**
 * This class represents a sort field.
 * 
 * It contains the name of the field used to sort, and the sorting order
 * 
 * 
 * @author rdunklau
 *
 */
public class SortField implements ConvertibleToJSObject{
	private String attribute;
	private boolean descending;
	
	/**
	 * @param attribute : name of the field used to sort
	 * @param descending : sorting order
	 */
	public SortField(String attribute, boolean descending) {
		this.attribute = attribute;
		this.descending = descending;
	}

	/**
	 * @return the name of the field used to sort
	 */
	public String getAttribute() {
		return attribute;
	}

	/**
	 * @return the sorting order
	 */
	public boolean isDescending() {
		return descending;
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.ConvertibleToJSObject#toJSObject()
	 */
	public JavaScriptObject toJSObject() {
		return buildJSSortField(attribute, descending);
	}
	
	/**
	 * Internal method used to create the javascript sortField object
	 * 
	 * @param attribute
	 * @param descending
	 * @return
	 */
	private native JavaScriptObject buildJSSortField(String attribute, boolean descending)/*-{
		var toReturn = {};
		toReturn.attribute = attribute;
		toReturn.descending = descending;
		return toReturn;
	}-*/;
}
