package com.objetdirect.tatami.jamendoplayer.client;

import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.objetdirect.tatami.client.data.Item;
import com.objetdirect.tatami.client.tree.Tree;

public class ArtistItem extends SearchTreeItem {

	public static final String type="artist";
	
	public static final String nameKey = "name";
	public static final String url = "url";
	public static final String image = "image";
	public static final String genre = "genre";
	
	public static final String[] attributesToFill = { idKey , nameKey , url , image  , genre};
	
	public ArtistItem(String id , String label){
		super(id ,label);
	}
	
	


	public ArtistItem(JavaScriptObject jsonItem){
		this.setValue(SearchTreeItem.typeKey, type);
		for(int i = 0; i < attributesToFill.length ; i++){
			String attrName = attributesToFill[i];
			this.setValue(attrName, JamendoQueryMaker.extractFieldFromOBJ(jsonItem, attrName));
		}
		this.setValue(Item.labelAttribute, JamendoQueryMaker.extractFieldFromOBJ(jsonItem, nameKey) );
		loadingItem = new LoadingItem("__ALBUM_LOADING_ITEM__" , "Please wait while loading albums ... ");
		this.setValue(Tree.folderOpenedClassAttribute , "artistTreeItemOpen");
		this.setValue(Tree.folderClosedClassAttribute ,"artistTreeItem");
		this.setValue(Tree.folderOpenedClassAttribute ,"artistTreeItemLabel");
		this.addChild(loadingItem);
	}
	
	
}
