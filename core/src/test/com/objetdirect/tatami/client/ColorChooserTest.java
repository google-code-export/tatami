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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootPanel;
import com.objetdirect.tatami.client.test.Task;
import com.objetdirect.tatami.client.test.TestUtil;

public class ColorChooserTest extends DefaultTatamiTest {

	private boolean clicked = false;
	private boolean changed = false;
	private ColorChooser chooser;
	

	/**
	 * Tests is the 70 palette colors is well created
	 * 
	 */
	public void testSeventyColors() {
		assertNotNull(getColorChooser(false));
	}

	/**
	 * Tests is the 12 palette colors is well created
	 * 
	 */
	public void testTwelveColors() {
		assertNotNull(getColorChooser(true));

	}
	
	
	/**
	 * Tests the setColor() and getColor()
	 *
	 */
	public void testSetColor() {
		chooser = getColorChooser(false);
		String color = "#ff1122";
		chooser.setColor(color);
		assertEquals(color,chooser.getColor());
	}
	
	/**
	 * Tests if the click event
	 *
	 */
	public void testOnClick() {
		chooser = getColorChooser(false);
		final Task task = new Task() {
			public void run() {
                assertTrue(clicked);
			}
		};

		Timer timer = new Timer() {
			public void run(){
 			    TestUtil.fireEvent("click", 35,35,TestUtil.MOUSE_BUTTON_1,1,task);
				finishTest();
			}
		};
		
		this.delayTestFinish(150);
		timer.schedule(100);
		
	}
	
	/**
	 * Returns the instance of the ColorChooser.
	 * @param small indicates if the palette will have 70 or 12 colors
	 * @return an instance of ColorChooser, a new instance if created if it was not existed
	 */
	private ColorChooser getColorChooser(boolean small) {
		if ( chooser == null) {
			String size = ColorChooser.SEVENTY_COLORS;
			if ( small) {
	            	size = ColorChooser.TWELVE_COLORS;
	        }
			chooser = new ColorChooser(size);	
		    chooser.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					clicked = true;
				}
			});
			
			RootPanel.get().add(chooser,10,10);
		} 
		return chooser;
	}
	
	
	public void testChangeValue() {
		chooser = getColorChooser(false);
		final String color = "#FFFFFF";
		chooser.setColor(color);
		chooser.addValueChangeHandler(new ValueChangeHandler<String>() {
			public void onValueChange(ValueChangeEvent<String> event) {
                assertFalse(color.equals(chooser.getValue()));
                assertNotNull(chooser.getValue());
                changed = true;
			}
		});
		chooser.setColor("#FF00FF");
		assertEquals("#FF00FF",chooser.getValue());
		assertTrue(changed);
	}
	
	public void testNotChangeValue() {
		chooser = getColorChooser(false);
		final String color = "#FFFFFF";
		chooser.setColor(color);
		chooser.addValueChangeHandler(new ValueChangeHandler<String>() {
			public void onValueChange(ValueChangeEvent<String> event) {
                assertFalse(color.equals(chooser.getValue()));
                assertNotNull(chooser.getValue());
                changed = true;
			}
		});
		chooser.setColor("#FFFFFF");
		assertEquals(color,chooser.getValue());
		assertFalse(changed);
	}
	
	public void gwtTearDown() throws Exception{
		this.clicked = false;
		changed = false;
		chooser = null;
		super.gwtTearDown();
	}

}//end of class
