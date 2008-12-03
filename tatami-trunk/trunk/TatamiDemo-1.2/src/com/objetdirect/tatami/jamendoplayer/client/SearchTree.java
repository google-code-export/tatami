package com.objetdirect.tatami.jamendoplayer.client;

import java.util.ArrayList;
import java.util.Collection;

import com.google.gwt.core.client.JavaScriptObject;
import com.objetdirect.tatami.client.data.Item;
import com.objetdirect.tatami.client.tree.Tree;
import com.objetdirect.tatami.client.tree.TreeListener;
import com.objetdirect.tatami.jamendoplayer.client.JamendoQueryMaker.JSONHandler;

public class SearchTree extends Tree{

private static SearchTree instance;
	

	private SearchTree(SearchTreeStore store) {
		super(new SearchTreeItem("Collection","Collection"),store);
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
