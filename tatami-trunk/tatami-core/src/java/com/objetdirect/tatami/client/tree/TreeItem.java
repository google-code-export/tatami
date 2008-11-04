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
package com.objetdirect.tatami.client.tree;

import java.util.ArrayList;
import java.util.List;

import com.objetdirect.tatami.client.data.Item;

public class TreeItem extends Item{

	private TreeItem parentItem;
	
	public TreeItem(Item item){
		String[] attributes = item.getAttributes();
		for (int i = 0; i < attributes.length; i++) {
			String currentAttribute = attributes[i];
			this.addAttribute(currentAttribute, item.getValue(currentAttribute, null));
		}
	}
	
	public TreeItem(){
		
	}
	
	public TreeItem(String label, String id){
		addAttribute("label", label);
		addAttribute("id", id);
	}
	
	
	public void addChild(TreeItem item){
		List<TreeItem> children = getChildren();
		if(children == null){
			children = new ArrayList<TreeItem>();
			addAttribute("children", children);
		}
		item.setParentItem(this);
		children.add(item);
	}
	
	public void removeChild(TreeItem item){
		item.setParentItem(null);
		List<?> children = (List<?>) getValue("children", null);
		if(children != null){
			children.remove(item);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<TreeItem> getChildren(){
		List<TreeItem> children = (List<TreeItem>) attributes.get("children");
		return children;
	}

	@Override
	public Object getValues(String attributeName) {
		if(attributeName == "children"){
			return getChildren();
		}else{
			return super.getValues(attributeName);
		}
	}

	public TreeItem getParentItem() {
		return parentItem;
	}

	public void setParentItem(TreeItem parentItem) {
		this.parentItem = parentItem;
	}
	
	public void setLeafIconClass(String iconClass){
		addAttribute("leaf-class", iconClass);
	}
	
	public void setFolderOpenIconClass(String iconClass){
		addAttribute("folder-open-class", iconClass);
	}
	
	public void setFolderClosedIconClass(String iconClass){
		addAttribute("folder-closed-class", iconClass);
	}
	
	public String getFolderOpenIconClass(){
		return (String) getValue("folder-open-class", null);
	}
	
	public String getFolderClosedIconClass(){
		return (String) getValue("folder-closed-class", null);
	}
	
	public String getLeafIconClass(){
		return (String) getValue("leaf-class", null);
	}
	
	public void setLabelClass(String labelClass){
		addAttribute("labelClass", labelClass);		
	}

	public String getLabelClass(){
		return (String) getValue("labelClass", null);
	}
	
	public void clearChildren(){
		this.removeAttribute("children");
	}
	
}
