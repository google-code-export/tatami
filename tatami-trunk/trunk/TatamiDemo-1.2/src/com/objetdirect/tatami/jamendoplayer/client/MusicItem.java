package com.objetdirect.tatami.jamendoplayer.client;

import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.objetdirect.tatami.client.data.Item;
import com.objetdirect.tatami.client.tree.Tree;

public class MusicItem extends SearchTreeItem{

	public static final String type="track";
	
	public static final String idKey = "track_id";
	public static final String nameKey = "track_name";
	public static final String url = "url";
	public static final String image = "image";
	public static final String album_name = "album_name";
	public static final String album_id = "album_id";
	public static final String artist_name = "artist_name";
	public static final String artist_id = "artist_id";
	public static final String duration = "track_duration";
	public static final String album_image = "album_image";
	public static final String track_number = "numalbum";
	
	public static final String[] attributesToFill = { idKey , nameKey , url , image , album_name , album_id ,  artist_id , duration , album_image , artist_name , track_number};
	
	public MusicItem(JavaScriptObject jsonItem){
		this.setValue(typeKey, type);
		for(int i = 0; i < attributesToFill.length ; i++){
			String attrName = attributesToFill[i];
			this.setValue(attrName, JamendoQueryMaker.extractFieldFromOBJ(jsonItem, attrName));
		}
		this.setValue(Tree.leafClassAttribute,"musicTreeItem");
		this.setValue(Tree.labelClassAttribute,"musicTreeItemLabel");
		this.setId(JamendoQueryMaker.extractFieldFromOBJ(jsonItem, idKey));
		this.setLabel(JamendoQueryMaker.extractFieldFromOBJ(jsonItem,track_number) + " - " + JamendoQueryMaker.extractFieldFromOBJ(jsonItem, nameKey) );
	}
	
	
	
	public MusicItem(String label, String id) {
		super(label, id);
	}

	@Override
	public Object getId() {
		return attributes.get(idKey);
	}

	@Override
	public void setId(Object id) {
		attributes.put(idKey, id);
	}



	@Override
	public String getLabel() {
		return attributes.get(track_number) + " - " + attributes.get(nameKey);
	}



	@Override
	public void setLabel(String label) {
		throw new UnsupportedOperationException("You cannot set this item label");
	}



	@Override
	public String[] getLabelAttributes() {
		return new String[]{track_number,nameKey};
	}
	
	
	
}
