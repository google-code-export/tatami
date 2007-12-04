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
package com.objetdirect.tatami.demo.client;

import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.ColorChooser;
import com.objetdirect.tatami.client.ColorPicker;

public class ColorDemo extends Composite implements ChangeListener{

	private ColorChooser small;
	private ColorChooser big;
	private ColorPicker picker;
	private HTML colorLabel;
	private DockPanel panel;
	
	/**
	 * Creates the color demo
	 *
	 */
	public ColorDemo() {
		initComponents();
		initWidget(panel);
	}
	
	/**
	 * Inits the components.
	 * Show the <code>ColorChooser</code> and the 
	 * <code>ColorPicker</code> which is experimental in Tatami-1.1. 
	 * 
	 *
	 */
	private void initComponents() {
		panel = new DockPanel();
		panel.setSpacing(30);
		
		colorLabel = new HTML("<b>No color selected.</b>");
		big = new ColorChooser();
		big.setTitle("70 colors");
		big.addChangeListener(this);
		small = new ColorChooser(ColorChooser.TWELVE_COLORS);
		small.addChangeListener(this);
		small.setTitle("12 colors");
		picker = new ColorPicker();
		
		VerticalPanel vPanel = new VerticalPanel();
		vPanel.setSpacing(20);
		vPanel.add(new HTML("<b>ColorChooser</b>: 2 sizes available, click on color to change the color of text."));
		vPanel.add(big);
		vPanel.add(colorLabel);
		vPanel.add(small);
		panel.add(vPanel,DockPanel.WEST);
		VerticalPanel vPanel2 = new VerticalPanel();
		vPanel2.add(new HTML("<b>ColorPicker</b> : Provides an interactive HSV ColorPicker similar to PhotoShop's color selction tool. Will eventually mixin FormWidget and be used as a suplement or a	'more interactive' replacement for ColorChooser"));
		vPanel2.add(picker);
		
		panel.add(vPanel2,DockPanel.EAST);
	}
	
	
	
	/**
	 * Changes the color of the label when a the select color of the <code>ColorChooser</code> change
	 */
	public void onChange(Widget sender) {
		String color = null;
		if ( sender.equals(big)) {
			color = big.getColor();
		} else {
			color = small.getColor();
		}
		
		if (color != null) {
			colorLabel.setHTML("<font color=\""+ color +"\">The color selected : " + color + "</font>");
		}
		
	}
}
