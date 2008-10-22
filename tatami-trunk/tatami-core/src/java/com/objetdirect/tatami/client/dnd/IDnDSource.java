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
 * This interface represents a Drag and Drop Source.
 * A drag and drop Source is a target which elements are draggable.
 * 
 * 
 * @author rdunklau
 *
 * @param <T> : an IDnDElement type the source can manage
 */
public interface IDnDSource<T extends IDnDElement> extends IDnDTarget{

	/**
	 * @return all registered IDnDElement from this source
	 */
	public Collection<T> getRegisteredDndElement();
	
	/**
	 * 
	 * @param element : a IDnDElement to remove
	 */
	public void removeElement(T element);
	
	/**
	 * @param element : an IDnDElement to add
	 */
	public void addElement(T element);
	
	/**
	 * @param
	 * @return the DnDElement corresponding to the given id
	 */
	public T getDndElementById(String id);

}
