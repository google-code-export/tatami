package com.objetdirect.tatami.jamendoplayer.client;

import com.google.gwt.core.client.JavaScriptObject;

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
		this.addAttribute("type", "artist");
		for(int i = 0; i < attributesToFill.length ; i++){
			String attrName = attributesToFill[i];
			this.addAttribute(attrName, JamendoQueryMaker.extractFieldFromOBJ(jsonItem, attrName));
		}
		this.addAttribute("label", JamendoQueryMaker.extractFieldFromOBJ(jsonItem, "name") );
		loadingItem = new LoadingItem("Please wait while loading albums ... " , "__ALBUM_LOADING_ITEM__");
		this.setFolderOpenIconClass("artistTreeItemOpen");
		this.setFolderClosedIconClass("artistTreeItem");
		this.setLabelClass("artistTreeItemLabel");
		this.addChild(loadingItem);
	}
	
	
	
}
