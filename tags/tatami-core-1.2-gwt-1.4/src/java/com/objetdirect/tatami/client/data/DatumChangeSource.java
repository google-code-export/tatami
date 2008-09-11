package com.objetdirect.tatami.client.data;

public interface DatumChangeSource {

	/**
	 * Adds a DatumChangeListener
	 * @param listener
	 * 
	 * @see {@link DatumChangeListener}
	 */
	public void addDatumChangeListener(DatumChangeListener listener);
	
	
	/**
	 * Removes a DatumChangeListener
	 * @param listener
	 * 
	 * @see {@link DatumChangeListener}
	 */
	public void removeDatumChangeListener(DatumChangeListener listener);
	
}
