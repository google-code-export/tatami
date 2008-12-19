package com.objetdirect.tatami.jamendoplayer.client;

import org.miller.gwt.client.sound.Callback;

import com.objetdirect.tatami.client.Toaster;

public class OSDToaster extends Toaster implements Callback{

	public OSDToaster() {
		super(Toaster.PLAIN_MESSAGE, Toaster.BOTTOM_RIGHT_LEFT);
		this.setDuration(2000);
		//this.setSize("300px", "500px");
		Player.addOnPlayCallback(this);
		this.setStyleName("toaster");
	}

	public void execute() {
		MusicItem item = Player.getPlayed();
		String content = "<h1 class='toasterTitle'>Now Playing:</h1>" +
			"<span class='toasterTrack'><b>Track:</b>"+item.getValue(MusicItem.nameKey, "Unknown album") + "</span><br/>" +		
			"<span class='toasterArtist'><b>Artist:</b>"+item.getValue(MusicItem.artist_name, "Unknown artist") + "</span><br/>" +
			"<span class='toasterAlbum'><b>Album:</b>"+item.getValue(MusicItem.album_name, "Unknown album") + "</span><br/>" +
			"<img height='100px' width='100px' class='toasterCover' src='" + item.getValue(MusicItem.album_image, "icons/default-cover.png") + "'/>";
		this.setMessage(content);
		this.show();
	}

}
