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
import java.util.List;

/**
 * Tests the <code>VirtualGroup</code> object
 * @author Vianney
 *
 */
public class VirtualGroupTest extends TestGraphicObject {

	/**
	 * Creates an <code>VirtualGroup</code>
	 * @return an instance of <code>VirtualGroup</code>
	 */
	protected GraphicObject createInstance() {
	    return new VirtualGroup();
	}

	/**
	 * Tests the add and remove methos
	 *
	 */
	public void testAddRemove() {
		initGraphics();
		Circle c = new Circle(20);
		getGroup().add(c);
		assertTrue(getGroup().contains(c));
	    assertSame(component,c.getGroup());
	    getGroup().remove(c,true);
	    assertNull("The circle shound not have a group",c.getGroup());
	    assertEquals("The group should be empty",0,getGroup().size());
	}

	/**
	 * Tests the clear method of a <code>VirtualGroup</code>
	 *
	 */
	public void testClear() {
		initGraphics();
		Rect  r = new Rect(10,60);
		Circle circle = new Circle(50);
		getGroup().add(r);
		getGroup().add(circle);
		List list = new ArrayList();
		list.add(r);
		list.add(circle);
		assertTrue(getGroup().containsAll(list));
		assertEquals(2,getGroup().size());
		getGroup().clear();
		assertNull("The circle should not have a group",circle.getGroup());
		assertNull("The rectangle should not have a group",r.getGroup());
		assertEquals("The group should empty",0,getGroup().size());
	}
	
	/**
	 * Returns the <code>VirtualGroup</code>
	 * @return the <code>VirtualGroup</code>
	 */
    public VirtualGroup getGroup() {
    	return (VirtualGroup)component;
    }

}//end of class
