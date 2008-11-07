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
 * Authors:  Ronan Dunklau
 * Initial developer(s): Ronan Dunklau
 * Contributor(s):
 */
package com.objetdirect.tatami.testpages.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class TestMainPage implements EntryPoint {

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
	  addTestPage(new TestSpinnerPage());
	  addTestPage(new TestSimpleGridPage());
	  addTestPage(new TestMoreComplexGridPage());
	  addTestPage(new TestLayoutGridPage());
	  addTestPage(new TestPourDoc());
	  addTestPage(new TestTreePage());
	  addTestPage(new TestDnDEventsPage());
	  addTestPage(new TestDNDPage());
	  addTestPage(new TestDnDToTreePage());
	  addTestPage(new TestGridRowStylerBugPage());
	  addTestPage(new TestFillingEmtyGridPage());
	  addTestPage(new TestChartPage());
	  addTestPage(new TestChartUpdatingPage());
	  addTestPage(new TestPieChartPage());
	  addTestPage(new TestChartEffectPage());
	  addTestPage(new TestChartLabelsPage());
	  addTestPage(new TestChartCustomEffectPage());
	  addTestPage(new TestChartAxes());
  }
  
  public void addTestPage(TestPage testPage){
	  Button button = new DisplayTestPageButton(testPage);
	  RootPanel.get().add(button);
  }
  
  public void loadTestPage(TestPage page){
	  RootPanel.get().clear();
	  RootPanel.get().setStyleName("tundra");
	  RootPanel.get().add(page.getTestPage());
  }
  
  private class DisplayTestPageButton extends Button{
	  final TestPage pageToLoad;
	  DisplayTestPageButton(TestPage page){
		  super(page.TESTPAGE_LABEL);
		  this.pageToLoad = page;
		  DOM.setElementAttribute(this.getElement() , "id" , page.TESTPAGE_ID);
		  this.addClickListener(new ClickListener(){
			  public void onClick(Widget sender) {
				  	((Button) sender).setText("CLCIKED");
					loadTestPage(pageToLoad);			
				}
		  });
	  }
  }
  
}
