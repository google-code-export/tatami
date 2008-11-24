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
public abstract class TestMainPage implements EntryPoint {

  /**
   * This is the entry point method.
   */
  
  public void addTestPage(TestPage testPage){
	  Button button = new DisplayTestPageButton(testPage);
	  RootPanel.get().add(button);
  }
  
  public void loadTestPage(TestPage page){
	  RootPanel.get().clear();
	  RootPanel.get().setStyleName("tundra");
	  RootPanel.get().add(page.getTestPage());
	  RootPanel.get().add(new Button("Back",new ClickListener() {
		public void onClick(Widget sender) {
			RootPanel.get().clear();
			onModuleLoad();
		}
	}));
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
