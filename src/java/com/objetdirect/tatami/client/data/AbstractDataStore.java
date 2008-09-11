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
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.core.client.JavaScriptObject;
import com.objetdirect.tatami.client.DateUtil;
import com.objetdirect.tatami.client.DojoController;
import com.objetdirect.tatami.client.HasDojo;
import com.objetdirect.tatami.client.JSHelper;

public abstract class AbstractDataStore  implements HasDojo,FetchEventSource,DataStore {

	/**
	 * LastRequest performed. Used to keep parameters when we use pagination
	 * 
	 */
	protected Request lastRequest = new Request();
	
	/**
	 * The name used to declare the generated dojo class
	 */
	private final String dojoReadStoreClassName = "dojox.data.store.TatamiDataStore";
	
	
	
	/**
	 * The DataStore items : the key is the item's idAttribute value, 
	 * 	while the item itself is an {@link Item}.
	 */
	protected Map items = new HashMap();
	
	
	/**
	 * The fetch listeners. They observe the fetch process
	 * @see FetchListener
	 */
	protected List fetchListeners = new ArrayList();
	
	
	/**
	 * The load listeners. They observe the loading process
	 */
	protected List loadListeners = new ArrayList();
	
	/**
	 * The Datum change listeners. They are notified whenever an item is 
	 * added , deleted or modified
	 * @see DatumChangeListener
	 */
	protected List datumChangeListeners = new ArrayList();
	
	
	
	/**
	 * The underlying Dojo Object
	 * 
	 */
	protected JavaScriptObject dojoStore;
	
	/**
	 * Attribute used to retrieve an Item identity
	 */
	private String idAttribute = "id";
	
	
	/**
	 * Attribute used to retrieve an Item Label
	 */
	private String labelAttribute = "label";
	
	
	
	/**
	 * @param readStoreClassName : The name used to declare the generated dojo class
	 */
	public AbstractDataStore(){
		DojoController.getInstance().loadDojoWidget(this);
		doAfterCreation();
	}
	
	/**
	 * @param readStoreClassName : The name used to declare the generated dojo class
	 * @param idAttribute : Attribute used to retrieve an Item identity
	 * @param labelAttribute : Attribute used to retrieve an Item Label
	 */
	public AbstractDataStore(String idAttribute , String labelAttribute){
		this();
		this.idAttribute = idAttribute;
		this.labelAttribute = labelAttribute;
	}
	
	/**
	 * This method is used for developper convenience to declare 
	 * a new Dojo DataStore class without the need to know anything about dojo. 
	 * It declares the methods required to conform to Dojo data APIs, and connect
	 * them to the corresponding GWT methods
	 * @param dojoReadStoreClassName : The name used to declare the generated dojo class
	 * 
	 */
	private native void defineReadStore(String dojoReadStoreClassName )/*-{
		$wnd.dojo.declare(dojoReadStoreClassName, [$wnd.dojo.data.api.Identity ,$wnd.dojo.data.api.Write , $wnd.dojo.data.api.Notification]   , {
			 getValue: function (item,attribute,defaultValue) {
			 	var newDefaultValue = (defaultValue == undefined ? null : defaultValue);
			 	var toReturn = this.gwtStore.@com.objetdirect.tatami.client.data.AbstractDataStore::dojoGetValue(Lcom/objetdirect/tatami/client/data/Item;Ljava/lang/String;Ljava/lang/Object;)(item,attribute,newDefaultValue);
			 	return toReturn;
			 },
			 isItem: function (item) {
			 	return this.gwtStore.@com.objetdirect.tatami.client.data.AbstractDataStore::isItem(Ljava/lang/Object;)(item);
			 },
			 getValues: function(item,attribute) {
			 	var toReturn = this.gwtStore.@com.objetdirect.tatami.client.data.AbstractDataStore::dojoGetValues(Lcom/objetdirect/tatami/client/data/Item;Ljava/lang/String;)(item,attribute);
			 	return toReturn;
			 },
			 getAttributes: function(item) {
			 	var toReturn = this.gwtStore.@com.objetdirect.tatami.client.data.AbstractDataStore::dojoGetAttributes(Lcom/objetdirect/tatami/client/data/Item;)(item);
			 	return toReturn;
			 },
		 	 hasAttribute: function(item , attribute){
		 		return this.gwtStore.@com.objetdirect.tatami.client.data.AbstractDataStore::hasAttribute(Lcom/objetdirect/tatami/client/data/Item;Ljava/lang/String;)(item,attribute);
		 	 },
		 	 containsValue: function (item , attribute , value){
		 	 	return this.gwtStore.@com.objetdirect.tatami.client.data.AbstractDataStore::containsValue(Lcom/objetdirect/tatami/client/data/Item;Ljava/lang/String;Ljava/lang/Object;)(item,attribute,value);
		 	 },
		 	 isItemLoaded: function(item){
		 	 	return this.gwtStore.@com.objetdirect.tatami.client.data.AbstractDataStore::isItemLoaded(Lcom/objetdirect/tatami/client/data/Item;)(item);
		 	 },
		 	 loadItem: function(keywordArgs){
			 	return this.gwtStore.@com.objetdirect.tatami.client.data.AbstractDataStore::loadItem(Lcom/objetdirect/tatami/client/data/Item;)(keywordArgs.item);
			 },
			 fetch: function(keywordArgs){
			 	this.gwtStore.@com.objetdirect.tatami.client.data.AbstractDataStore::fetch(Lcom/google/gwt/core/client/JavaScriptObject;)(keywordArgs);
			 },
			 getFeatures: function(){
			 	return { 'dojo.data.api.Read' : true ,
			 			 'dojo.data.api.Identity' : true ,
			 			 'dojo.data.api.Write' : true ,
			 			 'dojo.data.api.Notification' : true
			 		};
			 },
			 getLabel: function(item){
			 	return this.gwtStore.@com.objetdirect.tatami.client.data.AbstractDataStore::getLabel(Lcom/objetdirect/tatami/client/data/Item;)(item);
			 },
			 getLabelAttributes: function(item){
			 	return this.gwtStore.@com.objetdirect.tatami.client.data.AbstractDataStore::getLabelAttributes(Lcom/objetdirect/tatami/client/data/Item;)(item);
			 },
			 getIdentity: function(item){
			 	var toReturn = this.gwtStore.@com.objetdirect.tatami.client.data.AbstractDataStore::dojoGetIdentity(Lcom/objetdirect/tatami/client/data/Item;)(item); 
			 	return toReturn;
			 },
			 getIdentityAttributes: function(item){
			 	return this.gwtStore.@com.objetdirect.tatami.client.data.AbstractDataStore::getIdentityAttributes(Lcom/objetdirect/tatami/client/data/Item;)(item);
			 },
			 setValue : function(item , attribute , value){
			 	return this.gwtStore.@com.objetdirect.tatami.client.data.AbstractDataStore::setValue(Lcom/objetdirect/tatami/client/data/Item;Ljava/lang/String;Ljava/lang/Object;)(item,attribute,value + ""); 
			 },
			 setValues : function(item , attribute , values){
			 	return this.gwtStore.@com.objetdirect.tatami.client.data.AbstractDataStore::dojoSetValues(Lcom/objetdirect/tatami/client/data/Item;Ljava/lang/String;Lcom/google/gwt/core/client/JavaScriptObject;)(item,attribute,values); 
			 },
			 onSet : function(item, attribute, oldValue, newValue){
			 },
			 onNew : function(item){
			 },
			 onDelete : function(item){
			 },
			 newItem : function(item , parentInfo){
			 	return this.gwtStore.@com.objetdirect.tatami.client.data.AbstractDataStore::dojoAdd(Lcom/objetdirect/tatami/client/data/Item;)(item);
			 },
			 deleteItem : function(item){
			 	this.gwtStore.@com.objetdirect.tatami.client.data.AbstractDataStore::remove(Lcom/objetdirect/tatami/client/data/Item;)(item);
			 }
	 });
	}-*/;
	
	
	
	
	/**
	 * Method required by dojo. This method can be overriden by subclasses to provide their
	 * own definition of what an item should be
	 * @param item : the object to test
	 * @return true if item is an instance of Item and the store contains this item,
	 * 		  false otherwise
	 */
	public boolean isItem(Object item){
		boolean isItem;
		if(item instanceof Item  && getItemByIdentity(getIdentity((Item)item)) != null){
			isItem = true;
		}else{
			isItem = false;
		}
		return isItem;
	}
	
	
	
	/**
	 * @param item : the item to get the attribute's value for
	 * @param attribute : the name of the attribute
	 * @param defaultValue : value to return if the item is not present, or if
	 * 		  the item does not contain the desired attribute.
	 * @return the attribute's value, or the default value.
	 * 
	 */
	public Object getValue(Item item , String attribute , Object defaultValue) {
		if(!isItem(item)){
			return defaultValue;
		}
		return item.getValue(attribute, defaultValue);
	}
	
	
	/**
	 * @param item : the javascript item coming from dojo 
	 * @param attribute : attribute : the name of the attribute
	 * @param defaultValue :  value to return if the item is not present, or if
	 * 		  the item does not contain the desired attribute.
	 * @return 
	 * @see {@link #getValue(Item, String, Object)}
	 */
	private Object dojoGetValue(Item item , String attribute , Object defaultValue) {
		Object value = defaultValue;
		value = getValue(item, attribute, defaultValue);
		if( value instanceof String ||	value instanceof Boolean || value instanceof Number){
			return value;
		}else if(value instanceof Date){
			return DateUtil.getJSDate((Date)value);
		}else if(value instanceof Item){
			return value;
		}else{
			JavaScriptObject toReturn = JSHelper.convertObjectToJSObject(value); 
			return toReturn;
		}
	}
	
	private JavaScriptObject dojoGetValues(Item item , String attribute){
		Object value =  getValue(item, attribute, null);
		JavaScriptObject valuesArray;
		if(value == null){
			return JavaScriptObject.createArray();
		}
		if(value instanceof Collection || value instanceof Object[]){
			valuesArray = JSHelper.convertObjectToJSObject(value);
		}else{
			Object[] toConvert =  {value};
			valuesArray = JSHelper.convertObjectToJSObject(toConvert);
		}
		return valuesArray;
	}
	
	/**
	 * @param item : the item on which to set the attribute 
	 * @param attribute : the attribute to set
	 * @param value : value to set the attribute to 
	 */
	public void setValue(Item item , String attribute , Object value) {
		if(isItem(item)){
			Object oldValue = (Object) getValue(item, attribute, null);
			item.addAttribute(attribute, value);
			items.put(getIdentity(item), item);
			onSet(item, attribute, oldValue, value);
		}
	}
	
	
	
	
	
	/**
	 * Search items by an attritbute value
	 * @param attrName : the name of the attribute
	 * @param attrValue : the value to match
	 * @return : a List of items which match the specified attribute name / attribute value couple
	 */
	public List searchItemsByAttributes(String attrName , Object attrValue){
		List toReturn = new ArrayList();
		Collection concreteItems = items.values();
		for (Iterator iterator = concreteItems.iterator(); iterator.hasNext();) {
			Item currItem = (Item) iterator.next();
			Object value;
			value = getValue(currItem , attrName , null);
			if(value.equals(attrValue)){
				toReturn.add(currItem);
			}
		}
		return toReturn; 
	}
	
	
	
	
	/**
	 * @param item : the item to get the attributes from
	 * @return : an array containing the attributes names.
	 */
	public String[] getAttributes(Item item) {
		if(!isItem(item)){
			return new String[0];
		}
		return item.getAttributes();
	}
	
	
	/**
	 * Simple wrapper around {@link #getAttributes(Item)} to be called by dojo
	 * @param : the item to get the attributes from
	 * @return : an array containing the attributes names
	 */
	private JavaScriptObject dojoGetAttributes(Item item) {
		return JSHelper.convertObjectToJSObject(getAttributes(item));
	}
	
	
	/**
	 * @param item 
	 * @param attributeName : attribute to test
	 * @return : true if the item has this attribute, 
	 * 			 false otherwise.
	 */
	public boolean hasAttribute(Item item , String attributeName) {
		if(!isItem(item)){
			return false ;
		}
		return item.hasAttribute(attributeName);
	}
	
	
	/**
	 * Returns true if item's attribute contains the specified value
	 * @param item
	 * @param attribute
	 * @param value
	 * @return true if item contains the value for the given attribute, false otherwise
	 */
	public boolean containsValue(Item item , String attribute , Object value)  {
		if(!isItem(item)){
			return false;
		}
		return item.containsValue( attribute , value);
	}
	
	
	
	/**
	 * @param id : the id to search the item by
	 * @return the item with the specified id, null if no one does.
	 */
	public Item getItemByIdentity(Object id){
		return (Item) items.get(id);
	}

	/**
	 * @param item : the item to get the label from
	 * @return the label
	 */
	public String getLabel(Item item) {
		return (String) item.getValue(labelAttribute, "DefaultLabel");
	}
	

	/**
	 * This implementation only returns the label attribute from this item.
	 * Subclasses implementor could override this method to provide other behavior
	 * 
	 * @param item 
	 * @return an array containing the attributes used to construct the
	 * 	specified item's label 
	 */
	public String[] getLabelAttributes(Item item) {
		String[] attrs = new String[1];
		attrs[0] = labelAttribute;
		return attrs;
	}
	
	
	
	
	
	/**
	 * @param item
	 * @return item's identity
	 */	
	public Object getIdentity(Item item) {
		return item.getValue(idAttribute, null);
	}
	
	/**
	 * @return the attribute used as identifier
	 */
	public String getIdentityAttribute(){
		return idAttribute;
	}
	
	/**
	 * Simple wrapper around {@link #getIdentity(Item)} to be called by dojo
	 * @param item
	 * @return
	 */
	private Object dojoGetIdentity(Item item){
		Object id =  getIdentity(item);
		return id;
	}

	/**
	 * This implementation only returns the id attribute from this item.
	 * Subclasses implementor could override this method to provide other behavior
	 * @param item
	 * @return the names from attributes which are used to construct item's identity
	 */
	public String[] getIdentityAttributes(Item item) {
		String[] attrs = new String[1];
		attrs[0] = idAttribute;
		return attrs;
	}
	
	

	/**
	 * Template method implemented for concrete ReadStore implementer's convenience
	 * Test if the item has been loaded , and if not try to load the item.
	 * If it loads it, it notify the itemFetchListeners.
	 * 
	 * @see com.objetdirect.tatami.client.data.ReadStore#loadItem(com.objetdirect.tatami.client.data.Item, com.objetdirect.tatami.client.data.LoadItemListener)
	 */
	public boolean loadItem(Item item){
		if(isItemLoaded(item)){
			return false;
		}else{
			if(loadItemImpl(item)){
				notifyLoadItemListeners(item);
				return true;
			}else{
				return false;
			}
		}
	}
	
	/**
	 * This method has to be implemented by data store implementors.
	 * It permits to load an Item if it has to. 
	 * (Exemple : if the item is just a "stub" for a real item, containing only
	 *  an attribute reffering to how to load the other ones (an url for exemple)).
	 * 
	 * @param item : the item to load
	 * @return true if the item has been loaded , false otherwise.
	 */
	public abstract boolean loadItemImpl(Item item);
	
	
	/**
	 * @param item : the item to test
	 * @return true if the item has already been loaded, false otherwise
	 */
	public abstract boolean isItemLoaded(Item item);
	
	
	
	
	/**
	 * Concrete implementation of the fetch method
	 * @param request : the Request object to fetch the item from
	 */
	public abstract void fetch(Request request);
	
	

	/**
	 * Simple wrapper around {@link #fetch(Request)} to be called by dojo
	 * @param object : the javascript object containing the request
	 */
	private void fetch(JavaScriptObject object){
		Request request = new Request(object);
		fetch(request);
	}
	
	
	
	
	
	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.HasDojo#getDojoName()
	 */
	public String getDojoName() {
		return "dojo.data.api.Identity";
	}
	
	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.HasDojo#onDojoLoad()
	 */
	public void onDojoLoad(){
		defineReadStore(dojoReadStoreClassName);
	}


	/**
	 * Adds an item to the store, then fire the onNew event
	 * @param item
	 */
	public void add(Item item) {
		Object id = getIdentity(item);
		Item oldItem = (Item) items.get(id);
		items.put( id , item);
		if(oldItem != null){
			String[] attributes = oldItem.getAttributes();
			for (int i = 0; i < attributes.length; i++) {
				String attr = attributes[i];
				onSet(item , attr , oldItem.getValue(attr, null) , item.getValue(attr, null));
			}
		}else{
			notifyOnNewListeners(item);
			if(dojoStore != null){
				callJSOnNew(item);
			}
		}
	}
	
	/**
	 * Method used by dojo to add an item to the store
	 * 
	 * @param item
	 * @return
	 */
	private Item dojoAdd(Item item){
		Object id = getIdentity(item);
		items.put( id , item);
		Item oldItem = (Item) items.get(id); 
		notifyOnNewListeners(item);
		return item;
	}
	
	private void dojoSetValues(Item item , String attribute , JavaScriptObject values){
		setValue(item, attribute, JSHelper.convertJSArrayToCollection(values));
	}
	
	/**
	 * Fires a onNew event
	 * @param item : item which has been added to the store
	 */
	/*
	private void onNew(Item item , boolean notifyDojo){
		callJSOnNew(item.toJSObject());
		notifyOnNewListeners(item);
	}*/
	
	/**
	 * Calls underlying DojoStore's onNew method, on which dojo can connect
	 * to propagate the event.
	 * @param item : the javascript item to pass to dojo
	 */
	protected native void callJSOnNew(Item item)/*-{
		this.@com.objetdirect.tatami.client.data.AbstractDataStore::dojoStore.onNew(item);
	}-*/;
	
	
	
	/**
	 * Fires an onSet event.
	 * 
	 * @param item : item on which an attribute has changed
	 * @param attribute : the attribute which has changed
	 * @param oldValue 
	 * @param newValue
	 */
	private void onSet(Item item , String attribute , Object oldValue , Object newValue) {
		notifyDatumChangeListeners(item, attribute, oldValue, newValue);
		if(newValue instanceof Date){
			newValue = DateUtil.getJSDate((Date)newValue);
		}
		if(oldValue instanceof Date){
			oldValue = DateUtil.getJSDate((Date)oldValue);
		}
		if(dojoStore != null){
			callJSOnSet(item, attribute, oldValue, newValue);
		}
	}
	
	
	/**
	 * Calls underlying DojoStore's onSet method, on which dojo can connect
	 * to propagate the event.
	 * 
	 * @param item
	 * @param attribute
	 * @param oldValue
	 * @param newValue
	 */
	protected native void callJSOnSet(Item item , String attribute , Object oldValue , Object newValue)/*-{
		this.@com.objetdirect.tatami.client.data.AbstractDataStore::dojoStore.onSet(item , attribute , oldValue , newValue);
	}-*/;
	
	
	/**
	 * Fires an onDelete event
	 * @param item : the item which has been deleted
	 */
	private void onDelete(Item item){
		callJSOnDelete(item);
		notifyOnDeleteListeners(item);
	}
	
	/**
	 * Calls underlying DojoStore's onDelete method, on which dojo can connect
	 * to propagate the event.
	 * 
	 * @param item
	 */
	protected native void callJSOnDelete(Item item)/*-{
		this.@com.objetdirect.tatami.client.data.AbstractDataStore::dojoStore.onDelete(item);
	}-*/;
	
	
	
	
	/**
	 * Remove an item from the store, the fires an onDelete event
	 * 
	 * @param item
	 */
	public void remove(Item item) {
		if(isItem(item)){
			Object id = getIdentity(item);
			onDelete(item);
			items.remove(id);
		}
	}

	
	/**
	 * @return the current store's size
	 */
	public int size() {
		return items.size();
	}
	
	/**
	 * Adds a LoadItemListener
	 * @param listener
	 * 
	 * @see {@link LoadItemListener}
	 */
	public void addLoadItemListener(LoadItemListener listener){
		loadListeners.add(listener);
	}
	
	/**
	 * Removes a DatumChangeListener
	 * @param listener
	 * 
	 * @see {@link LoadItemListener}
	 */
	public void removeLoadItemListener(LoadItemListener listener){
		loadListeners.remove(listener);
	}
	
	/**
	 * Notifies the load listeners that an item has been loaded
	 * 
	 * @param item : the item which has been load
	 * 
	 */
	protected void notifyLoadItemListeners(Item item){
		for (Iterator iterator = loadListeners.iterator(); iterator.hasNext();) {
			LoadItemListener loadListeners = (LoadItemListener) iterator.next();
			loadListeners.onLoad(item);
		}
	}
	
	
	/**
	 * Adds a DatumChangeListener
	 * @param listener
	 * 
	 * @see {@link DatumChangeListener}
	 */
	public void addDatumChangeListener(DatumChangeListener listener){
		datumChangeListeners.add(listener);
	}
	
	/**
	 * Removes a DatumChangeListener
	 * @param listener
	 * 
	 * @see {@link DatumChangeListener}
	 */
	public void removeDatumChangeListener(DatumChangeListener listener){
		datumChangeListeners.remove(listener);
	}
	
	/**
	 * Notifies the datum change listeners that an item has been modified
	 * 
	 * @param item
	 * @param attribute
	 * @param oldValue
	 * @param newValue
	 * 
	 * @see {@link DatumChangeListener}
	 */
	protected void notifyDatumChangeListeners(Item item , String attribute , Object oldValue , Object newValue){
		for (Iterator iterator = datumChangeListeners.iterator(); iterator.hasNext();) {
			DatumChangeListener datumChangeListener = (DatumChangeListener) iterator.next();
			datumChangeListener.onDataChange(item, attribute, oldValue, newValue);
		}
	}
	
	/**
	 * Notifies the datum change listeners that an item has been added
	 * 
	 * @param item
	 * 
	 * @see {@link DatumChangeListener}
	 * 
	 */
	protected void notifyOnNewListeners(Item item){
		for (Iterator iterator = datumChangeListeners.iterator(); iterator.hasNext();) {
			DatumChangeListener datumChangeListener = (DatumChangeListener) iterator.next();
			datumChangeListener.onNew(item);
		}
	}
	

	/**
	 * Notifies the datum change listeners that an item has been deleted
	 * 
	 * @param item
	 * 
	 * @see {@link DatumChangeListener}
	 */
	protected void notifyOnDeleteListeners(Item item){
		for (Iterator iterator = datumChangeListeners.iterator(); iterator.hasNext();) {
			DatumChangeListener datumChangeListener = (DatumChangeListener) iterator.next();
			datumChangeListener.onDelete(item);
		}
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
	
	/**
	 * Notifies the fetch listeners of the fetch's end
	 * 
	 * @param items : items returned from the fetch
	 * 
	 * @see {@link FetchListener}
	 */
	protected void notifyCompleteFetchListeners(List items , Request request){
		for (Iterator iterator = fetchListeners.iterator(); iterator.hasNext();) {
			FetchListener listener = (FetchListener) iterator.next();
			listener.onComplete(this, items , request);
		}
	}
	
	/**
	 * Notifies the fetch listeners of the fetch's befin
	 * 
	 * @param size : expected fetch size
	 * 
	 * @see {@link FetchListener}
	 */
	protected void notifyBeginFetchListeners(int size , Request request){
		for (Iterator iterator = fetchListeners.iterator(); iterator.hasNext();) {
			FetchListener listener = (FetchListener) iterator.next();
			listener.onBegin(this,size , request);
		}
	}
	
	/**
	 * Notifies the fetch listeners that an error occured during the fetch
	 * 
	 * @see {@link FetchListener}
	 */
	protected void notifyErrorFetchListeners(){
		for (Iterator iterator = fetchListeners.iterator(); iterator.hasNext();) {
			FetchListener listener = (FetchListener) iterator.next();
			listener.onError(this);
		}
	}
	
	/**
	 * Notifies the fetch listeners that an item has been loaded/fetched
	 * 
	 * @param item : the item which has been loaded/fetched
	 */
	protected void notifyItemFetchListeners(Item item){
		for (Iterator iterator = fetchListeners.iterator(); iterator.hasNext();) {
			FetchListener listener = (FetchListener) iterator.next();
			listener.onItem(this ,item);
		}
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.HasDojo#getDojoWidget()
	 */
	public JavaScriptObject getDojoWidget() {
		return dojoStore;
	}

	/**
	 * Used to initialize the store by setting a reference to the gwt store 
	 * in the dojo store.
	 * 
	 * @param dojoStore
	 * @param gwtStore
	 */
	private native void setGWTStore(JavaScriptObject dojoStore,AbstractDataStore gwtStore)
	/*-{
        try {
        dojoStore.gwtStore = gwtStore;
        } catch(e) {
        }
	 }-*/;
	
	
	/**
	 * Used before destruction to break the reference from dojo store
	 * to gwt store
	 * 
	 * @param dojoStore
	 */
	private native void unSetGWTStore(JavaScriptObject dojoStore)/*-{
		try{
			dojoStore.gwtStore = null;
		} catch(e) {
		
		}
	}-*/;
	
	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.HasDojo#doAfterCreation()
	 */
	public void doAfterCreation() {
		try {
			createDojoWidget();
			setGWTStore(dojoStore, this);
			Collection itemsValues = items.values();
			for (Iterator iterator = itemsValues.iterator(); iterator.hasNext();) {
				Item item = (Item) iterator.next();
				callJSOnNew(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * Method that should be overriden by subclasses implementors 
	 * to provide further finalization behaviors.
	 */
	private native void destroyStore()/*-{
	}-*/;

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.HasDojo#free()
	 */
	public void free() {
		destroyStore();
		this.dojoStore = null;
	}
	
	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.HasDojo#doBeforeDestruction()
	 */
	public void doBeforeDestruction() {
		unSetGWTStore(dojoStore);
		free();
	}
	
	/**
	 * @return : the last request performed
	 */
	public Request getLastRequest() {
		return lastRequest;
	}
	
	public void clearDataStore(){
		items.clear();
	}
	
	/**
	 * Execute the given request against the given collection of items
	 * 
	 * @param items
	 * @param request
	 * @return
	 */
	public List executeQuery(Collection items , Request request){
		List itemsMatchingAndSorted = new ArrayList();
		ItemComparator comparator = new ItemComparator(request.getSortFields());
		for (Iterator iterator = items.iterator(); iterator
				.hasNext();) {
			Item item = (Item) iterator.next();
			if(itemMatchQuery(item , request)){
				itemsMatchingAndSorted.add(item);
			}
		}
		Collections.sort(itemsMatchingAndSorted , comparator);
		return itemsMatchingAndSorted;
	}
	
	/**
	 * Inner class used compare items with one another when we have to sort them.
	 * 
	 * @author rdunklau
	 *
	 */
	private static class ItemComparator implements Comparator{

		private List sortFields;
		
		public ItemComparator(List sortFields){
			this.sortFields = sortFields;
		}
		
		public int compare(Object arg0, Object arg1)  {
			Item item1 = (Item) arg0;
			Item item2 = (Item) arg1;
			int result = 0;
			for (Iterator iterator = sortFields.iterator(); iterator.hasNext();) {
				SortField sortField = (SortField) iterator.next();
				
				Object value1 = item1.getValue( sortField.getAttribute() , null);
				Object value2 = item2.getValue( sortField.getAttribute() , null);
				if(value1 == null && value2 != null){
					result = Integer.MIN_VALUE;
				}else if(value2 == null && value1 != null){
					result = Integer.MAX_VALUE;
				}else if(value1 == value2){
					result = 0;
				}else if(value1 instanceof Comparable && value2 instanceof Comparable){
					try{
						result = ((Comparable)value1).compareTo(value2);
					}catch(ClassCastException e){
						result = value1.toString().compareTo(value2.toString());
					}
				}else{
					result = value1.toString().compareTo(value2.toString()); 
				}
				if(!sortField.isDescending()){
					result = -result;
				}
				if(result != 0){
					return result;
				}
			}
			return result;
		}

	}

	

	/**
	 * @param item
	 * @param request
	 * @return : true it item matches the criteria of the request
	 */
	public boolean itemMatchQuery(Item item , Request request){
		boolean match = true;
		Map query = request.getQuery();
		Set keys = query.keySet();
		boolean result = true;
		for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
			String queryparameter = (String) iterator.next();
			if(query.get(queryparameter).toString().compareTo("*") != 0){
				Object actualProperty = item.getValues(queryparameter);
				Object expectedProperty = query.get(queryparameter); 
				if(actualProperty == null){
					result = false;
				}else if(actualProperty instanceof Comparable && expectedProperty instanceof Comparable){
					try{
						result = ((Comparable)actualProperty).compareTo(expectedProperty) == 0 ? true : false;
					}catch(ClassCastException e){
						result = actualProperty.toString().compareTo(expectedProperty.toString()) == 0 ? true : false;
					}
				}else{
					result = actualProperty.toString().compareTo(expectedProperty.toString()) == 0 ? true : false;
				}
				if(result == false){
					return result;
				}
			}
		}
		return result;
	}
	
	public void createDojoWidget() throws Exception {
		this.dojoStore = createDataStore();
	}
	
	private native JavaScriptObject createDataStore()/*-{
		var dataStore = new $wnd.dojox.data.store.TatamiDataStore();
		return dataStore;
	}-*/;
	
	
}
