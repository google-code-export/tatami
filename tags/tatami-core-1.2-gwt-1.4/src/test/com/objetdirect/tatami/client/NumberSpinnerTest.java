package com.objetdirect.tatami.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.DefaultTatamiTest;
import com.objetdirect.tatami.client.NumberSpinner;
import com.objetdirect.tatami.client.test.Task;
import com.objetdirect.tatami.client.test.TestUtil;

public class NumberSpinnerTest extends DefaultTatamiTest {
	
	
	boolean changed = false;
	
	private final float min = -10f;
	
	private final float max = 10f;
	
	private final float delta = 1f;
	
	private final float init = 0f;

	private NumberSpinner getNumberSpinner(){
		if(RootPanel.get().getWidgetCount() > 0 ){
			RootPanel.get().clear();
		}
		NumberSpinner spinner = new NumberSpinner(init , min , max , delta);
		RootPanel.get().add(spinner , 10 , 10);
		return spinner;
	}
	
	public void testGetSetValue(){
		NumberSpinner spinner = getNumberSpinner();
		float newValue = 56f;
		spinner.setValue(new Float(newValue));
		assertEquals(newValue, spinner.getValue().floatValue(), 0.1f);
	}
	
	public void testChangePropagation(){
		final NumberSpinner spinner = getNumberSpinner();
		final ChangeListener listener = new ChangeListener() {
			
			public void onChange(Widget sender) {
				changed = true;
			}
		
		};
		
		Timer timer = new Timer() {
			public void run(){
				assertEquals(7.4f , spinner.getValue().floatValue() ,0.1f);
				assertEquals(true, changed);
				changed = false;
				spinner.removeChangeListener(listener);
			}
		};
		
		spinner.addChangeListener(listener);
		spinner.setValue(new Float(7.4f));
		timer.schedule(100);
	}
	
	
	private native void fireKeyBoardEvent(int keycode)/*-{
		
	}-*/;
	
}
