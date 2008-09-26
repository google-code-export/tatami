package com.objetdirect.tatamix.client.widget;

import java.util.Iterator;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.Label;

public class HTMLListTest extends GWTTestCase {


	private HTMLList list ;

	public String getModuleName() {
		return "com.objetdirect.tatamix.Tatamix";

	}

	public void testAdd() {
		list =new HTMLList();
		for (int i=0; i < 20; i++) {
		  list.add(new Label("test" + i));
		}
	    assertEquals(20,list.countItems());
	}

	public void testCountItems() {
		list =new HTMLList();
		for (int i=0; i < 20; i++) {
		  list.add(new Label("test" + i));
		}
	    assertEquals(20,list.countItems());
	   list.clear();
	    assertEquals(0,list.countItems());


	}

	public void testRemove1() {
		list =new HTMLList();
		for (int i=0; i < 20; i++) {
		  list.add(new Label("test" + i));
		}
		assertTrue(list.remove(5));

	}

	public void testRemove2() {
		list =new HTMLList();
		for (int i=0; i < 5; i++) {
		  list.add(new Label("test" + i));
		}
		Label l = new Label("spec");
		list.add(l);
		assertTrue(list.remove(l));

	}



	public void testGetWidget() {
		list =new HTMLList();
		for (int i=0; i < 5; i++) {
		  list.add(new Label("test" + i));
		}
		Label l = new Label("spec");
		list.add(l);
		assertEquals(list.getWidget(5),l);

	}

	public void testIterator() {
		list=new HTMLList();
		for (int i=0; i < 35; i++) {
			  list.add(new Label("test" + i));
		}
		Iterator ite = list.iterator();
		int cpt = 0;
		while ( ite.hasNext()) {
		    ite.next();
		    cpt++;
		    if ( cpt == 20) {
		    	ite.remove();
		    }
		}
		assertEquals(cpt-1,list.countItems());
	}
}
