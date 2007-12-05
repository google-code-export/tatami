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
 * A form input for entering dates with a pop-up TimePicker to aid in selection
 * The DropDownTimePicker component aims to simplify the edition of an hour in a input form.
 * the  popup TimePicker aids to choose hour and minute, minute.     
   The zone text will have a AM or PM format according to selection of the user.
 * 
 * Below an example to get the modification of the edited time. 
 * 
 * <code>
 *  DropdownTimePicker ddtp = new DropdownTimePicker();
 *  ddtp.setTime(new Date());
 *  RootPanel.get().add(ddtp, 30, 30);
 *	ddtp.addChangeListener(new ChangeListener() {
 *		public void onChange(Widget sender) {
 * 			  Window.alert("New time selected : " + ddtp.getTime() );
 *		}
 *
 * 	}});
 * </code>
 * 
 * @author Henri, Vianney
 *
 */
public class DropdownTimePicker extends DropdownContainer {

	static public final String DEFAULT_PATTERN = "HH:mm"; 
	
	private String timePattern = DEFAULT_PATTERN;
	
	/**
	 * Creates  DropDownTimePicker component.
	 */
	public DropdownTimePicker() {
		this(DEFAULT_PATTERN);
	}

	/**
	 * Creates  DropDownTimePicker component.
	 */
	public DropdownTimePicker(String timePattern) {
		this(null,null,timePattern);
		
	}
	
	/**
	 * Creates  DropDownTimePicker component.
	 */
	public DropdownTimePicker(Date startDate, Date endDate,String timePattern) {
		super(startDate,endDate);
		this.timePattern = timePattern;
	}
		
	/**
	 * Returns the name of DOJO widget.
	 * @return "dijit.form.TimeTextBox"
	 */
	public String getDojoName() {
		return "dijit.form.TimeTextBox";
	}

		
    /**
     * A callBack method called by the DOJO widget each times a date is modified.
     * The method updates the date for the GWT widget.
     * @param date the new date.
     */
	protected void onValueChanged(JavaScriptObject date) {
		Date javaDate = adjust(DateUtil.getJavaDate(date));
  	    setInternDate(javaDate);

	}
	
	/**
	 * Returns the selected, edited time.
	 * @return the date corresponding to the time.
	 * @see #getDate()
	 */
	public Date getTime() {
		return getDate();
	}

	/**
	 * Sets a new Date 
	 * The date is adjusted before to be set.  
	 * @see #adjust(Date)
	 */
	public void setDate(Date date) {
		super.setDate(adjust(date));
	}
	
	/**
	 * Sets a new time. calls the method <code>setDate(Date)</code>.
	 * @param date the new time to edit.
	 * @see #setDate(Date)
	 */
	public void setTime(Date date) {
		setDate(date);
	}

	
	/**
	 * Creates the DOJO widget which the type is given by the method
	 * {@link getDojoName()}
	 */
	public void createDojoWidget() {
		this.dojoWidget = createTimeTextBox(timePattern,getMinJavaScriptDate(),getMaxJavaScriptDate());
	}

	
	

	
	
	private native JavaScriptObject createTimeTextBox(String timePattern,JavaScriptObject startDate, JavaScriptObject endDate)
	/*-{
	    if (startDate==null) startDate="1492-10-12";
	    if (endDate==null) endDate="2492-10-12";
	    var widget =  new $wnd.dijit.form.TimeTextBox({
	  
	     constraints : {
	       timePattern : timePattern,
	       min: startDate,
	       max:endDate,
	       
	     }
	    },$wnd.dojo.doc.createElement("div")
	  );
	   return widget;
	 }-*/;
	

	/**
	 * Adjusts the Date for the TimePciker, it means that the second and millisecond are removed from the date.
	 * @param date the date to adjust returned by the DOJO widget. 
	 * @return the date whitout seconds and millisecond information.
	 */
	protected Date adjust(Date date) {
		return  new Date(date.getTime() - (date.getTime() % 60000));
		
	}

	/**
	 * Returns the timepattern used
	 * @return the timePattern used, default is "HH:mm"
	 */
	public String getTimePattern() {
		return this.timePattern;
	}

	
}//end of  class
