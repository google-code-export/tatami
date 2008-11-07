package com.objetdirect.tatami.jamendoplayer.client;

import com.google.gwt.core.client.JavaScriptObject;

public class GenreItem extends SearchTreeItem {
	public GenreItem(JavaScriptObject jsonItem){
		this.addAttribute("type", "genre");
		this.addAttribute("label", JamendoQueryMaker.extractFieldFromOBJ(jsonItem, "idstr") );
		this.addAttribute("id", JamendoQueryMaker.extractFieldFromOBJ(jsonItem, "id") );
		this.setFolderOpenIconClass("genreTreeItemOpen");
		this.setFolderClosedIconClass("genreTreeItem");
		this.setLeafIconClass("genreTreeItem");
	}

	public GenreItem(String label , String id){
		super(label , id);
	}
	
	
}
