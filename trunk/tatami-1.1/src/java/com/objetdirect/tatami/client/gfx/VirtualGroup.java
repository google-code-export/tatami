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
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * TODO a TESTER 
 * @author Vianney
 *
 */
public class VirtualGroup extends GraphicObject {

	private List objects;
	
	
	
	/**
	 * Creates a <code>VirtualGroup</code>
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
	 * The <code>GraphicObject</code> have to added to the canvas first and 
	 * this <code>VirtualGroup</code> also. 
	 * @param object  the <code>GraphicObject</code> to add
	 */
	public void add(GraphicObject object){
		if ( getShape() !=null && object.getShape() !=null) {
			objects.add(object);
			add(getShape(),object.getShape());
		}
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
	
	
	
	
}
