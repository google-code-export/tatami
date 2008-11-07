package com.objetdirect.tatami.jamendoplayer.client;

import org.miller.gwt.client.sound.Callback;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.objetdirect.tatami.client.charting.Chart2D;
import com.objetdirect.tatami.client.charting.PiePiece;
import com.objetdirect.tatami.client.charting.PiePlot;
import com.objetdirect.tatami.client.charting.Serie;
import com.objetdirect.tatami.jamendoplayer.client.JamendoQueryMaker.JSONHandler;
import com.objetdirect.tatamix.client.widget.ImageLink;
import com.objetdirect.tatamix.client.widget.SpanText;
import com.objetdirect.tatamix.client.widget.Title;

public class CurrentInfoPanel extends FlowPanel implements Callback{

	
	private ImageLink albumImage;
	private SpanText currentArtist = new SpanText();
	private SpanText currentAlbum = new SpanText();
	private SpanText currentTitle = new SpanText();
	private Chart2D tagWeight;
	
	private class PlayerListenerSpanText extends SpanText implements Callback{
		public void execute() {
			int currentPos = Player.getCurrentPosition();
			int min = currentPos / 60;
			String minStr = min < 10 ? "0"+min :""+ min;
			int sec = currentPos%60;
			String secStr = sec < 10 ? "0"+sec : "" + sec;
			int totalDuration = Player.getDurationEstimate();
			int minTotal = totalDuration / 60;
			String minTotalStr = minTotal < 10 ? "0"+minTotal :""+ minTotal;
			int secTotal = totalDuration%60;
			String secTotalStr = secTotal < 10 ? "0"+secTotal : "" + secTotal;
			this.setText(minStr + ":" + secStr +" / " + minTotalStr + ":" + secTotalStr);
		}
	}
	
	 
	 public CurrentInfoPanel(){
		  this.setStyleName("contextInfoPanel");
		  PlayerListenerSpanText durationSpan = new PlayerListenerSpanText();
		  Player.addCallback(durationSpan);
		  Player.addOnPlayCallback(this);
		  SimplePanel imageContainer = new SimplePanel();
		  albumImage = new ImageLink();
		  albumImage.setImageSrc("icons/default-cover.png");
		  imageContainer.setStyleName("contextInfoCover");
		  imageContainer.add(albumImage);
		  this.add(imageContainer);
		  FlowPanel textInfo = new FlowPanel();
		  textInfo.setStyleName("contextInfoTextDiv");
		  textInfo.add(new Title("Now Playing"));
		  textInfo.add(initCurrentArtistPanel());
		  textInfo.add(initCurrentAlbumPanel());
		  textInfo.add(initCurrentTitlePanel());
		  textInfo.add(durationSpan);
		  this.add(textInfo);
		  tagWeight = initChart();
		  this.add(tagWeight);
	 }
	 
	 private Chart2D initChart(){
		 Chart2D chart = new Chart2D("80px","80px");
		 PiePlot<PiePiece> plot = new PiePlot<PiePiece>();
		 chart.addPlot(plot);
		 plot.addSerie(new Serie<PiePiece>("default"));
		 return chart;
	 }
	 
	 private Panel initCurrentArtistPanel(){
		 FlowPanel panel = new FlowPanel();
		 SpanText artistLabel = new SpanText();
		 artistLabel.setHTML("<b>Artist :</b>");
		 panel.add(artistLabel);
		 panel.add(currentArtist);
		 panel.setStyleName("contextInfoCurrentArtist");
		 return panel;
	 }
	 
	 private Panel initCurrentAlbumPanel(){
		 FlowPanel panel = new FlowPanel();
		 SpanText albumLabel = new SpanText();
		 albumLabel.setHTML("<b>Album :</b>");
		 panel.add(albumLabel);
		 panel.add(currentAlbum);
		 panel.setStyleName("contextInfoCurrentAlbum");
		 return panel;
	 }
	 private Panel initCurrentTitlePanel(){
		 FlowPanel panel = new FlowPanel();
		 SpanText titleLabel = new SpanText();
		 titleLabel.setHTML("<b>Title :</b>");
		 panel.add(titleLabel);
		 panel.add(currentTitle);
		 panel.setStyleName("contextInfoCurrentTitle");
		 return panel;
	 }


	public void execute() {
		albumImage.setImageSrc((String) Player.getPlayed().getValue(MusicItem.album_image, "icons/default-cover.png"));
		currentArtist.setText((String) Player.getPlayed().getValue(MusicItem.artist_name, "Unknown artist"));
		currentAlbum.setText((String) Player.getPlayed().getValue(MusicItem.album_name, "Unknown album"));
		currentTitle.setText((String) Player.getPlayed().getValue(MusicItem.nameKey, "Unknown Title"));
	}

	
}
