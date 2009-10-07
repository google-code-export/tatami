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

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;

/**
 * This abstract class aims to d'implement the necessaries method to integrate easily 
 * a Dojo widget in a GWT <code>Widget</code>. To do this, the class implements the interface <code>HasDojo</code>
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
 * @author Vianney
 * 
 */
public abstract class AbstractDojo extends Widget implements HasDojo {

	/**
	 * The Widget DOJO for the HasDojo interface
	 */
	protected JavaScriptObject dojoWidget;

	/**
	 * Loads scripts JavaScript that are necessaries for the dojo widget to integrate.
	 * A <code>Element</code> DIV is assiciated as DOM element for the GWT widget.
	 * @see DojoController#loadDojoWidget(HasDojo)
	 */
	protected AbstractDojo() {
		this(DOM.createDiv());
	}

	/**
	 * Loads the scripts JavaScript that are necessaries for the Dojo widget
	 * @param element an <code>Element</code> for the DOM tree to associate as an element DOM to the GWT widget.
	 * @see DojoController#loadDojoWidget(HasDojo)
	 */
	protected AbstractDojo(Element element) {
		super();
		DojoController.getInstance().loadDojoWidget(this);
		this.setElement(element);
	}

	
	public void onBrowserEvent(Event event) {
		
	}
	
	
	/**
	 * Returns the Dojo widget integrated in the GWT widget. 
	 * @return the JavaScript object modelling the Dojo widget
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
	
	protected NativeEvent createClickEvent(int detail,int screenX,int screenY,int clientX,int clientY, boolean ctrlKey,       boolean altKey,
            boolean shiftKey,
            boolean metaKey) {
		Document document = Document.get();
		return document.createClickEvent(detail, screenX, screenY, clientX, clientY, ctrlKey, altKey, shiftKey, metaKey);
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
	
    /** Does nothing,ovveride this method if you it's necessary for the widget*/
    public void doAfterCreation() {
    	
    }
    
    /** Does nothing,ovveride this method if you it's necessary for the widget*/
    public void onDojoLoad() {
    	
    }
    

	/**
	 * When the widget GWT is attached on the browser, the Dojo widget is created and link with the GWT widget.
	 * If the creation of the Dojo widget fails then an alert will be display on the screen (problem under IE)  
	 * A log is also written. This methode can be overrided to add some other dependencies between the dojo widget to create and the GWT widget. 
	 * @see Widget#onLoad() 
	 * @see #doAfterCreation()
	 */
	protected void onAttach() {
		DojoController.getInstance().constructDojoWidget(this,this);
		super.onAttach();
    }

} // end of class
