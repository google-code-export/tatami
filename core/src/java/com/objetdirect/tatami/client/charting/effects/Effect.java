/*
 * Tatami: 
 * Copyright (C) 2007 Objet Direct
 * Copyright (C) 2007 France Telecom
 * Contact: tatami@googlegroups.com
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
 * USA
 *
 * Authors: Ronan Dunklau
 * Initial developer(s): Ronan Dunklau
 * Contributor(s): 
 */
package com.objetdirect.tatami.client.charting.effects;

import com.objetdirect.tatami.client.charting.Chart2D;
import com.objetdirect.tatami.client.charting.Plot;

/**
 * This interface represents a chart effect
 * 
 * @author rdunklau
 *
 */
public interface Effect {
	
	/**
	 * @param event: the EffectEvent to be processed
	 */
	public void processEvent(EffectEvent event);
	
	void destroyEffect();
	
	void initEffect(Chart2D chart, Plot<?> plot);
}
