package com.objetdirect.tatami.jamendoplayer.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.objetdirect.tatami.client.data.DefaultDataStore;
import com.objetdirect.tatami.client.data.Item;
import com.objetdirect.tatami.client.data.Request;
import com.objetdirect.tatami.jamendoplayer.client.JamendoQueryMaker.JSONHandler;

class SearchTreeStore extends DefaultDataStore implements JSONHandler {
	
	private Map<Item, Request> pendingRequests = new HashMap<Item, Request>();
	

	
	public SearchTreeStore(){
	}
	
	@Override
	public boolean loadItem(Item item) {
		if(isItemLoaded(item)){
			return false;
		}else{
			if(item instanceof ArtistItem){
				JamendoQueryMaker.searchAlbumByArtist(this, (ArtistItem) item);
			}else if(item instanceof AlbumItem){
				JamendoQueryMaker.searchTracksByAlbum(this, (AlbumItem) item);
			}else if(item instanceof LetterItem){
				JamendoQueryMaker.searchArtistByLetter(this,(LetterItem)item);
			}else if(item.getParentItem() == null){
				JamendoQueryMaker.searchGenre(this,(SearchTreeItem)item);
			}else{
				item.setFullyLoaded(true);
				notifyLoadItemListeners(item);
				pendingRequests.remove(item);
				return false;
			}
			return true;
		}
	}

	public void fetch(Request request) {
		boolean canFetch = true;
		if(request.getQuery().containsKey(Item.parentAttribute)){
			Item parentItem = (Item) request.getQuery().get(Item.parentAttribute);
			if(!isItemLoaded(parentItem)){
				pendingRequests.put(parentItem,request);
				loadItem(parentItem);
				canFetch = false;
			}
		}
		if(canFetch){
			finishFetch(request);
		}
	}
	
	private void finishFetch(Request request){
		List<Item> fetched = new ArrayList<Item>();
		fetched = this.executeQuery(items.values(),request);
		Collections.sort(fetched,new Comparator<Item>(){
			public int compare(Item o1, Item o2) {
				return o1.getLabel().compareTo(o2.getLabel());
			}
		});
		lastRequest = request;
		int size = fetched.size();
		// We notify the fetch listeners that the request is being performed
		notifyBeginFetchListeners(size , request);
		List<Item> result = new ArrayList<Item>();
		for (int i = 0 ; i < size ; i++) {
			Item item = (Item) fetched.get(i);
			notifyItemFetchListeners(item);
			result.add(item);
		}
		//Finally , we pass the result to the fetch listeners
		notifyCompleteFetchListeners(result , request);
	}
	
	public void handleJSON(JavaScriptObject obj  , SearchTreeItem parent) {
		int length = JamendoQueryMaker.getLength(obj);
		List<Item> items = new ArrayList<Item>();
		if(parent.getParentItem() == null){
			for(int i = 0 ; i < length ; i++){
				SearchTreeItem genreItem = new GenreItem(JamendoQueryMaker.extractItemsFromResponse(obj, i));
				genreItem.setFullyLoaded(true);
				items.add(genreItem);
				String num = "0-9";
				LetterItem numbers = new LetterItem(num);
				List<Item> children = new ArrayList<Item>();
				numbers.setId("Letter-"+num+"-"+genreItem.getLabel());
				children.add(numbers);
				for(char j = 'A'; j<= 'Z' ; j++){
					String letters = j+"";
					LetterItem letter = new LetterItem(letters);
					letter.setId("Letter-"+letter+"-"+genreItem.getLabel());
					children.add(letter);
				} 
				genreItem.setFullyLoaded(true);
				genreItem.addChildren(children);
			}
			parent.setFullyLoaded(true);
			parent.addChildren(items);
		}
		if(parent instanceof LetterItem){
			for(int i = 0 ; i < length ; i++){
				Item artistItem = new ArtistItem(JamendoQueryMaker.extractItemsFromResponse(obj, i));
				items.add(artistItem);
			}
			parent.setFullyLoaded(true);
			parent.addChildren(items);
			notifyLoadItemListeners(parent);
		}
		if(parent instanceof ArtistItem){
			ArtistItem artist = (ArtistItem) parent;
			for(int i = 0 ; i < length ; i++){
				Item albumItem = new AlbumItem(JamendoQueryMaker.extractItemsFromResponse(obj, i));
				items.add(albumItem);
			}
			parent.setFullyLoaded(true);
			artist.addChildren(items);
			artist.removeChild(artist.getLoadingItem());
			notifyLoadItemListeners(artist);
		}
		
		if(parent instanceof AlbumItem){
			AlbumItem album = (AlbumItem) parent;
			for(int i = 0 ; i < length ; i++){
				MusicItem trackItem = new MusicItem(JamendoQueryMaker.extractItemsFromResponse(obj, i));
				items.add(trackItem);
			}
			Collections.sort(items,new TrackNumberComparator());
			parent.setFullyLoaded(true);
			album.addChildren(items);
			album.removeChild(album.getLoadingItem());
			notifyLoadItemListeners(album);
		}
		Request toFinish = pendingRequests.get(parent);
		if(toFinish != null){
			finishFetch(toFinish);
			pendingRequests.remove(toFinish);
		}
		
	}
	
	private class TrackNumberComparator implements Comparator<Item>{
		public int compare(Item o1, Item o2) {
			return new Integer((String) o1.getValue("numalbum", "-1")).compareTo(new Integer( (String) o2.getValue("numalbum", "-1")));
		}
	}

	@Override
	public void onSet(Item item, String attribute, Object oldValue,
			Object newValue) {
		if(Item.childAttribute.equals(attribute)){
			return;
		}
		super.onSet(item, attribute, oldValue, newValue);
	}
	




	

	
}
