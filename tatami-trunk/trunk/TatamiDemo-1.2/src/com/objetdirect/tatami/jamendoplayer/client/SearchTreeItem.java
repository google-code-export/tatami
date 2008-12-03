package com.objetdirect.tatami.jamendoplayer.client;

import java.util.List;

import com.objetdirect.tatami.client.data.Item;

public class SearchTreeItem extends Item{

	public static final String idKey = "id";
	private boolean childHaveBeenLoaded = false;
	
	final public static String typeKey = "___type"; 
	
	Item loadingItem;
	
	{
		setFullyLoaded(false); 
	}
	
	public Item getLoadingItem() {
		return loadingItem;
	}
	

	public SearchTreeItem() {
		super();
	}

	public SearchTreeItem(String id, String label) {
		super(id, label);
	}

	@Override
	public Object getId() {
		return attributes.get(idKey);
	}

	@Override
	public void setId(Object id) {
		attributes.put(idKey,id);
	}

	
	
}
