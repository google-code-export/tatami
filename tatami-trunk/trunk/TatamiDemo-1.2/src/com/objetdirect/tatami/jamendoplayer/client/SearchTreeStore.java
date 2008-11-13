package com.objetdirect.tatami.jamendoplayer.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.objetdirect.tatami.client.data.Item;
import com.objetdirect.tatami.client.data.Request;
import com.objetdirect.tatami.client.tree.TreeStore;
import com.objetdirect.tatami.jamendoplayer.client.JamendoQueryMaker.JSONHandler;

class SearchTreeStore extends TreeStore implements JSONHandler {
	
	
	public boolean isItemLoaded(Item item) {
		return true;
	}

	public boolean loadItemImpl(Item item) {
		return true;
	}

	public void fetch(Request request) {
		request.addSortParameter("label",true);
		super.fetch(request);
	}
	
	public void handleJSON(JavaScriptObject obj  , SearchTreeItem parent) {
		int length = JamendoQueryMaker.getLength(obj);
		if(parent instanceof LetterItem){
			LetterItem letter = (LetterItem) parent;
			Item[] items = new Item[length];
			for(int i = 0 ; i < length ; i++){
				Item artistItem = new ArtistItem(JamendoQueryMaker.extractItemsFromResponse(obj, i));
				items[i] = artistItem;
			}
			letter.setChildHaveBeenLoaded(true);
			this.addChildToItem(parent, items);
			int pageNb = letter.getNbChildPagesAlreadyLoaded() +1;
			letter.setNbChildPagesAlreadyLoaded(pageNb);
			if(length >= 50){
				JamendoQueryMaker.searchArtistByLetter(this,letter);
			}else if(letter.getCurrentLetterRequested() < letter.getLetters().length -1 ){
					letter.setCurrentLetterRequested(letter.getCurrentLetterRequested()+1);
					JamendoQueryMaker.searchArtistByLetter(this,letter);
			}else{
				letter.removeChild(letter.getLoadingItem());
			}
		}
		if(parent instanceof ArtistItem){
			ArtistItem artist = (ArtistItem) parent;
			Item[] items = new Item[length];
			for(int i = 0 ; i < length ; i++){
				Item albumItem = new AlbumItem(JamendoQueryMaker.extractItemsFromResponse(obj, i));
				items[i] = albumItem;
			}
			artist.setChildHaveBeenLoaded(true);
			this.addChildToItem(artist, items);
			artist.removeChild(artist.getLoadingItem());
			notifyLoadItemListeners(artist);
		}
		
		if(parent instanceof AlbumItem){
			AlbumItem album = (AlbumItem) parent;
			MusicItem[] items = new MusicItem[length];
			List<MusicItem> list = new ArrayList<MusicItem>();
			for(int i = 0 ; i < length ; i++){
				MusicItem trackItem = new MusicItem(JamendoQueryMaker.extractItemsFromResponse(obj, i));
				list.add(trackItem);
			}
			Collections.sort(list,new TrackNumberComparator());
			album.setChildHaveBeenLoaded(true);
			album.addChildren(list.toArray(items));
			album.removeChild(album.getLoadingItem());
			notifyLoadItemListeners(album);
		}
	}
	
	private class TrackNumberComparator implements Comparator<MusicItem>{
		public int compare(MusicItem o1, MusicItem o2) {
			return new Integer((String) o1.getValue("numalbum", "-1")).compareTo(new Integer( (String) o2.getValue("numalbum", "-1")));
		}
	}
	
	public void addChildToItem(Item parent, Item[] children){
		for (int i = 0; i < children.length; i++) {
			parent.addChild(children[i]);
		}
	}
	
	public Object getValue(Item item, String attribute, Object defaultValue) {
		try{
			SearchTreeItem concreteItem = (SearchTreeItem) item;
		
		if(attribute.equalsIgnoreCase("children") && !concreteItem.isChildHaveBeenLoaded()){
			if(concreteItem instanceof LetterItem){
				JamendoQueryMaker.searchArtistByLetter(this, (LetterItem)concreteItem);
			}
			if(concreteItem instanceof ArtistItem){
				JamendoQueryMaker.searchAlbumByArtist(this, (ArtistItem)concreteItem );
			}
			if(concreteItem instanceof AlbumItem){
				JamendoQueryMaker.searchTracksByAlbum(this,(AlbumItem)concreteItem );
			}
			return concreteItem.getChildren();
		}
		}catch(ClassCastException e){
			
		}
		return item.getValue(attribute, defaultValue);
	}




	

	
}
