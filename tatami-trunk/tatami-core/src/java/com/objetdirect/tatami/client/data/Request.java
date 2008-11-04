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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gwt.core.client.JavaScriptObject;
import com.objetdirect.tatami.client.ConvertibleToJSObject;
import com.objetdirect.tatami.client.JSHelper;

/**
 * This object wraps a query object sent by dojo to perform a fetch in the 
 * store.
 * 
 * It can be used to perform a fetch in the store.
 * 
 * It contains various information, such as the sort fields , the filtering options, 
 * number of items to return ...
 * 
 * @author rdunklau
 *
 */
public class Request implements ConvertibleToJSObject{
	
	/**
	 * Maximum number of item to return 
	 */
	private int nbItemToReturn = -1;
	
	/**
	 * Item number from which the fetch should begin
	 */
	private int startItemNumber = -1;
	
	
	/**
	 * Map containing the filtering options
	 */
	private Map<String, Object> query = new HashMap<String, Object>();
	
	/**
	 * List containing the sort fields
	 * @see {@link SortField}
	 */
	private List<SortField> sortFields = new ArrayList<SortField>();
	
	private JavaScriptObject onCompleteCallback;
	
	public JavaScriptObject getOnCompleteCallback() {
		return onCompleteCallback;
	}

	public JavaScriptObject getOnErrorCallback() {
		return onErrorCallback;
	}

	public void setOnErrorCallback(JavaScriptObject onErrorCallback) {
		this.onErrorCallback = onErrorCallback;
	}

	public JavaScriptObject getOnItemCallback() {
		return onItemCallback;
	}

	public void setOnItemCallback(JavaScriptObject onItemCallback) {
		this.onItemCallback = onItemCallback;
	}

	public JavaScriptObject getOnBeginCallback() {
		return onBeginCallback;
	}

	public void setOnBeginCallback(JavaScriptObject onBeginCallback) {
		this.onBeginCallback = onBeginCallback;
	}

	public void setOnCompleteCallback(JavaScriptObject onCompleteCallback) {
		this.onCompleteCallback = onCompleteCallback;
	}

	private JavaScriptObject onErrorCallback;
	
	private JavaScriptObject onItemCallback;
	
	private JavaScriptObject onBeginCallback;
	
	/**
	 * Constructor which is used to construct a Request object
	 * from a dojo javascript request object
	 * 
	 * @param object
	 */
	public Request(JavaScriptObject object){
		constructFromJSObject(object);
	}
	
	public Request(){
		
	}
	
	/**
	 * Internal method used to construct a Request object
	 * from a dojo javascript request object
	 * 
	 * @param jsRequest
	 */
	private native void constructFromJSObject(JavaScriptObject jsRequest)/*-{
		if(jsRequest.count != undefined ){
			this.@com.objetdirect.tatami.client.data.Request::nbItemToReturn = jsRequest.count;
		}
		if(jsRequest.start != undefined){
			this.@com.objetdirect.tatami.client.data.Request::startItemNumber = jsRequest.start;
		}
		if(jsRequest.query != undefined ){
			for(prop in jsRequest.query){
				var currentQueryParameterValue = jsRequest.query[prop];
				if(typeof currentQueryParameterValue == 'number'){
					this.@com.objetdirect.tatami.client.data.Request::addQueryParameter(Ljava/lang/String;F)(prop,currentQueryParameterValue);
				}else{
					this.@com.objetdirect.tatami.client.data.Request::addQueryParameter(Ljava/lang/String;Ljava/lang/Object;)(prop,currentQueryParameterValue);
				}
			}
		}
		if(jsRequest.sort != undefined ){
			var sortFields = jsRequest.sort; 
			this.@com.objetdirect.tatami.client.data.Request::sortFields.@java.util.List::clear()();
			for(prop in sortFields){
				var value = sortFields[prop];
				this.@com.objetdirect.tatami.client.data.Request::addSortParameter(Ljava/lang/String;Z)(value.attribute,value.descending);
			}
		}
		if(jsRequest.queryOptions != undefined){
		}
		if(jsRequest.onComplete != undefined){
			this.@com.objetdirect.tatami.client.data.Request::setOnCompleteCallback(Lcom/google/gwt/core/client/JavaScriptObject;)(jsRequest.onComplete);
		}
		if(jsRequest.onBegin != undefined){
			this.@com.objetdirect.tatami.client.data.Request::setOnBeginCallback(Lcom/google/gwt/core/client/JavaScriptObject;)(jsRequest.onBegin);
		}
		if(jsRequest.onError != undefined){
			this.@com.objetdirect.tatami.client.data.Request::setOnErrorCallback(Lcom/google/gwt/core/client/JavaScriptObject;)(jsRequest.onError);
		}
		if(jsRequest.onItem != undefined){
			this.@com.objetdirect.tatami.client.data.Request::setOnItemCallback(Lcom/google/gwt/core/client/JavaScriptObject;)(jsRequest.onItem);
		}
		if(jsRequest.identity != undefined){
			this.@com.objetdirect.tatami.client.data.Request::addQueryParameter(Ljava/lang/String;Ljava/lang/Object;)("identity",jsRequest.identity);
		}
		
	}-*/;

	
	/**
	 * @return the maximum number of item to return 
	 */
	public int getNbItemToReturn() {
		return nbItemToReturn;
	}

	/**
	 * @param nbItemToReturn :the maximum number of item to return 
	 */
	public void setNbItemToReturn(int nbItemToReturn) {
		this.nbItemToReturn = nbItemToReturn;
	}

	/**
	 * @return  the maximum number of item to return 
	 * 
	 */
	public int getStartItemNumber() {
		return startItemNumber;
	}

	/**
	 * @param startItemNumber :  the maximum number of item to return.
	 * For example, if startItemNumber = 10 , the fetch process invoked with this request
	 * should return only item from position 10 in the sorted list.
	 */
	public void setStartItemNumber(int startItemNumber) {
		this.startItemNumber = startItemNumber;
	}
	
	/**
	 * Adds
	 * 
	 * @param attrName
	 * @param attrValue
	 */
	public void addQueryParameter(String attrName , Object attrValue){
		query.put(attrName , attrValue);
	}
	
	/**
	 * Removes  a filtering option from the request
	 * 
	 * @param attrName
	 */
	public void removeQueryParameter(String attrName){
		query.remove(attrName);
	}
	
	
	public void clearQueryParameters(){
		query.clear();
	}
	
	/**
	 * Internal method used when constructing the Request Object 
	 * from the javascript object
	 * 
	 * @param attrName
	 * @param attrValue
	 */
	private void addQueryParameter(String attrName , float attrValue){
		query.put(attrName , new Float(attrValue));
	}
	
	/**
	 * @return the map of filtering options
	 */
	public Map<String, Object> getQuery() {
		return query;
	}
	

	/**
	 * @param query : the map of filtering options
	 */
	public void setQuery(Map<String, Object> query) {
		this.query = query;
	}
	
	/**
	 * Adds a sort parameter
	 * 
	 * @param sortField : the field name on which the items should be fetched
	 * @param ascending : whether  it is sorted in ascending or descending order
	 */
	public void addSortParameter(String sortField , boolean descending){
		this.sortFields.add(new SortField(sortField ,descending));
	}

	public void clearSortOptions(){
		this.sortFields.clear();
	}


	/**
	 * @return the list of {@link SortField} objects which determines
	 * how the result from a fetch process invoked with this request should 
	 * be sorted 
	 */
	public List<SortField> getSortFields() {
		return sortFields;
	}

	/**
	 * @param sortFields : the list of {@link SortField} objects which determines
	 * how the result from a fetch process invoked with this request should 
	 * be sorted 
	 */
	public void setSortFields(List<SortField> sortFields) {
		this.sortFields = sortFields;
	}


	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.ConvertibleToJSObject#toJSObject()
	 */
	public JavaScriptObject toJSObject() {
		return createJSRequest(startItemNumber, nbItemToReturn, JSHelper.convertObjectToJSObject(query) , JSHelper.convertObjectToJSObject(sortFields) , onCompleteCallback , onBeginCallback , onItemCallback , onErrorCallback);
	}
	
	/**
	 * Internal method used to create the javascript object representing this
	 * request.
	 * 
	 * @param start
	 * @param count
	 * @param query
	 * @param sort
	 * @return
	 */
	private native JavaScriptObject createJSRequest(int start , int count , JavaScriptObject query , JavaScriptObject sort , JavaScriptObject onCompleteCallback , JavaScriptObject onBeginCallback,JavaScriptObject onItemCallback,JavaScriptObject onErrorCallback)/*-{
		var toReturn = {};
		toReturn.start = start;
		toReturn.count = count;
		toReturn.query = query;
		toReturn.sort = sort;
		toReturn.onComplete = onCompleteCallback;
		toReturn.onBegin = onBeginCallback;
		toReturn.onItem = onItemCallback;
		toReturn.onError = onErrorCallback;
		return toReturn;
	}-*/;
	
	
}
