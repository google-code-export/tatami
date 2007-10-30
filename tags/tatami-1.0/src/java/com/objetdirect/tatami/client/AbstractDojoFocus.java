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
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ChangeListenerCollection;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.Widget;

/**
 * This abstract class aims to d'implement the necessaries method to integrate easily 
 * a Dojo widget in a GWT <code>FocusWidget</code>. To do this, the class implements the interface <code>HasDojo</code>
 *  
 * There are 3 points to know for implementing correctly this class : <br>
 * <ul>
 * <li> How to  create the Dojo widget ? </li>
 * <li> Do we do something after that the scripts JavaScript are loaded for the Dojo widget ? </li>
 * <li> Know the name of the Dojo widget to create.</li>
 * </ul> 
 * 
 * This abstract class permits to call methods at " the good times" in order to avoid some memory leak problems 
 * and assume that scripts JavaScript Dojo are well loaded by the browser and they are load only once. 
 * 
 * The class has a container of <code>ChangeListener</code>, notifying a value changement displayed and  edited of the widget.
 * @author Vianney
 */
public abstract class AbstractDojoFocus extends FocusWidget implements HasDojo {

	/**
	 * The Widget DOJO for the HasDojo interface
	 */
	protected JavaScriptObject dojoWidget = null;

	/**
	 * Collection for the listener notfiying change values.
	 */
	protected ChangeListenerCollection changeListeners;

	/**
	 * Loads the scripts JavaScript that are necessaries for the Dojo widget
	 * @param element an <code>Element</code> for the DOM tree to associate as an element DOM to the GWT widget.
	 * @see DojoController#loadDojoWidget(HasDojo)
	 */
	protected AbstractDojoFocus(Element element) {
		super(element);
		DojoController.getInstance().loadDojoWidget(this);
		

	}

	/**
	 * Loads scripts JavaScript that are necessaries for the dojo widget to integrate.
	 * A <code>Element</code> DIV is assiciated as DOM element for the GWT widget.
	 * @see DojoController#loadDojoWidget(HasDojo)
	 */
	protected AbstractDojoFocus() {
		this(DOM.createDiv());

	}

	/**
	 * Returns the Dojo widget integrated in the GWT widget. 
	 * @return the JavaScript object modelling the Dojo widget
	 */
	public JavaScriptObject getDojoWidget() {
		return this.dojoWidget;
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
	 * Frees the resources used by the dojo widget in the widget GWT. 
	 * Don't call the method directly let the <code>DojoController</code> calling it.
	 * 
	 */
	public void free() {
		this.dojoWidget = null;
	}

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
	 * Adds a listener interface to receive change values events.
	 * @param listener the listener interface to add
	 */
	public void addChangeListener(ChangeListener listener) {
		if (changeListeners == null) {
			changeListeners = new ChangeListenerCollection();
		}
		changeListeners.add(listener);
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
	 * Removes a previously added listener interface.
	 * @param listener the listener interface to remove
	 */
	public void removeChangeListener(ChangeListener listener) {
		if (changeListeners != null) {
			changeListeners.remove(listener);
		}
	}

}// end of class
