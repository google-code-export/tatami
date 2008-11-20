package com.objetdirect.tatami.client.grid.editor;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseEditor implements GridEditor {

	private Map<String, Object> attributes = new HashMap<String, Object>();

	public BaseEditor(){
		
	}
	
	public BaseEditor(Boolean alwaysEditing){
		setAlwaysEditing(alwaysEditing);
	}
	
	public Map<String,Object> getAttributes() {
		return attributes;
	}

	public void setAlwaysEditing(Boolean always){
		attributes.put("alwaysEditing",always);
	}

	public boolean getAlwaysEditing(boolean always){
		return (Boolean) attributes.get("alwaysEditing");
	}
	
	public void addAttribute(String attrName,Object attrValue){
		attributes.put(attrName,attrValue);
	}
	
	public Object getAttribute(String attrName){
		return attributes.get(attrName);
	}
	
}
