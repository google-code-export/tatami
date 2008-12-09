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

import java.util.Collection;











/**
 * @author rdunklau
 *
 * This interface defines a drag and drop behavior. A drag and drop behavior is usually attached to
 * a drag and drop source. A drag and drop source can handle dnd events by itself, but it is recommended to
 * delegate it to a behaviour, which can be common to multiple source.
 * 
 * Implement this interface to listen for drag and drop events.
 *  
 *  @see {@link DnDElement}
 *  @see {@link IDnDSource}
 *  @see {@link Target}
 */
public interface IDnDBehavior<E extends IDnDElement , S extends IDnDSource<?> , T extends IDnDTarget> {

	
	/**
	 * Triggered when one or more dnd elements are dropped on a target.
	 * This is where you should implement your DOM modification after the drop.
	 * (Maybe moving a widget from one place to another ...)
	 *  
	 * @param dndElements : Collection of the dropped DnDElements
	 * @param source  : the dnd Source object from which the DnDElements are originated
	 * @param target : the dnd Target object containing the drop location.
	 * @param targetNodeId : Id of the DOM element on which we drop, inside the dnd target (can be null, or anything).
	 * @param isCopy : wether the nodes should be copied or moved. This flag is usually set up by the user pressing "Ctrl" key while dragging.
	 * @return : true if the drop has been accepted, false otherwise
	 */
	public boolean onDrop(Collection<E> dndElements, S source, T target, String targetNodeId , boolean isCopy );
	
	public  void elementsAccepted(S source,T target, Collection<E> elements, boolean copied,IDnDController<?, T> controller);
	
	
	/**
	 * Triggered when an element is dragged over a target
	 * 
	 * @param target : the target over which a DnDElement is being dragged
	 */
	public void dragOver(T target);
	
	
	
	/**
	 * Triggered to check if the target would accept the drop. 
	 * 
	 * 
	 * @param source : the dnd Source object from which the DnDElements are originated
	 * @param target :  the dnd Target object containing the drop location.
	 * @param dndElements : Collection of the dragged DnDElements 
	 * @return true if the drop should be accepted, false otherwise. Usually, it 
	 * triggers a change in the "avatar" appearance
	 */
	public  boolean checkItemAcceptance(S source , T target , Collection<E> dndElements);
	
	
	/**
	 * Triggered when the user starts a drag.
	 * 
	 * @param elementBeingDragged : a collection containing the DnDElements being dragged
	 * @param source : the DnDSource from which the DnDElements are originated
	 * @param ctrlKey : wether the "ctrl" key is presses, providing a "should copy" information.
	 */
	public void onDndStart(Collection<E> elementBeingDragged , S source ,  boolean ctrlKey);
	
	/**
	 * Triggered when the DnD operation is canceled
	 * 
	 */
	public void onCancel();
	
	
}
