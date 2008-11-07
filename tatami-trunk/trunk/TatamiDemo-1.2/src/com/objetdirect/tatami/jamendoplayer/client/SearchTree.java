package com.objetdirect.tatami.jamendoplayer.client;

import java.util.ArrayList;
import java.util.Collection;

import com.google.gwt.core.client.JavaScriptObject;
import com.objetdirect.tatami.client.tree.Tree;
import com.objetdirect.tatami.client.tree.TreeItem;
import com.objetdirect.tatami.client.tree.TreeListener;
import com.objetdirect.tatami.jamendoplayer.client.JamendoQueryMaker.JSONHandler;

public class SearchTree extends Tree implements JSONHandler{

private static SearchTree instance;
	
	private TreeItem root;

	private SearchTree(SearchTreeStore store) {
		super(store);
		this.setRootLabel("Collection");
		this.setShowRoot(false);
		root = new TreeItem("Collection","COLLECTIONROOT");
		root.setFolderClosedIconClass("collection");
		root.setLeafIconClass("collection");
		root.setFolderOpenIconClass("collectionOpen");
		this.addRootItem(root);
		JamendoQueryMaker.searchGenre(this);
		this.addTreeListener(new TreeListener(){
			public void onClick(TreeItem item) {
			}
			public void onClose(TreeItem item) {
			}

			public void onDblClick(TreeItem item) {
				try{
					PlaylistGrid.getInstance().addItemToPlaylist(item);
				}catch(Exception e){
					
				}
			}
			public void onOpen(TreeItem item) {
			}
		});
	}

	public static SearchTree getInstance(){
		if(instance ==null){
			instance = new SearchTree(new SearchTreeStore());
		}
		return instance;
	}
	
	public boolean mayHaveChildren(TreeItem item) {
		if(item instanceof LoadingItem || item instanceof MusicItem){
			return false;
		}else{
			return true;
		}
	}

	public void handleJSON(JavaScriptObject obj, SearchTreeItem item) {
		int length = JamendoQueryMaker.getLength(obj);
		Collection<TreeItem> genres = new ArrayList<TreeItem>();
		for(int i = 0 ; i < length ; i++){
			SearchTreeItem genreItem = new GenreItem(JamendoQueryMaker.extractItemsFromResponse(obj, i));
			genres.add(genreItem);
			String[] num = {"0-9"};
			LetterItem numbers = new LetterItem(num);
			numbers.addAttribute("id","letter-"+numbers.getValue("label", null)+"-"+genreItem.getValue("label","unknown"));
			numbers.addAttribute("genre",genreItem.getValue("label","unknown"));
			addChildToItem(genreItem, numbers);
			Collection<TreeItem> children = new ArrayList<TreeItem>();
			for(char j = 'A'; j<= 'Z' ; j++){
				String[] letters = {j+""};
				LetterItem letter = new LetterItem(letters);
				letter.addAttribute("id","letter-"+letter.getValue("label", null)+"-"+genreItem.getValue("label","unknown"));
				letter.addAttribute("genre",genreItem.getValue("label","unknown"));
				children.add(letter);
			} 
			genreItem.setChildHaveBeenLoaded(true);
			addChildrenToItem(genreItem, children);
		}
		this.addChildrenToItem(root, genres);
		this.refreshTree();
	}
	
	
}
