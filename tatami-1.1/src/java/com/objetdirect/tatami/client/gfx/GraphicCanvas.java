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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.DojoController;

public class GraphicCanvas extends Widget {

	/**
	 * The list of <code>GraphicObject</code>
	 */
	private List objects = new ArrayList(); 
	/**
	 * the DOJO surface it means the DOJO canvas 
	 */
	private JavaScriptObject surface;
	/**
	 * List of <code>GraphicObjectListener</code>
	 */
	private List listeners = new ArrayList();
	/**
	 * List of <code>GraphicObjectListener</code>
	 */
	private List currentListeners = null;
	
	/**
	 * Map the key are the <code>JavaScriptObject</code> representing
	 * the source of an event, and the values are the <code>GraphicObject</code>
	 */
	private Map graphicObjects = new HashMap();
	
	/**
	 * Creates a canvas
	 * @param el DOM element according to the GWT widget
	 *
	 */
	public GraphicCanvas(Element el) {
		super();
		setElement(el);
	    DojoController controller = DojoController.getInstance();
	    controller.require("dojox.gfx");
	 }

	/**
	 * Creates a canvas, the DOM element of thf GWT widget
	 * will be a DIV
	 *
	 */
	public GraphicCanvas() {
		this(DOM.createDiv());
	}
	
    /**
     * Creates the surface when the widget is attached.
     */		
	public void onAttach() {
		super.onAttach();
		surface = initGraphics(getElement(), this, this.getOffsetWidth(), this.getOffsetHeight());
		attachAllGraphicObjects();
	}
	
		
	/**
	 * Releases the surface and the graphic components
	 * when the widget is detached from the browser
	 */
	public void onDetach() {
		detachAllGraphicObjects();
		releaseGraphics(this.getElement(), surface);
		super.onDetach();
	}

	/**
	 * Shows all the <code>GraphicObject</code> in the surface
	 *
	 */
	private void attachAllGraphicObjects() {
		final Iterator ite = objects.iterator();
		while (ite.hasNext()) {
			final GraphicObject graphicObject = (GraphicObject)ite.next();
			graphicObject.show(surface);
			graphicObjects.put(getEventSource(graphicObject.getShape()), graphicObject);
		}
	}
	
	/**
	 * Hides all the <code>GraphicObject</code> from the surface 
	 *
	 */
	private void detachAllGraphicObjects() {
	
		final Iterator ite = objects.iterator();
		while (ite.hasNext()) {
			final GraphicObject graphicObject = (GraphicObject)ite.next();
			graphicObject.hide();
		}
	}
	
	/**
	 * Adds a <code>GraphicObject</code> to the canvas at the specified 
	 * position
	 * @param graphicObject
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public void add(GraphicObject graphicObject, int x, int y) {
		if (objects.add(graphicObject)) {
			graphicObject.setLocation(x, y);
			
			if (isAttached()) {
				attachGraphicObject(graphicObject);
			}
		}
	}
	
	/**
	 * Removes the <code>GraphicObject</code> from the canvas
	 * @param graphicObject the <code>GraphicObject</code> to remove
	 */
	public void remove(GraphicObject graphicObject) {
		if (objects.remove(graphicObject) && isAttached()) {
			detachGraphicObject(graphicObject);
		}
	}
	
	/**
	 * 
	 *
	 */
	public void removeAllGraphics() {
		final Iterator ite = objects.iterator();
		while (ite.hasNext()) {
			remove((GraphicObject)ite.next());
		}
	}
	
	/**
	 * Shows a <code>GraphicObject</code> to the surface
	 * @param graphicObject the <code>GraphicObject</code> to show
	 */
	private void attachGraphicObject(GraphicObject graphicObject) {
		graphicObject.show(surface);
		graphicObjects.put(getEventSource(graphicObject.getShape()), graphicObject);
	}
	
	/**
	 * Hides a <code>GraphicObject</code> to the surface
	 * @param graphicObject the <code>GraphicObject</code> to hide and remove
	 */
	private void detachGraphicObject(GraphicObject graphicObject) {
		graphicObject.hide();
		final JavaScriptObject shape = graphicObject.getShape();
		if ( shape != null) {
		  graphicObjects.remove(getEventSource(shape));
		}
	}

	/**
	 * Performs a click event
	 * @param evtSource the JavaScriptObject source of the event
	 * @param x the x coordinate of the mouse
	 * @param y the y coordinate of the mouse
	 */
	protected void doClick(JavaScriptObject evtSource, int x, int y) {
		GraphicObject graphicObject = (GraphicObject)graphicObjects.get(evtSource);
		final Iterator ite = getCurrentListeners().iterator();
		while (ite.hasNext()) {
			final GraphicObjectListener listener = (GraphicObjectListener)ite.next();
			listener.mouseClicked(graphicObject, x, y);
		}
	}
	
	/**
	 * Performs a mouse down event
	 * @param evtSource the JavaScriptObject source of the event
	 * @param x the x coordinate of the mouse
	 * @param y the y coordinate of the mouse
	 */
	protected void doMouseDown(JavaScriptObject evtSource, int x, int y) {
		GraphicObject graphicObject = (GraphicObject)graphicObjects.get(evtSource);
		final Iterator ite = getCurrentListeners().iterator();
		while (ite.hasNext()) {
			final GraphicObjectListener listener = (GraphicObjectListener)ite.next();
			listener.mousePressed(graphicObject, x, y);
		}
	}

	/**
	 * Performs a mouse move event
	 * @param evtSource the JavaScriptObject source of the event
	 * @param x the x coordinate of the mouse
	 * @param y the y coordinate of the mouse
	 */
	protected void doMouseMove(JavaScriptObject evtSource, int x, int y) {
		final GraphicObject graphicObject = (GraphicObject)graphicObjects.get(evtSource);
		Iterator ite = getCurrentListeners().iterator();
		while (ite.hasNext()) {
			final GraphicObjectListener listener = (GraphicObjectListener)ite.next();
			listener.mouseMoved(graphicObject, x, y);
		}
	}

	/**
	 * Performs a mouse released event
	 * @param evtSource the JavaScriptObject source of the event
	 * @param x the x coordinate of the mouse
	 * @param y the y coordinate of the mouse
	 */
	protected void doMouseUp(JavaScriptObject evtSource, int x, int y) {
		final GraphicObject graphicObject = (GraphicObject)graphicObjects.get(evtSource);
		final Iterator ite = getCurrentListeners().iterator();
		while (ite.hasNext()) {
			final GraphicObjectListener listener = (GraphicObjectListener)ite.next();
			listener.mouseReleased(graphicObject, x, y);
		}
	}

	/**
	 * Returns the current listeners, 
	 * If there are no listeners, a new list (empty) is created
	 * @return a list of <code>GraphicObjectListener</code>, can be empty
	 */
	private List getCurrentListeners() {
		if (currentListeners==null) {
			currentListeners = new ArrayList(listeners);
		}
		return currentListeners;
	}
	
	/**
	 * Adds a <code>GraphicObjectListener</code> to the listeners 
	 * @param listener the <code>GraphicObjectListener</code> to add
	 */
	public void addGraphicObjectListener(GraphicObjectListener listener) {
		listeners.add(listener);
		currentListeners = null;
	}
	
	/**
	 * Removes the <code>GraphicObjectListener</code>.
	 * @param listener the <code>GraphicObjectListener</code> to remove
	 */
	public void removeGraphicObjectListener(GraphicObjectListener listener) {
		listeners.remove(listener);
		currentListeners = null;
	}
	
	/**
	 * Returns the number of <code>GraphicObject</code>
	 * in this <code> GraphicCanvas</code>
	 * @return
	 */
	public int countGraphicObject() {
		return this.objects.size();
	}
	/**
	 * Creates the DOJO surface for the canvas
	 * @param node the DOM Node 
	 * @param canvas the GWT canvas
	 * @param width the width of the surface
	 * @param height the height of the surface
	 * @return the DOJO surface
	 */
	private static native JavaScriptObject initGraphics(Element node, GraphicCanvas canvas, int width, int height) /*-{
		var surface = $wnd.dojox.gfx.createSurface(node, width, height);
		surface.canvas = canvas;
		surface.handleMouseClick = function(event) {
			surface.canvas.@com.objetdirect.tatami.client.gfx.GraphicCanvas::doClick(Lcom/google/gwt/core/client/JavaScriptObject;II)(event.target, event.clientX, event.clientY);
		}
		surface.handleMouseDown = function(event) {
			surface.canvas.@com.objetdirect.tatami.client.gfx.GraphicCanvas::doMouseDown(Lcom/google/gwt/core/client/JavaScriptObject;II)(event.target, event.clientX, event.clientY);
		}
		surface.handleMouseMove = function(event) {
			surface.canvas.@com.objetdirect.tatami.client.gfx.GraphicCanvas::doMouseMove(Lcom/google/gwt/core/client/JavaScriptObject;II)(event.target, event.clientX, event.clientY);
		}
		surface.handleMouseUp = function(event) {
			surface.canvas.@com.objetdirect.tatami.client.gfx.GraphicCanvas::doMouseUp(Lcom/google/gwt/core/client/JavaScriptObject;II)(event.target, event.clientX, event.clientY);
		}
		surface.handleEventClick     = $wnd.dojo.connect(node, 'onclick',     surface.handleMouseClick);
		surface.handleEventMouseDown = $wnd.dojo.connect(node, 'onmousedown', surface.handleMouseDown);
		surface.handleEventMouseMove = $wnd.dojo.connect(node, 'onmousemove', surface.handleMouseMove);
		surface.handleEventMouseUp   = $wnd.dojo.connect(node, 'onmouseup',   surface.handleMouseUp);
		surface.handleDragStart      = $wnd.dojo.connect(node,'ondragstart',$wnd.dojo, 'stopEvent');
	    surface.handleSelectStart    = $wnd.dojo.connect(node,'onselectstart',$wnd.dojo, 'stopEvent');
		
		return surface;
	}-*/;
	
	/**
	 * returns the source of the event of the graphicObject
	 * @param graphicObject the graphicObject
	 * @return the source of the event
	 */
	private static native JavaScriptObject getEventSource(JavaScriptObject graphicObject) /*-{
		return graphicObject.getEventSource();
	}-*/;
	
	/**
	 * Removes the DOM element from the given elemet and release the canvas
	 * @param e
	 * @param surface
	 * @see #releaseCanvas(JavaScriptObject)
	 */
	private static void releaseGraphics(Element element, JavaScriptObject surface) {
		final int c = DOM.getChildCount(element);
		for (int i=0; i<c; i++) {
			DOM.removeChild(element, DOM.getChild(element, 0));
		}
		releaseCanvas(surface);
	}
	
	
	/**
	 * Releases the GWT canvas in the DOJO surface 
	 * @param s
	 */
	private static native void releaseCanvas(JavaScriptObject surface) /*-{
		$wnd.dojo.disconnect(surface.handleEventClick);
		$wnd.dojo.disconnect(surface.handleEventMouseUp);
		$wnd.dojo.disconnect(surface.handleEventMouseDown);
		$wnd.dojo.disconnect(surface.handleEventMouseMove);
		$wnd.dojo.disconnect(surface.handleDragStart);
		$wnd.dojo.disconnect(surface.handleSelectStart);
		surface.canvas=null;
	}-*/;
	
	

		
}//end of class
