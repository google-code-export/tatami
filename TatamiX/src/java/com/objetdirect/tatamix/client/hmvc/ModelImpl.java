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
 * @author: Olivier Refalo orefalo@yahoo.com
 */
public class ModelImpl extends AbstractComponent implements Model {

	/**
	 * put your documentation comment here
	 */
	public ModelImpl() {
		super();
	}

	/**
	 * Used internaly by the MVC Facade
	 */
	ModelImpl(MVCComponent component) {
		super(component);
	}

	/**
	 * returns true is the event has the appropriate type
	 */
	protected boolean checkEventType(Event event) {
		return (event instanceof ModelEvent);
	}

	/**
	 * returns true is the listener has the appropriate type
	 */
	protected boolean checkListenerType(MVCComponent component) {
		return (component instanceof Controller || component instanceof Model);
	}
}
