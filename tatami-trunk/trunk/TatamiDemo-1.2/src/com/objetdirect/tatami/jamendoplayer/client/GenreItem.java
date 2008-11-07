package com.objetdirect.tatami.jamendoplayer.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.objetdirect.tatami.client.tree.Tree;

public class GenreItem extends SearchTreeItem {
	public GenreItem(JavaScriptObject jsonItem){
		this.setValue("type", "genre");
		this.setValue("label", JamendoQueryMaker.extractFieldFromOBJ(jsonItem, "idstr") );
		this.setValue("id", JamendoQueryMaker.extractFieldFromOBJ(jsonItem, "id") );
		this.setValue(Tree.folderOpenedClassAttribute , "genreTreeItemOpen");
		this.setValue(Tree.folderClosedClassAttribute , "genreTreeItem");
		this.setValue(Tree.leafClassAttribute , "genreTreeItem");
	}

	public GenreItem(String label , String id){
		super(label , id);
	}
	
	
}
