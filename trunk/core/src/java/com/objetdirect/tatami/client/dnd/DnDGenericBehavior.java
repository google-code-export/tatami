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
 * This class implements a default generic behavior.
 * This behavior can be applied for all kinds of source and targets, and is 
 * defined for one kind of element. 
 * 
 * @author rdunklau
 *
 * @param <E>
 */
public abstract class DnDGenericBehavior<E extends IDnDElement> implements IDnDBehavior<E, IDnDSource<? extends E>, IDnDTarget>{

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.dnd.IDnDBehavior#checkSourceItemAcceptance(com.objetdirect.tatami.client.dnd.IDnDSource, com.objetdirect.tatami.client.dnd.IDnDTarget, java.util.Collection)
	 */
	public boolean checkItemAcceptance(IDnDSource<? extends E> source,
			IDnDTarget target,Collection<E> dndElements) {
		return true;
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.dnd.IDnDBehavior#dragOver(com.objetdirect.tatami.client.dnd.IDnDTarget)
	 */
	public void dragOver(IDnDTarget target) {
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.dnd.IDnDBehavior#elementsAccepted(com.objetdirect.tatami.client.dnd.IDnDSource, com.objetdirect.tatami.client.dnd.IDnDTarget, java.util.Collection, boolean, com.objetdirect.tatami.client.dnd.IDnDController)
	 */
	public void elementsAccepted(IDnDSource<? extends E> source,
			IDnDTarget target, Collection<E> elements,
			boolean copied,
			IDnDController<?, IDnDTarget> controller) {
		
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.dnd.IDnDBehavior#onCancel()
	 */
	public void onCancel() {
		
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.dnd.IDnDBehavior#onDndStart(java.util.Collection, com.objetdirect.tatami.client.dnd.IDnDSource, boolean)
	 */
	public void onDndStart(Collection<E> elementBeingDragged,
			IDnDSource<? extends E> source, boolean ctrlKey) {
		
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.dnd.IDnDBehavior#onDrop(java.util.Collection, com.objetdirect.tatami.client.dnd.IDnDSource, com.objetdirect.tatami.client.dnd.IDnDTarget, java.lang.String, boolean)
	 */
	public boolean onDrop(Collection<E> dndElements,
			IDnDSource<? extends E> source, IDnDTarget target,
			String targetNodeId, boolean isCopy) {
		return true;
	}

	
}
