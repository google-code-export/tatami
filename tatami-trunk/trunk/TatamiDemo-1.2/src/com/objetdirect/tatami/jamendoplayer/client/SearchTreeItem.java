package com.objetdirect.tatami.jamendoplayer.client;

import com.objetdirect.tatami.client.data.Item;

public class SearchTreeItem extends Item{

	private boolean childHaveBeenLoaded = false;
	
	Item loadingItem;
	
	public Item getLoadingItem() {
		return loadingItem;
	}
	
	public boolean isChildHaveBeenLoaded() {
		return childHaveBeenLoaded;
	}

	public void setChildHaveBeenLoaded(boolean childHaveBeenLoaded) {
		this.childHaveBeenLoaded = childHaveBeenLoaded;
	}

	public SearchTreeItem() {
		super();
	}

	public SearchTreeItem(String label, String id) {
		super(label, id);
	}

}
