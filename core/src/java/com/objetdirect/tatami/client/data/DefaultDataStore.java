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


import java.util.ArrayList;
import java.util.List;

import com.objetdirect.tatami.client.data.AbstractDataStore;
import com.objetdirect.tatami.client.data.Item;
import com.objetdirect.tatami.client.data.Request;
 
/**
 * This DataStore is specific to the Grid.
 * It provides pagination methods
 * 
 * @author rdunklau
 *
 */
public class DefaultDataStore extends AbstractDataStore{


	
	/**
	 * Number of rows per page
	 * 
	 */
	
	/**
	 * Current page.
	 */
	private int currentPage = 0;
	
	public DefaultDataStore(){
		super();
	}
	
	@Deprecated
	public DefaultDataStore(String idAttr){
		super( idAttr , "label");
	}
	
	
	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.data.AbstractDataStore#fetch(com.objetdirect.tatami.client.data.Request)
	 */
	
	//TODO : Better callback management
	@Override
	public void fetch(Request request) {
		
		//Saves the request to keep filtering and sorting parameter for later use
		// (for example, to fetch multiple pages)
		lastRequest = request;
		//Gets the items matching the query , and sorts them according to the
		//request
		List<?> itemsSortedAndMatchingQuery = this.executeQuery(items.values() , request);
		
		
		int size = itemsSortedAndMatchingQuery.size();
		
		
		// We notify the fetch listeners that the request is being performed
		notifyBeginFetchListeners(size , request);
		List<Item> result = new ArrayList<Item>();
		
		// We determine which item should be the first returned. 
		int count = request.getNbItemToReturn() == -1 ? size : Math.min(size, request.getNbItemToReturn());
		int startItem = request.getStartItemNumber() == -1  ? 0 : request.getStartItemNumber() ;
		int end =  Math.min(startItem + count , itemsSortedAndMatchingQuery.size());
		
		// We load all items between start item and end item,
		// notify the fetchListeners that an item is being fetched , and
		// add it to the result
		for (int i = (startItem ) ; i < end ; i++) {
			Item item = (Item) itemsSortedAndMatchingQuery.get(i);
			loadItem(item);
			item.setFullyLoaded(true);
			notifyItemFetchListeners(item);
			result.add(i-startItem,item);
		}
		
		//Finally , we pass the result to the fetch listeners
		notifyCompleteFetchListeners(result , request);
	}

	

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.data.AbstractDataStore#isItemLoaded(com.objetdirect.tatami.client.data.Item)
	 */
	@Override
	public boolean isItemLoaded(Item item) {
		return item.isFullyLoaded();
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.data.AbstractDataStore#loadItemImpl(com.objetdirect.tatami.client.data.Item)
	 */
	@Override
	public boolean loadItemImpl(Item item) {
		if(item.isFullyLoaded()){
			return false;
		}else{
			item.setFullyLoaded(true);
			return true;
		}
		
	}

	
}
