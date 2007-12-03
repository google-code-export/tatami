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
package com.objetdirect.tatami.client;


import java.util.Date;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootPanel;
import com.objetdirect.tatami.client.gfx.Color;

/**
 * Tests the Clock component
 * @author Vianney
 *
 */
public class ClockTest extends DefaultTatamiTest {

	private Clock clock = null;
	
	public void testClock() {
		final Clock clock = getClock("browser.png",200);
		assertEquals(200,clock.getWidth());
		assertEquals("browser.png",clock.getImage());
		final long time = clock.getTime().getTime();
		Timer timer = new Timer() {
			public void run() {
				assertFalse(time == clock.getTime().getTime());
				finishTest();
			}
		};
		timer.schedule(2000);
		this.delayTestFinish(4000);
	}
	
	/**
	 * Tests methods for the needle of the hours
	 *
	 */
	public void testHour() {
		clock = getClock();
		clock.setHourColor(Color.RED);
		clock.setHourStrokeColor(Color.PURPLE);
		clock.setHourStrokeWidth(2);
		assertEquals(Color.RED,clock.getHourColor());
		assertEquals(Color.PURPLE,clock.getHourStrokeColor());
		assertEquals(2,clock.getHourStrokeWidth());
	}
	
	/**
	 * Tests methods for the needle of the minutes
	 *
	 */
	public void testMinute() {
		clock = getClock();
		clock.setMinuteColor(Color.RED);
		clock.setMinuteStrokeColor(Color.PURPLE);
		clock.setMinuteStrokeWidth(2);
		assertEquals(Color.RED,clock.getMinuteColor());
		assertEquals(Color.PURPLE,clock.getMinuteStrokeColor());
		assertEquals(2,clock.getMinuteStrokeWidth());
	}
	
	/**
	 * Tests methods for the needle of the seconds
	 *
	 */
	public void testSecond() {
		clock = getClock();
		clock.setSecondColor(Color.BLACK);
		clock.setSecondStrokeColor(Color.GREEN);
		clock.setSecondStrokeWidth(2);
		assertEquals(Color.BLACK,clock.getSecondColor());
		assertEquals(Color.GREEN,clock.getSecondStrokeColor());
		assertEquals(2,clock.getSecondStrokeWidth());
	}
	
	/**
	 * Returns an instance of <code>Clock</code>
	 * @param image the image for the background to use
	 * @param size the size of the clock
	 * @return ans instance of <code>Clock</code>
	 */ 
	public Clock getClock(String image, int size) {
		if ( clock == null) {
			clock = new Clock(image,size);
	        RootPanel.get().add(clock);		
		}
		return clock;
	}
	
	
	/**
	 * Returns an instance of <code>Clock</code> with a default size and 
	 * no background image.
	 * @return an instance of <code>Clock</code>
	 */ 
	public Clock getClock() {
		if ( clock == null) {
			clock = new Clock();
	        RootPanel.get().add(clock);		
		}
		return clock;
	}
	
	
	public void tearDown() {
		this.clock = null;
	}
	
	
}
