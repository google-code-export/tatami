package com.objetdirect.tatami.jamendoplayer.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONBoolean;
import com.objetdirect.tatami.client.data.Item;
import com.objetdirect.tatami.client.tree.Tree;

public class GenreItem extends SearchTreeItem {
	
	public static final String labelKey = "idstr";
	public static final String type="genre";
	
	public GenreItem(JavaScriptObject jsonItem){
		super();
		this.setId(JamendoQueryMaker.extractFieldFromOBJ(jsonItem,idKey));
		this.setLabel(JamendoQueryMaker.extractFieldFromOBJ(jsonItem,labelKey));
		this.setValue(SearchTreeItem.typeKey,type);
		this.setValue(Tree.folderOpenedClassAttribute , "genreTreeItemOpen");
		this.setValue(Tree.folderClosedClassAttribute , "genreTreeItem");
		this.setValue(Tree.leafClassAttribute , "genreTreeItem");
	}

	public GenreItem(String id , String label){
		super(id , label);
	}

	@Override
	public String getLabel() {
		return (String) attributes.get(labelKey);
	}

	@Override
	public void setLabel(String label) {
		attributes.put(labelKey, label);
	}
	
	
}
