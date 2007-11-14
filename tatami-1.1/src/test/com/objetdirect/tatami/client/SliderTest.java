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

import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class SliderTest extends DefaultTatamiTest {

	
	private Slider slider;
	
	
	
	/**
	 *Test if the change values are well done by the component. 
	 */
	public void testChangeValue() {
	    
		slider = getSlider(true,0,100,0);
		
		
		final int defaultValue = 10;
		
		slider.addChangeListener(new ChangeListener() {
		
			public void onChange(Widget sender) {
				//System.out.println(slider.getValue());
				assertTrue(defaultValue == slider.getValue());
			}
		});
		
		slider.setValue(defaultValue);
		
		assertTrue(10 == slider.getValue());
		assertTrue(100 == slider.getMaximum());
		assertTrue(0 == slider.getMinimum());
		
	}
	
	
	
	public void testMinMaxValues() {
		slider = getSlider(true,0,100,0);
		assertEquals(0,slider.getMinimum());
		assertEquals(100,slider.getMaximum());
		
	}
	
	
	public void testSetSize() {
		slider = getSlider(true,0,100,0);
		slider.setSize(400, 20);
		assertEquals(400,slider.getWidth());
		assertEquals(20,slider.getHeight());
		
	}
	
	public void tearDown() {
		this.slider = null;
	}
	
	/**
	 * Returns the instance of the Slider.
	 * @param horizontal positions the slider horizontally or vertically
	 * @param min minimal value for slide
	 * @param max maximal value for slide
	 * @param init initial value for slide
	 * @return an instance of Slider, a new instance if created if it was not existed
	 */
	public Slider getSlider(boolean horizontal, int min, int max, int init) {
	   if ( slider == null) {
		   String position = Slider.HORIZONTAL;
		   if ( horizontal) {
				position = Slider.HORIZONTAL;
		   } 
		   slider = new Slider(position,min,max,init,true);
		   
	   }
	   RootPanel.get().add(slider,50,50);
	   return slider;
   }
	
}
