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
 * Authors: Henri Darmet, Vianney Grassaud
 * Initial developer(s):
 * Contributor(s):
 */
package com.objetdirect.tatami.client;

import java.util.Date;

import com.google.gwt.core.client.JavaScriptObject;

/**
 *A selector of temporal information : hour and minute. 
 *
 */
public class TimePicker extends BasePicker {

	/**
	 * Creates a selector of hour and minutes 
	 */
	public TimePicker() {
		super();
	}

	
    /**
     * Returns the name of the DOJO widget
     * @return "TimePicker"
     */
	public String getDojoName() {
		return "TimePicker";
	}

	/**
	 * Creates the TimePicker Dojo.
	 */
	public void createDojoWidget() {
		this.dojoWidget = DojoController.getInstance().createSimple(this);
	}

	/**
	 * Returns the selected time. Calls the method <code>getDate()</code>
	 * @return a Date with the select hour and minutes.
	 */
	public Date getTime() {
		return getDate();
	}

	/**
	 * Sets a time to select in the selector.
	 * Calls the method <code>setDate(Date)</code>
	 * @param time date to select
	 * @see #setDate(Date)
	 */
	public void setTime(Date time) {
			setDate(time);
	}   

	/**
	 * Adjusts the date returned by the DOJO widget.  
	 * Removes the second and the millisecond from the date. 
	 * @param date the date returned by the DOJO widget in JAVA type. 
	 * @return the date adjusted, it means without the second and millisecond
	 */
	protected Date adjust(Date date) {
		return new Date(date.getTime() - (date.getTime() % 60000));
    }

	/**
	 * Sets the date on the selector. 
	 * Calls the <code>setDojoTime(JavaScriptObject,JavaScriptObject)</code> method 
	 * @param date the new JavaScript date to select.
	 */
	protected void setDateOnPicker(JavaScriptObject date) {
		setDojoTime(getDojoWidget(), date);
	}

	/**
	 * Sets the time to select on the DOJO widget
	 * @param dojowidget the DOJO widget.(TimePicker) 
	 * @param date the date that the TimePicker has to select
	 */
	private  native void setDojoTime(JavaScriptObject dojoWidget,	JavaScriptObject date)
	/*-{
 	     dojoWidget.setTime(date);
	 }-*/;

}//end of class
