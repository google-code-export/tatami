package com.objetdirect.tatami.jamendoplayer.client;

import com.google.gwt.core.client.GWT;
import com.objetdirect.tatami.client.data.Item;
import com.objetdirect.tatami.client.tree.Tree;
import com.objetdirect.tatami.client.tree.TreeListener;

public class SearchTree extends Tree{

private static SearchTree instance;
	

	private SearchTree(SearchTreeStore store) {
		super(new SearchTreeItem("Collection","Collection"),store);
		getRootItem().setValue(folderClosedClassAttribute, "collection");
		getRootItem().setValue(leafClassAttribute, "collection");
		getRootItem().setValue(folderOpenedClassAttribute, "collectionOpen");
		this.addTreeListener(new TreeListener(){
			public void onClick(Item item) {
			}
			public void onClose(Item item) {
			}
			public void onDblClick(Item item) {
				try{
					PlaylistGrid.getInstance().addItemToPlaylist(item);
				}catch(Exception e){
					
				}
			}
			public void onOpen(Item item) {
			}
		});
		setSize("100%","100%");
		
	}

	public static SearchTree getInstance(){
		if(instance ==null){
			instance = new SearchTree(new SearchTreeStore());
		}
		return instance;
	}
	
	public boolean mayHaveChildren(Item item) {
		if(item instanceof LoadingItem || item instanceof MusicItem){
			return false;
		}else{
			return true;
		}
	}

	
	
}
