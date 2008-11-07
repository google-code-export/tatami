package com.objetdirect.tatami.jamendoplayer.client;

import org.miller.gwt.client.sound.Callback;

import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.Button;
import com.objetdirect.tatami.client.Slider;

public class ControlPanel extends FlowPanel implements Callback{

	Slider positionSlider;
	Slider volumeSlider;
	
	public ControlPanel(){
		super();
		this.setStyleName("buttonPanel");
		Player.addCallback(this);
		Button previousButton = new Button("","button-icon-previous");
		previousButton.setStyleName("button-previous");
		Button playButton = new Button("","button-icon-play");
		playButton.setStyleName("button-play");
		Button pauseButton = new Button("","button-icon-pause");
		pauseButton.setStyleName("button-pause");
		Button stopButton = new Button("","button-icon-stop");
		stopButton.setStyleName("button-stop");
		Button nextButton = new Button("","button-icon-next");
		nextButton.setStyleName("button-next");

		previousButton.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				Player.previous();
			};
		});
		nextButton.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				Player.next();
			};
		});
		playButton.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				Player.play();
			};
		});
		pauseButton.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				Player.pause();
			};
		});
		stopButton.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				Player.stop();
			};
		});
		volumeSlider = new Slider(Slider.HORIZONTAL , 0 , 100 , 75, true);
		volumeSlider.addChangeListener(new ChangeListener() {
			public void onChange(Widget sender) {
				Player.setVolume(((Slider)sender).getValue());
			}
		});
		volumeSlider.setStyleName("volumeSlider");
		positionSlider = new Slider(Slider.HORIZONTAL , 0 , 100 , 0 , true);
		positionSlider.addChangeListener(new ChangeListener() {
			public void onChange(Widget sender) {
				Player.goToPosition(((Slider)sender).getValue());
			}
		});
		positionSlider.setStyleName("positionSlider");
		FlowPanel volumePanel= new FlowPanel();
		volumePanel.setStyleName("volumePanel");
		volumePanel.add(volumeSlider);
		this.add(previousButton);
		this.add(playButton);
		this.add(pauseButton);
		this.add(stopButton);
		this.add(nextButton);
		this.add(volumePanel);
		this.add(positionSlider);
	}

	public void execute() {
		int currentPos = Player.getCurrentPosition();
		int estimate = Player.getDurationEstimate();
		float grou = ((float)currentPos / (float)estimate)*100;
		positionSlider.setValue((int) grou); 
	}

}
