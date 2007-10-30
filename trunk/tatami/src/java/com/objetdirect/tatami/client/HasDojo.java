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
 * Authors:  Vianney Grassaud
 * Initial developer(s):
 * Contributor(s):
 */
package com.objetdirect.tatami.client;

import com.google.gwt.core.client.JavaScriptObject;


/**
 * This interface describes a GWT widget integrating a Dojo widget.
 * To do this you need to know : <br>
 * <ul>
 * <li>the name of the Dojo</li>
 * <li>How create the widget : parameters...</li>
 * <li>How if you need to create, load some objets before the creation of the dojo widget<li>
 * </ul>
 * 
 * 
 * @author Vianney
 */

public interface HasDojo {

	/**
	 *Returns the Dojo widget 
	 *@return a JavaScript Object corresponding to the widget Dojo.
	 */ 
	public JavaScriptObject getDojoWidget();
	 
	 
	/**
	 * Creates the widget Dojo using JSNI for the most of the cases.
	 * This method is not to be executed directly in your widget GWT, a controller has to execute it. 
	 * @throws Exception if the dojo widget can't be created. Warning, check the parameters and their type of JavaScript objects when JSNI is used. A JavaScript error  
	 *         can cause a fatal failure for the module.
	 */
	public  void createDojoWidget() throws Exception ;

	
	
	/**
	 *This method executes the Java instrcutions or JavaScript (with JSNI ). 
	 *This should be called after that the scripts JavaScript needed  for the widget Dojo are loaded.
	 *It's not necessary to implement this method every time it depends of the widget Dojo to integrate.
	 */
	public void onDojoLoad();

	/**
     * Returns the name of the dojo widget example : <code>FisheyeList</code>
	 * This method is used to load the scripts JavaScript for Dojo and create the dojo widget.
	 * See the Dojo documentation to know the good name.
	 * @return the name of dojo widget
	 */
	public String getDojoName();
	 

	/**
	 * Do something after that the widget Dojo is created. 
	 * Don't use directly this method, use a Dojo controller instead : <code>DojoController</code> 
	 */
	public void doAfterCreation();
	
	/**
	 * Do something before the desctruction of the Dojo widget, use this method to free some memory tp prevent memory leak.
	 * Don't use directly this method, use a Dojo controller instead : <code>DojoController</code>
	 */
	public void doBeforeDestruction();
	
	/**
	 * Free,turn to null  the resource taken by the Dojo widget in the GWT widget. 
	 * Don't use directly this method, use a Dojo controller instead : <code>DojoController</code>
	 */
	public void free();
}
