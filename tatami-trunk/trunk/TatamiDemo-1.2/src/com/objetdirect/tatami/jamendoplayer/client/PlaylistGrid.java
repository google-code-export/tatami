package com.objetdirect.tatami.jamendoplayer.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.miller.gwt.client.sound.Callback;


import com.objetdirect.tatami.client.data.AbstractDataStore;
import com.objetdirect.tatami.client.data.Item;
import com.objetdirect.tatami.client.data.LoadItemListener;
import com.objetdirect.tatami.client.grid.Cell;
import com.objetdirect.tatami.client.grid.GridDataStore;
import com.objetdirect.tatami.client.grid.RowStyler;
import com.objetdirect.tatami.client.grid.Grid;
import com.objetdirect.tatami.client.grid.GridLayout;
import com.objetdirect.tatami.client.grid.GridListener;
import com.objetdirect.tatami.client.grid.GridView;

public class PlaylistGrid extends Grid implements LoadItemListener , Callback{

	private static PlaylistGrid instance;
	
	private GridLayout mainlayout;
	
	private List<SearchTreeItem> waitingToBeLoaded = new ArrayList<SearchTreeItem>();
	
	private class MyStyler implements RowStyler{

		public String getRowCSSClasses(int rowIndex, boolean selected,
				boolean mouseover, boolean odd) {
			Item corresponding = getItemFromRow(rowIndex);
			if(corresponding == null){
				return "";
			}
			Boolean bool = (Boolean) corresponding.getValue("isPlayed", Boolean.FALSE);
			if(bool.booleanValue()){
				return "currentlyPlayed";
			}else{ 
				return "";
			}
		}

		public String getRowCSSStyles(int rowIndex, boolean selected,
				boolean mouseover, boolean odd) {
			return "";
		}
		
	}
	
	private PlaylistGrid(AbstractDataStore store) {
		super();
		this.addFilter("type", "track");
		this.setStyler(new MyStyler());
		this.addGridListener(new GridListener(){
		
			public void onSelectionChanged(Grid grid) {
			}
		
		
			public void onCellDblClick(Grid grid, int rowIndex, int colIndex,
					String colField) {
				if(colIndex == 4){
					PlaylistGrid.getInstance().removeRow(rowIndex);
					return;
				}
				Player.play((MusicItem) getItemFromRow(rowIndex));
			}
		
			public void onCellClick(Grid grid, int rowIndex, int colIndex,
					String colField) {
			}

			public void onDataChange(Grid grid, Item itemWhichChanged,
					String attributeName, Object oldValue, Object newValue) {
				
			}
		});
		setStore(store);
		mainlayout = new GridLayout();
		GridView view = new GridView();
		view.setWidth("100%");
		Cell trackNameCell = new Cell("track_name","Title");
		Cell duration = new Cell("track_duration","Duration");
		trackNameCell.setWidth("auto");
		Cell artistName = new Cell("artist_name" , "Artist");
		Cell albumName = new Cell("album_name" , "Album");
		Cell eraseFromGrid = new Cell("Remove");
		eraseFromGrid.setDefaultValue("<img src='icons/process-stop.png' alt='Remove from playlist'></img>");
		view.addCellToLastRow(trackNameCell);
		view.addCellToLastRow(artistName);
		view.addCellToLastRow(albumName);
		view.addCellToLastRow(duration);
		view.addCellToLastRow(eraseFromGrid);
		mainlayout.addView(view);
		setLayout(mainlayout);
		setAutoWidth(false);
		setAutoHeight(false);
		setSize("100%", "100%");
		setElasticView(2);
		setStyleName("gridPanel");
	}

	public static PlaylistGrid getInstance(){
		if(instance ==null){
			instance = new PlaylistGrid(new GridDataStore());
		}
		return instance;
	}

	public void onLoad(Item item) {
		if(item instanceof AlbumItem){
			if(waitingToBeLoaded.contains(item)){
				waitingToBeLoaded.remove(item);
				addAlbumToPlaylist((AlbumItem) item);
			}
		}
		if(item instanceof ArtistItem){
			if(waitingToBeLoaded.contains(item)){
				waitingToBeLoaded.remove(item);
				addArtistToPlaylist((ArtistItem) item);
			}
		}
	}
	
	public void addTrackToPlaylist(MusicItem track){
		this.addRow(track);
	}
	
	public void addItemToPlaylist(Item item) throws Exception{
		if(item instanceof AlbumItem){
			addAlbumToPlaylist((AlbumItem) item);
		}else
		if(item instanceof ArtistItem){
			addArtistToPlaylist((ArtistItem) item);
		}else
		if(item instanceof MusicItem){
			addTrackToPlaylist((MusicItem) item);
		}else{
			throw new IllegalArgumentException("This Item cannot be added to the playlist");
		}
	}
	
	public void addAlbumToPlaylist(AlbumItem album){
		List<?> tracks = (List<?>) SearchTree.getInstance().getStore().getValue(album, "children", null);
		if(album.isFullyLoaded()){
			for (Iterator<?> iterator = tracks.iterator(); iterator.hasNext();) {
				MusicItem track = (MusicItem) iterator.next();
				addTrackToPlaylist(track);
			}
		}else{
			waitingToBeLoaded.add(album);
		}
	}
	
	public void addArtistToPlaylist(ArtistItem artist){
		List<?> tracks = (List<?>) SearchTree.getInstance().getStore().getValue(artist, "children", null);
		if(artist.isFullyLoaded()){
			for (Iterator<?> iterator = tracks.iterator(); iterator.hasNext();) {
				AlbumItem album = (AlbumItem) iterator.next();
				addAlbumToPlaylist(album);
			}
		}else{
			waitingToBeLoaded.add(artist);
		}
	}

	public void execute() {
		Player.getLastPlayed().setValue("isPlayed", Boolean.FALSE);
		Player.getPlayed().setValue("isPlayed", Boolean.TRUE);
	}
	
	
}
