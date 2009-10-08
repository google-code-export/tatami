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

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.objetdirect.tatami.client.Slider;
import com.objetdirect.tatamix.client.hmvc.CompositeView;
import com.objetdirect.tatamix.client.widget.Paragraph;

/**
 * Demo page for the Slider component. When the cursor of the Slider is changed then
 * the dimension of an image is also modify according to the values of the cursor.
 *
 */

public class SliderDemo extends CompositeView implements ValueChangeHandler<Integer>{

	 private Slider horizontalSlider;
	 private Slider verticalSlider;
	 private FlowPanel layout;

	 private Image cubicImage;

	/**
	 * Creates the SliderDemo
	 *
	 */
	 public SliderDemo() {
		 super();
		 layout = new FlowPanel();
		 initWidget(layout);
		 initComponents();
         setStylePrimaryName("block");
	 }



	 /**
	  * Creates 2 <code>Slider</code> components with marks an labels.
	  * When the position of the cursor of this <code>Slider</code> is changing the size of the
	  * image is also changing in function of the values of the <code>Slider</code>.
	  *
	  */
	 private void initComponents() {
		 Paragraph intro = new Paragraph();
		 intro.setHTML(TatamiDemo.getMessages().slider_intro());
		 layout.add(intro);

		 verticalSlider = new Slider(Slider.VERTICAL, 0, 100, 100,true);
		 verticalSlider.setStylePrimaryName("verticalSlider");
         verticalSlider.setRuleMarkLeft(6, "5px");
         verticalSlider.setSize(10,200);
		 verticalSlider.setRuleMarkRight(12, "3px");
		 String[] labels = {" ","20%","40%","60%","80%", " "};

		 verticalSlider.setLabelsLeft(labels,"margin: 0px -0.5em 0px -2em;color:gray");
		 
		 layout.add(verticalSlider);


		 FlowPanel wrapper = new FlowPanel();
		 cubicImage = new Image(TatamiDemo.getIconURL("cubic.jpg"));
		 cubicImage.setStylePrimaryName("sliderImage");
		 wrapper.add(cubicImage);

		 horizontalSlider = new Slider(Slider.HORIZONTAL, 0, 100, 100,true);
		 horizontalSlider.setStylePrimaryName("horizontalSlider");
		 horizontalSlider.setRuleMarkBottom(6, "5px");
		 horizontalSlider.setLabelsTop(labels,"margin:  -0.5em 0px 2em 0px;color:gray");
		 

		 horizontalSlider.addValueChangeHandler(this);
		 wrapper.add(horizontalSlider);

         layout.add(wrapper);
         
         verticalSlider.addValueChangeHandler(this);
		 verticalSlider.setValue(100);


	 }

	 /**
	  * Changes the size image when a <code>Slider</code> fired a change value.
	  */
	 public void onValueChange(ValueChangeEvent<Integer> event) {
		 if (event.getSource().equals(verticalSlider) &&  verticalSlider.getValue() != -1) {
				cubicImage.setHeight((verticalSlider.getValue()*2) + "px");
		 } else if ( event.getSource().equals(horizontalSlider) && horizontalSlider.getValue() != -1 ) {
			 cubicImage.setWidth((horizontalSlider.getValue()*2) + "px");
		 }
	 }

}//end of class
