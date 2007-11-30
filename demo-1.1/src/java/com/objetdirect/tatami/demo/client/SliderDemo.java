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
package com.objetdirect.tatami.demo.client;

import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.RuleLabels;
import com.objetdirect.tatami.client.Slider;

/**
 * Demo page for the Slider component. When the cursor of the Slider is changed then 
 * the dimension of an image is also modify according to the values of the cursor.
 * 
 */

public class SliderDemo extends Composite implements ChangeListener{

	 private Slider horizontalSlider;
	 private Slider verticalSlider;
	 private DockPanel panel;
	 private Image cubicImage;
	
	 public SliderDemo() {
		 initComponents();
		 initWidget(panel);
	 }
	 
	 public Slider getSlider() {
		 return this.horizontalSlider;
	 }
	 
	 
	 private void initComponents() {
		 panel = new DockPanel();
		 panel.setSpacing(10);
		
		 HTML html = new HTML("Move the cursor of each <b>Slider</b> to modify the size of the image below");
		 verticalSlider = new Slider(Slider.VERTICAL, 0, 100, 100,true);
         verticalSlider.setRuleMarkLeft(6, "5px");
		 verticalSlider.setRuleMarkRight(12, "3px");
		 String[] labels = {" ","20%","40%","60%","80%", " "};
		 
		 verticalSlider.setLabelsLeft(labels,"margin: 0px -0.5em 0px -2em;color:gray");
		 
		 horizontalSlider = new Slider(Slider.HORIZONTAL, 0, 100, 100,true);
		 
		 horizontalSlider.setRuleMarkBottom(6, "5px");
		 horizontalSlider.setLabelsTop(labels,"margin: -0.5em 0px -3.5em 0px;color:gray");
		 horizontalSlider.setWidth("205px");
		 cubicImage = new Image("cubic.jpg");
		 cubicImage.setStyleName("SliderDemo-image");
		 cubicImage.setSize("200px", "200px");
		 
		 verticalSlider.addChangeListener(this);
		 verticalSlider.setValue(100);
		 verticalSlider.setStylePrimaryName("SliderDemo-vSlider");
		 horizontalSlider.addChangeListener(this);
         panel.add(html,DockPanel.NORTH);
		 panel.add(cubicImage,DockPanel.CENTER);
		 		
 		panel.setCellWidth(cubicImage, "205px");
		panel.setCellHeight(cubicImage, "205px");
		panel.add(verticalSlider,DockPanel.WEST);
		panel.setCellHorizontalAlignment(verticalSlider,DockPanel.ALIGN_RIGHT);	
		panel.add(horizontalSlider,DockPanel.SOUTH);
		panel.setCellVerticalAlignment(cubicImage,DockPanel.ALIGN_MIDDLE);
		panel.setCellHorizontalAlignment(horizontalSlider,DockPanel.ALIGN_LEFT);
            
	 }

	 public void onChange(Widget sender) {
		 if (sender.equals(verticalSlider) &&  verticalSlider.getValue() != -1) {
				cubicImage.setHeight((verticalSlider.getValue()*2) + "px");
		 } else if ( sender.equals(horizontalSlider) && horizontalSlider.getValue() != -1 ) {
			 cubicImage.setWidth((horizontalSlider.getValue()*2) + "px");
		 }
	 }

}//end of class
