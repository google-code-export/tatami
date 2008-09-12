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

public abstract class Processor implements EventProcessor {

	// back to the MVC component
	protected MVCComponent notifier_;

	/**
	 * 
	 * @param _e
	 *            com.crionics.fw.ui.Event
	 */
	public void fire(Event _e) {
		notifier_.fire(_e);
	}

	/**
	 * 
	 * @param _ep
	 *            java.lang.Object
	 */
	public void setNotifier(MVCComponent _n) {
		notifier_ = _n;
	}
}
