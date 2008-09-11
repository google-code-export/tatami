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

import com.google.gwt.user.client.ui.RootPanel;

public class ColorPickerTest extends DefaultTatamiTest {

	private ColorPicker colorPicker;

	/**
	 * 
	 *
	 */
	public void testColorPicker1() {
		assertNotNull(getColorPicker(true, true, true, true));
	}

	/**
	 * 
	 *
	 */
	public void testColorPicker2() {
		assertNotNull(getColorPicker(false, false, false, false));
	}

	
	/**
	 * Creates a Color Picker
	 * 
	 * @param showHsv  show/update HSV input nodes
	 * @param showRgb  show/update RGB input nodes
	 * @param showHex  show/update Hex value field
	 * @param animatePoint  toggle to use slideTo (true) or just place the cursor (false) on click
	 * @return a ColorPicker widget
	 */
	private ColorPicker getColorPicker(boolean showHsv, boolean showRgb,
			boolean showHex, boolean animatePoint) {
		if (colorPicker == null) {
			colorPicker = new ColorPicker(showHsv, showRgb, showHex,
					animatePoint);
			RootPanel.get().add(colorPicker);
		}
		return colorPicker;
	}
	
	/**
	 * Sets the color picker to null
	 */
	public void gwtTearDown() {
		this.colorPicker = null;
	}

}// end of class
