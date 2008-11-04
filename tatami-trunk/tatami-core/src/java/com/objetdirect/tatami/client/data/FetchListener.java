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

import java.util.List;

/**
 * @author rdunklau
 *
 * Implement this interface to listen for "fetch" events from a DataStore
 *
 */
public interface FetchListener {

	
	/**
	 * Called when the fetch process is complete
	 * 
	 * @param items : fetched items
	 * @param request : request which was used to fetch those items
	 */
	public void onComplete(FetchEventSource source ,List<?> items , Request request );
	
	/**
	 *  Called at the beginning of the fetch process
	 * 
	 * @param size : total expected number of item, which 
	 * does not necessary equals the total number of item fetched.
	 * Usually, it should be the maximum amount of item that could have been fetched (for example,
	 * if the request "maxNbItemToReturn" didn't limit it).
	 * 
	 * @param request : request used during the fetch process
	 */
	public void onBegin(FetchEventSource source,int size , Request request);
	
	/****
	 * Called each time an item has been fetched.
	 *
	 * 
	 * @param item : item which has been fetched
	 */
	public void onItem(FetchEventSource source, Item item);
	
	/**
	 * Called if an error occured during the fetch process
	 * 
	 */
	public void onError(FetchEventSource source);
	
	
}
