package com.objetdirect.tatami.jamendoplayer.client;

import java.util.ArrayList;
import java.util.Collection;

import org.miller.gwt.client.sound.Callback;
import org.miller.gwt.client.sound.SoundManager;

import com.objetdirect.tatami.client.data.Item;
import com.objetdirect.tatami.client.grid.GridSelectionManager;

public class Player {

	private static MusicItem played;
	private static MusicItem lastPlayed;
	
	public static MusicItem getPlayed() {
		return played;
	}

	public static MusicItem getLastPlayed() {
		return lastPlayed;
	}

	
	private static int lastSeenAtIndex;
	private static boolean isPlaying = false;
	private static GridSelectionManager selectionManager;
	
	private static Collection<Callback> whileplayingcallbacks = new ArrayList<Callback>();
	private static Collection<Callback> onPlayCallBacks = new ArrayList<Callback>();
	private static SoundManager sm;
	
	private static Callback  whilePlaying;
	
	static{
		whilePlaying = new Callback(){
			public void execute() {
				for (Callback callback : whileplayingcallbacks) {
					callback.execute();
				}
			}
		};
		sm = SoundManager.getInstance();
		sm.getDefaultOptions().onLoad(new Callback() {
			public void execute() {
				sm.setNullURL("./null.mp3");
			}
		});
		sm.getDefaultOptions().onBeforeFinish(new Callback(){
			public void execute() {
				next();
			}
		});
		
		
		selectionManager = new GridSelectionManager(PlaylistGrid.getInstance());
		
		
		
	}
	
	public static void addCallback(Callback callback){
		whileplayingcallbacks.add(callback);
	}
	
	public static void removeCallback(Callback callback){
		whileplayingcallbacks.remove(callback);
	}
	
	public static void addOnPlayCallback(Callback callback){
		onPlayCallBacks.add(callback);
	}
	
	public static void removeOnPlayCallback(Callback callback){
		onPlayCallBacks.remove(callback);
	}
	
	public static int getCurrentPosition(){
		return SoundManager.getInstance().getSoundById("0").getPosition();
	}
	
	public static int getDurationEstimate(){
		return SoundManager.getInstance().getSoundById("0").getDurationEstimate();
	}
	
	public static int getDurationLoaded(){
		return SoundManager.getInstance().getSoundById("0").getDuration();
	}
	
	public static void play(){
		MusicItem toBePlayed = getFirstItemToBePlayed();
		if(toBePlayed != null){
			play(toBePlayed);
		}
	}
	
	public static void setVolume(int volume){
		SoundManager.getInstance().setVolume("0", volume);
	}
	
	public static void play(MusicItem item){
		lastPlayed = played;
		if(lastPlayed != null){
			lastPlayed.setValue("isPlayed", false);
		}
		played = item;
		played.setValue("isPlayed",true);
		isPlaying = true;
		sm.setAllowPolling(true);
		sm.getDefaultOptions().whilePlaying(whilePlaying);
		SoundManager.getInstance().destroySound("0");
		SoundManager.getInstance().createSound("0", "http://api.jamendo.com/get2/stream/track/redirect/?id="+(String)item.getId() + "&streamencoding=mp31");
		SoundManager.getInstance().play("0");
		for(Callback cb : onPlayCallBacks){
			cb.execute();
		}
	}
	
	public static MusicItem getNextItemToBePlayed(){
		int rowCount = PlaylistGrid.getInstance().getRowCount();
		if(rowCount >0){
			if(isPlaying){
				int currentindex  =  PlaylistGrid.getInstance().getRowFromItem(played);
				currentindex = currentindex >= 0 ? currentindex : lastSeenAtIndex;
				int nextIndex = (currentindex + 1) %  rowCount;
				selectionManager.setMode(GridSelectionManager.SELECTION_MODE_SINGLE);
				selectionManager.addToSelection(nextIndex);
				return (MusicItem) PlaylistGrid.getInstance().getItemFromRow(nextIndex);
			}else{
				return getFirstItemToBePlayed();
			}
		}else{
			return null;
		}
	}
	
	public static MusicItem getPreviousItemToBePlayed(){
		int rowCount = PlaylistGrid.getInstance().getRowCount();
		if(rowCount >0){
			if(isPlaying){
				int currentindex  =  PlaylistGrid.getInstance().getRowFromItem(played);
				currentindex = currentindex >= 0 ? currentindex : lastSeenAtIndex;
				int previousIndex = (currentindex - 1) %  rowCount;
				selectionManager.setMode(GridSelectionManager.SELECTION_MODE_SINGLE);
				selectionManager.addToSelection(previousIndex);
				return (MusicItem) PlaylistGrid.getInstance().getItemFromRow(previousIndex);
			}else{
				return getFirstItemToBePlayed();
			}
		}else{
			return null;
		}
	}
	
	public static MusicItem getFirstItemToBePlayed(){
		PlaylistGrid grid = PlaylistGrid.getInstance();
		if(grid.getRowCount() > 0){
			Item[] selectedItems =  grid.getSelectedItems();
			if(selectedItems.length > 0){
				return (MusicItem)selectedItems[0];
			}else{
				return (MusicItem) grid.getItemFromRow(0); 
			}
		}else{
			return null;
		}
	}
	
	public static void pause(){
		SoundManager.getInstance().togglePause("0");
	}
	
	public static void stop(){
		isPlaying = false;
		SoundManager.getInstance().stop("0");
	}
	
	public static void next(){
		MusicItem toBePlayed = getNextItemToBePlayed();
		if(toBePlayed != null){
			play(toBePlayed);
		}
	}
	 
	public static void previous(){
		MusicItem toBePlayed = getPreviousItemToBePlayed();
		if(toBePlayed != null){
			play(toBePlayed);
		}
	}
	
	public static void goToPosition(int percent){
		int estimatePositionToGo = SoundManager.getInstance().getSoundById("0").getDurationEstimate() * percent / 100;
		if(Math.abs((estimatePositionToGo - getCurrentPosition()))<2){
			return;
		}
		SoundManager.getInstance().setPosition("0",estimatePositionToGo *1000 );
	}
	
	
}
