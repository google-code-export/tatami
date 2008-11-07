package com.objetdirect.tatami.jamendoplayer.client;

import com.objetdirect.tatami.client.data.Item;
import com.objetdirect.tatami.client.tree.TreeItem;

public class SearchTreeItem extends TreeItem{

	private boolean childHaveBeenLoaded = false;
	
	TreeItem loadingItem;
	
	public TreeItem getLoadingItem() {
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

	public SearchTreeItem(Item item) {
		super(item);
	}

	public SearchTreeItem(String label, String id) {
		super(label, id);
	}

}
