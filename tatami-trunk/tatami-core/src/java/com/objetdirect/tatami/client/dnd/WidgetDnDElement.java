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
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;

/**
 * This class implements a IDnDElement for a widget.
 * It is used to wrap a widget as a draggable element
 * 
 * @author rdunklau
 *
 */
public class WidgetDnDElement implements IDnDElement{

		private static Map widgetsToElementMap = new HashMap();
		
		private Widget widget;
		
		private IDnDSource<WidgetDnDElement> source;
		
		public Widget getWidget() {
			return widget;
		}

		private String id;
		
		
		/**
		 * @param widget : the widgets which should be turned into a
		 * a draggable object
		 */
		public WidgetDnDElement(Widget widget) {
			widgetsToElementMap.put(widget, this);
			this.widget = widget;
			Element widgetElement = widget.getElement();
			String id = DOM.getElementAttribute(widgetElement,"id");
			if((id == null || id == "")){
				id = DnD.dojoGetUniqueId();
				DOM.setElementAttribute(widgetElement, "id", id);
			}
			this.id = id;
		}

		
		/* (non-Javadoc)
		 * @see com.objetdirect.tatami.client.dnd.IDnDElement#getDndId()
		 */
		public String getDndId() {
			return this.id;
		}


		/* (non-Javadoc)
		 * @see com.objetdirect.tatami.client.dnd.IDnDElement#getElement()
		 */
		public Element getElement() {
			return widget.getElement();
		}
		
		
		/**
		 * Destroy this widget element
		 */
		public void destroy(){
			this.widget = null;
		}


		/* (non-Javadoc)
		 * @see com.objetdirect.tatami.client.dnd.IDnDElement#getSource()
		 */
		public IDnDSource getSource() {
			return source;
		}


		/* (non-Javadoc)
		 * @see com.objetdirect.tatami.client.dnd.IDnDElement#setSource(com.objetdirect.tatami.client.dnd.IDnDSource)
		 */
		public void setSource(IDnDSource source) {
			this.source = source;
		}

		
	}
