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

import com.google.gwt.user.client.ui.RootPanel;

public class RuleMarkTest extends DefaultTatamiTest {

	private RuleMark ruleMark = null;

	/**
	 * Returns an instance of <code>RuleMark</code>
	 * @param type the type Horizontal or vertical
	 * @param count the number of marks
	 * @param size the size of the marks (in pixel, em..)
	 * @param position the position in the parent container
	 * @return an instance of of <code>RuleMark</code>
	 */
   public RuleMark getRuleMark(String type,int count,String size,String position) {
	  if ( ruleMark == null) {
		 ruleMark = new RuleMark(type,count,size,position);
		 RootPanel.get().add(ruleMark);	  
		  
	  }
	  return ruleMark;
   }

   public void tearDown() {
	   ruleMark = null;
   }

   /**
    * Tests <code>RuleMark</code> with VERTICAL type
    *
    */
   public void testVertical() {
	   ruleMark = getRuleMark(RuleMark.VERTICAL,5,"3px","left");
	   assertEquals(5,ruleMark.getCount());
	   assertEquals("3px",ruleMark.getSize());
	   assertEquals(RuleMark.VERTICAL,ruleMark.getType());
	   assertEquals("left",ruleMark.getPosition());
	   
   }
   
   /**
    * Tests <code>RuleMark</code> with HORIZONTAL type
    *
    */
   public void testHorizontal() {
	   ruleMark = getRuleMark(RuleMark.HORIZONTAL,5,"3px","null");
	   assertEquals(5,ruleMark.getCount());
	   assertEquals("3px",ruleMark.getSize());
	   assertEquals(RuleMark.HORIZONTAL,ruleMark.getType());
	   assertEquals("null",ruleMark.getPosition());
	   
   }
}//end of class
