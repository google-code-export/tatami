package com.objetdirect.tatami.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.Button;
import com.objetdirect.tatami.client.DefaultTatamiTest;
import com.objetdirect.tatami.client.test.Task;
import com.objetdirect.tatami.client.test.TestUtil;

public class ButtonTest extends DefaultTatamiTest{

	
	private final String label = "TestLabel" ; 
	
	private final String iconClass = "TestIconClass" ; 
	
	private Button getButton(){
		RootPanel.get().clear();
		Button	button = new Button(label , iconClass);
		RootPanel.get().add(button,10,10);
		return button;
	}
	

	public void testCreation(){
		Button button = getButton();
		assertEquals(label,button.getLabel());
		assertEquals(iconClass, button.getIconClass());
	}
	
	public void testSetLabel(){
		Button button = getButton();
		String testLabel = "TestLabel";
		button.setLabel(testLabel);
		assertEquals(testLabel, button.getLabel());
		button.setLabel(label);
	}
	


	protected void gwtTearDown() throws Exception {
		super.gwtTearDown();
	}
	
	
}
