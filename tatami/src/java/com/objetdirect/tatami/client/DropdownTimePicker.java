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

	/**
	 * Creates  DropDownTimePicker component.
	 */
	public DropdownTimePicker() {
		super();
	}

	/**
	 * Returns the name of DOJO widget.
	 * @return "DropdownTimePicker"
	 */
	public String getDojoName() {
		return "DropdownTimePicker";
	}

	
	/**
	 * Initializes the callback for the DropDownTimePicker in order that the 
	 * GWT widget is synchrone with the DOJO widget.
	 * a redefinition of the initUI method from the DOJO widget was been necessary..  
	 * In the goal that the onValueChanged(Date) is thrown 3 times instead of once. 
	 * This DOJO "bug" is tracked {@link http://trac.dojotoolkit.org/ticket/2046}
	 * So, a callback is thrown each time the TimePicker is modify.
	 * @see #onTimePickerChange(JavaScriptObject)
	 * @param dojoWidget The DOJO widget
	 */
	protected native void setCallBackForDropDown(JavaScriptObject dojoWidget) /*-{
	 dojoWidget.timePicker.initUI = function() { 
	        
  	       // set UI to match the currently selected time
	       if(!dojoWidget.timePicker.selectedTime.anyTime && dojoWidget.timePicker.time) {
	          var amPmHour = $wnd.dojo.widget.TimePicker.util.toAmPmHour(dojoWidget.timePicker.time.getHours()); 
	          var hour = amPmHour[0]; 
	          var isAm = amPmHour[1]; 
	          var minute = dojoWidget.timePicker.time.getMinutes(); 
	          var minuteIndex = parseInt(minute/5); 

	         dojoWidget.timePicker.onClearSelectedAnyTime(); 
	         dojoWidget.timePicker.onClearSelectedHour(); 
	         dojoWidget.timePicker.setSelectedHour(dojoWidget.timePicker.hourIndexMap[hour]); 
	         dojoWidget.timePicker.onClearSelectedMinute();
	         dojoWidget.timePicker.setSelectedMinute(this.minuteIndexMap[minuteIndex]); 
             dojoWidget.timePicker.onClearSelectedAmPm();
	         dojoWidget.timePicker.setSelectedAmPm(isAm); 
	         dojoWidget.timePicker.selectedTime.anyTime = false;
	         dojoWidget.timePicker.onSetTime(); 
	       
	      } else {
	         dojoWidget.timePicker.onSetSelectedAnyTime(); 
	       }
	 };

  	   dojoWidget.timePicker.onValueChanged = function(date) {
	       dojoWidget._updateText();
	       dojoWidget.gwtWidget.@com.objetdirect.tatami.client.DropdownTimePicker::onTimePickerChange(Lcom/google/gwt/core/client/JavaScriptObject;)(date);
  	  	   
	 };
	 }-*/;

	
    /**
     * A callBack method called by the DOJO widget each times a date is modified.
     * The method updates the date for the GWT widget.
     * @param date the new date.
     */
	protected void onTimePickerChange(JavaScriptObject date) {
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
	 * FIXME : call of the method <code>setTime(date)</code> of the DOJO widget <code>DropDownTimePicker</code> 
	 * poses problem because the JavaScript object date given in parameter is not recognized as a Date instance. 
	 * The instruction <code>if(dateObj instanceof Date)</code> of the method <code>setTime(dateObj)</code>
	 * returns every time <code>false</code>. That is not normal because before the call we well have a date instance.
     * Still once a bug of passage type... Is it really a GWT problem of the 1.3 version ?
     * To avoid this bug, because for each call the current time was returned, we re-write  directly in JSNI the JavaScript code  
	 * Pour eviter ce probleme facheux, car a chaque appel de setTime l'heure courante était renvoyée, on réécrit directement dans 
	 * which modify the time to selected of the DOJO du DropDownTimePicjer.
	 * @param dojoWidget the Dojo widgetDropDownTimePicker
	 * @param date the new date to select
	 */
	private native void setDojoTime(JavaScriptObject dojoWidget,JavaScriptObject date) /*-{
	  	 
	  	 var val = "";
	     if (date instanceof Date) {
        	val = date;
	     } else {
         	if (dojoWidget.value) {
		   	   var orig = this.value;
  			   var d = $wnd.dojo.date.format(new Date(), {selector:"dateOnly", datePattern:"yyyy-MM-dd"});
			   var c = orig.split(":");
			   for (var i = 0; i < c.length; ++i) {
				   if (c[i].length == 1) {
					  c[i] = "0" + c[i];
				   }
			   }
			   orig = c.join(":");
  			   val = $wnddojo.date.fromRfc3339(d + "T" + orig);
            } 
	     }
	     dojoWidget.timePicker.setTime(val);
         dojoWidget._syncValueNode();

 
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
	 * Asks to DOJO to update the temporal information edited by the selector.
	 * This method calls the <code>setDojoTime(JavaScriptObject, JavaScriptObject)</code>. 
	 * @param date the new date to edit 
	 */
	protected void setDateOnContainer(JavaScriptObject date) {
		setDojoTime(getDojoWidget(), date);
		
	}

	
}//end of  class
