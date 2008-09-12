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

import java.util.HashMap;
import java.util.Map;

/**
 * This class implements code reuse between View and Model Adapters
 */
abstract class AbstractProcessor implements MVCComponent {
	protected Map registered_ = null;

	public boolean process(Event _e) {

		if (registered_ != null) {
			// Category.getInstance(Constants.LOGGER_NAME).info("Looking for " +
			// _e + " in registered processors");
			EventProcessor p = (EventProcessor) registered_.get(new Integer(_e.getType()));

			if (p != null) {
				// Category.getInstance(Constants.LOGGER_NAME).info("Running
				// processors");
				p.run(_e);
				return true;
			}
		}
		return false;
	}

	/**
	 * Registers a processor with a given event type. Processors are triggered
	 * when events are fired.
	 * 
	 * @param _t
	 *            the type of event this processor will process
	 * @param _processor
	 *            the action
	 */

	public void register(int _t, EventProcessor _processor) {
		if (_processor != null) {
			if (registered_ == null) {
				registered_ = new HashMap();
			}

			_processor.setNotifier(this);
			// Category.getInstance(Constants.LOGGER_NAME).info("Adding event
			// processor " + _t);
			registered_.put(new Integer(_t), _processor);
		}

	}
}