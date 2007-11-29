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
package com.objetdirect.tatami.demo.client;

import java.util.Date;

import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.BasePicker;
import com.objetdirect.tatami.client.Clock;
import com.objetdirect.tatami.client.DatePicker;
import com.objetdirect.tatami.client.DropdownContainer;
import com.objetdirect.tatami.client.DropdownDatePicker;
import com.objetdirect.tatami.client.DropdownTimePicker;
import com.objetdirect.tatami.client.TimePicker;
import com.objetdirect.tatami.client.TimePickerConstraints;

/**
 * Demo page for date time component classes. 
 * DatePicker, TimePicker, DropdownTimePicker, DropdownDatePicker and clock 
 * are presented. 
 * 
 */
public class DateTimeDemo extends Composite {

  private DatePicker datePicker;
  private TimePicker timePicker;
  private DropdownDatePicker inputDate;
  private DropdownTimePicker inputTime;
  private HorizontalPanel mainPanel ;
	
	
  public DateTimeDemo() {
	  initComponents();
	  inputDate.setDate(new Date());
	 
	  initWidget(mainPanel);
	  
  }

  private void initComponents() {
	  mainPanel = new HorizontalPanel();  
	  mainPanel.setSpacing(50);
	  VerticalPanel timePanel = new VerticalPanel();
	  VerticalPanel datePanel = new VerticalPanel();
	  datePanel.setSpacing(20);
	  timePanel.setSpacing(20);
	  
	  HTML htmlInputDate = new HTML("<b>DropdownDatePicker</b> : <br>To Help a user to write a well formed Date with a calendar.");
	  HTML htmlDatePicker= new HTML("<b>DatePicker</b> : <br>   A Calendar object to help user to choose a date and work with this date.");
	  HTML htmlInputTime = new HTML("<b>DropdownTimePicker</b> : <br>To Help a user to write a well formed time with a pciker object.");
	  HTML htmlTimePicker= new HTML("<b>TimePicker</b> : <br>   A Picker object to help user to choose a time and work with this time.");
	
	  
	  
	  inputDate = new DropdownDatePicker("inputDate");
	  inputDate.setInvalidMessage("the date is incorrect");

	  datePicker = new DatePicker();
	  datePicker.setDate(new Date());
	  datePanel.add(htmlInputDate);
	  datePanel.add(inputDate);
	  datePanel.add(htmlDatePicker);
	  datePanel.add(datePicker);
	  datePanel.add(new HTML("If you modify the <b>DatePicker</b>, the <b>DropdowDatePicker</b> will be modified too and vice-versa"));
	  
	  linkDropdownAndPicker(inputDate, datePicker);
	  
	  
	  inputTime = new DropdownTimePicker("inputTime");
	  inputTime.setPromptMessage("HH:mm");
		
	  TimePickerConstraints constraints= new TimePickerConstraints();
	  constraints.clickableIncrement = TimePickerConstraints.EVERY_HALF_HOUR;

	  timePicker = new TimePicker(constraints);
	 
	  timePanel.add(htmlInputTime);
	  timePanel.add(inputTime);
	  timePanel.add(htmlTimePicker);
	  timePanel.add(timePicker);
	  timePanel.add(new HTML("If you modify the <b>TimePicker</b>, the <b>DropdowTimePicker</b> will be modified too and vice-versa"));
	  this.linkDropdownAndPicker(inputTime, timePicker);
	  
	  mainPanel.add(datePanel);
	  mainPanel.add(timePanel);
	  Clock clock = new Clock("clock_face.jpg",385);
	  
	  mainPanel.add(clock);

  }
  
  
  
  /**
	 * Creates a link between a <code>DropdownContainer</code> and 
	 * <code>BasePicker</code> with a <code>ChangeListener</code>. 
	 * So if the container is modify, it will update (if it's necessary) the picker and  
	 * si le container est modifié, il mettra à jour si nécessaire le picker et
	 * vice versa.
	 * @param container DropdownContainer to link
	 * @param picker BasePicker to link
	 */
	private void linkDropdownAndPicker(final DropdownContainer container,
			final BasePicker picker) {
		container.addChangeListener(new ChangeListener() {
			public void onChange(Widget sender) {
				if (!equalsObj(picker.getDate(), container.getDate())) {
					picker.setDate(container.getDate());
				}

			}
		});

		picker.addChangeListener(new ChangeListener() {
			public void onChange(Widget sender) {
				
				if (!equalsObj(container.getDate(), picker.getDate())) {
					
					container.setDate(picker.getDate());

				}
			}
		});

	}
	
	/**
	 * 
	 * @param o1
	 * @param o2
	 * @return
	 */
	private boolean equalsObj(Object o1, Object o2) {
		if (o1 == null && o2 == null) {
			return true;
		} else if (o1 == null || o2 == null) {
			return false;
		} else {
			return o1.equals(o2);
		}
	}

}//end of class
