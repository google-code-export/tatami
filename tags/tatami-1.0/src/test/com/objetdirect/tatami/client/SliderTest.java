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
import com.google.gwt.user.client.ui.Widget;

public class SliderTest extends DefaultTatamiTest {

	/**
	 *Test if the change values are well done by the component. 
	 *FIXME a warning message is thrown during the test (not every time).
	 * a call to the method setValue(0) is done. More over 
	 *the test doesn't fail, mystery to be solved. 
	 */
	public void testChangeValue() {
	    
		SliderModule module = new SliderModule(true,0,100,0);
		module.onModuleLoad();
		final Slider slider = module.getSlider();
		final int defaultValue = 10;
		
		slider.addChangeListener(new ChangeListener() {
		
			public void onChange(Widget sender) {
				System.out.println(slider.getValue());
				assertTrue(defaultValue == slider.getValue());
			}
		});
		
		slider.setValue(defaultValue);
		
		assertTrue(10 == slider.getValue());
		assertTrue(100 == slider.getMaximum());
		assertTrue(0 == slider.getMinimum());
		
	}
	
	
}
