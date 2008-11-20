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

import com.google.gwt.core.client.JavaScriptObject;

/**
 * A form input for entering dates with a pop-up DatePicker to aid in selection
 * 
 * @author Henry, Vianney
 * 
 */
public class DropdownDatePicker extends DropdownContainer {

	
	
   
	
	private String datePattern = null;
	
	/**
	 * Creates the form input with a default date pattern to dd/MM/yyyy
	 * 
	 */
	public DropdownDatePicker() {
		this(null,null, null);

	}

	/**
	 * Creates the form input specifying a  date pattern.
	 * @param datePattern
	 */
	public DropdownDatePicker(String datePattern) {
		this(datePattern,null, null);

	}
	
	
	/**
	 * 
	 * @param startDate
	 * @param endDate
	 */
	public DropdownDatePicker(Date startDate, Date endDate) {
		this(null,startDate,endDate);
		
	}
	
	
	/**
	 * Creates DropdownDatePicker specifying some options
	 * 
	 */
	public DropdownDatePicker(String datePattern,Date startDate, Date endDate) {
		super(startDate,endDate);
		this.datePattern = datePattern;
	}

	/**
	 * Returns the name of the DOJO widget
	 * @return "dijit.form.DateTextBox"
	 */
	public String getDojoName() {
		return "dijit.form.DateTextBox";
	}

	/**
	 * Returns the date pattern used to display the date in the text box
	 * @return a date pattern, see <a href="http://www.unicode.org/reports/tr35/#Date_Format_Patterns">http://www.unicode.org/reports/tr35/#Date_Format_Patterns</a>
	 */
	public String getDatePattern() {
		return this.datePattern;
	}
	
	
	/**
	 * Creates the DOJO widget which the type is given by the method
	 * {@link getDojoName()}
	 */
	public void createDojoWidget() {
		this.dojoWidget = createDateTextBox(this.datePattern,getMinJavaScriptDate(), getMaxJavaScriptDate());
	
	}

	/**
	 * Creates the DOJO widget DateTextBox
	 * @param startDate the first date selectable in the calendar.
	 * @param endDate the last date selectable in the calendar.
	 * @return the DOJO widget or <code>null</code> if there was a problem
	 */
	private native JavaScriptObject createDateTextBox(String datePattern,JavaScriptObject startDate, JavaScriptObject endDate)
	/*-{
	 if (startDate==null) startDate="1492-10-12";
	 if (endDate==null) endDate="2492-10-12";
	 var dateTextBox = null;
	 try {
	  dateTextBox =  new $wnd.dijit.form.DateTextBox(
	     {
	       constraints: { 
	          datePattern : datePattern,
	          
	          min: startDate,
	          max: endDate
	       }
	     },$wnd.dojo.doc.createElement("div")
	 );
    
	 } catch (e) {
	   
	 }
	 return dateTextBox;
 
	 }-*/;

	
	
	
	/**
	 * Adjusts the date returned by the DOJO widget. the method returns the
	 * given date itself, because the date is already cleaned. Temporal
	 * information : hour, minute, millisecon are removed to compare correctly
	 * the date.
	 * 
	 * @param date the date
	 * @return the given date without procressing
	 */
	protected Date adjust(Date date) {
		return date;

	}

	
	/**
	 * Updates the date for the GWT widget when the DOJO widget notify a
	 * changing value.
	 * @param date  the JavaScriptObject corresponding to the new date.
	 * @see #setDate(Date)
	 */
	protected void onValueChanged(JavaScriptObject date) {
		Date theDate = adjust(DateUtil.getJavaDate(date));
		setDate(theDate);

	}

}// end of class
