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

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * Toggler is a widget allowing to make visible or invisible another widget through a mouse click.
 * The Toggler is inivisible. it is associated to the ID DOJO of the widget that we want to hide or show.
 * 
 * @author Henry, Vianney
 *
 */
public class Toggler extends SimplePanel implements HasDojo {

	/** The DOJO widget  */
	private JavaScriptObject dojoWidget;
	
	/** Id of the target DOJO widget */ 
	private  String targetId = "";
	
	/**
	 * The widget to add to the panel of Toggler
	 */
	private Widget widgetAdded = null;
	
	/**
	 * Creates the Toggler. 
	 *@param id id of the DOJO target to accociate with this <code>Toggler</code> 
	 */
	public Toggler(String id) {
		super();
		this.targetId = id;
		DojoController.getInstance().loadDojoWidget(this);
     }

	
	/**
	 * Returns the id ot the target of the Toggler.
	 * @return the id of the DOJO widget target
	 */
	public String getTargetId() {
		return this.targetId;
	}
	
	
	/**
	 * Returns the Dojo widget integrated in the GWT widget. 
	 * @return the JavaScript object modelling the Dojo widget
	 */
	public JavaScriptObject getDojoWidget() {
		return this.dojoWidget;
	}

	/**
	 * Returns the DOM element of the container.Returns the DOM element of the 
	 * DOJO widget. 
	 * @return Element the DOM element of the DOJO widget.
	 * @see DojoController#getDomNode(HasDojo)
	 */
	protected Element getContainerElement() {
		Element element = super.getContainerElement();
		
		if (getDojoWidget() != null) {
			element = DojoController.getInstance().getDomNode(this);
		}
		return element;
	}
	
	
		
	/**
	 * Adds a widget to the Toggler.
	 * @param w widget to add
	 */
	public void add(Widget w)	{
	    this.widgetAdded = w;
        // on appelle pas la methode add mère, celle-ci sera appelait dans onAttach() avant 
	    // que la widget GWT soit attachée au navigateur, car sinon une erreur est déclanchée.
	    // de type java.lang.IllegalStateException.
		
	}

	/**
	 * When the GWT widget is detached from the browser, the link between it and the Dojo widget is drawn. 
	 * The Dojo widget is also destroy. The DOM element fulfilling the Dojo widget is also destroy. 
	 * @see DojoController#destroyDojoWidget(HasDojo, com.google.gwt.user.client.Element)
	 */
	protected void onDetach() {
		DojoController.getInstance().destroyDojoWidget(this,this);
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
	 */
	protected void onAttach() {
		DojoController.getInstance().constructDojoWidget(this,this);
		//ajoute la widget au simple panel, il faut que cette methode soit appelé avant même
		//que la widget soir attaché. sinon on se retrouve dans un état incohérent pour la version 1.4 de GWT
		super.add(widgetAdded);
		super.onAttach();
	}
    
	
	
	/**
	 * Returns the name of the DOJO widget. 
	 * @return "Toggler"
	 */
	public String getDojoName() {
		return "Toggler";
	}
	
    /**
	 * Creates the DOJO widget Toggler
	 * Calls the method <code>{@link #createToggler(String)}</code>
	 * @return the DOJO widget
	 */
	public void createDojoWidget() throws Exception {
		this.dojoWidget =  createToggler(targetId);
	}
	
	/**
	 * Creates the DOJO widget Toggler
	 * @param targetId id of the target for the  Toggler
	 * @return the DOJO widget.
	 */
	private native JavaScriptObject createToggler(String targetId)
	/*-{
		var toggler = $wnd.dojo.widget.createWidget(
			"Toggler",
			{
				templateString: '<div></div>',
				targetId : targetId
			}
		);
		return toggler;
	}-*/;

}//end of class
