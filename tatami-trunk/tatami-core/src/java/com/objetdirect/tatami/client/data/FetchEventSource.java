package com.objetdirect.tatami.client.data;

public interface FetchEventSource {

	/**
	 * Adds a FetchListener
	 * @param listener
	 * 
	 * @see {@link FetchListener}
	 */
	public void addFetchListener(FetchListener listener);
	
	
	/**
	 * Removes a FetchListener
	 * @param listener
	 * 
	 * @see {@link FetchListener}
	 */
	public void removeFetchListener(FetchListener listener);
	
}
