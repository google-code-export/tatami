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
 * A form input for entering dates with a pop-up DatePicker to aid in selection
 * @author Henry, Vianney
 *
 */
public class DropdownDatePicker extends DropdownContainer {

	/**
	 * Creates the from input.
	 *
	 */
	public DropdownDatePicker() {
		super();
	}

	/**
	 * Returns the name of the DOJO widget
	 * @return "DropdownDatePicker"
	 */
	public String getDojoName() {
		return "DropdownDatePicker";
	}

	/**
	 * Modifies the selected date on the DOJO widget
	 * @param dojoWidget the DOJO widget to modify
	 * @param date the new date to edit.
	 */
	private native void setDojoDate(JavaScriptObject dojoWidget, JavaScriptObject date)
	/*-{
	 dojoWidget.setDate(date);
	 }-*/;

	/**
	 * Adjusts the date returned by the DOJO widget.
	 * the method returns the given date itself, because the date is already cleaned. 
	 * Temporal information : hour, minute, millisecon are removed to compare correctly the date. 
	 * @param date the date
	 * @return the given date without procressing
	 */
	protected Date adjust(Date date) {
   	    return date;
		
	}

	/**
	 * Calls <code>setDojoDate(JavaScriptObject, JavaScriptObject)</code>
	 */
	protected void setDateOnContainer(JavaScriptObject date) {
		setDojoDate(getDojoWidget(), date);
	}

     /**
      *A Callback who call the <code>setDropDownEventCallback(JavaScriptObject)</code> method
      *@param dojoWidget the DOJO widget
      */
	protected void setCallBackForDropDown(JavaScriptObject dojoWidget) {
		setDropDownEventCallback(dojoWidget);
	}

	/**
	 * Arms callback for the changing value of the edited date.
	 * The DOJO widget calls the <code>onValueChanged</code> 
	 * of the GWT widget when the edited date is changed. 
	 * @param dojoWidget the DOJO widget.
	 */
	private native void setDropDownEventCallback(JavaScriptObject dojoWidget)
	/*-{
	 dojoWidget.onValueChanged = function(date) {
	 dojoWidget.gwtWidget.@com.objetdirect.tatami.client.DropdownDatePicker::onValueChanged(Lcom/google/gwt/core/client/JavaScriptObject;)(date);
	 
	 };
	 
	 }-*/;

	/**
	 * Updates the date for the GWT widget when the DOJO widget notify a changing value.
	 * @param date the JavaScriptObject corresponding to the new date.
	 * @see #setDate(Date) 
	 */
	protected void onValueChanged(JavaScriptObject date) {
		Date theDate = adjust(DateUtil.getJavaDate(date));
		setDate(theDate);
	}

}//end of class
