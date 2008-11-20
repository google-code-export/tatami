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
 * 
 * <p>
 * Class of widgets that permit to choose a temporal information 
 * (date, hour,...) from a Dojo widget represented by a selector. This is the basic class
 * for the <code>DatePicker,TimePicker</code> components. 
 * Note that the widgets which associate an input and a small popup for selection.   
 * (DropdownDatePicker and DropdownTimePicker) use another basic class.
 * </p>
 * <p>
 * This component is an encapsulation GWT of Dojo components. Warning the widget exist under 3 faces : 
 * <ul>
 * <li>a GWT widget (this!)</li>
 * <li>a Dojo widget (which play the role of 'this' for GWT)</li>
 * <li>a structur of DOM elements, the only exploitable presentation by the navigator.</li>
 * </ul>
 * </p>
 * 
 * 
 * 
 * @author Henry, Vianney
 */
public abstract class BasePicker extends AbstractDojoFocus {
	/**
	 * temporal information displayed and edited.
	 */
	private Date date;

	/**
	 * The minimum selectable date
	 */
	private Date min;

	/**
	 * The maximum selectable date
	 */
	private Date max;
	
	
	
	/**
	
	 * Creates the temporal information selector. Note that its principal element 
	 * is not an object from DOJO. It's a 'DIV' created by GWT. The elements coming 
	 * from DOJO will be inserted into this 'DIV'. This permits to manage more easily
	 * for GWT the standard mouse events ( onmouseover, click,...)
	 * 
	
	 */
	protected BasePicker(Date startDate,Date endDate) {
		super();
		this.min = startDate;
		this.max = endDate;
	}

	/**
	 * Returns the minimum date selectable
	 * @return the minimum date selectable, null if not defined
	 */
	public Date getMinDate() {
		return this.min;
	}
	
	/**
	 * Returns the maximal date selectable
	 * @return the maximal date selectable, null if not defined
	 */
	public Date getMaxDate() {
		return this.max;
	}
	
	
	/**
	 * Returns the minimum date in a JavaScript Date object
	 * @return the minimum date in a JavaScript Date object, null if no minimum date was set
	 */
	protected JavaScriptObject getMinJavaScriptDate() {
		return min == null ? null : DateUtil.getJSDate(min);
    }

	/**
	 * Returns the maximum date in a JavaScript Date object
	 * @return the maximum date in a JavaScript Date object, null if no maximum date was set
	 */

	protected JavaScriptObject getMaxJavaScriptDate() {
		return  max == null ? null : DateUtil.getJSDate(max);
	
	}
	
	
	/**
	 * ask Dojo to take into account an updating of information 
	 * introduced and edited by the selector of date.
	 * @param date the date that the selector has to display
	 */
	private native void setDojoDate(JavaScriptObject dojoWidget, JavaScriptObject date)
	/*-{
	 dojoWidget.setValue(date);
	 }-*/;
	
	
	/**
	
	 * Adds a callback method to the DOJO widget in order that this one recalls 
	 * the method <code>onValueChanged</code> of the GWT widget when the associated value 
	 * to the selector will be modified.
	 * 
	
	 */
	abstract protected  void setEventCallback(JavaScriptObject dojoWidget);
	
	/**
	 * Arms the callbacks after that the Dojo widget is created and re-select the last date selected on the selector.
	 */
	public void doAfterCreation() {
		setEventCallback(getDojoWidget());
		if (date != null) {
			setDateOnPicker(DateUtil.getJSDate(date));
		}
	}

	
	
	/**
	 * Returns the temporal information displayed and edited by the selector.
	 * @return temporal information selected by the selector.
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Instanciates or replaces the temporal information displayed and editer by the selector.
	 * This method assume that this information is  used by the graphic representation of the selector 
	 * (so by the Dojo widget). It's also this method which notifies the modification at the listeners. 
	 * @param date  temporal information that the selector has to display.
	 */
	public void setDate(Date date) {
		if (this.date == null && date != null || this.date != null
				&& date == null || this.date != null && !this.date.equals(date)) {
			this.date = date;
			if (isAttached()) {
				setDateOnPicker(DateUtil.getJSDate(date));
			}
			if (changeListeners != null) {
				changeListeners.fireChange(this);
			}
		}
	}

	/**
	 * Asks to DOJO to take care of a temporal information updated.
	 * @param date temporal information that the selector has to displayed.
	 */
		
	protected void setDateOnPicker(JavaScriptObject date) {
		setDojoDate(getDojoWidget(), date);
	}
	

	/**

	 * Cleans the given date from the DOJO widget. This method is necessary
	 * to chunk that it seems to be a bug from DOJO : the returned information 
	 * is construted from the current date/hour. So, this information contains   
     * "invisible" datas to the screen.( the current hour for a date, or the date of the day for an hour
     * while the edited information is an hour)  
	 * @param date temporal information returned by the DOJO widget.
	 * @return the date cleaned.
	 *  
	
	 */
	protected abstract Date adjust(Date date);

	/**
	 * Callback method used by DOJO in order that the GWT widget take count of the 
	 * selected value by the user.
	 * @param jsDate temporal information (not cleaned) returned by the Dojo widget.
	 */
	public void onValueChanged(JavaScriptObject jsDate) {
		Date theDate = adjust(DateUtil.getJavaDate(jsDate));
		setDate(theDate);

	}

}//end of class
