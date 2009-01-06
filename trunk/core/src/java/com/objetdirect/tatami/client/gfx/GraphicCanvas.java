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
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.DojoController;

/**
 *  
 * Class that drawing graphical objects. 
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
	 * Creates a canvas with a default size of 500 X 500 pixels.
	 * @param el DOM element according to the GWT widget
	 */
	public GraphicCanvas(Element el) {
		super();
		setElement(el);
		setSize("500px","500px");
	    DojoController controller = DojoController.getInstance();
	    controller.require("dojox.gfx");
	    //to catch mouse events
	    this.sinkEvents(Event.ONCLICK);
	    this.sinkEvents(Event.ONDBLCLICK);
	    this.sinkEvents(Event.MOUSEEVENTS);

	    
	 }

	/**
	 * Creates a canvas. The DOM element which will wrap 
	 * the Dojo gfx surface will be a DIV element.
	 */
	public GraphicCanvas() {
		this(Document.get().createDivElement());
	}
	
    /**
     * Creates the Dojo surface when the widget is attached.
     */		
	public void onAttach() {
		super.onAttach();
		int width = this.getOffsetWidth();
		int height = this.getOffsetHeight();
		surface = initGraphics(getElement(), this, width,height );
		attachAllGraphicObjects();
	}
	
	/**
	 * Returns the width in pixels of this canvas
	 * @return the width in pixels of this canvas
	 */
	public int getWidth() {
		int result = getOffsetWidth();
		if ( surface != null) {
			result = getWidth(surface);
		}
		return result;
	}
	
	/**
	 * Returns the height in pixels of the canvas
	 * @return the height in pixels of the canvas
	 */
	public int getHeight() {
		int result = getOffsetHeight();
		if ( surface != null) {
			result = getHeight(surface);
		}
		return result;
	}

	
	private native int getWidth(JavaScriptObject surface)/*-{
	  var dim = surface.getDimensions();
	  return dim.width;
	}-*/;
	
	private native int getHeight(JavaScriptObject surface)/*-{
	  var dim = surface.getDimensions();
	  return dim.height;
	}-*/;
	
	/**
	 * Sets the dimensions of the canvas. 
	 * @param width a width in pixels 
	 * @param height a height in pixels
	 */
	public void setDimensions(int width,int height) {
		setPixelSize(width,height);
	}
	
	
	/**
	 * Sets the size of the Element that wrapping the Dojo surface. 
	 * Sets also the Dojo surface dimensions. 
	 */
	public void setPixelSize(int width,int height) {
		super.setPixelSize(width, height);
		 if ( surface != null) {
				setDimensions(surface,String.valueOf(width),String.valueOf(height));
			}
	}
	
	/**
	 * Nota :  due to a bug under IE, you should use the 
	 * <code>setDimensions(int,int)</code> method instead. 
	 * Only pixel values without the "px" suffix are 
	 * permitted for IE e.g. : <code>canvas.setSize("500","500")</code>  
	 */
	public void setSize(String width,String height) {
		super.setSize(width, height);
		if ( surface != null) {
			setDimensions(surface,width,height);
		}
		
	}
	
		
	/**
	 * Sets the dimensions of a Dojo surface.
	 * @param surface the Dojo surface.
	 * @param width the default width of the surface in pixels.
	 * @param height the default height of the surface in pixels.
	 */
	private native void setDimensions(JavaScriptObject surface,String width,String height) /*-{
           surface.setDimensions(width,height);		
	}-*/;
	
	
	
	
	/**
	 * Releases the surface and the graphic components
	 * when the widget is detached from the browser.
	 */
	public void onDetach() {
		detachAllGraphicObjects();
		releaseGraphics(this.getElement(), surface);
		super.onDetach();
	}

	/**
	 * Shows all the <code>GraphicObject</code> in the surface.
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
	 * Returns the DOJO GFX canvas.
	 * @return the DOJO GFX canvas.
	 */
	protected JavaScriptObject getDojoCanvas() {
		return this.surface;
	}
	
	/**
	 * Returns all the <code>GraphicObject</code> containing by this
	 * <code>GraphicCanvas</code>.
	 * @return a collection of <code>GraphicObject</code>.
	 */
	public Collection<GraphicObject> getGraphicObjects() {
		return this.objects;
	}
	
	/**
	 * Hides all the <code>GraphicObject</code> from the surface.
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
	 * position.
	 * @param graphicObject the <code>GraphicObject</code> to add.
	 * @param x the x coordinate for the object in the canvas. 
	 * @param y the y coordinate for the object in the canvas. 
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
	 * Removes the <code>GraphicObject</code> from the canvas.
	 * @param graphicObject the <code>GraphicObject</code> to remove.
	 * @see #clear()
	 */
	public void remove(GraphicObject graphicObject) {
		if (objects.remove(graphicObject) && isAttached()) {
			detachGraphicObject(graphicObject);
		}
	}
	
	/**
	 * Removes all the <code>GraphicObject</code> in this 
	 * <code>GraphicCanvas</code>.
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
	 * Returns the nth <code>GraphicObject</code> in this <code>GraphicCanvas</code>.
	 * @param n the index of the component to get.
	 * @return the nth component in this <code>GraphicCanvas</code>.
	 */
	public GraphicObject getGraphicObject(int n) {
		return (GraphicObject)this.objects.get(n);
	}
	
	
	/**
	 * Shows a <code>GraphicObject</code> to the surface.
	 * @param graphicObject the <code>GraphicObject</code> to show.
	 */
	private void attachGraphicObject(GraphicObject graphicObject) {
		graphicObject.show(this);
		//Collection<?> shapes = graphicObject.getShapes();
		putEventSource(graphicObject.getShape(),graphicObject);
		//graphicObjects.put(getEventSource(graphicObject.getShape()), graphicObject);
	}
	
	
	/**
	 * Associates the event source of a DOJO GFX shape to a <code>GraphicObject</code>.
	 * @param shape a DOJO gfx shape of a <code>GraphicObject</code>.
	 * @param graphicObject a <code>GraphicObject</code>.
	 */
	protected void putEventSource(JavaScriptObject shape,GraphicObject graphicObject) {
		graphicObjects.put(getEventSource(shape), graphicObject);		
	}
	
	/**
	 * Associates a the events sources of a collection of shapes 
	 * (DOJO GFX shape) width a <code>GraphicalObject</code>.
	 * @param shapes the collection of <code>JavaScriptObject</code> 
	 *        corresponding to a collection of DOJO GFX shape.
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
	 * Hides a <code>GraphicObject</code> to the surface.
	 * @param graphicObject the <code>GraphicObject</code> to hide and remove.
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
	 * Performs a click event.
	 * @param evtSource the JavaScriptObject source of the event.
	 * @param evt the event itself.
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
	 * Performs a double click event.
	 * @param evtSource the JavaScriptObject source of the event.
	 * @param evt the event itself.
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
	 * Performs a mouse down event.
	 * @param evtSource the JavaScriptObject source of the event.
	 * @param evt the event itself.
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
	 * Performs a mouse move event.
	 * @param evtSource the JavaScriptObject source of the event.
	 * @param evt the event itself.
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
	 * Performs a mouse released event.
	 * @param evtSource the JavaScriptObject source of the event.
	 * @param evt the event itself.
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
	 * Returns the current listeners.
	 * If there are no listeners, a new list (empty) is created.
	 * @return a list of <code>GraphicObjectListener</code>, can be empty.
	 */
	private List<GraphicObjectListener> getCurrentListeners() {
		if (currentListeners==null) {
			currentListeners = new ArrayList<GraphicObjectListener>(listeners);
		}
		return currentListeners;
	}
	
	/**
	 * Adds a <code>GraphicObjectListener</code> to the listeners. 
	 * @param listener the <code>GraphicObjectListener</code> to add.
	 */
	public void addGraphicObjectListener(GraphicObjectListener listener) {
		listeners.add(listener);
		currentListeners = null;
	}
	
	/**
	 * Removes the <code>GraphicObjectListener</code>.
	 * @param listener the <code>GraphicObjectListener</code> to remove.
	 */
	public void removeGraphicObjectListener(GraphicObjectListener listener) {
		listeners.remove(listener);
		currentListeners = null;
	}
	
	
	/**
	 *Manages the event from the browser.
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
	 * in this <code> GraphicCanvas</code>.
	 * @return Returns the number of <code>GraphicObject</code>
	 * 		   in this <code> GraphicCanvas</code>.
	 */
	public int countGraphicObject() {
		return this.objects.size();
	}
	/**
	 * Creates the DOJO surface for the canvas.
	 * @param node the DOM Node. 
	 * @param canvas the GWT canvas.
	 * @param width the width of the surface.
	 * @param height the height of the surface.
	 * @return the DOJO surface.
	 */
	private static native JavaScriptObject initGraphics(Element node, GraphicCanvas canvas, int width, int height) /*-{
		var surface = $wnd.dojox.gfx.createSurface(node, width, height);
		surface.canvas = canvas;
    	surface.handleDragStart      = $wnd.dojo.connect(node,'ondragstart',$wnd.dojo, 'stopEvent');
	    surface.handleSelectStart    = $wnd.dojo.connect(node,'onselectstart',$wnd.dojo, 'stopEvent');
		
		return surface;
	}-*/;
	
	/**
	 * Returns the source of the event of the graphicObject.
	 * @param graphicObject the graphicObject.
	 * @return the source of the event.
	 */
	protected static native JavaScriptObject getEventSource(JavaScriptObject graphicObject) /*-{
		return graphicObject.getEventSource();
	}-*/;
	
	/**
	 * Removes the DOM element from the given element and release the canvas.
	 * @param element
	 * @param surface
	 * @see #releaseCanvas(JavaScriptObject)
	 */
	private static void releaseGraphics(Element element, JavaScriptObject surface) {
		NodeList<Node> childs = element.getChildNodes();
		
		//final int c = DOM.getChildCount(element);
		
		for (int i=0; i< childs.getLength(); i++) {
			Node nodeToRemove = childs.getItem(i); 
			element.removeChild(nodeToRemove);
			//DOM.removeChild(element, DOM.getChild(element, 0));
		}
		releaseCanvas(surface);
	}
	
	
	/**
	 * Releases the GWT canvas in the DOJO surface. 
	 * @param surface
	 */
	private static native void releaseCanvas(JavaScriptObject surface) /*-{
		$wnd.dojo.disconnect(surface.handleDragStart);
		$wnd.dojo.disconnect(surface.handleSelectStart);
		
		surface.canvas=null;
	}-*/;
	
	

		
}//end of class
