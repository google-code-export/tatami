package com.objetdirect.tatamix.client.widget;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
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
	private List<Widget> errors;
	
	/**
	 * 
	 */
	public Form() {
		super();
		clickListener = new InnerClickListener();
		errors = new ArrayList<Widget>();
	}
	
	public void setValidator(SourcesClickEvents sources) {
		if ( validator != null) {
			validator.removeClickListener(clickListener);
		}
		validator = sources;
		validator.addClickListener(clickListener);
		
	}
	
	
	
    protected void addDefaultError(String message, Widget source) {
	    	SpanText label = new SpanText();
	    	label.setText(message);
	    	label.setStylePrimaryName("error");
	    	addError(label,source);
	
	}
    
    
    protected void addError(Widget errorWidget,Widget source) {
    	Panel panel = getPanelParent(source);
    	if ( panel != null) {
    		panel.add(errorWidget);
    		
    	}
    	errors.add(errorWidget);
    	
    }
    
    private Panel getPanelParent(Widget widget) {
		Panel parent = null; 
		if ( widget.getParent() instanceof Panel) {
			parent = (Panel)widget.getParent();
		}
		return parent;
	}
	
    private void removeErrors() {
    	for(Widget w : errors) {
    		w.removeFromParent();
    	}
    	errors.clear();
    }
    
	
	public void setCancel(SourcesClickEvents sources) {
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
	
	
	
	/**
	 * 
	 * @return
	 */
	public boolean validate() {
		return true;
	}
	
		
	private class InnerClickListener implements ClickListener {
		public void onClick(Widget sender ) {
			removeErrors();
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
