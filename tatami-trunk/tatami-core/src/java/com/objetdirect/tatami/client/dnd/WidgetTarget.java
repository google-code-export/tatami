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


import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Panel;

/**
 * This class represents a WidgetTarget, that means, a Panel which can accept drops.
 * 
 * @author rdunklau
 *
 */
public class WidgetTarget implements IDnDTarget {

	private Panel panel;
	
	
	/**
	 * @return the panel associated with this target
	 */
	public Panel getPanel() {
		return panel;
	}

	/**
	 * @param panel the panel associated with this target
	 */
	public WidgetTarget(Panel panel){
		this.panel = panel;
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.dnd.IDnDTarget#getDomElement()
	 */
	public Element getDomElement() {
		return panel.getElement();
	}
	
}
