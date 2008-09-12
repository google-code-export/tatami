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

public class ViewEvent extends Event {

	/**
	 * DisplayEvent constructor comment.
	 *
	 * @param _type
	 *            com.crionics.fw.ui.EventType
	 * @param _source
	 *            java.lang.Object
	 */
	public ViewEvent(int _type, Object _source) {
		super(_type, _source);
	}

	/**
	 * DisplayEvent constructor comment.
	 *
	 * @param _type
	 *            com.crionics.fw.ui.EventType
	 * @param _source
	 *            java.lang.Object
	 * @param _data
	 *            java.lang.Object
	 */
	public ViewEvent(int _type, Object _source, Object _data) {
		super(_type, _source, _data);
	}

	public ViewEvent(Event _e) {
		super(_e);
	}

	public boolean isViewEvent() {
		return true;
		}

	public boolean isModelEvent() {
		return false;
	}



}
