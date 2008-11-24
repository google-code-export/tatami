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

import java.util.ArrayList;
import java.util.Collection;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

/**
 * This class is designed to make the Behavior implementor work easier.
 * It converts WidgetSource and WidgetTarget to Panels, and WidgetDnDElement to Element.
 * 
 * @author rdunklau
 *
 */
public abstract class WidgetDnDBehavior implements IDnDBehavior<WidgetDnDElement, WidgetSource, WidgetTarget>{

	
	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.dnd.IDnDBehavior#elementsAccepted(com.objetdirect.tatami.client.dnd.IDnDSource, com.objetdirect.tatami.client.dnd.IDnDTarget, java.util.Collection, boolean, com.objetdirect.tatami.client.dnd.IDnDController)
	 */
	public void elementsAccepted(WidgetSource source,
			WidgetTarget target, Collection<WidgetDnDElement> elements,
			boolean copied, IDnDController<?, WidgetTarget> controller) {
			elementsAccepted(source.getPanel(), target.getPanel(), getWidgetFromWidgetDnDElement(elements), copied);
	}
	
	
	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.dnd.IDnDBehavior#onDndStart(java.util.Collection, com.objetdirect.tatami.client.dnd.IDnDSource, boolean)
	 */
	public void onDndStart(Collection<WidgetDnDElement> elementBeingDragged,
			WidgetSource source, boolean ctrlKey) {
	}
	
	/**
	 * Implement this to provide basic DnD behavior.
	 * 
	 * This method is called if an only if the target accepted the drop.
	 * It may be implemented to provide post-drop behavior.
	 * 
	 * @param source : the panel from which the elements are dragged
	 * @param target : the panel on which the elements are dropped
	 * @param draggedWidgets : the widgets being dropped.
	 * @param copied : whether the widgets should be copied (ie, whether the user pressed the "CTRL" key
	 * while dragging the widgets)
	 */
	public abstract void elementsAccepted(Panel source,
			Panel target, Collection<Widget> draggedWidgets,
			boolean copied );
	
	/**
	 * Implement this to provide basic DnD behavior.
	 * 
	 * This method is called when an object is dropped on the target, AND both source and targets accepted the
	 * drop.
	 * 
	 *  
	 * @param draggedWidgets : the widgets being dropped
	 * @param source : the panel from which the elements are dragged
	 * @param target : the panel on which the elements are dropped
	 * @param targetNodeId : a String representing the DOM node on which the elements are dropped within the target. To
	 * get the most accurately associated widget, use {@link DnD#getWidgetUnderDrop(String, Panel)} with this id.
	 * @param copied : whether the widgets should be copied (ie, whether the user pressed the "CTRL" key
	 * while dragging the widgets)
	 * @return true if the drop is considered as "accepted" , false otherwise
	 */
	public abstract boolean onDrop(Collection<Widget> draggedWidgets,
			Panel source, Panel target, String targetNodeId, boolean isCopy);
	
	
	/**
	 * It checks whether the source could accept the drop operation. The drop operation can still be refused 
	 * when the onDrop event is fired.
	 * 
	 * @param target: the target
	 * @param source : the source
	 * @param draggedWidgets : 
	 * @return
	 */
	public abstract boolean checkItemAcceptance(Panel target, Panel source, Collection<Widget> draggedWidgets);

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.dnd.IDnDBehavior#dragOver(com.objetdirect.tatami.client.dnd.IDnDTarget)
	 */
	public void dragOver(IDnDTarget target) {
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.dnd.IDnDBehavior#onCancel()
	 */
	public void onCancel() {
	}

	/**
	 * Helper method to extract widgets from a collection of WidgetDnDElement
	 * 
	 * @param elems
	 * @return a collection of Widgets
	 */
	private Collection<Widget> getWidgetFromWidgetDnDElement(Collection<? extends WidgetDnDElement> elems){
		Collection<Widget> draggedWidgets = new ArrayList<Widget>();
		for (WidgetDnDElement element : elems) {
			draggedWidgets.add(element.getWidget());
		}
		return draggedWidgets;
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.dnd.IDnDBehavior#onDrop(java.util.Collection, com.objetdirect.tatami.client.dnd.IDnDSource, com.objetdirect.tatami.client.dnd.IDnDTarget, java.lang.String, boolean)
	 */
	public boolean onDrop(Collection<WidgetDnDElement> dndElements,
			WidgetSource source, WidgetTarget target, String targetNodeId,
			boolean isCopy) {
		return onDrop(getWidgetFromWidgetDnDElement(dndElements) , source.getPanel(), target.getPanel() , targetNodeId, isCopy);
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.dnd.IDnDBehavior#checkSourceItemAcceptance(com.objetdirect.tatami.client.dnd.IDnDSource, com.objetdirect.tatami.client.dnd.IDnDTarget, java.util.Collection)
	 */
	public boolean checkItemAcceptance(
			WidgetSource source, WidgetTarget target,
			Collection<WidgetDnDElement> dndElements) {
			return  checkItemAcceptance(source.getPanel(), target.getPanel(), getWidgetFromWidgetDnDElement(dndElements));
	}

	
	
	

	 
}

