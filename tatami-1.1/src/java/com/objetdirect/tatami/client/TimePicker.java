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
		
	private TimePickerConstraints constraints;
	
	
	/**
	 * Creates a selector of hour and minutes 
	 * @param startDate the first date that a user can select
	 * @param endDate the last date that a use can select
	 * @param _constraints constraints to format the time to select
	 */
	public TimePicker(Date startDate, Date endDate,TimePickerConstraints _constraints) {
		super(startDate,endDate);
		this.constraints = _constraints;
	}

	public TimePicker(TimePickerConstraints constraints) {
		this(null,null,constraints);
	}
	
	public TimePicker() {
		this(new TimePickerConstraints());
	}
	
	
    
	
	/**
	
	 * Adds a callback method to the DOJO widget in order that this one recalls 
	 * the method <code>onValueChanged</code> of the GWT widget when the associated value 
	 * to the selector will be modified.
	 * 
	
	 */
	protected native void setEventCallback(JavaScriptObject dojoWidget)
	/*-{
	  dojoWidget.onValueSelected = function(date) {
	  dojoWidget.gwtWidget.@com.objetdirect.tatami.client.BasePicker::onValueChanged(Lcom/google/gwt/core/client/JavaScriptObject;)(date);
	 };
	 }-*/;
	
	public String getTimePattern() {
		return this.constraints.timePattern;
	}
	
	public String getVisibleIncrement() {
		return this.constraints.visibleIncrement;
	}
	
	public String getClickableIncrement() {
		return this.constraints.clickableIncrement;
	}
	
	public String getVisibleRange() {
		return this.constraints.visibleRange;
	}
	
    /**
     * Returns the name of the DOJO widget
     * @return "TimePicker"
     */
	public String getDojoName() {
		return "dijit._TimePicker";
	}

	/**
	 * Creates the TimePicker Dojo.
	 */
	public void createDojoWidget() {
		this.dojoWidget = createDojoTimePicker(getMinJavaScriptDate(),getMaxJavaScriptDate(),constraints.timePattern,constraints.clickableIncrement,constraints.visibleIncrement,constraints.visibleRange);
	}

	private native JavaScriptObject createDojoTimePicker(JavaScriptObject startDate,JavaScriptObject endDate,
			                                             String timePattern,String clickableIncrement,String visibleIncrement,String visibleRange) 
	/*-{
	  if (startDate==null) startDate="1492-10-12";
	  if (endDate==null) endDate="2492-10-12";
	  var widget = new $wnd.dijit._TimePicker({
		 clickableIncrement: clickableIncrement,
		 visibleIncrement :  visibleIncrement,
		 visibleRange     :visibleRange,
		 constraints :{ 
		   min : startDate,
		   max : endDate,
		   timePattern : timePattern
		 }
		}
		); 
		
		return widget;
	}-*/;
	
	
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

	
	
	
   
	

}//end of class
