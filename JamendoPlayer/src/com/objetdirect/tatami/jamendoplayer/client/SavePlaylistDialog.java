package com.objetdirect.tatami.jamendoplayer.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.Button;
import com.objetdirect.tatamix.client.widget.Title;

public class SavePlaylistDialog extends PopupPanel{

	private String currentPlaylistName;
	private TextBox savePlaylistBox;
	
	public String getCurrentPlaylistName() {
		return currentPlaylistName;
	}

	public void setCurrentPlaylistName(String currentPlaylistName) {
		this.currentPlaylistName = currentPlaylistName;
	}

	private void savePlaylist(String playlistName){
		try{
			currentPlaylistName = playlistName;
			PlaylistCookieManager.getInstance().saveState(currentPlaylistName);
			this.hide();
		}catch(IllegalArgumentException e){
			Window.alert("Please choose a valid name for your playlist.");
		}
	}
	
	public SavePlaylistDialog(){
		super(false,true);
		this.hide();
		FlowPanel content = new FlowPanel();
		this.add(content);
		this.setStyleName("savePlaylistPopup");
		this.center();
		savePlaylistBox = new TextBox();
		savePlaylistBox.addKeyboardListener(new KeyboardListener() {
			public void onKeyUp(Widget sender, char keyCode, int modifiers) {
			}
			public void onKeyPress(Widget sender, char keyCode, int modifiers) {
				if(keyCode == '\n'){
					savePlaylist(savePlaylistBox.getText());
				}
			}
		
			public void onKeyDown(Widget sender, char keyCode, int modifiers) {
			}
		});
		Button button = new Button("Save",  new ClickListener() {
			public void onClick(Widget sender) {
				savePlaylist(savePlaylistBox.getText());
			}
		});
		content.add(new Title("Save playlist ..."));
		content.add(savePlaylistBox);
		content.add(button);
		this.hide();
	}
	
	
}
