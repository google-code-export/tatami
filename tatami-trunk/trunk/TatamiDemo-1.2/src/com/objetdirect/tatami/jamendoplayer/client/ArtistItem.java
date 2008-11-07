package com.objetdirect.tatami.jamendoplayer.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.objetdirect.tatami.client.tree.Tree;

public class ArtistItem extends SearchTreeItem {

	public static final String idKey = "id";
	public static final String nameKey = "name";
	public static final String url = "url";
	public static final String image = "image";
	public static final String genre = "genre";
	
	public static final String[] attributesToFill = { idKey , nameKey , url , image  , genre};
	
	public ArtistItem(String label , String id){
		super(label , id);
	}
	
	
	public ArtistItem(JavaScriptObject jsonItem){
		this.setValue("type", "artist");
		for(int i = 0; i < attributesToFill.length ; i++){
			String attrName = attributesToFill[i];
			this.setValue(attrName, JamendoQueryMaker.extractFieldFromOBJ(jsonItem, attrName));
		}
		this.setValue("label", JamendoQueryMaker.extractFieldFromOBJ(jsonItem, "name") );
		loadingItem = new LoadingItem("Please wait while loading albums ... " , "__ALBUM_LOADING_ITEM__");
		this.setValue(Tree.folderOpenedClassAttribute , "artistTreeItemOpen");
		this.setValue(Tree.folderClosedClassAttribute ,"artistTreeItem");
		this.setValue(Tree.folderOpenedClassAttribute ,"artistTreeItemLabel");
		this.addChild(loadingItem);
	}
	
	
	
}
