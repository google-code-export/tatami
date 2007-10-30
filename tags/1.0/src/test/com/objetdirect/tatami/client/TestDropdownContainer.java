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

public abstract class TestDropdownContainer extends DefaultTatamiTest {
	
	private int xClickPicker = 30;
    private boolean textChanged = false;
	private int yClickPicker = 75;

	/**
	 * Returns an instance of DropdownContainerModule.
	 * Implements this method to specify an implementation of DropdownContainerModule to create
	 * @return an instance of DropdownContainerModule
	 */
	protected abstract DropdownContainerModule createInstance();

	
	
	/**
	 * Returns the text to write in the input zone of DropdownContainer component
	 * @return the text to write in the input zone of DropdownContainer component
	 */
	abstract protected String getText();
	
	/**
	 * Tests the setDate() and getDate() methods
	 *
	 */
	public void testSetDate() {
		DropdownContainerModule module = createInstance();
		module.onModuleLoad();

		final DropdownContainer timer = module.getDropdownContainer();
		final Date firstDate = timer.getDate();

		timer.addChangeListener(new ChangeListener() {
			public void onChange(Widget sender) {
				
				assertNotSame(firstDate, timer.getDate());
			}
		});

		timer.setDate(new Date());
		
	}
	
	/**
	 * Tests the setText() and getText() methods
	 *
	 */
	public void testSetText() {
		DropdownContainerModule module = createInstance();
		module.onModuleLoad();

		final DropdownContainer container = module.getDropdownContainer();
		final String firstDate = container.getText();

		container.addChangeListener(new ChangeListener() {
			public void onChange(Widget sender) {
				textChanged = true;
				assertNotSame(firstDate, container.getText());
			}
		});

		
		container.setText(getText());
		assertTrue(textChanged);
		textChanged = false;
	}

	/**
	 * Tests the mouse event on the component
	 *
	 */
	public void testClickOnPicker() {
		final DropdownContainerModule module = createInstance();
		module.onModuleLoad();
		
		DropdownContainer container = module.getDropdownContainer();
		
		final int xTextBoxCoordinate = container.getAbsoluteLeft() + container.getOffsetWidth() - 5;
		final int yTextBoxCoordinate = container.getAbsoluteTop()  + container.getOffsetHeight() - 5;		
		final int xPickerCoordinate = xClickPicker +  container.getAbsoluteLeft();
		final int yPickerCoordinate = yClickPicker + container.getAbsoluteTop() + container.getOffsetHeight();;
		
		
		final Task task = new Task() {
			public void run() {
				assertTrue(module.isClicked());
				assertNotNull(module.getDropdownContainer().getDate());
			}
		};

		Timer timer = new Timer() {
			public void run() {
				TestUtil.fireEvent("click", xTextBoxCoordinate, yTextBoxCoordinate,	TestUtil.MOUSE_BUTTON_1, 1, null);
				TestUtil.fireEvent("click", xPickerCoordinate, yPickerCoordinate,  TestUtil.MOUSE_BUTTON_1, 1, task);
				finishTest();
			}
		};
		timer.schedule(500);
		this.delayTestFinish(1000);

	}

}//end of class
