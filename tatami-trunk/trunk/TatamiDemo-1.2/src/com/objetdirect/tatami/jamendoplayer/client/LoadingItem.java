package com.objetdirect.tatami.jamendoplayer.client;

import com.objetdirect.tatami.client.data.Item;
import com.objetdirect.tatami.client.tree.Tree;


public class LoadingItem extends Item{

	public LoadingItem(String label, String id) {
		super(label, id);
		this.setValue(Tree.leafClassAttribute,"loadingTreeItem");
		this.setValue(Tree.labelClassAttribute,"loadingTreeItemLabel");
	}
	
}
