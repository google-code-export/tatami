/*
 * Tatami: 
 * Copyright (C) 2007 Objet Direct
 * Copyright (C) 2007 France Telecom
 * Contact: tatami@googlegroups.com
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
 * USA
 *
 * Authors:  Ronan Dunklau
 * Initial developer(s): Ronan Dunklau
 * Contributor(s):
 */
package com.objetdirect.tatami.client.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataStorePaginator implements FetchEventSource, FetchListener {

	private DataStore store;
	
	/**
	 * Number of rows per page
	 * 
	 */
	private int rowsPerPage = -1 ;
	
	/**
	 * Current page.
	 */
	private int currentPage = 0;
	
	/**
	 * The fetch listeners. They observe the fetch process
	 * @see FetchListener
	 */
	protected List<FetchListener> fetchListeners = new ArrayList<FetchListener>();
	
	private Request lastPagingRequest;

	public DataStorePaginator(DataStore store) {
		super();
		this.store = store;
		store.addFetchListener(this);
	}

	public DataStore getStore() {
		return store;
	}

	public void setStore(AbstractDataStore store) {
		this.store.removeFetchListener(this);
		this.store = store;
		store.addFetchListener(this);
	}
	
	/**
	 * @param nbPage : page index which should be fetched
	 * @param fetchStep : the number of items we want to fetch at a time
	 */
	public void fetchPage(int nbPage){
		this.currentPage = nbPage;
		Request lastRequest = store.getLastRequest();
		this.lastPagingRequest = lastRequest;
		lastRequest.setNbItemToReturn(rowsPerPage);
		lastRequest.setStartItemNumber(nbPage * rowsPerPage);
		store.fetch(lastRequest);
	}
	 
	/**
	 * Fetches the next page.
	 * 
	 * @param fetchStep 
	 */
	public void nextPage(){
		fetchPage(currentPage + 1);
	}

	/**
	 * Fetches the previous page
	 * 
	 * @param fetchStep
	 */
	public void previousPage(){
		if(currentPage - 1 < 0){
			fetchPage(currentPage);
		}else{
			fetchPage(currentPage - 1);
		}
	}
	
	/**
	 * @return number of rows per page
	 */
	public int getRowsPerPage() {
		return rowsPerPage;
	}

	/**
	* Sets the number of rows per page.
	*
	* @param rowsPerPage : the number of rows per page.  
	* A value of -1 indicates that all rows should fit on a single page. 
	* That does not mean they are all loaded at one time, since only the displayed rows are loaded, 
	* and updated when the user scrolls. 
	*
	* Default : -1
	*
	*/
	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}
	
	/**
	 * Adds a FetchListener
	 * @param listener
	 * 
	 * @see {@link FetchListener}
	 */
	public void addFetchListener(FetchListener listener){
		fetchListeners.add(listener);
	}
	
	
	/**
	 * Removes a FetchListener
	 * @param listener
	 * 
	 * @see {@link FetchListener}
	 */
	public void removeFetchListener(FetchListener listener){
		fetchListeners.remove(listener);
	}

	public void onBegin(FetchEventSource source, int size, Request request) {
		if(rowsPerPage > -1){
			size = Math.min(size - (rowsPerPage * currentPage), rowsPerPage);
		}
		if(size > 0){
			notifyBeginFetchListeners(source, size, request);
		}
	}

	public void onComplete(FetchEventSource source, List<?> items, Request request) {
		lastPagingRequest = request;
		int startItemNumber = lastPagingRequest.getStartItemNumber();
		if(rowsPerPage > -1){
			lastPagingRequest.setStartItemNumber(Math.max(0,startItemNumber - currentPage * rowsPerPage));
			int i = 0;
			Iterator<?> iterator = items.iterator();
			for (Iterator<?> it = iterator;  iterator.hasNext() && i < rowsPerPage;) {
				iterator.next();
			}
			for (Iterator<?> it = iterator;  iterator.hasNext();) {
				iterator.next();
				iterator.remove();
			}
		}
		notifyCompleteFetchListeners(source, items, lastPagingRequest);
		lastPagingRequest.setStartItemNumber(startItemNumber);
	}

	public void onError(FetchEventSource source) {
		notifyErrorFetchListeners(source);
	}

	public void onItem(FetchEventSource source, Item item) {
		notifyItemFetchListeners(source , item);
	}
	
	/**
	 * Notifies the fetch listeners that an error occured during the fetch
	 * 
	 * @see {@link FetchListener}
	 */
	protected void notifyErrorFetchListeners(FetchEventSource source){
		for (Iterator<FetchListener> iterator = fetchListeners.iterator(); iterator.hasNext();) {
			FetchListener listener = (FetchListener) iterator.next();
			listener.onError(source);
		}
	}
	/**
	 * Notifies the fetch listeners that an item has been loaded/fetched
	 * 
	 * @param item : the item which has been loaded/fetched
	 */
	protected void notifyItemFetchListeners(FetchEventSource source, Item item){
		for (Iterator<FetchListener> iterator = fetchListeners.iterator(); iterator.hasNext();) {
			FetchListener listener = (FetchListener) iterator.next();
			listener.onItem(source ,item);
		}
	}
	
	/**
	 * Notifies the fetch listeners of the fetch's befin
	 * 
	 * @param size : expected fetch size
	 * 
	 * @see {@link FetchListener}
	 */
	protected void notifyBeginFetchListeners(FetchEventSource source,int size , Request request){
		for (Iterator<FetchListener> iterator = fetchListeners.iterator(); iterator.hasNext();) {
			FetchListener listener = (FetchListener) iterator.next();
			listener.onBegin(source,size , request);
		}
	}
	
	/**
	 * Notifies the fetch listeners of the fetch's end
	 * 
	 * @param items : items returned from the fetch
	 * 
	 * @see {@link FetchListener}
	 */
	protected void notifyCompleteFetchListeners(FetchEventSource source,List<?> items , Request request){
		for (Iterator<FetchListener> iterator = fetchListeners.iterator(); iterator.hasNext();) {
			FetchListener listener = (FetchListener) iterator.next();
			listener.onComplete(source, items , request);
		}
	}

	public int getCurrentPage() {
		return currentPage;
	}
	
	
}
