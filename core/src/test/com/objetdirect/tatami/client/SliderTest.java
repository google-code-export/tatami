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

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.RootPanel;

public class SliderTest extends DefaultTatamiTest {

	
	private Slider slider;
	
	
	
	/**
	 *Test if the change values are well done by the component. 
	 */
	public void testChangeValue() {
	    
		slider = getSlider(true,0,100,0);
		
		
		final int defaultValue = 10;
		
		slider.addValueChangeHandler(new ValueChangeHandler<Integer>() {
		
			public void onValueChange(ValueChangeEvent<Integer> event) {
				//
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
	
	public void gwtTearDown() throws Exception {
		this.slider = null;
		super.gwtTearDown();
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
	
	/**
	 * Tests rule marl at the top and bottom positions
	 *
	 */
   public void testRuleMarkHorinzontal() {
	   slider = getSlider(true,0,100,0);
	   slider.setRuleMarkBottom(6, "5");
	   slider.setRuleMarkTop(7, "3");
	   assertEquals(6,slider.countRuleMarkBottom());
	   assertEquals(7,slider.countRuleMarkTop());
	   assertEquals("3",slider.getSizeRuleMarkTop());
	   assertEquals("5",slider.getSizeRuleMarkBottom());
	   slider.removeRuleMarkBottom();
	   assertEquals(0,slider.countRuleMarkBottom());
	   assertEquals("0",slider.getSizeRuleMarkBottom());
	   slider.removeRuleMarkTop();
	   assertEquals(0,slider.countRuleMarkTop());
	   assertEquals("0",slider.getSizeRuleMarkTop());
   }

   /**
	 * Tests rule marl at the left and right positions
	 *
	 */
   public void testRuleMarkVertical() {
	   slider = getSlider(false,0,100,0);
	   slider.setRuleMarkLeft(6, "5");
	   slider.setRuleMarkRight(7, "3");
	   assertEquals(6,slider.countRuleMarkLeft());
	   assertEquals(7,slider.countRuleMarkRight());
	   assertEquals("3",slider.getSizeRuleMarkRight());
	   assertEquals("5",slider.getSizeRuleMarkLeft());
	   slider.removeRuleMarkLeft();
	   assertEquals(0,slider.countRuleMarkLeft());
	   assertEquals("0",slider.getSizeRuleMarkLeft());
	   slider.removeRuleMarkRight();
	   assertEquals(0,slider.countRuleMarkRight());
	   assertEquals("0",slider.getSizeRuleMarkRight());
   }

   /**
	 * Tests labels at the left and right positions
	 *
	 */
   public void testRuleLabelsVertical() {
	   slider = getSlider(false,0,100,0);
	   String[] labels1 = {"0","1","2","3"};
	   String style1 = "color:red";
	   String[] labels2 = {"zero","one","two","three"};
	   String style2 = "color:green";
	   slider.setLabelsLeft(labels1,style1);
	   slider.setLabelsRight(labels2, style2);
	   assertEquals(labels1,slider.getLabelsLeft());
	   assertEquals(labels2,slider.getLabelsRight());
	   assertEquals(style1,slider.getLabelsLeftStyle());
	   assertEquals(style2,slider.getLabelsRightStyle());
	   slider.removeLabelsLeft();
	   assertEquals(null,slider.getLabelsLeft());
	   assertEquals(null,slider.getLabelsLeftStyle());
	   slider.removeLabelsRight();
	   assertEquals(null,slider.getLabelsRight());
	   assertEquals(null,slider.getLabelsRightStyle());
	   
   }
   
    /**
	 * Tests rule marl at the right and left positions
	 *
	 */
   public void testRuleLabelsHorizontal() {
	   slider = getSlider(false,0,100,0);
	   String[] labels1 = {"0","1","2","3"};
	   String style1 = "color:red";
	   String[] labels2 = {"zero","one","two","three"};
	   String style2 = "color:green";
	   slider.setLabelsTop(labels1,style1);
	   slider.setLabelsBottom(labels2, style2);
	   assertEquals(labels1,slider.getLabelsTop());
	   assertEquals(labels2,slider.getLabelsBottom());
	   assertEquals(style1,slider.getLabelsTopStyle());
	   assertEquals(style2,slider.getLabelsBottomStyle());
	   slider.removeLabelsTop();
	   assertEquals(null,slider.getLabelsTop());
	   assertEquals(null,slider.getLabelsTopStyle());
	   slider.removeLabelsBottom();
	   assertEquals(null,slider.getLabelsBottom());
	   assertEquals(null,slider.getLabelsBottomStyle());
	   
   }


}
