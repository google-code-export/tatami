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
 * Authors:
 * Initial developer(s):
 * Contributor(s):
 */
package com.objetdirect.tatami.client.test;


import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootPanel;

public class TestUtil {

	public static final String EVENT_CLICK = "clik";

	public static final String EVENT_MOUSEMOVE = "mousemove";

	public static final int MOUSE_BUTTON_1 = 1;

	public static final int MOUSE_BUTTON_2 = 2;

	public static final int MOUSE_BUTTON_3 = 3;

	private TestUtil() {
		
	}
	
	
	/**
	 * Fires a mouse event on an DOM element.
	 * @param e the DOM element
	 * @param eventType The type for the mouse event without the prefix "on" :
	 *                   EVENT_CLICK,EVENT_MOUSEMOVE
	 * @param x  the x coordinate of the mouse
	 * @param y  the y coordinate of the mouse
	 * @param button the button of the mouse to use, 1 for the left button
	 * @param click the number of click to do
	 */
	static native void fireMouseEvent(Element e, String eventType, int x,int y, int button, int click) /*-{
	     $wnd.fireMouseEvent(e,eventType,x,y,button,click);
	 }-*/;

	/**
	 * Fires a mouse event on a DOM element and runs a task after that the event
	 * is executed.
	 * 
	 * @param event The type for the mouse event without the prefix "on" :
	 *              EVENT_CLICK, EVENT_MOUSEMOVE
	 * @param x  the x coordinate of the mouse
	 * @param y  the y coordinate of the mouse
	 * @param button  the button of the mouse to use, MOUSE_BUTTON_1,
	 *                MOUSE_BUTTON_2,MOUSE_BUTTON_3
	 * @param nb_click the number of click to do
	 * @param task the task to run after the event. can be null
	 */
	static public void fireEvent(String event, int x, int y, int button,int nb_click, final Task task) {
		Element el = getElement(x, y);
		fireMouseEvent(el, event, x, y, button, nb_click);
		// the task will be started with a delay of 500 ms
		if (task != null) {
			Timer timer = new Timer() {
				public void run() {
					task.run();

				}
			};
			timer.schedule(500);
		}
	}

	/**
	 * Returns the DOM element at the specific position
	 * @param x the x coordinate for the element
	 * @param y the y coordinate for the element
	 * @return the element at the position (x,y) or the element of the RootPanel
	 *         if not found
	 */
	public static Element getElement(int x, int y) {
		Element el = RootPanel.get().getElement();
		el = getElement(el, x, y);
		return el;
	}

	/**
	 * 
	 * @param el
	 * @param x
	 * @param y
	 * @return
	 */
	static Element getElement(Element el, int x, int y) {
		
		int cc = DOM.getChildCount(el);
		for (int i = 0; i < cc; i++) {
			Element ce = DOM.getChild(el, i);
			int top = DOM.getAbsoluteTop(ce);
			int left = DOM.getAbsoluteLeft(ce);
			int width = DOM.getElementPropertyInt(ce, "offsetWidth");
			int height = DOM.getElementPropertyInt(ce, "offsetHeight");
			if (x >= left && x <= left + width && y >= top && y <= top + height) {
				return getElement(ce, x, y);
			}
		}
		return el;
	}

}//end of class
