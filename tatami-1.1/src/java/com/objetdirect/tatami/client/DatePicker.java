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
 * Authors:  Henri Darmet, Vianney Grassaud
 * Initial developer(s):
 * Contributor(s):
 */
package com.objetdirect.tatami.client;

import java.util.Date;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * This class permits to choose a date from a calendar of a Dojo Widget
 * 
 */
public class DatePicker extends BasePicker {


	/**
	 * Creates a DatePicker. The range of available dates begins with the discovery of the America and finish one millenium after
	 */
	public DatePicker() {
		this(null, null);
	
	}

	/**
	 * Creates a selector of dates.
	 * @param startDate a date of the beginning of validity of the calendar
	 * @param endDate date of the end of validity of the calendar
	 */
	public DatePicker(Date startDate, Date endDate) {
		super(startDate,endDate);
		
	}

	
/**
	
	 * Adds a callback method to the DOJO widget in order that this one recalls 
	 * the method <code>onValueChanged</code> of the GWT widget when the associated value 
	 * to the selector will be modified.
	 * 
	
	 */
	protected native void setEventCallback(JavaScriptObject dojoWidget)
	/*-{
	 dojoWidget.onChange = function(date) {
	 dojoWidget.gwtWidget.@com.objetdirect.tatami.client.BasePicker::onValueChanged(Lcom/google/gwt/core/client/JavaScriptObject;)(date);
	 };
	 }-*/;
	
	
	/**
	 * Create the Dojo widget to choose a date from a calendar.
	 */
	public void createDojoWidget() throws Exception {
		
		this.dojoWidget =  createDatePicker(getMinJavaScriptDate(), getMaxJavaScriptDate());
	}

	/**
	 * Creates a Dojo widget DatePicker.
	 * @param startDate date of the beginning of validity of the calendar
	 * @param endDate date of the end this validity of the calendar
	 * @return the Dojo widget.
	 */
	native JavaScriptObject createDatePicker(JavaScriptObject startDate,JavaScriptObject endDate)
	/*-{
	 if (startDate==null) startDate="1492-10-12";
	 if (endDate==null) endDate="2492-10-12";
	  var widget = new $wnd.dijit._Calendar( 
	    {
	    constraints : {min:startDate,
	                   max:endDate
	    }} 
	  );
	                
	 return widget;
	 }-*/;

	
	/**
	 * Returns the date adjusted according to the needs. For the DatePicker no adjustement is necessaryIl n'y a pas 
	 * the Dojo widget returns the date cleaned (the information about hours, 
	 * minutes, seconds et milliseconds are removed) . 
     * @param date the date returned by the Dojo widget.
	 * @return the date itself
	 */
	protected Date adjust(Date date) {
		return date;
	}

		/**
	 * Returns the name of Dojo widget.
	 * @return "DatePicker"
	 */
	public String getDojoName() {
		return "dijit._Calendar";
	}

}//end of class
