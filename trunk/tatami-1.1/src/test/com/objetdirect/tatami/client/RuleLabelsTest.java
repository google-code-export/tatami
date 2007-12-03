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

public class RuleLabelsTest extends DefaultTatamiTest {

   private RuleLabels ruleLabels;
	/**
	 * Returns an instance of <code>RuleMark</code>
	 * @param type the type Horizontal or vertical
	 * @param labels the labels
	 * @param style the style for the labels
	 * @param position the position in the parent container
	 * @return an instance of of <code>RuleMark</code>
	 */
   public RuleLabels getRuleLabels(String type,String[] labels,String style,String position) {
	  if ( ruleLabels == null) {
		  ruleLabels = new RuleLabels(type,labels,style,position);
		 RootPanel.get().add(ruleLabels);	  
		  
	  }
	  return ruleLabels;
   }

   public void tearDown() {
	   ruleLabels = null;
   }

   /**
    * Tests <code>Rulelabelsk</code> with VERTICAL type
    *
    */
   public void testVertical() {
	   String[] labels = {"0","1","2","3"};
	   String style = "color:red";
	   ruleLabels = getRuleLabels(RuleLabels.VERTICAL,labels,style,"left");
	   assertEquals(labels,ruleLabels.getLabels());
	   assertEquals(style,ruleLabels.getStyle());
	   assertEquals(RuleMark.VERTICAL,ruleLabels.getType());
	   assertEquals("left",ruleLabels.getPosition());
	   
   }
   
   /**
    * Tests <code>RuleLabels</code> with HORIZONTAL type
    *
    */
   public void testHorizontal() {
	   String[] labels = {"0","1","2","3"};
	   String style = "color:red";
	   ruleLabels = getRuleLabels(RuleLabels.HORIZONTAL,labels,style,"null");
	   assertEquals(labels,ruleLabels.getLabels());
	   assertEquals(style,ruleLabels.getStyle());
	   assertEquals(RuleMark.HORIZONTAL,ruleLabels.getType());
	   assertEquals("null",ruleLabels.getPosition());
	   
   }



}
