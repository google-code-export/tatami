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
	
	protected Map<String,EventProcessor> registered = null;

	public boolean process(Event event) {
		boolean result = false;
		if (registered != null) {
			EventProcessor p = registered.get(event.getType());
			if (p != null) {
				p.run(event);
				result= true;
			}
		}
		return result;
	}

	/**
	 * Registers a processor with a given event type. Processors are triggered
	 * when events are fired.
	 * 
	 * @param type   the type of event this processor will process
	 * @param processor the action should not be <code>null</code>
	 */

	public void register(String type, EventProcessor processor) {
		//if (processor != null) {
			if (registered == null) {
				registered = new HashMap<String,EventProcessor>();
			}
			processor.setNotifier(this);
			registered.put(type, processor);
		//}

	}
}//end of class