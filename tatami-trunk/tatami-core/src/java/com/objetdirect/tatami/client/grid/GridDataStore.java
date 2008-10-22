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

package com.objetdirect.tatami.client.grid;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.objetdirect.tatami.client.data.AbstractDataStore;
import com.objetdirect.tatami.client.data.FetchListener;
import com.objetdirect.tatami.client.data.Item;
import com.objetdirect.tatami.client.data.Request;
import com.objetdirect.tatami.client.data.SortField;
 
/**
 * This DataStore is specific to the Grid.
 * It provides pagination methods
 * 
 * @author rdunklau
 *
 */
public class GridDataStore extends AbstractDataStore{


	
	/**
	 * Number of rows per page
	 * 
	 */
	
	/**
	 * Current page.
	 */
	private int currentPage = 0;
	
	public GridDataStore(){
		super();
	}
	
	public GridDataStore(String idAttr){
		super( idAttr , "label");
	}
	
	
	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.data.AbstractDataStore#fetch(com.objetdirect.tatami.client.data.Request)
	 */
	
	//TODO : Better callback management
	public void fetch(Request request) {
		
		//Saves the request to keep filtering and sorting parameter for later use
		// (for example, to fetch multiple pages)
		lastRequest = request;
		GWT.log("FETCHING "+ request.getNbItemToReturn() +" ITEMS FROM " + request.getStartItemNumber() ,null);
		
		//Gets the items matching the query , and sorts them according to the
		//request
		List itemsSortedAndMatchingQuery = this.executeQuery(items.values() , request);
		
		
		int size = itemsSortedAndMatchingQuery.size();
		
		
		// We notify the fetch listeners that the request is being performed
		//(it should include a grid , since this store is designed to be used by  
		notifyBeginFetchListeners(size , request);
		List result = new ArrayList();
		
		// We determine which item should be the first returned. 
		// To do this , we use :
		// 	-- firstItemFromPageToLoad : the position of the first wanted item in the current page
		//	-- currentPage * rowsPerPage : the number of items which should be on previous pages 
		int count = request.getNbItemToReturn() == -1 ? size : Math.min(size, request.getNbItemToReturn());
		int startItem = request.getStartItemNumber();
		int end =  Math.min(startItem + count , itemsSortedAndMatchingQuery.size());
		
		// We load all items between start item and end item,
		// notify the fetchListeners that an item is being fetched , and
		// add it to the result
		for (int i = (startItem ) ; i < end ; i++) {
			Item item = (Item) itemsSortedAndMatchingQuery.get(i);
			loadItem(item);
			notifyItemFetchListeners(item);
			result.add(i-startItem,item);
		}
		
		//Finally , we pass the result to the fetch listeners
		notifyCompleteFetchListeners(result , request);
	}

	

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.data.AbstractDataStore#isItemLoaded(com.objetdirect.tatami.client.data.Item)
	 */
	public boolean isItemLoaded(Item item) {
		return getItemByIdentity(getIdentity(item)) != null;
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.data.AbstractDataStore#loadItemImpl(com.objetdirect.tatami.client.data.Item)
	 */
	public boolean loadItemImpl(Item item) {
		return false;
	}

	
}
