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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.objetdirect.tatami.client.ConvertibleToJSObject;
import com.objetdirect.tatami.client.JSHelper;

/**
 * This class represents an item in the datastore. Items are composed
 * of attributes, identified by their names, which values can be any object
 * 
 * @author rdunklau
 *
 */
public class Item  implements ConvertibleToJSObject{
	
	
	private DataStore store;
	
	final public static String childAttribute = "_____tatami__children____attribute"; 
	final public static String labelAttribute = "_____tatami__label____attribute";
	final public static String idAttribute = "_____tatami__id____attribute";
	

	/**
	 * Item's attributes
	 */
	protected Map<String, Object> attributes;
	
	Item parentItem;
	

	
	/**
	 * Default constructor
	 */
	public Item(){
		attributes = new HashMap<String, Object>();
	}
	
	
	
	/**
	 * Constructs an item from a map containing Item's attributes
	 * 
	 * @param map
	 */
	public Item(Map<String, Object> map){
		attributes = map;
	}
	
	
	public Item(String id, String label) {
		this();
		setId(id);
		setLabel(label);
	}

	public Object getId(){
		return attributes.get(idAttribute);
	}
	
	public void setId(Object id){
		attributes.put(idAttribute , id);
	}
	
	public Object getLabel(){
		return attributes.get(labelAttribute);
	}
	
	public void setLabel(String label){
		attributes.put(labelAttribute , label);
	}
	

	/**
	 * @param attributeName : the attribute we want to test the presence
	 * @return : true if item has the given attribute, 
	 * 			false otherwise
	 */
	public boolean hasAttribute(String attributeName){
		return attributes.containsKey(attributeName);
	}
	
	/**
	 * @param attribute : the attribute we want to test the value of
	 * @param value : the value expected for the attribute
	 * @return true if the given attribute has the given value,
	 * false otherwise
	 */
	public boolean containsValue(String attribute , Object value){
		Object valueFromMap = attributes.get(attribute);
		if(valueFromMap == value){
			return true;
		}else if(valueFromMap instanceof Collection){
			if(((Collection<?>) valueFromMap).contains(value)){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	/**
	 * @return a String array containing the attributes names.
	 */
	public String[] getAttributes(){
		Collection<String> values = attributes.keySet();
		String[] toReturn = new String[values.size()];
		int i = 0;
		for (Iterator<String> iterator = values.iterator(); iterator.hasNext();) {
			Object object = iterator.next();
			toReturn[i] = object != null ? object.toString() : "";
			i++;
		}
		return toReturn;
	}
	
	/**
	 * @param attributeName
	 * @return the value for the given attribute
	 */
	public Object getValues(String attributeName){
		return this.attributes.get(attributeName);
	}
	
//	/**
//	 * Adds an attribute to the item
//	 * 
//	 * @param attrname
//	 * @param attrvalue
//	 */
//	public void addAttribute(String attrname , Object attrvalue){
//		this.attributes.put(attrname, attrvalue);
//	}
//	
//	/**
//	 * Adds an attribute to the item
//	 * 
//	 * @param attrname
//	 * @param attrvalue
//	 */
//	public void addAttribute(String attrname , String attrvalue){
//		this.attributes.put(attrname, attrvalue);
//	}
//	
//	/**
//	 * Adds an attribute to the item
//	 * 
//	 * @param attrname
//	 * @param attrvalue
//	 */
//	public void addAttribute(String attrname , Date attrvalue){
//		this.attributes.put(attrname, attrvalue);
//	}
//	
//	
//	/**
//	 * Adds an attribute to the item
//	 * 
//	 * @param attrname
//	 * @param attrvalue
//	 */
//	public void addAttribute(String attrname , JavaScriptObject attrvalue){
//		this.attributes.put(attrname, attrvalue);
//	}
//	
//	/**
//	 * Adds an attribute to the item
//	 * 
//	 * @param attrname
//	 * @param attrvalue
//	 */
//	public void addAttribute(String attrname , Number attrvalue){
//		this.attributes.put(attrname, attrvalue);
//	}
//	
//	
//	/**
//	 * Adds an attribute to the item
//	 * 
//	 * @param attrname
//	 * @param attrvalue
//	 */
//	public void addAttribute(String attrname , Boolean attrvalue){
//		this.attributes.put(attrname, attrvalue);
//	}
	
	/**
	 * @param attributeName
	 * @param defaultValue
	 * @return the value for the given attribute, or defaultValue if item 
	 * does not contain any value for the given attribute or if this value equals null.
	 * 
	 */
	public Object getValue(String attributeName , Object defaultValue){
		Object value = getValues(attributeName);
		if(value == null){
			return defaultValue;
		}else{
			return value;
		}
	}
	
	public Object getValue(String attributeName){
		return getValue(attributeName , null);
	}
	
	public void setValue(String attributeName , Object value){
		Object oldValue = attributes.get(attributeName);
		attributes.put(attributeName, value);
		if(store != null){
			store.onSet(this, attributeName , oldValue, value);
		}
	}

	/**
	 * Removes the given attribute
	 * @param attributeName : the attribute to remove
	 */
	public void removeAttribute(String attributeName){
		Object oldValue = attributes.get(attributeName);
		attributes.remove(attributeName);
		if(store != null){
			store.onSet(this, attributeName , oldValue, null);
		}
	}


	public JavaScriptObject toJSObject() {
		return JSHelper.convertObjectToJSObject(attributes);
	}
	
	public DataStore getStore() {
		return store;
	}

	public void setStore(DataStore store) {
		this.store = store;
	}
	
	public void setChildren(List<Item> children){
		attributes.put(childAttribute, children);
	}
	
	public List<Item> getChildren(){
		return (List<Item>) attributes.get(childAttribute);
	}
	
	public void addChild(Item item){
		List<Item> children = getChildren();
		if(children == null){
			children = new ArrayList<Item>();
			setValue(childAttribute, children);
		}
		parentItem = item;
		children.add(item);
	}
	
	public void removeChild(Item item){
		item.setParentItem(null);
		List<Item> children = (List<Item>) getValue(childAttribute, null);
		if(children != null){
			children.remove(item);
		}
	}
	
	public void setParentItem(Item item){
		this.parentItem = item;
	}

	public Item getParentItem(){
		return parentItem;
	}

}
