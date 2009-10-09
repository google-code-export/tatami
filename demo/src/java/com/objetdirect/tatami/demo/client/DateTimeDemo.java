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
package com.objetdirect.tatami.demo.client;

import java.util.Date;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.objetdirect.tatami.client.BasePicker;
import com.objetdirect.tatami.client.Clock;
import com.objetdirect.tatami.client.DatePicker;
import com.objetdirect.tatami.client.DropdownContainer;
import com.objetdirect.tatami.client.DropdownDatePicker;
import com.objetdirect.tatami.client.DropdownTimePicker;
import com.objetdirect.tatami.client.TimePicker;
import com.objetdirect.tatami.client.TimePickerConstraints;
import com.objetdirect.tatamix.client.hmvc.CompositeView;
import com.objetdirect.tatamix.client.widget.Paragraph;
import com.objetdirect.tatamix.client.widget.Title;

/**
 * Demo page for date time component classes.
 * DatePicker, TimePicker, DropdownTimePicker, DropdownDatePicker and clock
 * are presented.
 *
 */
public class DateTimeDemo extends CompositeView {

  private DatePicker datePicker;
  private TimePicker timePicker;
  private DropdownDatePicker inputDate;
  private DropdownTimePicker inputTime;

  private FlowPanel layout ;



  /**
   * Creates the DateTimeDemo

   */
  public DateTimeDemo() {
	  layout = new FlowPanel();
      initWidget(layout);
      setStylePrimaryName("block");
	  initComponents();
	  inputDate.setDate(new Date());


  }


  /**
   * Inits the components
   * Creates panel with <code>DropdownContainer</code>,
   * <code>BasePicker</code> and <code>Clock</code>
   *
   */
  private void initComponents() {
	  FlowPanel timePanel = new FlowPanel();
	  timePanel.setStylePrimaryName("demi");
	  FlowPanel datePanel = new FlowPanel();
	  datePanel.setStylePrimaryName("demi");

      Title inputDateTitle = new Title(Title.H3);
      inputDateTitle.setText("DropdownDatePicker");
      Paragraph htmlInputDate = new Paragraph();
	  htmlInputDate.setHTML(TatamiDemo.getMessages().date_inputDate());

	  Title datePickerTitle = new Title(Title.H3);
	  datePickerTitle.setText("DatePicker");
	  Paragraph htmlDatePicker = new Paragraph();
	  htmlDatePicker.setHTML(TatamiDemo.getMessages().date_datePicker());

	  Title inputTimeTitle = new Title(Title.H3);
	  inputTimeTitle.setText("DropdownTimePicker");
	  Paragraph htmlInputTime = new Paragraph();
	  htmlInputTime.setHTML(TatamiDemo.getMessages().date_inputTime());


	  Title timePickerTitle = new Title(Title.H3);
	  timePickerTitle.setText("TimePicker");
	  Paragraph htmlTimePicker= new Paragraph();
	  htmlTimePicker.setHTML(TatamiDemo.getMessages().date_timePicker());



	  inputDate = new DropdownDatePicker();
	  
	  inputDate.setInvalidMessage(TatamiDemo.getMessages().date_invalid());

	  datePicker = new DatePicker();
	  datePicker.setDate(new Date());


	  inputTime = new DropdownTimePicker();
	  inputTime.setPromptMessage("HH:mm");

	  TimePickerConstraints constraints= new TimePickerConstraints();
	  constraints.clickableIncrement = TimePickerConstraints.EVERY_HALF_HOUR;

	  timePicker = new TimePicker(constraints);



      datePanel.add(inputDateTitle);
	  datePanel.add(htmlInputDate);
	  datePanel.add(inputDate);
	  datePanel.add(datePickerTitle);
	  datePanel.add(htmlDatePicker);
	  datePanel.add(datePicker);

	  Paragraph modifyDateExplain = new Paragraph();
	  modifyDateExplain.setHTML(TatamiDemo.getMessages().date_modify_date());
	  datePanel.add(modifyDateExplain);
	  linkDropdownAndPicker(inputDate, datePicker);


	  timePanel.add(inputTimeTitle);
	  timePanel.add(htmlInputTime);
	  timePanel.add(inputTime);
	  timePanel.add(timePickerTitle);
	  timePanel.add(htmlTimePicker);
	  timePanel.add(timePicker);
	  Paragraph modifyTimeExplain = new Paragraph();
	  modifyTimeExplain.setHTML(TatamiDemo.getMessages().date_modify_time());
	  timePanel.add(modifyTimeExplain);

	  this.linkDropdownAndPicker(inputTime, timePicker);

	  linkDropdown(inputDate,inputTime);

	  layout.add(datePanel);
	  layout.add(timePanel);

	  FlowPanel clockPanel = new FlowPanel();
	  clockPanel.setStylePrimaryName("clockPanel");
	  Title title = new Title(Title.H3);
	  title.setText(TatamiDemo.getMessages().date_clock());
	  clockPanel.add(title);
	  Clock clock = new Clock(TatamiDemo.getIconURL("clock_face.jpg"),385);
	  clockPanel.add(clock);

	  layout.add(clockPanel);
	  
	  

  }



  /**
	 * Creates a link between a <code>DropdownContainer</code> and
	 * <code>BasePicker</code> with a <code>ChangeListener</code>.
	 * So if the container is modify, it will update (if it's necessary) 
	 * the picker 	 * and  si le container est modifié, il mettra 
	 * à jour si nécessaire le picker et vice versa.
	 * @param container DropdownContainer to link
	 * @param picker BasePicker to link
	 */
	private void linkDropdownAndPicker(final DropdownContainer container,
			final BasePicker picker) {
		container.addValueChangeHandler(new ValueChangeHandler<Date>() {
			public void onValueChange(ValueChangeEvent<Date> event) {
				if (!equalsObj(picker.getDate(), container.getDate())) {
					picker.setDate(container.getDate());
				}

			}
		});

		picker.addValueChangeHandler(new ValueChangeHandler<Date>() {
			public void onValueChange(ValueChangeEvent<Date> event) {

				if (!equalsObj(container.getDate(), picker.getDate())) {

					container.setDate(picker.getDate());
					//container.selectAll();

				}
			}
		});

	}



	private void linkDropdown(final DropdownContainer container1,
	    final DropdownContainer container2) {
		container1.addValueChangeHandler(new ValueChangeHandler<Date>() {
			public void onValueChange(ValueChangeEvent<Date> event) {
				if (!equalsObj(container2.getDate(), container1.getDate())) {

					container2.setDate(container1.getDate());
				}

			}
		});

		container2.addValueChangeHandler(new ValueChangeHandler<Date>() {
			public void onValueChange(ValueChangeEvent<Date> event) {

				if (container1.getDate() != null && !equalsObj(container1.getDate(), container2.getDate())) {
					Date newDate = new Date(container1.getDate().getTime());
					newDate.setHours(container2.getDate().getHours());
					newDate.setMinutes(container2.getDate().getMinutes());
					newDate.setSeconds(container2.getDate().getSeconds());
					//Window.alert("the new Date " + newDate );
					//container1.setDate(container2.getDate());

				}
			}
		});

	}



	/**
	 * Checks if two object are equals.
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
