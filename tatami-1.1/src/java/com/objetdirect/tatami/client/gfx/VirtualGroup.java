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
 * Authors:  Vianney Grassaud
 * Initial developer(s): Vianney Grassaud
 * Contributor(s):
 */
package com.objetdirect.tatami.client.gfx;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * This call permits to gather some <code>GraphicObject</code>
 * So when a transformation is executing, the tranformation will be applied 
 * on all of the <code>GraphicObject</code> contained in the <code>VirtualGroup</code>.
 * TODO Determinate bounds and the center positions 
 * @author Vianney
 *
 */
public class VirtualGroup extends GraphicObject {

	/** List of <code>GraphicObject</code> */
	private List objects;
	
	
	
	
	
	/**
	 * Creates an empty <code>VirtualGroup</code>
	 * 
	 */
	public VirtualGroup() {
		super();
		objects = new ArrayList();
	}
	
	
	/**
	 * Creates a DOJO GFX VirtualGroup
	 * @param surface the canvas
	 */
	protected JavaScriptObject createGfx(JavaScriptObject surface) {
		return createGroup(surface);
	
	}

	/**
	 * Tests if this <code>VirtualGroup</code> contains the given 
	 * <code>GraphicalObject</code>
	 * @param object the <code>GraphicObject</code> to test
	 * @return true if <code>VirtualGroup</code> contains it false otherwise
	 */
	public boolean contains(GraphicObject object) {
		return this.objects.contains(object);
	}
	
	/**
	 * Tests if this <code>VirtualGroup</code> contains all the given 
	 * <code>GraphicalObject</code> in the <code>Collection</code>
	 * @param collection a collection of  <code>GraphicObject</code> 
	 * @return true if <code>VirtualGroup</code> contains them false otherwise
	 */
	public boolean containsAll(Collection collection) {
		return this.objects.containsAll(collection);
	}
	
	
	
	/**
	 * Adds a DOJO GFX shape to the DOJO GFX VirtualGroup.
	 * The shape will be removed from its parent
	 * @param group the DOJO GFX VirtualGroup
	 * @param shape the DOJO GFX shape to add
	 */
	private native void add(JavaScriptObject group,JavaScriptObject shape)/*-{
	   group.add(shape);
	}-*/;
	
	/**
	 * Creates a Virtual Group DOJO GFX
	 * @param surface the canvas permitting the creation
	 * @return the Virtual Group DOJO GFX
	 */
	private native JavaScriptObject createGroup(JavaScriptObject surface) /*-{
	    return surface.createGroup();
	}-*/;
	
	
	/**
	 * Adds a <code>GraphicObject</code> to the list.
	 * The <code>GraphicObject</code> which are added, will be drawn only when this 
	 * <code>VirutalGroup</code> is attached to the canvas.
     * @param object  the <code>GraphicObject</code> to add
	 */
	public void add(GraphicObject object){
		objects.add(object);
		if ( getShape() !=null ) {
		    if ( getParent() != null) {
		    	object.show(getParent());
		    	Collection shapes = object.getShapes();
		    	getParent().putEventSource(shapes,object);
		    	add(getShape(),object.getShape());
		    }
		}
	}
	
	/**
	 * Sets the color of the fill for this <code>VirtualGroup</code>.
	 * All of the <code>GraphicObject</code> will have the same color.
	 * @param color the color to apply for the fill 
	 * @return this <code>VirtualGroup</code>
	 */
	public GraphicObject setFillColor(Color color ) {
		super.setFillColor(color);
	    Iterator ite = objects.iterator();
	    while ( ite.hasNext()) {
	    	((GraphicObject)ite.next()).setFillColor(color);
	    }
		return this;
	}
	
	/**
	 * Sets the stroke color and width for this <code>VirtualGroup</code>.
	 * All <code>GraphicObject</code> contained in this <code>VirtualGroup</code>
	 * will have the same width and color of the stroke. 
	 * @param color the color for the stroke
	 * @param width the width for the stroke
	 * @return this <code>VirtualGroup</code>
	 */
	public GraphicObject setStroke(Color color,int width ) {
		super.setStroke(color,width);
	    Iterator ite = objects.iterator();
	    while ( ite.hasNext()) {
	    	((GraphicObject)ite.next()).setStroke(color,width);
	    }
		return this;
	}
	
	/**
	 * Applies a pattern to each <code>GraphicObject</code> 
	 * of this <code>VirtualGroup</code>
	 * @param pattern the pattern to apply.
	 * @return this <code>VirtualGroup</code>
	 */
	public GraphicObject applyPattern(Pattern pattern) {
		super.applyPattern(pattern);
	    Iterator ite = objects.iterator();
	    while ( ite.hasNext()) {
	    	((GraphicObject)ite.next()).applyPattern(pattern);
	    }
		return this;
	}
	
	
	
	/**
	 * Removes a <code>GraphicObject</code> from the list
	 * @param object the <code>GraphicObject</code> to remove
	 * @param silently if true, do not redraw a picture yet
	 */
	public void remove(GraphicObject object, boolean silently) {
		this.objects.remove(object);
		if ( object.getShape() != null ) {
			remove(getShape(),object.getShape(),silently);
		}
	}

	/**
	 * Removes a <code>GraphicObject</code> from the list
	 * @param group the DOJO GFX VirtualGroup
	 * @param object the <code>GraphicObject</code> to remove
	 * @param silently if true, do not redraw a picture yet
	 */
	private native void remove(JavaScriptObject group,JavaScriptObject shape,boolean silently)/*-{
	   group.remove(shape,silently);
	}-*/;
	
	/**
	 * Removes all the <code>GraphicObject</code> containing by this 
	 * <code>VirtualGroup</code>
	 */
	public void clear() {
		this.objects.clear();
		if ( getShape() != null) {
			clear(getShape());
		}
	}
	/**
	 * Removes all the DOJO shape GFX containing by this 
	 * DOJO GFX VirtualGroup. 
	 * @param group the DOJO GFX Virtual Group
	 */
	private native void clear(JavaScriptObject group)/*-{
	  group.clear();
	}-*/;
	
	/**
	 * Returns the number of <code>GraphicObject</code> that  this <code>VirtualGroup</code>
	 * contains.
	 * @return the number of <code>GraphicObject</code> that  this <code>VirtualGroup</code>
	 * contains.
	 */
	public int size() {
		return objects.size();
	}
	
	/**
	 * Shows this <code>GraphicalObject</code> in the canvas.
	 * @param surface the canvas
	 */
	protected void show(GraphicCanvas surface) {
		super.show(surface);
		buildObjects();
		
	}
	
	/**
	 * Returns a collection of the shapes that this <code>VirtualGroup</code> contains.
	 * @return a collection of <code>JavaScriptObject</code> corresponding to the DOJO
	 *         GFX shape. 
	 */
	protected Collection getShapes() {
		List list = new ArrayList();
		list.add(getShape());
		Iterator ite = objects.iterator();
		while ( ite.hasNext()) {
			GraphicObject object = (GraphicObject)ite.next();
			list.addAll(object.getShapes());
		}
		return list;
	}
	
	/**
	 * Builds the <code>GraphicObject</code> of this <code>VirtualGroup</code>.
	 * assumes that the surface is not null
	 *
	 */
	private void buildObjects() {
		Iterator ite = objects.iterator();
		while ( ite.hasNext()) {
			GraphicObject object = (GraphicObject)ite.next();
			object.show(getParent());
			Collection shapes = object.getShapes();
			//to control the event on the different shape
			getParent().putEventSource(shapes,object);
			// add the shape to the virtual group
			add(getShape(),object.getShape());
		}
		
	}
}
