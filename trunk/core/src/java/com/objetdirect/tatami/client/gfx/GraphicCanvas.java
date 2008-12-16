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
 * Authors: Henri Darmet, Vianney Grassaud
 * Initial developer(s):
 * Contributor(s):
 */
package com.objetdirect.tatami.client.gfx;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.DojoController;

/**
 * TODO fix the bug when the original size of the canvas is set to zero. 
 * And if we add some GraphicObject and we change the size of the canvas
 * @author Vianney
 *
 */
public class GraphicCanvas extends Widget {

	/**
	 * The list of <code>GraphicObject</code>
	 */
	private List<GraphicObject> objects = new ArrayList<GraphicObject>(); 
	/**
	 * the DOJO surface it means the DOJO canvas 
	 */
	private JavaScriptObject surface;
	/**
	 * List of <code>GraphicObjectListener</code>
	 */
	private List<GraphicObjectListener> listeners = new ArrayList<GraphicObjectListener>();
	/**
	 * List of <code>GraphicObjectListener</code>
	 */
	private List<GraphicObjectListener> currentListeners = null;
	
	/**
	 * Map the key are the <code>JavaScriptObject</code> representing
	 * the source of an event, and the values are the <code>GraphicObject</code>
	 */
	private Map<JavaScriptObject, GraphicObject> graphicObjects = new HashMap<JavaScriptObject, GraphicObject>();
	
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
	    //to catch mouse events
	    this.sinkEvents(Event.ONCLICK);
	    this.sinkEvents(Event.ONDBLCLICK);
	    this.sinkEvents(Event.MOUSEEVENTS);
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
		surface = initGraphics(getElement(), this, this.getOffsetWidth(), this.getOffsetHeight());
		attachAllGraphicObjects();
		super.onAttach();
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
		final Iterator<GraphicObject> ite = objects.iterator();
		while (ite.hasNext()) {
			final GraphicObject graphicObject = (GraphicObject)ite.next();
			graphicObject.show(this);
			final Collection<?> shapes = graphicObject.getShapes();
			putEventSource(shapes,graphicObject);
     	}
	}
	
	/**
	 * Returns the DOJO GFX canvas
	 * @return the DOJO GFX canvas
	 */
	protected JavaScriptObject getDojoCanvas() {
		return this.surface;
	}
	
	/**
	 * Returns all the <code>GraphicObject</code> containing by this
	 * <code>GraphicCanvas</code>
	 * @return a collection of <code>GraphicObject</code>
	 */
	public Collection<GraphicObject> getGraphicObjects() {
		return this.objects;
	}
	
	/**
	 * Hides all the <code>GraphicObject</code> from the surface 
	 *
	 */
	private void detachAllGraphicObjects() {
		final Iterator<GraphicObject> ite = objects.iterator();
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
			graphicObject.translate(x, y);
			
			if (isAttached()) {
				attachGraphicObject(graphicObject);
			}
		}
	}
	
	/**
	 * Removes the <code>GraphicObject</code> from the canvas
	 * @param graphicObject the <code>GraphicObject</code> to remove
	 * @see #clear()
	 */
	public void remove(GraphicObject graphicObject) {
		if (objects.remove(graphicObject) && isAttached()) {
			detachGraphicObject(graphicObject);
		}
	}
	
	/**
	 * Removes all the <code>GraphicObject</code> in this 
	 * <code>GraphicCanvas</code>
	 * @see #remove(GraphicObject)
	 *
	 */
	public void clear() {
		Iterator<GraphicObject> ite = objects.iterator();
		while ( ite.hasNext()) {
			if ( isAttached()) { 
				detachGraphicObject((GraphicObject)ite.next());
			}
		}
		objects.clear();
	}
	
	
	
	
	/**
	 * Returns the nth <code>GraphicObject</code> in this <code>GraphicCanvas</code>
	 * @param n the index of the component to get.
	 * @return the nth component in this <code>GraphicCanvas</code>
	 */
	public GraphicObject getGraphicObject(int n) {
		return (GraphicObject)this.objects.get(n);
	}
	
	
	/**
	 * Shows a <code>GraphicObject</code> to the surface
	 * @param graphicObject the <code>GraphicObject</code> to show
	 */
	private void attachGraphicObject(GraphicObject graphicObject) {
		graphicObject.show(this);
		Collection<?> shapes = graphicObject.getShapes();
		putEventSource(graphicObject.getShape(),graphicObject);
		//graphicObjects.put(getEventSource(graphicObject.getShape()), graphicObject);
	}
	
	
	/**
	 * Associates the event source of a DOJO GFX shape to a <code>GraphicObject</code>
	 * @param shape a DOJO gfx shape of a <code>GraphicObject</code>
	 * @param graphicObject a <code>GraphicObject</code>
	 */
	protected void putEventSource(JavaScriptObject shape,GraphicObject graphicObject) {
		graphicObjects.put(getEventSource(shape), graphicObject);		
	}
	
	/**
	 * Associates a the events sources of a collection of shapes (DOJO GFX shape) width a <code>GraphicalObject</code>.
	 * @param shapes the collection of <code>JavaScriptObject</code> corresponding to a collection of DOJO GFX shape
	 * @param graphicObject the <code>GraphicObject</code>  to associates the event sources
	 */
	protected void putEventSource(Collection<?> shapes,GraphicObject graphicObject) {
		Iterator<?> ite = shapes.iterator();
		while (ite.hasNext()) {
			JavaScriptObject shape = (JavaScriptObject)ite.next();
			putEventSource(shape,graphicObject);	
		}
				
	}
	
	
	
	/**
	 * Hides a <code>GraphicObject</code> to the surface
	 * @param graphicObject the <code>GraphicObject</code> to hide and remove
	 */
	private void detachGraphicObject(GraphicObject graphicObject) {
		graphicObject.hide();
		Collection<?> shapes = graphicObject.getShapes();
		Iterator<?> ite = shapes.iterator();
		while (ite.hasNext()) {
			final JavaScriptObject shape = (JavaScriptObject)ite.next();
			if ( shape != null) {
				  graphicObjects.remove(getEventSource(shape));
				}
		}
		
		
	}

	/**
	 * Performs a click event
	 * @param evtSource the JavaScriptObject source of the event
	 * @param evt the event itself
	 */
	protected void doClick(JavaScriptObject evtSource, Event evt) {
		GraphicObject graphicObject = (GraphicObject)graphicObjects.get(evtSource);
		final Iterator<GraphicObjectListener> ite = getCurrentListeners().iterator();
		while (ite.hasNext()) {
			final GraphicObjectListener listener = (GraphicObjectListener)ite.next();
			listener.mouseClicked(graphicObject,evt);
		}
	}
	
	
	/**
	 * Performs a double click event
	 * @param evtSource the JavaScriptObject source of the event
	 * @param evt the event itself
	 */
	protected void doDoubleClick(JavaScriptObject evtSource, Event evt) {
		GraphicObject graphicObject = (GraphicObject)graphicObjects.get(evtSource);
		final Iterator<GraphicObjectListener> ite = getCurrentListeners().iterator();
		while (ite.hasNext()) {
			final GraphicObjectListener listener = (GraphicObjectListener)ite.next();
			listener.mouseDblClicked(graphicObject,evt);
			
		}
	}
	/**
	 * Performs a mouse down event
	 * @param evtSource the JavaScriptObject source of the event
	 * @param evt the event itself
	 */
	protected void doMouseDown(JavaScriptObject evtSource, Event evt) {
		GraphicObject graphicObject = (GraphicObject)graphicObjects.get(evtSource);
		final Iterator<GraphicObjectListener> ite = getCurrentListeners().iterator();
		while (ite.hasNext()) {
			final GraphicObjectListener listener = (GraphicObjectListener)ite.next();
			listener.mousePressed(graphicObject, evt);
		}
	}

	/**
	 * Performs a mouse move event
	 * @param evtSource the JavaScriptObject source of the event
	 * @param evt the event itself
	 */
	protected void doMouseMove(JavaScriptObject evtSource, Event evt) {
		final GraphicObject graphicObject = (GraphicObject)graphicObjects.get(evtSource);
		Iterator<GraphicObjectListener> ite = getCurrentListeners().iterator();
		while (ite.hasNext()) {
			final GraphicObjectListener listener = (GraphicObjectListener)ite.next();
			listener.mouseMoved(graphicObject,evt);
		}
	}

	/**
	 * Performs a mouse released event
	 * @param evtSource the JavaScriptObject source of the event
	 * @param evt the event itself
	 */
	protected void doMouseUp(JavaScriptObject evtSource,  Event evt) {
		final GraphicObject graphicObject = (GraphicObject)graphicObjects.get(evtSource);
		final Iterator<GraphicObjectListener> ite = getCurrentListeners().iterator();
		while (ite.hasNext()) {
			final GraphicObjectListener listener = (GraphicObjectListener)ite.next();
			listener.mouseReleased(graphicObject,evt);
		}
	}

	
	
	
	/**
	 * Returns the current listeners, 
	 * If there are no listeners, a new list (empty) is created
	 * @return a list of <code>GraphicObjectListener</code>, can be empty
	 */
	private List<GraphicObjectListener> getCurrentListeners() {
		if (currentListeners==null) {
			currentListeners = new ArrayList<GraphicObjectListener>(listeners);
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
	 *Manages the event from the browser  
	 *@param event
	 */
	public void onBrowserEvent(Event event) {
	    super.onBrowserEvent(event);

	    int type = DOM.eventGetType(event);

	    if (type == Event.ONCLICK) {
            this.doClick(DOM.eventGetTarget(event),event);
	    } else if ( type == Event.ONDBLCLICK) {
	    	this.doDoubleClick(DOM.eventGetTarget(event),event);
	    } else if ( type == Event.ONMOUSEDOWN) {
	       this.doMouseDown(DOM.eventGetTarget(event),event);	
	    }else if ( type == Event.ONMOUSEUP) {
           this.doMouseUp(DOM.eventGetTarget(event), event);	    	
	    }else if ( type == Event.ONMOUSEMOVE) {
           this.doMouseMove(DOM.eventGetTarget(event),event);	    	
	    }
	  }
	
	
	
	
	/**
	 * Returns the number of <code>GraphicObject</code>
	 * in this <code> GraphicCanvas</code>
	 * @return Returns the number of <code>GraphicObject</code>
	 * in this <code> GraphicCanvas</code>
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
    	surface.handleDragStart      = $wnd.dojo.connect(node,'ondragstart',$wnd.dojo, 'stopEvent');
	    surface.handleSelectStart    = $wnd.dojo.connect(node,'onselectstart',$wnd.dojo, 'stopEvent');
		
		return surface;
	}-*/;
	
	/**
	 * returns the source of the event of the graphicObject
	 * @param graphicObject the graphicObject
	 * @return the source of the event
	 */
	protected static native JavaScriptObject getEventSource(JavaScriptObject graphicObject) /*-{
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
		$wnd.dojo.disconnect(surface.handleDragStart);
		$wnd.dojo.disconnect(surface.handleSelectStart);
		
		surface.canvas=null;
	}-*/;
	
	

		
}//end of class
