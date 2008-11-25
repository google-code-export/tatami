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

import java.util.EventListener;

public interface MVCComponent extends EventListener {

	/**
	 *
	 */
	void addListener(MVCComponent _sl);

	/**
	 *
	 * @param e
	 *            com.crionics.fw.ui.Event
	 */
	void fire(Event e);

	/**
	 *
	 * @return java.lang.Object
	 * @param _e
	 *            com.crionics.fw.ui.Event
	 */
	boolean process(Event _e);

	/**
	 *
	 * @param _t
	 *            com.crionics.fw.ui.EventType
	 * @param _processor
	 *            java.lang.Object
	 */
	void register(String _t, EventProcessor _processor);

	/**
	 *
	 */
	void removeListener(MVCComponent _sl);
}
