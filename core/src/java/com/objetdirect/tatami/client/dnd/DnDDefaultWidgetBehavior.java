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

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

/**
 * This class implements a default WidgetBehavior.
 * 
 * It accepts any kind of dnd, and move the dropped widgets from the source to the target
 * 
 * @author rdunklau
 *
 */
public abstract class DnDDefaultWidgetBehavior extends WidgetDnDBehavior{

	@Override
	public boolean checkItemAcceptance(Panel target, Panel source,
			Collection<Widget> draggedWidgets) {
		return true;
	}

	@Override
	public void elementsAccepted(Panel source, Panel target,
			Collection<Widget> draggedWidgets, boolean copied) {
	}

	@Override
	public boolean onDrop(Collection<Widget> draggedWidgets, Panel source,
			Panel target, String targetNodeId, boolean isCopy) {
		for (Widget widget : draggedWidgets) {
			DnD.move(widget, source, target);
		}
		return true;
	}


}
