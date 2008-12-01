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
 * Author: Ronan Dunklau
 * Initial developer(s):
 * Contributor(s):
 */
package com.objetdirect.tatami.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.ClickListenerCollection;
import com.google.gwt.user.client.ui.HasText;




/**
 * This class is a simple wrapper around dojo button.
 * It implements SourcesClickEvents to dispatch the onClick events received
 * from dojo.  
 *  
 * @author rdunklau
 *
 */
public class Button extends AbstractDojo  implements HasDojo, HasText {

	private ClickListenerCollection listeners;
	
	private final static String dojoName = "dijit.form.Button" ;
	
	private String label = "";
	
	private String iconClass = "";
	
	private Command command = null;

	private boolean enabled = true;

	private JavaScriptObject dojoWidget;
	
	
	public Button() {
		this(Document.get().createDivElement() , null , null);
	}

	public Button(String html, ClickListener listener) {
		this(html);
		addClickListener(listener);
	}

	/**
	 * Simplest Constructor. 
	 * @param label : the button's caption
	 */
	public Button(String label) {
		this(label,"");
	}
	
	public Button(String label , String iconClass){
		this(Document.get().createDivElement(), label , iconClass);
	}
	
	
	
	/**
	 * Same as Button(String label), except it takes the css class name for 
	 * the icon. 	 * 
	 * @param label : the button's caption
	 * @param iconClass :  The css class name. 
	 * The css class it refers to should be something like :
	 *  .myButtonIcon {
	 *		background-image: url("button.png");
	 *		height: 32px;
	 *		width: 32px;	
     *	}
	 */
	public Button(Element element ,String label , String iconClass){
		super(element);
		this.label = label;
		this.iconClass = iconClass;

	}
	

	
	
	public void setCommand(Command command) {
		this.command = command;
	}
	
	public Command getCommand() {
		return this.command;
	}

	public String getIconClass() {
		return iconClass;
	}
	
	public void setIconClass(String iconClass) {
		this.iconClass = iconClass;
	}
	

	/**
	 * Method which actually sets the label on the dojo button widget 
	 * 
	 * @param dojoWidget 
	 * @param label
	 */
	private native void dojoSetLabel(String label , JavaScriptObject dojoWidget)
	/*-{
	 dojoWidget.setLabel(label);
	}-*/
	;
	
	
	public void setEnabled(boolean b) {
		this.enabled = b;
		if ( isAttached()) {
			setEnabled(getDojoWidget(),b);
		}
	}
	
	
	private native void setEnabled(JavaScriptObject dojoWidget, boolean enabled) /*-{
	   dojoWidget.setDisabled(!enabled);
	}-*/;
	
	
	/**
	 * Method used to override default dojo's button onClick behavior
	 * to call the onClick method of this Tatami widget.
	 * 
	 */
	
	private native void defineTatamiButton()
	/*-{
	 $wnd.dojo.declare("dojox.form.TatamiButton", $wnd.dijit.form.Button, {
	 onClick:function (e) {
	 	this.gwtWidget.@com.objetdirect.tatami.client.Button::onClick()();
	 }});
	  
	 }-*/;
	
	
	/**
	 * Creates the concrete dojo javascript object
	 * @param label
	 * @return
	 */
	
	private native JavaScriptObject createDojoButton(String label , String iconClass)
	/*-{
		 var button = new $wnd.dojox.form.TatamiButton({
		 label: label,
		 iconClass : iconClass
		 });
		 return button;
	}-*/
	;
	
	
	public void onDojoLoad() {
		defineTatamiButton();
	}
	
	
	public void createDojoWidget() throws Exception {
		this.dojoWidget = createDojoButton(label , iconClass);
	}

	
	
	public String getDojoName() {
		return dojoName;
	}



	
	/**
	 * This method is called back by dojo when it catches a onClick event. 
	 * It notifies the registered clickListeners that a click occured
	 * 
	 */
	public void onClick(){
		if ( command != null ) {
			command.execute();
		}
		if (listeners != null) {
		listeners.fireClick(this);
		}
	}
	
	
	public void addClickListener(ClickListener listener) {
		if ( listeners == null) {
			listeners = new ClickListenerCollection();
		}
		listeners.add(listener);	
	}


	public void removeClickListener(ClickListener listener) {
		if ( listeners != null) {
		listeners.remove(listener);
		}
	}

	

	public void doAfterCreation() {
		DojoController.startup(this);
		setEnabled(enabled);
	}

	
	public boolean isEnabled() {
		return this.enabled;
	}
	
	
	public void doBeforeDestruction() {
	}

	public void free() {
		this.dojoWidget = null;
	}

	public JavaScriptObject getDojoWidget() {
		return dojoWidget;
	}

	

	public String getLabel() {
		return this.label;
	}

	/**
	 * Changes the button label
	 * @param text
	 */
	public void setLabel(String text) {
		this.label = text;
		dojoSetLabel(text , dojoWidget);
	}
	
	public void setText(String text) {
		setLabel(text);
	}

	public String getText() {
		return getLabel();
	}


}//end of class
