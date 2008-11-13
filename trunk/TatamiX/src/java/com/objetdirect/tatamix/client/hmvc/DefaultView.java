package com.objetdirect.tatamix.client.hmvc;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * The default view using a <code>FlowPanel</code> as layout. 
 * @author vgrassaud
 *
 */
public class DefaultView extends CompositeView {

	private FlowPanel layout;
	
	/**
	 * Creates the view with an empty layout
	 */
	public DefaultView() {
		super();
	    layout = new FlowPanel();  
	    initWidget(layout);    
	}
	
	/**
	 * Returns the layout of the view
	 * @return the layout of the view
	 */
	protected FlowPanel getLayout() {
		return layout;
	}
	
	/**
	 * Add a widget to the layout of the view
	 * @param widget the widget to add
	 */
	protected void add(Widget widget) {
		layout.add(widget);
	}
	
	/**
	 * Insert a widget in the layout of the view
	 * @param widget the widget to insert
	 * @param beforeIndex the index to insert the widget
	 */
	protected void insertWidget(Widget widget,int beforeIndex) {
		layout.insert(widget,beforeIndex);
	}
	
}//end of class
