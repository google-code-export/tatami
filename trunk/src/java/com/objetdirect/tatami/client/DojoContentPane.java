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
import com.google.gwt.user.client.Element;

/**
 *  
 * Class of DOJO panels that present only the feature that this to gather widgets (Dojo of other).   
 * These panels are useful when some widgets created from DOJO know only manipulate DOJO widget. 
 * (as the Toggler, TaskBar for example).
 * <p>
 * This component is an encapsulation GWT of Dojo components. Warning the widget exist under 3 faces : 
 * <ul>
 * <li>a GWT widget (this!)</li>
 * <li>a Dojo widget (which play the role of 'this' for GWT)</li>
 * <li>a structur of DOM elements, the only exploitable presentation by the navigator.</li>
 * </ul>
 * </p>

 * 
 * @author Henry, Vianney
 */
public class DojoContentPane extends AbstractDojoComplexPane {

	/**
	 * 
	 * Id for the Dojo pannel, this is used by some methods of DOJO.
	 */
	private String id = "";

	/**
 
	 * Creates a Panel 
	 * @param id id DOJO of the panel.
	 */
	public DojoContentPane(String id) {
		super();
		this.id = id;
	}

	/**
	 * 
	 * Returns the id of the panel, it's the id of the dohjo widget integrated by this component.
	 * @return id of the panel
	 */
	public String getId() {
		return this.id;
	}
	
	/**
	 *  
	 * Creates a link between the content of the panel and the DOJO panel. Indeed, 
	 * we use the method <code>add(Widget,Element)</code> to add widgets to the panel. The link 
	 * permits to the DOJO panel to know all the widgets added.
	 * 
	 * @see #setContent(JavaScriptObject, Element)
	 */
	protected void setDojoContent() {
		setContent(getDojoWidget(), getContent());
	}

	/**
	 * <p> 
	 * Sets a DOM element as a content of the DOJO widget fulfilling the simple Dojo panel.
     * @param widgetDojo widget DOJO fulfilling the DOJO panel
	 * @param content the DOM element that will be the content of the DOJO widget
	 * 
	 */
	private native void setContent(JavaScriptObject widgetDojo, Element content)
	/*-{
	     widgetDojo.setContent(content);
	 }-*/;

	/**
	 * Returns the name of the Dojo widget.
	 * @return "ContentPane"
	 */
	public String getDojoName() {
		return "dijit.layout.ContentPane";
	}

	/**
	 * Creates the DOJO panel that will contain widgets. The id is associated to this panel.
	 * @return the DOJO panel 
	 *         
	 */
	public void createDojoWidget() {
		this.dojoWidget =  createContentPane(id);
	}



	/**
	 * Creates the DOJO component that fulfil the panel
	 * @param id the DOJO id of the panel.
	 * @return the DOJO widget 
	 */
	private native JavaScriptObject createContentPane(String id)
	/*-{
	    var panel = new $wnd.dijit.layout.ContentPane({ id: id }
	     ,$wnd.dojo.doc.createElement('div'));
	    
	    return panel;
	 }-*/;

}//end of class
