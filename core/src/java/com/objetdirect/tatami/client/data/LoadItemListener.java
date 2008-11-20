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
 * This interface defines a listener for item loading
 * in the store.
 * 
 * @see {@link AbstractDataStore#loadItem(Item)}
 * @see {@link AbstractDataStore#isItemLoaded(Item)}
 * @author rdunklau
 *
 *	
 */
public interface LoadItemListener {
	
	
	/**
	 * Called whenever an item is loaded in a store.
	 *  @see {@link AbstractDataStore#loadItem(Item)}
	 *	@see {@link AbstractDataStore#isItemLoaded(Item)}
	 * 
	 * @param item : item which has been loaded
	 */
	public void onLoad(Item item);
	
}
