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
 * Authors: Vianney Grassaud
 * Initial developer(s):
 * Contributor(s):
 */
package com.objetdirect.tatami.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * This abstract class aims to simplify the integration of a Dojo widget in 
 * GWT <code>ComplexPane</code>. The class implements the interface 
 * <code>HasDojo</code> permitting to use the <code>DojoController</code>. As 
 * a ComplexPanel is associated to a container element, it will have to be 
 * associated to the container element of the Dojo widget using the method
 * <code>setDojoContent()</code>.This method is called when the widget is attached to the browser.
 * 
 * @author Vianney
 * 
 */
public abstract class AbstractDojoComplexPane extends ComplexPanel implements
		HasDojo {

	/**
	 * The Widget DOJO for the HasDojo interface
	 */
	protected JavaScriptObject dojoWidget = null;

	/**
	 *Element DOM (DIV) which fulfils the contents of the panel
	 */
	private Element content;

	/**
	 * Create the complex panel to integrate a Dojo widget.
	 * @param element <code>Element</code> DOM element to associate with the GWT widget
	 * @param content <code>Element</code> DOM element serving the content for the panel. 
	 * 
	 */
	protected AbstractDojoComplexPane(Element element, Element content) {
		DojoController.getInstance().loadDojoWidget(this);
		this.content = content;
		setElement(element);

	}

	/**
	 * Calls the constructor with parameter. The parameters are a DIV element for the element associated with the GWT widget.
	 * And a DIV element also for the content.
	 * @see #(Element,Element)
	 */
	protected AbstractDojoComplexPane() {
		this(DOM.createDiv(), DOM.createDiv());

	}

	/**
	 * When the widget GWT is attached on the browser, the Dojo widget is created and link with the GWT widget.
	 * If the creation of the Dojo widget fails then an alert will be display on the screen (problem under IE)  
	 * A log is also written. This methode can be overrided to add some other dependencies between the dojo widget to create and the GWT widget.
	 * The  setDojoContent is called here.
	 * @see Widget#onLoad() 
	 * @see #doAfterCreation()
	 * @link #setDojoContent()
	 */
	protected void onAttach() {
	    DojoController.getInstance().constructDojoWidget(this, this);
	 	setDojoContent();
		super.onAttach();

	}

	/** 
	 * Frees the resources used by the dojo widget in the widget GWT. 
	 * Don't call the method directly let the <code>DojoController</code> calling it.
	 * 
	 */
	public void free() {
		this.dojoWidget = null;
	}

	/** Does nothing,ovveride this method if you it's necessary for the widget*/
	public void doBeforeDestruction() {

	}

	/** Does nothing,ovveride this method if you it's necessary for the widget*/
	public void doAfterCreation() {

	}

	/** Does nothing,ovveride this method if you it's necessary for the widget*/
	public void onDojoLoad() {

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

	/**
	 * Returns the DOM element that fulfil the content of the panel.
	 * @return  <code>Element</code> from the DOM tree containing the content of the panel.
	 */
	public Element getContent() {
		return this.content;
	}

	/**
	 * Links the content of the GWT panel with the content of the Dojo panel
	 * Implement this method according to the Dojo widget panel to integrate, the property of the Dojo container can change.
	 */
	protected abstract void setDojoContent();

	/**
	 * Adds a widget to the panel.
	 * @param w  the widget to add
	 */
	public void add(Widget w) {
		add(w, content);
	}

	/**
	 * Returns the Dojo widget integrated in the GWT widget. 
	 * @return the JavaScript object modelling the Dojo widget
	 */
	public JavaScriptObject getDojoWidget() {
		return this.dojoWidget;
	}

}// end of class
