package com.objetdirect.tatamix.client.widget;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Class to manage a collection of interfaces <code>SortListener</code>
 * @author Vianney Grassaud
 *
 */
public class SortListenerCollection {

	private List<SortListener> list;
	
	/**
	 * Create the class
	 *
	 */
	public SortListenerCollection() {
		list = new ArrayList<SortListener>();
	}
	
	/**
	 * Adds a <code>SortListener</code> to the collection
	 * @param listener  <code>SortListener</code> to add
	 */
	public void add(SortListener listener) {
		list.add(listener);
	}
	
	/**
	 * Removes a <code>SortListener</code> from this collection
	 * @param listener  <code>SortListener</code> to remove
	 */
	public void remove(SortListener listener) {
		list.remove(listener);
	}
	
	/**
	 * Fires  a sort event was notified
	 * @param asc order for the sort
	 * @param colIndex the index of the column which was sorted
	 */
	public void fireSort(boolean asc,int colIndex) {
		Iterator<SortListener> ite = list.iterator();
		while (ite.hasNext()) {
			((SortListener)ite.next()).onSort(asc,colIndex);
		}
	}
}//end of class
