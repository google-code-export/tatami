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
	protected int type_;

	protected Object data_;

	/**
	 * Insert the method's description here. Creation date: (6/5/2001 10:47:24
	 * PM)
	 */
	public Event(int _type, Object _source) {
		super(_source);
		type_ = _type;
		data_ = null;
	}

	/**
	 * Insert the method's description here. Creation date: (6/5/2001 10:47:24
	 * PM)
	 */
	public Event(int _type, Object _source, Object _data) {
		super(_source);
		type_ = _type;
		data_ = _data;
	}

	/**
	 * Copy constructor
	 */
	public Event(Event _e) {
		super(_e.getSource());
		type_ = _e.type_;
		data_ = _e.data_;
	}

	/**
	 * Insert the method's description here. Creation date: (6/6/2001 12:12:22
	 * AM)
	 *
	 * @return java.lang.Object
	 */
	public Object getData() {
		return data_;
	}

	/**
	 * Insert the method's description here. Creation date: (6/6/2001 12:12:22
	 * AM)
	 *
	 * @return com.test.EventType
	 */
	public int getType() {
		return type_;
	}

	/**
	 * Insert the method's description here. Creation date: (6/6/2001 12:12:22
	 * AM)
	 *
	 * @param newData_
	 *            java.lang.Object
	 */
	public void setData(Object newData_) {
		data_ = newData_;
	}

	/**
	 * Insert the method's description here. Creation date: (6/6/2001 12:12:22
	 * AM)
	 *
	 * @param newType_
	 *            com.test.EventType
	 */
	public void setType(int newType_) {
		type_ = newType_;
	}

	/**
	 *
	 * @return java.lang.String
	 */
	public String toString() {
		return Integer.toString(type_);
	}

	public abstract boolean isViewEvent();
	public abstract boolean isModelEvent();

}
