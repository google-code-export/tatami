/*
 * Copyright 2001 by Olivier Refalo
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *
 * @author: Olivier Refalo orefalo@yahoo.com
 * @see http://www.crionics.com
 */

package com.objetdirect.tatamix.client.hmvc;

import java.util.EventObject;
  
/**
 * put your documentation comment here
 */
public abstract class Event extends EventObject {
	
	protected String type;
	protected Object data;

	/**
	 * Insert the method's description here. Creation date: (6/5/2001 10:47:24
	 * PM)
	 */
	public Event(String type, Object source) {
		super(source);
		this.type = type;
		this.data = null;
	}

	/**
	 * Insert the method's description here. Creation date: (6/5/2001 10:47:24
	 * PM)
	 */
	public Event(String type, Object source, Object data) {
		this(type,source);
		this.data = data;
	}

	/**
	 * Copy constructor
	 */
	public Event(Event event) {
		this(event.getType(),event.getSource(),event.getData());
	}

	/**
	 * Insert the method's description here. Creation date: (6/6/2001 12:12:22
	 * AM)
	 *
	 * @return java.lang.Object
	 */
	@SuppressWarnings("unchecked")
	public <T>T getData() {
		return (T)data;
	}

	/**
	 * Insert the method's description here. Creation date: (6/6/2001 12:12:22
	 * AM)
	 *
	 *
	 */
	public String getType() {
		return type;
	}

	/**
	 * Insert the method's description here. Creation date: (6/6/2001 12:12:22
	 * AM)
	 *
	 * @param newData_
	 *            java.lang.Object
	 */
	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * Insert the method's description here. Creation date: (6/6/2001 12:12:22
	 * AM)
	 *
	 * @param type
	 *            
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 *
	 * @return java.lang.String
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer("Event:");
		buffer.append(getType());
		return buffer.toString();
	}

	public abstract boolean isViewEvent();
	
	public abstract boolean isModelEvent();

}
