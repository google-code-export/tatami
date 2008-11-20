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
package com.objetdirect.tatami.client.dnd;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.objetdirect.tatami.client.tree.Tree;

/**
 * This class implements IDnDSource to provide a drag and drop source from a tree.
 * Each item from the tree will be draggable 
 * 
 * 
 * @author rdunklau
 *
 */
public class TreeSource extends TreeTarget implements IDnDSource<DndTreeElement> {

	private Map<String,DndTreeElement> elements;
	
	
	/**
	 * @param tree : the tree which should be turned into a TreeSource
	 */
	public TreeSource(Tree tree){
		super(tree);
		elements = new HashMap<String, DndTreeElement>();
	}
	
	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.dnd.IDnDSource#addElement(com.objetdirect.tatami.client.dnd.IDnDElement)
	 */
	public void addElement(DndTreeElement element) {
		DnDTreeController.getInstance().addDnDElementToJSSource(this, element);
		elements.put(element.getDndId() , element);
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.dnd.IDnDSource#getDndElementById(java.lang.String)
	 */
	public DndTreeElement getDndElementById(String id) {
		return elements.get(id);
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.dnd.IDnDSource#getRegisteredDndElement()
	 */
	public Collection<DndTreeElement> getRegisteredDndElement() {
		return elements.values();
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.dnd.IDnDSource#removeElement(com.objetdirect.tatami.client.dnd.IDnDElement)
	 */
	public void removeElement(DndTreeElement element) {
		DnDTreeController.getInstance().removeDnDElementFromJSSource(this , element);
		Collection<DndTreeElement> treeElems = elements.values();
		treeElems.remove(element);
	}


}
