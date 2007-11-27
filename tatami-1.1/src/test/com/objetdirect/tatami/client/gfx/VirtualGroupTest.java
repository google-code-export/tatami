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
