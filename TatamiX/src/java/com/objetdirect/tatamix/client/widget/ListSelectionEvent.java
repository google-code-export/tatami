package com.objetdirect.tatamix.client.widget;

import java.util.EventObject;


/**
 * An event that characterizes a change in selection
 * @author vgrassaud
 *
 */
public class ListSelectionEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private int index;
	
	
	public ListSelectionEvent(Object source, int index) {
		super(source);
		this.index = index;
	}
	
	
	public int getIndex() {
		return this.index;
	}
}
