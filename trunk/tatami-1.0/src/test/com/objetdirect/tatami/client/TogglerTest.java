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
 * Authors: Vianney Grassaud
 * Initial developer(s):
 * Contributor(s):
 */
package com.objetdirect.tatami.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;
import com.objetdirect.tatami.client.test.Task;
import com.objetdirect.tatami.client.test.TestUtil;

public class TogglerTest extends DefaultTatamiTest {

	
	/**
	 * Tests the click event on the toggler. It's means 
	 * if a component is well hide or show, when a click event is 
	 * catch on the Toogler component 
	 *
	 */
	public void testOnClickToggler() {
		final TogglerModule module = new TogglerModule();
		module.onModuleLoad();
		
		final Task task = new Task() {
			public void run() {
                  Element label = module.getPane().getElement();
                  Element child  = DOM.getChild(label, 0);
                  String property = DOM.getStyleAttribute(child, "display");
                  assertEquals("none",property);          
			}
		};

		Timer timer = new Timer() {
			public void run() {
				TestUtil.fireEvent("click", module.getTogglerX() + 5, module.getTogglerY()+5, TestUtil.MOUSE_BUTTON_1, 1,task);
				finishTest();
			}
		};

		timer.schedule(500);
		this.delayTestFinish(1000);

	}

}
