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
 * Authors:  Ronan Dunklau
 * Initial developer(s): Ronan Dunklau
 * Contributor(s):
 */
package com.objetdirect.tatami.client.dnd;

import java.util.HashMap;
import java.util.Map;


/**
 * @author rdunklau
 *
 * This class is used to keep mapping between objects and source and targets
 * 
 * @param <K> 
 * @param <S>
 * @param <T>
 */
public class DnDSourceTargetRegistry<K , S extends IDnDSource<?> , T extends IDnDTarget> {

	public Map<K ,  S> panelToSourceMapping = new HashMap<K ,  S>();
	
	public Map<K ,  T> panelToTargetMapping = new HashMap<K ,  T>();

	
	public  S getSource(K object){
		return  panelToSourceMapping.get(object);
	}
	
	public  T getTarget(K object){
		return  panelToTargetMapping.get(object);
	}
	
	public void addSourceMapping(K object, S source){
		panelToSourceMapping.put(object, source);
	}
	
	public void addTargetMapping(K object , T target){
		panelToTargetMapping.put(object, target);
	}
	
	public  void removeSourceMapping(K object){
		panelToSourceMapping.remove(object);
	}
	
	public void removeTargetMapping(K object){
		panelToTargetMapping.remove(object);
	}
	
	
	
	
}
