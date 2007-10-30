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
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class ColorChooserModule implements EntryPoint {

private ColorChooser chooser;
	
	private boolean clicked = false;
	
	private String size  = ColorChooser.SEVENTY_COLORS;
	
	/**
	 * Creates the module
	 * @param small indicates if the palette will have 70 or 12 colors
	 */
	public ColorChooserModule(boolean small) {
            if ( small) {
            	size = ColorChooser.TWELVE_COLORS;
            }
    }
	
	/**
	 * Loads the module to test components
	 */
	public void onModuleLoad() {
		chooser = getColorChooser();
		chooser.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				clicked = true;
			}
		});
		
		RootPanel.get().add(chooser,10,10);
	}
	
	
	  /**
	 * Permits to test is a mouse click was thrown.
	 * @return true if a click was done false otherwise.
	 */
	public boolean isClicked() {
		return this.clicked;
	}
	
	/**
	 * Returns the instance of the ColorChooser.
	 * @return an instance of ColorChooser, a new instance if created if it was not existed
	 */
	public ColorChooser getColorChooser() {
		if ( chooser == null) {
		    chooser = new ColorChooser(size);	
		    clicked = false;
		} 
		return chooser;
	}
}
