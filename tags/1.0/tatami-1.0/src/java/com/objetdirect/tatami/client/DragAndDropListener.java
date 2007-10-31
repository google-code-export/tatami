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
 * Authors: Henri Darmet
 * Initial developer(s):
 * Contributor(s):
 */
package com.objetdirect.tatami.client;

import com.google.gwt.user.client.ui.Widget;

/**
 * <p> 
 * Interface that all objects have to implement if they want to listen drag and drop events.
 * This interface permits 2 things : 
 * <ul>
 * <li>indicates to the panel if he accepts drop or not(Method <code>acceptDrop</code>)</li>
 * <li>takes into account a Drop (method <code>onDrop</code>)</li>
 * </ul>
 *
 * 
 * @author Henry
 *
 */
public interface DragAndDropListener {

	/**
	  
	 * Indicates if the object accept or not the listening of a drop event.
     * @param draggable object dragged and dropped.
     * @param target the target for the drop
	 * @return <code>true</code> si the drop is accepted, <code>false</code> otherwise.
	 * 
	 */
	public boolean acceptDrop(Widget draggable, Widget target);

	/**
	  
	 * Asks to the object to take account of the drop event.
	 * @param draggable object dragged and droped
	 * @param target target for the drop.
	 
	 */
	public void onDrop(Widget draggable, Widget target);

}//end of the interface
