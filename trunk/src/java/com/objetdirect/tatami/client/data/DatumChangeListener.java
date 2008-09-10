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
 * Authors:  Ronan Dunklau
 * Initial developer(s): Ronan Dunklau
 * Contributor(s):
 */
package com.objetdirect.tatami.client.data;

/**
 * This interface represents a DataStore data change listener.
 * It is notified whenever an item is added, removed, or modified in the store
 * 
 * @author rdunklau
 *
 */
public interface DatumChangeListener {

	/**
	 * @param item : item which has changed
	 * @param attributeName : the attribute which has changed
	 * @param oldValue : value before change
	 * @param newValue : value after change
	 */
	public void onDataChange(Item item , String attributeName , Object oldValue , Object newValue);
	
	/**
	 * @param item : Item which has been added to the store
	 */
	public void onNew(Item item);
	
	/**
	 * @param item : Item which has been removed from the store
	 */
	public void onDelete(Item item);
	
}
