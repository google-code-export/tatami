package com.objetdirect.tatami.jamendoplayer.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Cookies;
import com.objetdirect.tatami.client.data.Item;
import com.objetdirect.tatami.jamendoplayer.client.JamendoQueryMaker.JSONHandler;

public class PlaylistCookieManager  implements JSONHandler{

	private static PlaylistCookieManager instance;

	private Collection<String> knownPlaylists;
	
	private final static String delimiter = ":";
	
	private final static String playlistFieldName = "____savedplaylists";
	
	public  Collection<String> getKnownPlaylists() {
		return knownPlaylists;
	}


	public static PlaylistCookieManager getInstance() {
		if(instance == null){
			instance = new PlaylistCookieManager();
		}
		return instance;
	}

	private Collection<Item> list = new ArrayList<Item>();
	
	
	
	
	private PlaylistCookieManager(){
		loadPlaylistFromCookie();
	}

	private void loadPlaylistFromCookie(){
		String playlists = Cookies.getCookie(playlistFieldName);
		knownPlaylists = new ArrayList<String>();
		if(playlists!= null){
			String[] playlistsArray  = playlists.split(delimiter);
			for (int i = 0; i < playlistsArray.length; i++) {
				knownPlaylists.add(playlistsArray[i]);
			}
		}
	}
	
	public void saveState(String playlistName) throws IllegalArgumentException{
		String toRecord = "";
		if(!checkPlaylistNameIsValid(playlistName)){
			throw new IllegalArgumentException("Playlist name is not valid");
		}
		for(int i = 0; i< PlaylistGrid.getInstance().getRowCount();i++){
			toRecord += PlaylistGrid.getInstance().getStore().getIdentity(PlaylistGrid.getInstance().getItemFromRow(i)) + delimiter;
		}
		knownPlaylists.add(playlistName);
		Date date = new Date();
		date.setYear(date.getYear()+1);
		Cookies.setCookie(playlistName, toRecord ,date) ;
		String playlistList = ""; 
		for (Iterator<String> iterator = knownPlaylists.iterator(); iterator.hasNext();) {
			playlistList += (String) iterator.next() + delimiter;
		}
		Cookies.setCookie(playlistFieldName, toRecord , date);
	}
	
	public void restoreState(String playlistName) throws IllegalArgumentException{
		if(!checkPlaylistNameIsValid(playlistName) || !knownPlaylists.contains(playlistName)){
			throw new IllegalArgumentException("Playlist name is not valid");
		}
		String playlistCookie = Cookies.getCookie(playlistName);
		String[] musics = playlistCookie.split(delimiter);
		for (int i = 0; i < musics.length; i++) {
			JamendoQueryMaker.getMusicById(musics[i],this);
		}
	}

	private boolean checkPlaylistNameIsValid(String playlistName){
		if(playlistName.contains(delimiter) || playlistName.compareToIgnoreCase(playlistFieldName) == 0){
			return false;
		}
		return true;
	}
	

	public void handleJSON(JavaScriptObject obj, SearchTreeItem item) {
		MusicItem music = new MusicItem(JamendoQueryMaker.extractItemsFromResponse(obj, 0));
		PlaylistGrid.getInstance().addTrackToPlaylist(music);
	}

}
