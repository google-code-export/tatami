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

import com.google.gwt.user.client.ui.FlowPanel;
import com.objetdirect.tatami.client.ColorChooser;
import com.objetdirect.tatami.client.ColorPicker;
import com.objetdirect.tatamix.client.hmvc.CompositeView;
import com.objetdirect.tatamix.client.widget.ColorEditor;
import com.objetdirect.tatamix.client.widget.FieldSet;
import com.objetdirect.tatamix.client.widget.FormLabel;
import com.objetdirect.tatamix.client.widget.Paragraph;
import com.objetdirect.tatamix.client.widget.RoundedContainer;

public class ColorDemo extends CompositeView {

	private ColorEditor small;
	private ColorEditor big;
	private ColorPicker picker;

	private FlowPanel layout;

	/**
	 * Creates the color demo
	 *
	 */
	public ColorDemo() {
		layout = new FlowPanel();
		initWidget(layout);
		initComponents();
        setStylePrimaryName("color");
	}

	/**
	 * Inits the components.
	 * Show the <code>ColorChooser</code> and the
	 * <code>ColorPicker</code> which is experimental in Tatami-1.1.
	 *
	 *
	 */
	private void initComponents() {
		big = new ColorEditor(ColorChooser.SEVENTY_COLORS);
		big.setTitle("70 colors");

		small = new ColorEditor(ColorChooser.TWELVE_COLORS);
		small.setTitle("12 colors");

		RoundedContainer left = new RoundedContainer();

		Paragraph intro1 = new Paragraph();
		intro1.setHTML(TatamiDemo.getMessages().color_intro1());
		left.addWidget(intro1);

		FieldSet fieldset = new FieldSet();
		fieldset.setLegend("Color edition");
		left.addWidget(fieldset);

		FlowPanel entry1 = new FlowPanel();
		FormLabel label = new FormLabel();
		label.setText(TatamiDemo.getMessages().color_label1());
        label.setFor("colorBig", big.getInput());
        entry1.add(label);
        entry1.add(big);


        FlowPanel entry2 = new FlowPanel();
		FormLabel label2 = new FormLabel();
		label2.setText(TatamiDemo.getMessages().color_label2());
        label2.setFor("colorSmall", small.getInput());
        entry2.add(label2);
        entry2.add(small);

        fieldset.add(entry1);
        fieldset.add(entry2);

        layout.add(left);

        RoundedContainer right = new RoundedContainer();


		picker = new ColorPicker();

		Paragraph intro2 = new Paragraph();
		intro2.setHTML(TatamiDemo.getMessages().color_intro2());
		right.addWidget(intro2);
		right.addWidget(picker);
		layout.add(right);


	}




}
