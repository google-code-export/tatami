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

/**
 * Insert the type's description here. Creation date: (6/5/01 3:22:02 PM)
 *
 * @author: Refalo, Olivier
 */
public class ModelEvent extends Event {

	/**
	 * Insert the method's description here. Creation date: (6/5/2001 10:47:24
	 * PM)
	 */
	public ModelEvent(String type, Object source) {
		super(type, source);
	}

	/**
	 * Insert the method's description here. Creation date: (6/5/2001 10:47:24
	 * PM)
	 */
	public ModelEvent(String type, Object source, Object data) {
		super(type, source, data);
	}

	public ModelEvent(Event event) {
		super(event);
	}

	public boolean isViewEvent() {
		return false;
		}

	public boolean isModelEvent() {
		return true;
	}

}
