package com.objetdirect.tatami.jamendoplayer.client;

import java.util.Collection;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.Button;
import com.objetdirect.tatamix.client.widget.Title;

public class LoadPlaylistDialog extends PopupPanel{

	ListBox availablePlaylists = new ListBox();
	
	public LoadPlaylistDialog(){
		super(false,true);
		this.hide();
		FlowPanel content = new FlowPanel();
		this.add(content);
		this.center();
		this.setStyleName("loadPlaylistDialog");
		content.add(new Title("Load Playlist"));
		content.add(availablePlaylists);
		Button loadPlaylistButton = new Button("Load playlist");
		loadPlaylistButton.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				PlaylistCookieManager.getInstance().restoreState(availablePlaylists.getValue(availablePlaylists.getSelectedIndex()));
				hide();
			}
		});
		content.add(loadPlaylistButton);
		this.hide();
	}
	
	@Override
	public void show(){
		Collection<String> playlists = PlaylistCookieManager.getInstance().getKnownPlaylists();
		availablePlaylists.clear();
		for (String string : playlists) {
			availablePlaylists.addItem(string);
		}
		super.show();
	}
	
}
