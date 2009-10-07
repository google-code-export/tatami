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
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ChangeListenerCollection;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;

/**
 * Basic class of the widgets those associate an input and a popup window to
 * display or edit a temporal information. So, is the baisc class of the
 * components :
 * <ul>
 * <li>DropdownDatePicker</li>
 * <li>DropdownTimePicker</li>
 * </ul>
 * <p>
 * This component is an encapsulation GWT of Dojo components. Warning the widget
 * exist under 3 faces :
 * <ul>
 * <li>a GWT widget (this!)</li>
 * <li>a Dojo widget (which play the role of 'this' for GWT)</li>
 * <li>a structur of DOM elements, the only exploitable presentation by the
 * navigator.</li>
 * </ul>
 * </p>
 * 
 * @author Henry,Vianney
 */
public abstract class DropdownContainer extends AbstractDojoFocus implements
		HasText, HasValue<Date> {

	/** the min date available in the selector */
	private Date min;
	/** the max date available in the selector */
	private Date max;

	private String promptMessage = null;

	private String invalidMessage = null;

	/** The date to display and edit */
	private Date date;

	/**
	 * The validation result
	 */
	private boolean isValid = true;
	/**
	 * The Text representing the date
	 */
	private String text = "";

	/**
	 * collection of changeListener
	 * 
	 * @deprecated
	 * */
	private ChangeListenerCollection changeListeners;

	/**
	 * Creates a editor of temporal information.
	 * 
	 * @param element
	 *            <code>Element</code> The DOM element for the GWT widget the
	 *            DOJO widget will be adde to this element
	 * @param startDate
	 *            the first date that we can select in the calendar.
	 * @param endDate
	 *            the last date that we can select in the calendar.
	 */
	protected DropdownContainer(Element element, Date startDate, Date endDate) {
		super(element);

		this.max = endDate;
		this.min = startDate;

	}

	/**
	 * Creates an editor of temporal information. The GWT widget will have a
	 * 'DIV' for the DOM element.
	 * 
	 * @param startDate
	 *            the first date that we can select in the calendar. can be
	 *            <code>null</code>
	 * @param endDate
	 *            the last date that we can select in the calendar.
	 *            <code>null</code>
	 */
	protected DropdownContainer(Date startDate, Date endDate) {
		this(Document.get().createDivElement(), startDate, endDate);
	}

	/**
	 * Returns the prompt message to display
	 * 
	 * @return the prompt message to display
	 */
	public String getPromptMessage() {

		if (isAttached()) {
			promptMessage = getPromptMessage(getDojoWidget());
		}
		return this.promptMessage;
	}

	/**
	 * Returns the message to display when a value is invalid
	 * 
	 * @return the message to display when a value is invalid
	 */
	public String getInvalidMessage() {
		if (isAttached()) {
			invalidMessage = getInvalidMessage(getDojoWidget());
		}
		return this.invalidMessage;
	}

	/**
	 * Check is the input is valid or not.
	 * 
	 * @return true is it is, false otherwise
	 */
	public boolean isValid() {
		if (isAttached()) {
			this.isValid = isValid(getDojoWidget());
		}
		return this.isValid;
	}

	/**
	 * Check is the input is valid or not.
	 * 
	 * @param dojoWidget
	 *            the DOJO widget
	 * @return true is it is, false otherwise
	 */
	private native boolean isValid(JavaScriptObject dojoWidget)/*-{
																return dojoWidget.isValid();
																}-*/;

	/**
	 * Returns the fisrt date selectable in the calendar
	 * 
	 * @return a Date or <code>null</code> if not was set
	 */
	public Date getMinDate() {
		return this.min;
	}

	/**
	 * Returns the last date selectable in the calendar
	 * 
	 * @return a Date or <code>null</code> if not was set
	 */
	public Date getMaxDate() {
		return this.max;
	}

	/**
	 * Returns the fisrt date selectable in the calendar
	 * 
	 * @return the javascript Date or <code>null</code> if not was set
	 */
	protected JavaScriptObject getMinJavaScriptDate() {
		return min == null ? null : DateUtil.getJSDate(min);
	}

	/**
	 * Returns the last date selectable in the calendar
	 * 
	 * @return the javascript Date or <code>null</code> if not was set
	 */

	protected JavaScriptObject getMaxJavaScriptDate() {
		return max == null ? null : DateUtil.getJSDate(max);

	}

	/**
	 * Modifies the current date, without modify it in DOJO widget.This method
	 * is to use when the DOJO widget notify a change value.It's not necessary
	 * to change the value again. of the DOJO widget.
	 * 
	 * @param date
	 *            the new date
	 * @see #setDate(Date);
	 */
	protected void setInternDate(Date date) {
		if (isNotSameDate(date)) {
			this.date = date;
			//notifyDateChanged(true);
		}
	}

	protected void notifyDateChanged(boolean fire) {
		if (fire) {
			ValueChangeEvent.fire(this, this.date);
		}
		if (changeListeners != null) {
			changeListeners.fireChange(this);
		}
	}
	
	
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Date> handler) {
		return addHandler(handler,ValueChangeEvent.getType());
	}

	/**
	 * registers callbacks in order that all the events are listened "onclick",
	 * "onfocus","onblur","onkeydown","onkeyup" ,"onkeypress" to write the
	 * temporal information in the input zone. Arms also the callback for the
	 * possible changing states possible for an implementation of the
	 * DropDownContainer.
	 */
	public void doAfterCreation() {
		DojoController.startup(this);
		setBrowserEventCallback(getDojoWidget());
		setCallBackForDropDown(getDojoWidget());
		// Resets the date and the enable option for the widget.
		// this code have to be done after that the GWT widget was attached
		if (date != null) {
			setDateOnContainer(DateUtil.getJSDate(date));
		}
		setEnabled(isEnabled());
		setInvalidMessage(invalidMessage);
		setPromptMessage(promptMessage);
		if (text != null && !"".equals(text)) {
			setText(text);
		}
	}

	/**
	 * This method arms some callback
	 * 
	 * @param dojoWidget
	 *            the DOJO widget.
	 */
	private native void setBrowserEventCallback(JavaScriptObject dojoWidget)
	/*-{
	    dojoWidget.textbox.onbrowserevent = function(e) {
	    dojoWidget.gwtWidget.@com.objetdirect.tatami.client.DropdownContainer::onBrowserEvent(Lcom/google/gwt/user/client/Event;)(e);
	 };
	 }-*/;

	/**
	 * Returns the content of input zone.
	 * 
	 * @return a text representing the temporal information. The format of the
	 *         text depends of the information
	 */
	public String getText() {
		if (isAttached()) {
			text = getDojoText(getDojoWidget());
		}
		return text;
	}

	/**
	 * Sets the temporal information to edit from a formated text. the format
	 * depends of the temporal inforamtion example :
	 * <code>18/09/2007 for a date in FR local</code>
	 * 
	 * @param text
	 *            the new date to edit
	 */
	public void setText(String text) {
		this.text = text;
		if (isAttached()) {
			setDojoText(getDojoWidget(), text);
		}

	}

	/**
	 * Returns the content text of the input zone of DOJO widget
	 * 
	 * @param dojoWidget
	 *            the DOJO widget
	 * @return the temporal information under a string representation.
	 */
	private native String getDojoText(JavaScriptObject dojoWidget) /*-{
																	return dojoWidget.getDisplayedValue();
																	}-*/;

	/**
	 * Gives to the DOJO widget the temporal information to edit under a string
	 * formatted
	 * 
	 * @param rootWidget
	 *            widget 
	 * @param text
	 *            date 
	 */
	private native void setDojoText(JavaScriptObject dojoWidget, String text) /*-{
																				dojoWidget.setDisplayedValue(text);
																				dojoWidget._onBlur();
																				}-*/;

	/**
	 * Disables or enables the widget.
	 * 
	 * @param enabled
	 *            true to enable the widget false otherwise.
	 */
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		if (isAttached()) {
			setDojoEnabled(getDojoWidget(), isEnabled());
		}
	}

	/**
	 * Enables or Disables the DOJO widget.
	 * 
	 * @param dojoWidget
	 *            the DOJO widget
	 * @param enabled
	 *            true to enable the widget false otherwise.
	 */
	private native void setDojoEnabled(JavaScriptObject dojoWidget,
			boolean enabled)
	/*-{
	   dojoWidget.setDisabled(!enabled);
	 }-*/;

	/**
	 * Indicates to DOJO that an event have to be
	 * 
	 * @param eventname
	 *            the name of the event to redirect to GWT
	 * @param inputNode
	 *            the element DOM emetteur
	 */
	protected native void triggerEvent(String eventname, Element inputNode)
	/*-{
	     $wnd.dojo.connect(inputNode, eventname, inputNode, "onbrowserevent");
	 }-*/;

	/**
	 * Returns the current edited date.
	 * 
	 * @return a date
	 */
	public Date getDate() {
		return date;
	}

	
	public Date getValue() {
		return this.date;
	}
	
	
	protected boolean isNotSameDate(Date date) {
		return this.date == null && date != null || this.date != null && date == null || this.date != null && !this.date.equals(date);
	}

	public void setValue(Date date, boolean fire) {
		setDate(date,fire);
	}

	public void setValue(Date date) {
		setDate(date);
	}

	

	/**
	 * Sets the date to edit and notifies a changing value if it's a new date.
	 *  @param date  the new date
	 */
	public void setDate(Date date) {
		if (isNotSameDate(date)) {
			this.date = date;
			if (isAttached()) {
				//this method change the value of the Dojo Widget, so 
				//we don't need to notify that value changed after. 
				setDateOnContainer(DateUtil.getJSDate(date));
			} else {
				notifyDateChanged(true);
			}
     	}
	}
	
	
	/**
	 * Sets the date to edit and notifies a changing value if it's a new date.
	 * 
	 * @param date
	 *            the new date
	 */
	public void setDate(Date date,boolean fire) {
		if (isNotSameDate(date)) {
			this.date = date;
			if (isAttached()) {
				setDateOnContainer(DateUtil.getJSDate(date));
			} else {
				notifyDateChanged(fire);
			}
     	}
	}

	/**
	 * Adjusts a date if it's necessary.
	 * 
	 * @param date
	 *            the date to clean
	 * @return the date modify.
	 */
	protected abstract Date adjust(Date date);

	/**
	 * Sets the date to edit for the DOJO widget.
	 * 
	 * @param date
	 *            the date in JavaScript object
	 */
	protected void setDateOnContainer(JavaScriptObject date) {
		setDojoDate(getDojoWidget(), date);
	}

	/**
	 * Modifies the selected date on the DOJO widget
	 * 
	 * @param dojoWidget
	 *            the DOJO widget to modify
	 * @param date
	 *            the new date to edit.
	 */
	private native void setDojoDate(JavaScriptObject dojoWidget,JavaScriptObject date)
	/*-{
	    dojoWidget.setValue(date);
	    
	 }-*/;

	/**
	 * Returns the text element of the widget
	 * 
	 * @return the DOM element of the input
	 */
	protected Element getTextElement() {
		return getTextElement(getDojoWidget());
	}

	/**
	 * 
	 * @param dojoWidget
	 * @return
	 */
	private native Element getTextElement(JavaScriptObject dojoWidget)
	/*-{
	     return dojoWidget.domNode;
	 }-*/;

	/**
	 * Sets the message to display if value is invalid.
	 * 
	 * @param msg
	 *            the message
	 */
	public void setInvalidMessage(String msg) {
		this.invalidMessage = msg;
		if (isAttached() && invalidMessage != null) {
			setMessage(invalidMessage, getDojoWidget());
		}
	}

	/**
	 * Sets the prompt message to display.
	 * 
	 * @param msg
	 *            the message
	 */
	public void setPromptMessage(String msg) {
		this.promptMessage = msg;
		if (isAttached() && promptMessage != null) {
			setPromptMessage(msg, getDojoWidget());
		}

	}

	/**
	 * Arms callback for the changing value of the edited date. The DOJO widget
	 * calls the <code>onValueChanged</code> of the GWT widget when the edited
	 * date is changed.
	 * 
	 * @param dojoWidget
	 *            the DOJO widget.
	 */
	protected native void setCallBackForDropDown(JavaScriptObject dojoWidget) /*-{
	    dojoWidget.onChange = function(date) {
		  if ( date instanceof Date) {
			 dojoWidget.gwtWidget.@com.objetdirect.tatami.client.DropdownContainer::onValueChanged(Lcom/google/gwt/core/client/JavaScriptObject;)(date);
		} else {
		  if ( dojoWidget.getValue() != null) {
			dojoWidget.gwtWidget.@com.objetdirect.tatami.client.DropdownContainer::onValueChanged(Lcom/google/gwt/core/client/JavaScriptObject;)(dojoWidget.getValue());
		  }
         }
	};
   }-*/;

	/**
	 * Sets the prompt message to display.
	 * 
	 * @param msg
	 *            the message to display
	 * @param dojoWidget
	 *            the DOJO widget
	 */
	private native void setPromptMessage(String msg, JavaScriptObject dojoWidget) /*-{
																					dojoWidget.promptMessage = msg;
																					
																					}-*/;

	private native String getPromptMessage(JavaScriptObject dojoWidget) /*-{
																		return dojoWidget.promptMessage;
																		}-*/;

	private native String getInvalidMessage(JavaScriptObject dojoWidget) /*-{
																			return dojoWidget.invalidMessage;
																			}-*/;

	/**
	 * Sets the message to display if value is invalid.
	 * 
	 * @param msg
	 *            the message to display
	 * @param dojoWidget
	 *            the DOJO widget
	 */
	private native void setMessage(String msg, JavaScriptObject dojoWidget) /*-{
																			dojoWidget.invalidMessage = msg;
																			
																			}-*/;

	protected abstract void onValueChanged(JavaScriptObject date);

	/**
	 * Adds a listener interface to receive change values events.
	 * 
	 * @param listener
	 *            the listener interface to add
	 * @deprecated
	 */
	public void addChangeListener(ChangeListener listener) {
		if (changeListeners == null) {
			changeListeners = new ChangeListenerCollection();
		}
		changeListeners.add(listener);
	}

	/**
	 * Removes a previously added listener interface.
	 * 
	 * @param listener
	 *            the listener interface to remove
	 * @deprecated
	 */
	public void removeChangeListener(ChangeListener listener) {
		if (changeListeners != null) {
			changeListeners.remove(listener);
		}
	}

}// end of class
