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
package com.objetdirect.tatami.client.tree;

import java.util.Iterator;
import java.util.List;

import com.google.gwt.user.client.ui.TreeItem;
import com.objetdirect.tatami.client.data.AbstractDataStore;
import com.objetdirect.tatami.client.data.Item;
import com.objetdirect.tatami.client.data.Request;

public class TreeStore extends AbstractDataStore{

	private String childAttribute = "child";
	
	public TreeStore(){
		super();
	}
	
	@Override
	public void fetch(Request request) {
		Object[] concreteItems = items.values().toArray();
		List<?> itemsSortedAndMatchingQuery = this.executeQuery(items.values() , request);
		notifyBeginFetchListeners(itemsSortedAndMatchingQuery.size(), request);
		for (Iterator<?> iterator = itemsSortedAndMatchingQuery.iterator(); iterator
				.hasNext();) {
			Item item = (Item) iterator.next();
			notifyItemFetchListeners(item);
		}
		notifyCompleteFetchListeners(itemsSortedAndMatchingQuery , request);
	}


	@Override
	public boolean isItemLoaded(Item item) {
		return true;
	}

	@Override
	public boolean loadItemImpl(Item item) {
		return true;
	}

	@Override
	public boolean isItem(Object item){
		boolean isItem;
		if(item instanceof Item || item instanceof TreeItem && getItemByIdentity(getIdentity((Item)item)) != null){
			isItem = true;
		}else{
			isItem = false;
		}
		return isItem;
	}

	
}
