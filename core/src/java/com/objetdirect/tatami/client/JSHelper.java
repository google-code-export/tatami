package com.objetdirect.tatami.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;


import com.google.gwt.core.client.JavaScriptObject;
import com.objetdirect.tatami.client.data.Item;

public class JSHelper {
	 
 	/**
 	 * @param obj : Java Object to convert. It can be : 
 	 * 	- a Map 
 	 * 	- a String
 	 *  - a Number
 	 * 	- a Collection of any of the above , including a Collection of Collections
 	 *  
 	 * @return a JavaScripObject equivalent to the Java Object to convert
 	 * @throws NotJSONIzableObjectException
 	 */
 	
 	private native static JavaScriptObject convertMapToJSObject(Map<?, ?> map)/*-{
 		var obj = {};
 		var keys = map.@java.util.Map::keySet()();
 	 	var iterator = keys.@java.util.Set::iterator()();
 	 	while(iterator.@java.util.Iterator::hasNext()()){
 	 		var string = iterator.@java.util.Iterator::next()();
 	 		var value = map.@java.util.Map::get(Ljava/lang/Object;)(string);
 	 		var newvalue;
 	 		if(@com.objetdirect.tatami.client.JSHelper::isString(Ljava/lang/Object;)(value)){
 	 			newvalue = value;
 	 		}
 	 		else if(@com.objetdirect.tatami.client.JSHelper::isNumber(Ljava/lang/Object;)(value)){
 	 			newvalue = value.@java.lang.Number::doubleValue()();
 	 		}else if(@com.objetdirect.tatami.client.JSHelper::isDate(Ljava/lang/Object;)(value)){
 	 			newvalue = @com.objetdirect.tatami.client.DateUtil::getJSDate(Ljava/util/Date;)(value);
 	 		}else if(@com.objetdirect.tatami.client.JSHelper::isBoolean(Ljava/lang/Object;)(value)){
 	 			newvalue = value.@java.lang.Boolean::booleanValue()();
 	 		}else {
 	 			newvalue = @com.objetdirect.tatami.client.JSHelper::convertObjectToJSObject(Ljava/lang/Object;)(value);
 	 			newvalue = newvalue == null ? value : newvalue;
 	 		}
 	 		obj[string] = newvalue;
 	 	}
 	 	return obj; 
 	}-*/;
 	
 	public static boolean isString(Object object){
 		if(object instanceof String)
 			return true;
 		else
 			return false;
 	}
 	
 	public static boolean isNumber(Object object){
 		if(object instanceof Number)
 			return true;
 		else 
 			return false;
 	}
 	
 	public static boolean isDate(Object object){
 		if(object instanceof Date)
 			return true;
 		else 
 			return false;
 	}
	
 	public static boolean isBoolean(Object object){
 		if(object instanceof Boolean)
 			return true;
 		else 
 			return false;
 	}
 
	public static Collection<Object> convertJSArrayToCollection(JavaScriptObject array){
		Collection<Object> collec = new ArrayList<Object>();
		int length = getArraySize(array);
		for (int i = 0; i < length; i++) {
			collec.add(getElementAtIndex(array, i));
		}
		return collec;
	}
	
	public static native Object getElementAtIndex(JavaScriptObject array , int index)/*-{
		return array[index];
	}-*/;
	
	public static native Object getElementAtIndex(JavaScriptObject array , String index)/*-{
		return array[index];
	}-*/;
	
	public static Number getNumberElementAtIndex(JavaScriptObject array , String index){
		return (Number) jsGetNumberElementAtIndex(array, index);
	}
	
	private static native double jsGetNumberElementAtIndex(JavaScriptObject array , String index)/*-{
		return array[index];
	}-*/;
	
	public static native int getArraySize(JavaScriptObject array)/*-{
		return array ? array.length : 0;
	}-*/;
	
 	private  static JavaScriptObject convertArrayToJSObject(Object[] collec){
 		JavaScriptObject array = JavaScriptObject.createArray();
 		for (int i = 0; i < collec.length; i++) {
 			if(collec[i] instanceof String){
 				array = addStringToArray(array, i , (String) collec[i] );
 			}else if(collec[i] instanceof Number){
 				array = addNumberToArray(array, i , ((Number) collec[i]).doubleValue() );
 			}else if(collec[i] instanceof Date){
 				array = addDateToArray(array, i , (Date) collec[i]  );
 			}else if(collec[i] instanceof Boolean){
 				array = addBooleanToArray(array, i , ((Boolean) collec[i]).booleanValue()  );
 			}else if(collec[i] instanceof Item){
 				array = addObjectToArray(array, i , collec[i] );
 			}else{
 				array = addObjectToArray(array, i , convertObjectToJSObject(collec[i]) );
 			}
		}
 		return array;
 	};
 	 	
 	
 	
 	public static JavaScriptObject convertObjectToJSObject(Object object){
 		if(object instanceof JavaScriptObject){
 			return (JavaScriptObject) object;
 		}else if(object instanceof ConvertibleToJSObject){
 			return (((ConvertibleToJSObject)object).toJSObject());
 		}else if(object instanceof Map){
 			return convertMapToJSObject((Map<?, ?>)object);
 		}else if(object instanceof Object[]){
 			return convertArrayToJSObject((Object [])object);
 		}else if(object instanceof Collection){
 			return convertArrayToJSObject(((Collection<?>)object).toArray());
 		}else{
 			return null ; 
 		}
 	}
 	
 	public static native JavaScriptObject encapsulateObjectInArray(JavaScriptObject jsObject)/*-{
		var toReturn = @com.google.gwt.core.client.JavaScriptObject::createArray()();
		toReturn[0] = jsObject; 
		return toReturn;
	}-*/;
 	
 	private native static JavaScriptObject addObjectToArray(JavaScriptObject array , int index , JavaScriptObject toAdd)/*-{
 		array.push(toAdd);
 		return array;
 	}-*/;
 	
 	private native static JavaScriptObject addObjectToArray(JavaScriptObject array , int index , Object toAdd)/*-{
 		array.push(toAdd);
		return array;
	}-*/;
 	
 	private native static JavaScriptObject addStringToArray(JavaScriptObject array , int index , String toAdd)/*-{
 		array.push(toAdd);
		return array;
	}-*/;
 	
 	private native static JavaScriptObject addNumberToArray(JavaScriptObject array , int index , double toAdd)/*-{
 		array.push(toAdd);
		return array;
	}-*/;
 	
 	private native static JavaScriptObject addDateToArray(JavaScriptObject array , int index , Date toAdd)/*-{
 		array.push(toAdd);
		return array;
	}-*/;
 	
	private native static JavaScriptObject addBooleanToArray(JavaScriptObject array , int index , boolean toAdd)/*-{
 		array.push(toAdd);
	return array;
}-*/;
	
	public static native JavaScriptObject addAttribute(JavaScriptObject toModify , String attrName , JavaScriptObject attrValue)/*-{
 		array.push(toAdd);
	return toModify;
}-*/;

	public static native JavaScriptObject addAttribute(JavaScriptObject toModify , String attrName , String attrValue)/*-{
 		array.push(toAdd);
		return toModify;
}-*/;
 	
	public static native JavaScriptObject getBooleanValue(Boolean bool)/*-{
		return bool.@java.lang.Boolean::booleanValue()();
	}-*/;
 	
}
