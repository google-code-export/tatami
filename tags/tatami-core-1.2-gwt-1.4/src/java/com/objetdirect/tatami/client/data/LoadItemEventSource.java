package com.objetdirect.tatami.client.data;

public interface LoadItemEventSource {

	/**
	 * Adds a LoadItemListener
	 * @param listener
	 * 
	 * @see {@link LoadItemListener}
	 */
	public void addLoadItemListener(LoadItemListener listener);
	
	/**
	 * Removes a DatumChangeListener
	 * @param listener
	 * 
	 * @see {@link LoadItemListener}
	 */
	public void removeLoadItemListener(LoadItemListener listener);
	
}
