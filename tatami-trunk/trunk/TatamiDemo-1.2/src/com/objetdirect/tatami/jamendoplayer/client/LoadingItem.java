package com.objetdirect.tatami.jamendoplayer.client;

import com.objetdirect.tatami.client.tree.TreeItem;

public class LoadingItem extends TreeItem{

	public LoadingItem(String label, String id) {
		super(label, id);
		this.setLeafIconClass("loadingTreeItem");
		this.setLabelClass("loadingTreeItemLabel");
	}
	
}
