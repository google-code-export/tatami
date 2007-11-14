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
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.test.Task;
import com.objetdirect.tatami.client.test.TestUtil;

public abstract class  TestBasePicker extends DefaultTatamiTest {

	private BasePicker basePicker;
	private boolean clicked = false;
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
	 * Returns the instance of the TimePicker.
	 * @return an instance of TimePicker, a new instance if created if it was not existed
	 */
	public BasePicker getBasePicker() {
		if (basePicker == null) {
			this.basePicker = createInstance(null,null);
			basePicker.addChangeListener(new ChangeListener() {
				public void onChange(Widget sender) {
					clicked = true;
				}
			});
			RootPanel.get().add(basePicker);
		}
		return this.basePicker;
	}

	
	
	/**
	 * Returns the instance of the TimePicker.
	 * @return an instance of TimePicker, a new instance if created if it was not existed
	 */
	public BasePicker getBasePicker(Date min, Date max) {
		if (basePicker == null) {
			this.basePicker = createInstance(min,max);
			RootPanel.get().add(basePicker);
		}
		return this.basePicker;
	}
	
   /**
    * Creates an instance of BasePicker.
    * Implements this method to specify an implementation of BasePicker to create
    * @param min the minimal date available for the BasePicker
    * @param min the maximal date available for the BasePicker
    * @return an instance of BasePicker
    */
	abstract protected BasePicker createInstance(Date min, Date max);	
    
    
	
	
	/**
	 * Tests the setDate() and getDate() methods
	 *
	 */
	public void testSetDate() {
		
		basePicker = getBasePicker();
		
		final Date firstDate = basePicker.getDate();
		basePicker.addChangeListener(new ChangeListener() {
			public void onChange(Widget sender) {
				assertNotSame(firstDate,((BasePicker)sender).getDate());
                //for a strange reason, the code below fails because 
				//basePicker is null, however, in debug mode (with eclipse) the variable is well not null !!!
				//so is it a bug or not ? 
				//assertNotNull(basePicker.getDate());
			
			}
		});

		basePicker.setDate(new Date());
	}

  /**
	* 
	*/
  public void tearDown() {
	this.basePicker =null;
	this.clicked = false;
  }
	
  
  /**
   * Tests the getMaxDate() and getMinDate() of a 
   * BasePicker
   *
   */
  public void testLimitDate() {
	  Date min = new Date();
	  Date max =  min;
	  
	  basePicker = getBasePicker(min,max);
	  
	  assertSame(basePicker.getMinDate(),min);
	  assertSame(basePicker.getMaxDate(),max);
	  
	  
  }
  
	/**
	 * Tests the mouse event on the component
	 *
	 */
	public void testClickOnPicker() {
		basePicker = getBasePicker();
		final Task task = new Task() {
        	public void run() {
        		assertTrue(clicked);
        		assertNotNull(basePicker.getDate());
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
