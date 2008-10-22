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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Window;
import com.objetdirect.tatami.client.data.AbstractDataStore;
import com.objetdirect.tatami.client.data.Item;
import com.objetdirect.tatami.client.data.Request;
import com.objetdirect.tatami.client.grid.GridDataStore;

public class TreeStore extends AbstractDataStore{

	
	
	private String childAttribute = "child";
	
	private LabelBuilderStrategy buildLabelStrategy;
	
	public LabelBuilderStrategy getBuildLabelStrategy() {
		return buildLabelStrategy;
	}

	public void setBuildLabelStrategy(LabelBuilderStrategy buildLabelStrategy) {
		this.buildLabelStrategy = buildLabelStrategy;
	}

	class DefaultBuildLabelStrategy implements LabelBuilderStrategy{

		public String getLabel(Item item) {
			return (String) item.getValue("label", item.getValue(getIdentityAttribute(), "<No label>").toString());
		}

		public String[] getLabelAttributes(Item item) {
			String[] labelAttrs = {"label"};
			return labelAttrs;
		}
		
	}
	
	public TreeStore(){
		super();
		this.buildLabelStrategy = new DefaultBuildLabelStrategy();
	}
	
	public void fetch(Request request) {
		
		Object[] concreteItems = (Object[]) items.values().toArray();
		List itemsSortedAndMatchingQuery = this.executeQuery(items.values() , request);
		notifyBeginFetchListeners(itemsSortedAndMatchingQuery.size(), request);
		for (Iterator iterator = itemsSortedAndMatchingQuery.iterator(); iterator
				.hasNext();) {
			TreeItem item = (TreeItem) iterator.next();
			notifyItemFetchListeners(item);
		}
		notifyCompleteFetchListeners(itemsSortedAndMatchingQuery , request);
	}


	public boolean isItemLoaded(Item item) {
		return true;
	}

	public boolean loadItemImpl(Item item) {
		return true;
	}

	public boolean isItem(Object item){
		boolean isItem;
		if(item instanceof Item || item instanceof TreeItem && getItemByIdentity(getIdentity((Item)item)) != null){
			isItem = true;
		}else{
			isItem = false;
		}
		return isItem;
	}

	public String getLabel(Item item) {
		return buildLabelStrategy.getLabel(item);
	}

	public String[] getLabelAttributes(Item item) {
		return buildLabelStrategy.getLabelAttributes(item);
	}

	
	
}
