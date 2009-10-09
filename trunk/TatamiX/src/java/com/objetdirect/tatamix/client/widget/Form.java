package com.objetdirect.tatamix.client.widget;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 */
public abstract class Form extends Composite  {

	protected HasClickHandlers validator ;
	protected HasClickHandlers cancel ;
	
	
	private InnerClickListener clickListener;
	
	private List<FormListener> formListeners;
	
	private List<Widget> errors;
	
	private HandlerRegistration validatorRegistration;
	private HandlerRegistration cancelRegistration;
	
	/**
	 * 
	 */
	public Form() {
		super();
		clickListener = new InnerClickListener();
		errors = new ArrayList<Widget>();
	}
	
	public void setValidator(HasClickHandlers sources) {
		if (  validatorRegistration != null) {
			validatorRegistration.removeHandler();
		}
		validator = sources;
		validatorRegistration = validator.addClickHandler(clickListener);
		
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
    
	
	public void setCancel(HasClickHandlers sources) {
		if (  cancelRegistration != null) {
			cancelRegistration.removeHandler();
		}
		cancel = sources;
		cancelRegistration = cancel.addClickHandler(clickListener);
	
		
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
	
	
	public abstract void reset();
		
	
	
	
	/**
	 * 
	 * @return
	 */
	public boolean validate() {
		return true;
	}
	
		
	private class InnerClickListener implements ClickHandler {
		public void onClick(ClickEvent event ) {
			removeErrors();
			if ( event.getSource() == validator && validate() && formListeners != null ) {
				
				fireHandlersOnSubmit();
			}
			if ( event.getSource() == cancel && formListeners != null) {
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
