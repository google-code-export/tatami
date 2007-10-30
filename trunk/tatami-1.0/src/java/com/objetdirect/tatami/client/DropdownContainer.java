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
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ChangeListenerCollection;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * Basic class of the widgets those associate an input and a popup window 
 * to display or edit a temporal information. So, is the baisc class of the components : 
 * <ul>
 * <li>DropdownDatePicker</li>
 * <li>DropdownTimePicker</li>
 * </ul>  
 * <p>
 * This component is an encapsulation GWT of Dojo components. Warning the widget exist under 3 faces : 
 * <ul>
 * <li>a GWT widget (this!)</li>
 * <li>a Dojo widget (which play the role of 'this' for GWT)</li>
 * <li>a structur of DOM elements, the only exploitable presentation by the navigator.</li>
 * </ul>
 * </p>
 * @author Henry,Vianney
 */
public abstract class DropdownContainer extends TextBox implements HasDojo {

	/**
	 * The DOJO widget
	 */
	protected JavaScriptObject dojoWidget;

	/** The date to display and edit  */
	private Date date;

	/**
	 * The Text representing the date
	 */
	private String text = "";

	/** collection of changeListener  */
	private ChangeListenerCollection changeListeners;

	/**
	 * Creates a editor of temporal information.
	 * @param element  <code>Element</code> The DOM element for the GWT widget
	 * the DOJO widget will be adde to this element
	 */
	protected DropdownContainer(Element element) {
		super();
		setElement(element);
		DojoController.getInstance().loadDojoWidget(this);

	}

	/**
	 * Creates an editor of temporal information. The GWT widget 
	 * will have a 'DIV' for the DOM element.
	 * 
	 */
	protected DropdownContainer() {
		this(DOM.createDiv());
	}

	/**
	 * Returns the Dojo widget integrated in the GWT widget. 
	 * @return the JavaScript object modelling the DOJO widget
	 */
	public JavaScriptObject getDojoWidget() {
		return this.dojoWidget;
	}

	/** 
	 * Frees the resources used by the dojo widget in the widget GWT. 
	 * Don't call the method directly let the <code>DojoController</code> calling it.
	 * 
	 */
	public void free() {
		this.dojoWidget = null;
	}

	/**
	 * When the GWT widget is detached from the browser, the link between it and the Dojo widget is drawn. 
	 * The Dojo widget is also destroy. The DOM element fulfilling the Dojo widget is also destroy. 
	 * @see DojoController#destroyDojoWidget(HasDojo, com.google.gwt.user.client.Element)
	 * @see #doBeforeDestruction() 
	 */
	protected void onDetach() {
		DojoController.getInstance().destroyDojoWidget(this, this);
		super.onDetach();

	}

	/** Does nothing,ovveride this method if you it's necessary for the widget*/
	public void doBeforeDestruction() {

	}

	/**
	 * Modifies the current date, without modify it in DOJO widget.This method
	 * is to use when the DOJO widget notify a change value.It's not necessary to change the value again. 
	 * of the DOJO widget.     
	 * @param date the new date 
	 * @see #setDate(Date);
	 */
	protected void setInternDate(Date date) {
		if (this.date == null && date != null || this.date != null
				&& date == null || this.date != null && !this.date.equals(date)) {

			this.date = date;
			if (changeListeners != null) {
				changeListeners.fireChange(this);
			}
		}
	}

	/**
	 * registers callbacks in order that all the events are listened "onclick",
	 * "onfocus","onblur","onkeydown","onkeyup" ,"onkeypress" to write the temporal information
	 * in the input zone.
	 * Arms also the callback for the possible changing states possible for an implementation of the DropDownContainer.
	 */
	public void doAfterCreation() {
		triggerEvent("onclick", getTextElement());
		triggerEvent("onfocus", getTextElement());
		triggerEvent("onblur", getTextElement());
		triggerEvent("onkeydown", getTextElement());
		triggerEvent("onkeyup", getTextElement());
		triggerEvent("onkeypress", getTextElement());
		setBrowserEventCallback(getDojoWidget());
		setCallBackForDropDown(getDojoWidget());
		
		

	}

	/**
	 * Updates the DOJO widget when the GWT widget is attached on the browser 
	 */
	protected void onLoad() {
		super.onLoad();
		if (date != null) {
			setDateOnContainer(DateUtil.getJSDate(date));
		}
		setEnabled(isEnabled());

	}

	/** Does nothing,ovveride this method if you it's necessary for the widget*/
	public void onDojoLoad() {

	}
	
	/**
	 * This method arms some callback
	 * @param dojoWidget the DOJO widget.
	 */
	private native void setBrowserEventCallback(JavaScriptObject dojoWidget)
	/*-{
	    dojoWidget.inputNode.onbrowserevent = function(e) {
	    dojoWidget.gwtWidget.@com.objetdirect.tatami.client.DropdownContainer::onBrowserEvent(Lcom/google/gwt/user/client/Event;)(e);
	 };
	 }-*/;
	

	/**
	 * When the widget GWT is attached on the browser, the Dojo widget is created and link with the GWT widget.
	 * If the creation of the Dojo widget fails then an alert will be display on the screen (problem under IE)  
	 * A log is also written. This methode can be overrided to add some other dependencies between the dojo widget to create and the GWT widget. 
	 * @see Widget#onLoad() 
	 * @see #doAfterCreation()
	 */
	protected void onAttach() {
		DojoController.getInstance().constructDojoWidget(this, this);
		super.onAttach();
	}

	/**
	 * Returns the content of input zone.
	 * @return a text representing the temporal information.
	 *         The format of the text depends of the information
	 */
	public String getText() {
		if (isAttached()) {
			text = getDojoText(getDojoWidget());
		}
		return text;
	}

	/**
	 * Sets the temporal information to edit from a formated text.
	 * the format depends of the temporal inforamtion example : <code>18/09/2007 for a date in FR local</code>
	 * @param text the new date to edit
	 */
	public void setText(String text) {
		this.text = text;
        
		if (isAttached()) {
			setDojoText(getDojoWidget(), text);
		}

	}

	/**
	 * Returns the content text of the input zone of DOJO widget
	 * @param dojoWidget the DOJO widget
	 * @return the temporal information under a string representation.
	 */
	private native String getDojoText(JavaScriptObject dojoWidget) /*-{
	     return dojoWidget.getValue();
	 }-*/;

	/**
	 * Gives to the DOJO widget the temporal information to edit under a string formatted
	 * A bug was detected : The call of the native method  <code>dojowidget.setValue(text)</code> didn't change 
	 * the text in the input zone of the component(UI) and didn't take in count 
	 * a value change of the time (date or hour/minute). To fix this bug, we force the value change of the input node
	 * of the DOM object representing the input zone and we specify that the input zone was well modified.  

	 * @param rootWidget widget DOJO matérialisant l'éditeur d'information temporelle
	 * @param text date à éditer
	 */
	private native void setDojoText(JavaScriptObject dojoWidget, String text) /*-{
	     dojoWidget.inputNode.value = text;
         dojoWidget.onInputChange();
         
	 
	 }-*/;
	
	/**
	 * Creates the DOJO widget which the type is given by the method {@link getDojoName()}
	 */
	public void createDojoWidget() {
		this.dojoWidget = DojoController.getInstance().createSimple(getDojoName());
	}
	

	/**
	 * Disables or enables the widget.
	 * @param enabled true to enable the widget false otherwise.
	 */
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		if (isAttached()) {
			setDojoEnabled(getDojoWidget(), isEnabled());
		}
	}

	/**
	 * Enables or Disables the DOJO widget.
	 * @param dojoWidget the DOJO widget
	 * @param enabled true to enable the widget false otherwise.
	 */
	private native void setDojoEnabled(JavaScriptObject dojoWidget,
			boolean enabled)
	/*-{
	 if (enabled) {
	 dojoWidget.enable();
	 } else {
	 dojoWidget.disable();
	 }
	 }-*/;

	/**
	 * This method is overrided and does nothint to avoir some surprises
	 * 
	 */
	public void sinkEvents(int eventBitsToAdd) {
	
	}

	/**
	 * Indicates to DOJO that an event have to be  
	 * Indique à DOJO qu'un événement Javascript reçu doit être traitée par la
	 * méthode (standard GWT) onBrowserEvent de la widget GWT.
	 * 
	 * @param eventname the name of the event to redirect to GWT
	 * @param inputNode the element DOM emetteur 
	 */
	protected native void triggerEvent(String eventname, Element inputNode)
	/*-{
	 $wnd.dojo.event.connect(inputNode, eventname, inputNode, "onbrowserevent");
	 }-*/;

	/**
	 * A CallBack for an implementation of this DropDownContainer.
	 * @param dojoWidget the DOJO widget
	 */
	protected abstract void setCallBackForDropDown(JavaScriptObject dojoWidget); 
	
	

	/**
	 * Manages the event on the browser.
	 * @param e  event notifies by the browser. 
	 */
	public void onBrowserEvent(Event e) {
		super.onBrowserEvent(e);
	}

	
	/**
	 * Returns the current edited date.
	 * @return a date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Sets the date to edit and notifies a changing value if it's a new date.
	 * @param date the new date
	 */
	public void setDate(Date date) {

		if (this.date == null && date != null || this.date != null
				&& date == null || this.date != null && !this.date.equals(date)) {

			this.date = date;
			if (isAttached()) {
				setDateOnContainer(DateUtil.getJSDate(date));

			}
			if (changeListeners != null) {
				changeListeners.fireChange(this);
			}

		}
	}

	
	
	/**
	 * Adjusts a date if it's necessary. 
	 * @param date the date to clean
	 * @return the date modify.
	 */
	protected abstract Date adjust(Date date);


	/**
	 * Sets the date to edit for the DOJO widget. 
	 * @param date the date in JavaScript object
	 */
	protected abstract void setDateOnContainer(JavaScriptObject date);

	/**
	 * Returns the text element of the widget
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
	 return dojoWidget.inputNode;
	 }-*/;

	

	/**
	 * Adds a listener interface to receive change values events.
	 * @param listener the listener interface to add
	 */
	public void addChangeListener(ChangeListener listener) {
		if (changeListeners == null) {
			changeListeners = new ChangeListenerCollection();
		}
		changeListeners.add(listener);
	}

	/**
	 * Removes a previously added listener interface.
	 * @param listener the listener interface to remove
	 */
	public void removeChangeListener(ChangeListener listener) {
		if (changeListeners != null) {
			changeListeners.remove(listener);
		}
	}

}//end of class 
