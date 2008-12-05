package com.objetdirect.tatamix.client.widget;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SourcesClickEvents;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 */
public abstract class Form extends Composite  {

	protected SourcesClickEvents validator ;
	protected SourcesClickEvents cancel ;
	private InnerClickListener clickListener;
	private List<FormListener> formListeners;
	
	public Form() {
		super();
		clickListener = new InnerClickListener();
	}
	
	protected void setValidator(SourcesClickEvents sources) {
		if ( validator != null) {
			validator.removeClickListener(clickListener);
		}
		validator = sources;
		validator.addClickListener(clickListener);
		
	}
	
	protected void setCancel(SourcesClickEvents sources) {
		if ( cancel != null) {
			cancel.removeClickListener(clickListener);
		}
		cancel = sources;
		cancel.addClickListener(clickListener);
	
		
	}
	
	public void addFormListener(FormListener listener) {
		if ( formListeners == null) {
		this.formListeners = new ArrayList<FormListener>();
		}
		formListeners.add(listener);
	}
 	
	
	public void removeFormListener(FormListener listener) {
		if ( formListeners != null) {
		this.formListeners.remove(listener);
		}
		
	}
	
	public boolean validate() {
		return true;
	}
	
	
	
	
	
	
	private class InnerClickListener implements ClickListener {
		public void onClick(Widget sender ) {
			if ( sender == validator && formListeners != null && validate()) {
				fireHandlersOnSubmit();
			}
			if ( sender == cancel && formListeners != null) {
				fireHandlersOnCancel();
			}
		}
	}

    private void fireHandlersOnSubmit() {
    	for (FormListener handler : formListeners) {
    		handler.onSubmit(this);
    	}
    	
    }

    private void fireHandlersOnCancel() {
    	for (FormListener handler : formListeners) {
    		handler.onCancel(this);
    	}
    	
    }

}//end of class
