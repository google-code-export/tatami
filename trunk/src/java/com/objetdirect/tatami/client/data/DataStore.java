package com.objetdirect.tatami.client.data;

import java.util.List;

public interface DataStore extends FetchEventSource , DatumChangeSource , LoadItemEventSource{

	/**
	 * @param item
	 * @return item's identity
	 */	
	public Object getIdentity(Item item);
	

	/**
	 * @return the attribute used as identifier
	 */
	public String getIdentityAttribute();
	
	/**
	 * Adds an item to the store, then fire the onNew event
	 * @param item
	 */
	public void add(Item item);
	
	
	/**
	 * Remove an item from the store, the fires an onDelete event
	 * 
	 * @param item
	 */
	public void remove(Item item);
	
	
	/**
	 * @return : the last request performed
	 */
	public Request getLastRequest();
	
	/**
	 * @param item : the item on which to set the attribute 
	 * @param attribute : the attribute to set
	 * @param value : value to set the attribute to 
	 */
	public void setValue(Item item , String attribute , Object value);
	
	
	/**
	 * Concrete implementation of the fetch method
	 * @param request : the Request object to fetch the item from
	 */
	public void fetch(Request request);
	
	
	/**
	 * Method required by dojo. This method can be overriden by subclasses to provide their
	 * own definition of what an item should be
	 * @param item : the object to test
	 * @return true if item is an instance of Item and the store contains this item,
	 * 		  false otherwise
	 */
	public boolean isItem(Object item);
	
	/**
	 * @param item : the item to get the attribute's value for
	 * @param attribute : the name of the attribute
	 * @param defaultValue : value to return if the item is not present, or if
	 * 		  the item does not contain the desired attribute.
	 * @return the attribute's value, or the default value.
	 * 
	 */
	public Object getValue(Item item , String attribute , Object defaultValue);
	
	/**
	 * @param item : the item to get the attributes from
	 * @return : an array containing the attributes names.
	 */
	public String[] getAttributes(Item item);
	
	/**
	 * @param item 
	 * @param attributeName : attribute to test
	 * @return : true if the item has this attribute, 
	 * 			 false otherwise.
	 */
	public boolean hasAttribute(Item item , String attributeName);
	
	/**
	 * Returns true if item's attribute contains the specified value
	 * @param item
	 * @param attribute
	 * @param value
	 * @return true if item contains the value for the given attribute, false otherwise
	 */
	public boolean containsValue(Item item , String attribute , Object value);
	
	
	/**
	 * @param id : the id to search the item by
	 * @return the item with the specified id, null if no one does.
	 */
	public Item getItemByIdentity(Object id);
	
	/**
	 * @param item : the item to get the label from
	 * @return the label
	 */
	public String getLabel(Item item);
	
	/**
	 * @param item 
	 * @return an array containing the attributes used to construct the
	 * 	specified item's label 
	 */
	public String[] getLabelAttributes(Item item);
	
	/**
	 * Test if the item has been loaded , and if not try to load the item.
	 * If it loads it, it notify the itemFetchListeners.
	 * 
	 * @see com.objetdirect.tatami.client.data.ReadStore#loadItem(com.objetdirect.tatami.client.data.Item, com.objetdirect.tatami.client.data.LoadItemListener)
	 */
	public boolean loadItem(Item item);
	

	/**
	 * @param item : the item to test
	 * @return true if the item has already been loaded, false otherwise
	 */
	public  boolean isItemLoaded(Item item);
	

	/**
	 * @return the current store's size
	 */
	public int size();
	
}
