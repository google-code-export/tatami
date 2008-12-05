package com.objetdirect.tatami.client.grid.editor;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseEditor implements GridEditor {

	private Map<String, Object> attributes = new HashMap<String, Object>();

	public BaseEditor(){
		
	}
	
	public Map<String,Object> getAttributes() {
		return attributes;
	}

	
	public void addAttribute(String attrName,Object attrValue){
		attributes.put(attrName,attrValue);
	}
	
	public Object getAttribute(String attrName){
		return attributes.get(attrName);
	}
	
}
