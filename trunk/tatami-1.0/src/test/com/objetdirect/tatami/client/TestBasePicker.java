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

import java.util.Date;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.test.Task;
import com.objetdirect.tatami.client.test.TestUtil;

public abstract class  TestBasePicker extends DefaultTatamiTest {

	/**
	 * Returns an instance of BasePickerModule.
	 * Implements this method to specify an implementation of BasePickerModule to create
	 * @return an instance of BasePickerModule
	 */
	protected abstract BasePickerModule createInstance();
	
	private int xCoordinate = 25;
	private int yCoordinate = 50;
	
	/**
	 * Sets x coordinate for a mouse event 
	 * @param x the x position of the mouse
	 */
	public void setXCoordinate(int x) {
		this.xCoordinate = x;
	}
	
	/**
	 * Sets Y coordinate for a mouse event 
	 * @param y the y position of the mouse
	 */
	public void setYCoordinate(int y) {
		this.yCoordinate = y;
	}
	
	/**
	 * Tests the setDate() and getDate() methods
	 *
	 */
	public void testSetDate() {
		BasePickerModule module = createInstance();
		module.onModuleLoad();

		final BasePicker timer = module.getBasePicker();
		final Date firstDate = timer.getDate();

		timer.addChangeListener(new ChangeListener() {
			public void onChange(Widget sender) {
				assertNotSame(firstDate, timer.getDate());
			}
		});

		timer.setDate(new Date());
	}


	/**
	 * Tests the mouse event on the component
	 *
	 */
	public void testClickOnPicker() {
		final BasePickerModule module = createInstance();
		module.onModuleLoad();
        final Task task = new Task() {
        	public void run() {
        		assertTrue(module.isClicked());
        		assertNotNull(module.getBasePicker().getDate());
        	}
        };


        Timer timer = new Timer() {
			public void run() {
        		TestUtil.fireEvent("click", xCoordinate, yCoordinate, TestUtil.MOUSE_BUTTON_1, 1,task);
				finishTest();
			}
		};
		timer.schedule(500);
		this.delayTestFinish(1000);

	}
}
