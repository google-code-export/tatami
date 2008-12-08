package com.objetdirect.tatamix.client.widget;

/**
 * The listener that's notified when a lists selection value changes. 
 * @author vgrassaud
 *
 */
public interface ListSelectionListener {

	/**
	 * Called whenever the value of the selection changes.
	 * @param event the event that characterizes the change.
	 */
	public void valueChanged(ListSelectionEvent event);
	
}
