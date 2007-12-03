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
 * Authors:  Vianney Grassaud
 * Initial developer(s): Vianney Grassaud
 * Contributor(s):
 */
package com.objetdirect.tatami.client;

import java.util.Date;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.test.Task;
import com.objetdirect.tatami.client.test.TestUtil;

public abstract class TestDropdownContainer extends DefaultTatamiTest {
	
	private DropdownContainer dropdownContainer;
	private boolean clicked = false;
	private int xClickPicker = 30;
    private boolean textChanged = false;
	private int yClickPicker = 75;
   
	
	
	

	 /**
	    * Creates an instance of DropdownContainer.
	    * Implements this method to specify an implementation of DropdownContainer to create
	    * 
	    * @return an instance of DropdownContainer
	    */
	protected abstract DropdownContainer createInstance(Date min, Date max);
	
	
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
		
		dropdownContainer = getDropdownContainer();
		final Date firstDate = dropdownContainer.getDate();

		dropdownContainer.addChangeListener(new ChangeListener() {
			public void onChange(Widget sender) {
				
				assertNotSame(firstDate, dropdownContainer.getDate());
			}
		});

		dropdownContainer.setDate(new Date());
		
	}
	
	/**
	 * Tests the setText() and getText() methods
	 *
	 */
	public void testSetText() {
		
		dropdownContainer = getDropdownContainer();
		final String firstDate = dropdownContainer.getText();

		dropdownContainer.addChangeListener(new ChangeListener() {
			public void onChange(Widget sender) {
				textChanged = true;
				assertNotSame(firstDate, dropdownContainer.getText());
			}
		});

		
		dropdownContainer.setText(getText());
		assertTrue(textChanged);
		textChanged = false;
	}

	/**
	 * Tests the getMinDate et getMaxDate()
	 *
	 */
	public void testMinMaxDate() {
		Date min = new Date();
		Date max = min;
		dropdownContainer = getDropdownContainer(min,max);
		assertSame(min,dropdownContainer.getMinDate());
		assertSame(max,dropdownContainer.getMaxDate());		
	}
	
	/**
	 * Tests the setPromptMessage and setInvalidMessage
	 *
	 */
	public void testMessages() {
		dropdownContainer = getDropdownContainer();
		String promptMessage = "promptMessage";
		String invalidMessage = "invalidMessage"; 
		dropdownContainer.setPromptMessage(promptMessage);
		dropdownContainer.setInvalidMessage(invalidMessage);
		
		assertEquals(promptMessage,dropdownContainer.getPromptMessage());
		assertEquals(invalidMessage,dropdownContainer.getInvalidMessage());
	}
	
	/**
	 * Tests the validation
	 *
	 */
	public void testValidation() {
		dropdownContainer = getDropdownContainer();
		dropdownContainer.setText("a invalid input");
		assertFalse(dropdownContainer.isValid());
		dropdownContainer.setText(getText());
		assertTrue(dropdownContainer.isValid());
	}
	
	
	/**
	 * Tests the mouse event on the component
	 *
	 */
	public void testClickOnPicker() {
		
		dropdownContainer = getDropdownContainer();
		
		final int xTextBoxCoordinate = dropdownContainer.getAbsoluteLeft() + dropdownContainer.getOffsetWidth() - 5;
		final int yTextBoxCoordinate = dropdownContainer.getAbsoluteTop()  + dropdownContainer.getOffsetHeight() - 5;		
		final int xPickerCoordinate = xClickPicker +  dropdownContainer.getAbsoluteLeft();
		final int yPickerCoordinate = yClickPicker + dropdownContainer.getAbsoluteTop() + dropdownContainer.getOffsetHeight();
		
		
		final Task task = new Task() {
			public void run() {
				assertTrue(clicked);
				assertNotNull(dropdownContainer.getDate());
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
	
	
	
	/**
	 * Returns the instance of the DropdownContainer.

	 * @return an instance of DropdownContainer, a new instance if created if it was not existed
	 */
	public DropdownContainer getDropdownContainer() {
		 if ( this.dropdownContainer == null) {
			 dropdownContainer = createInstance(null,null);
			 dropdownContainer.addChangeListener(new ChangeListener() {
				public void onChange(Widget sender) {
					clicked = true;
				}
			 });
			 RootPanel.get().add(dropdownContainer,5,5);
				 
		 }
		 return this.dropdownContainer;
	}
	

	/**
	 * 
	 *
	 */
	public void testEnabled() {
		dropdownContainer = getDropdownContainer();
		dropdownContainer.setEnabled(false);
		assertFalse(dropdownContainer.isEnabled());
		dropdownContainer.setEnabled(true);
		assertTrue(dropdownContainer.isEnabled());
	}
	
	
	/**
	 * Returns the instance of the DropdownContainer.
     * @param min the minimal date
     * @param max the maximal date
	 * @return an instance of DropdownContainer, a new instance if created if it was not existed
	 */
	public DropdownContainer getDropdownContainer(Date min, Date max) {
		 if ( this.dropdownContainer == null) {
			 dropdownContainer = createInstance(min,max);
			 dropdownContainer.addChangeListener(new ChangeListener() {
				public void onChange(Widget sender) {
					clicked = true;
				}
			 });
			 RootPanel.get().add(dropdownContainer,5,5);
				 
		 }
		 return this.dropdownContainer;
	}

	
	/**
	 * 
	 */
	public void tearDown() {
		this.dropdownContainer = null;
		this.clicked = false;
		this.textChanged = false;
	}

}//end of class
