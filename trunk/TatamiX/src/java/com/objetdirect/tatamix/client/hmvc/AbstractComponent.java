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

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements code reuse between View and Model Adapters
 */
public abstract class AbstractComponent extends AbstractProcessor implements 	MVCComponent {
	

	protected List<MVCComponent> listeners = null;



	/**
	 *
	 */
	public AbstractComponent() {
	}

	/**
	 * Used internally by the MVC Facade
	 */
	AbstractComponent(MVCComponent _l) {
	//	defaultNotifier_ = _l;
	}

	/**
	 * adds a listener. listeners are triggered when events are fired from
	 * within the component
	 *
	 * @param _sl listener
	 */
	public void addListener(MVCComponent _sl) {
		if (_sl != null) {
			if (checkListenerType(_sl)) {
				if (listeners == null)
					listeners = new ArrayList<MVCComponent>();
				listeners.add(_sl);
			}
			
		}
	}

	/**
	 * @param _l event to check
	 * @return true if the event has the appropriate type
	 */
	protected abstract boolean checkEventType(Event _l);

	/**
	 * @param _l  listener to check
	 * @return true if the listener has the appropriate type
	 */
	protected abstract boolean checkListenerType(MVCComponent _l);

	/**
	 * Fires an event and indirectly triggers all the listeners associated with
	 * it.
	 *
	 * @param _e
	 *            Event to trigger
	 */
	public void fire(Event _e) {
		if (_e != null) {
			if (checkEventType(_e)) {
				if (listeners != null) {
					for (int i = listeners.size(); --i >= 0;) {
						MVCComponent l =  listeners.get(i);
						l.process(_e);
					}
				}
			}
			
		}
	}

	/**
	 * Removes the given listener from the list of listener attached with this
	 * component
	 *
	 * @param _sl
	 *            listener to remove
	 */
	public void removeListener(MVCComponent _sl) {
		if (_sl != null) {
			if (checkListenerType(_sl)) {
				if (listeners != null) {
					listeners.remove(_sl);
				}
			}
		
		}
	}
}//end of class
