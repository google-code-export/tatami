package com.objetdirect.tatami.jamendoplayer.client;

import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.objetdirect.tatami.client.data.Item;
import com.objetdirect.tatami.client.tree.Tree;

public class AlbumItem extends SearchTreeItem{

	public static final String type="album";
	
	public static final String nameKey = "name";
	public static final String url = "url";
	public static final String image = "image";
	public static final String genre = "genre";
	public static final String duration = "duration";
	public static final String artist_name = "artist_name";
	
	public static final String[] attributesToFill = { idKey , nameKey , url , image  , genre , duration , artist_name};
	
	
	public AlbumItem(JavaScriptObject jsonItem){
		this.setValue(SearchTreeItem.typeKey, type);
		for(int i = 0; i < attributesToFill.length ; i++){
			String attrName = attributesToFill[i];
			this.setValue(attrName, JamendoQueryMaker.extractFieldFromOBJ(jsonItem, attrName));
		}
		this.setValue(Item.labelAttribute, JamendoQueryMaker.extractFieldFromOBJ(jsonItem, "name") );
		loadingItem = new LoadingItem("__ALBUM_LOADING_ITEM__","Please wait while loading albums ... " );
		this.setValue(Tree.folderOpenedClassAttribute,"albumTreeItemOpen");
		this.setValue(Tree.folderClosedClassAttribute,"albumTreeItem");
		this.setValue(Tree.labelClassAttribute , "albumTreeItemLabel");
		super.addChild(loadingItem);
	}


	public AlbumItem(String id , String label){
		super(id ,label);
	}
	
	
	
}
