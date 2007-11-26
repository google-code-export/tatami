/*
 * Tatami: 
 * Copyright (C) 2007 Objet Direct
 * Copyright (C) 2007 France Telecom
 * Contact: tatami@objectweb.org
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
 * Authors: Henri Darmet, Vianney Grassaud
 * Initial developer(s):
 * Contributor(s):
 */
package com.objetdirect.tatami.client.gfx;

import com.google.gwt.user.client.Event;

public interface GraphicObjectListener {
	/**
	 * Invoked when the mouse button has been clicked (pressed and released) on a graphicObject.
	 * @param graphicObject the <code>GraphicObject</code> which launches the event
     * @param evt the event mouse
	 */
	void mouseClicked(GraphicObject graphicObject, Event event);
	/**
	 * Invoked when a mouse button has been pressed on a graphicObject.
	 * @param graphicObject the <code>GraphicObject</code> which launches the event
	 * @param evt the event mouse
	 */
	void mousePressed(GraphicObject graphicObject, Event event);
	/**
	 * Invoked when the mouse cursor has been moved
	 * @param graphicObject the <code>GraphicObject</code> which launches the event
	 *@param evt the event mouse
	 */
	void mouseMoved(GraphicObject graphicObject, Event event);
	
	/**
	 * Invoked when a mouse button has been released on a graphicObject.
	 * @param graphicObject the <code>GraphicObject</code> which launches the event
	 * @param evt the mouse event.
	 */
	void mouseReleased(GraphicObject graphicObject, Event event);
	
	/**
	 * Invoked when a mouse button has been double clicked on a graphicObject.
	 * @param graphicObject the <code>GraphicObject</code> which launches the event
	 * @param evt the mouse event.
	 */
	public void mouseDblClicked(GraphicObject graphicObject,Event event);
}
