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

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class SliderModule implements EntryPoint {
	
	private String position = Slider.VERTICAL;
	private int max = 100;
	private int min = 0;
	private int init = 0;
	private Slider slider;
	
	/**
	 * Creates the module containing the Slider. 
	 * 
	 * @param horizontal positions the slider horizontally or vertically
	 * @param min minimal value for slide
	 * @param max maximal value for slide
	 * @param init initial value for slide
	 */
	public SliderModule(boolean horizontal, int min, int max, int init) {
	
		this.max = max;
		this.min = min;
		this.init = init;
		if ( horizontal) {
			position = Slider.HORIZONTAL;
		}
	}
	
	/**
	 * Loads the module to test components
	 */
	public void onModuleLoad() {
	    		
	    slider = getSlider();
        RootPanel.get().add(slider,50,50);
		 
	}

	/**
	 * Returns the instance of the Slider.
	 * @return an instance of Slider, a new instance if created if it was not existed
	 */
	public Slider getSlider() {
	   if ( slider == null) {
		   slider = new Slider(position,min,max,init);
		   
	   }
	   return slider;
   }

   
   
   
}//
