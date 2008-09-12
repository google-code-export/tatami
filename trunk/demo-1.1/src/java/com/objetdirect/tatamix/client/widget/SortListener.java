package com.objetdirect.tatamix.client.widget;

/**
 * Listener to check if a column was sorted.
 * @author Vianney Grassaud
 *
 */
public interface SortListener {

	
	/**
	 * Manages a sort event
	 * @param asc the order of the sort <code>true</code> means ascending <code>false</code> means descending
	 * @param colIndex the index of the column which was sorted
	 */
	public void onSort(boolean asc, int colIndex);
	
	
}//end of the interface
