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



/**
 * 
 * Clock. This widget displays in the browser a clock with needles.
 *   
 * @author Vianney
 *
 */
public class Clock extends AbstractDojo {

	/**   
	 * Creates the clock with needles at the current time.
	 */
	public Clock() {
		super();
	}
	
	/** 
	 * Create the Dojo widget clock. 
	 * @throws Exception if the creation failed
	 */
	public void createDojoWidget() throws Exception  {
		this.dojoWidget = DojoController.getInstance().createSimple(getDojoName());
	}

	/**
	 * Returns the name of the widget 
	 * @return "Clock" 
	 */
	public String getDojoName() {
		return "Clock";
	}
	

}
