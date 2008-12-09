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

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.layout.ContentPanel;

/**
 * Implements a DnDSource for widgets.
 * 
 * It wraps a GWT Panel, and manages draggable widgets (WidgetDnDElement)
 * 
 * @author rdunklau
 *
 */
public class WidgetSource extends WidgetTarget implements IDnDSource<WidgetDnDElement>{

	private Map<String,WidgetDnDElement> elements;
	
	
	@Override
	public Widget getWidget() {
		return super.getWidget();
	}

	public Panel getPanel(){
		return ((Panel)super.getWidget());
	}

	/**
	 * @param panel : the panel which should be turned into a DnDSource
	 */
	public WidgetSource(Panel panel){
		super(panel);
		elements = new HashMap<String, WidgetDnDElement>();
	}
	
	/**
	 * @param panel : the panel which should be turned into a DnDSource
	 * @param registerAllCurrentChildWidgets : if true, all current child of this panel are turned
	 * into WidgetDndElement, false otherwise.
	 */
	public WidgetSource(Panel panel , boolean registerAllCurrentChildWidgets){
		this(panel);
		if(registerAllCurrentChildWidgets){
			for (Widget widget : panel) {
				addElement(new WidgetDnDElement(widget));
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.dnd.IDnDSource#addElement(com.objetdirect.tatami.client.dnd.IDnDElement)
	 */
	public void addElement(WidgetDnDElement element) {
		WidgetDnDController.getInstance().addDnDElementToJSSource(this, element);
		elements.put(element.getDndId() , element);
	}
	

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.dnd.IDnDSource#getDndElementById(java.lang.String)
	 */
	public WidgetDnDElement getDndElementById(String id) {
		return elements.get(id);
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.dnd.IDnDSource#getRegisteredDndElement()
	 */
	public Collection<WidgetDnDElement> getRegisteredDndElement() {
		return elements.values();
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.dnd.IDnDSource#removeElement(com.objetdirect.tatami.client.dnd.IDnDElement)
	 */
	public void removeElement(WidgetDnDElement element) {
		WidgetDnDController.getInstance().removeDnDElementFromJSSource(this , element);
		Collection<WidgetDnDElement> widgetElems = elements.values();
		widgetElems.remove(element);
	}

}
