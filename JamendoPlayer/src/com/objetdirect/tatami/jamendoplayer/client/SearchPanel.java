package com.objetdirect.tatami.jamendoplayer.client;


import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.objetdirect.tatami.client.Button;

public class SearchPanel extends HorizontalPanel{

	public ListBox searchOptions;
	public TextBox searchTextBox;
	public Button searchButton;
	
	
	public SearchPanel(){
		super();
		searchTextBox = new TextBox();
		searchTextBox.setStylePrimaryName("searchTextBox");
		searchButton = new Button("","searchIcon");
		this.add(searchButton);
		this.add(searchTextBox);
		this.setVerticalAlignment(ALIGN_MIDDLE);
		
		
	}
	
	
}
