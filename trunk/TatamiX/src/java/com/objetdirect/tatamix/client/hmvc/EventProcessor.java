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
public interface EventProcessor {

	/**
	 * Fires the event with the notifier
	 * @param event the event to fire by the notifier
	 *            
	 */
	public void fire(Event event);

	/**
	 * Runs the event
	 * @param event the event to run
	 *            
	 */
	public void run(Event event);

	/**
	 * Sets the notifier which will fire the events
	 * @param component the <code>MVCComponent</code>
	 *            
	 */
	public void setNotifier(MVCComponent component);
}
