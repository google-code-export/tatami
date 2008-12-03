package com.objetdirect.tatami.jamendoplayer.client;

import java.util.List;

import com.objetdirect.tatami.client.data.Item;
import com.objetdirect.tatami.client.tree.Tree;


public class LoadingItem extends SearchTreeItem{

	public LoadingItem(String id , String label) {
		super(id , label);
		this.setValue(Tree.leafClassAttribute,"loadingTreeItem");
		this.setValue(Tree.labelClassAttribute,"loadingTreeItemLabel");
	}
	
}
