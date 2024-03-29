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
package com.objetdirect.tatami.testpages.client.widgets;

import com.google.gwt.core.client.EntryPoint;
import com.objetdirect.tatami.testpages.client.TestMainPage;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class TestWidgetsPage extends TestMainPage implements EntryPoint  {

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
	  addTestPage(new TestDNDEventsPage());
	  addTestPage(new TestDNDPage());
	  addTestPage(new TestDNDToTreePage());
	  addTestPage(new TestFillingEmptyGridPage());
	  addTestPage(new TestGridRowStylerBugPage());
	  addTestPage(new TestGridSelectionManagerPage());
	  addTestPage(new TestLayoutGridPage());
	  addTestPage(new TestMoreComplexGridPage());
	  addTestPage(new TestSimpleGridPage());
	  addTestPage(new TestSpinnerPage());
	  addTestPage(new TestTreePage());
	  addTestPage(new TestChangeDataStorePage());
	  addTestPage(new TestDNDSimplestPage());
	  addTestPage(new TestBorderContainerPage());
	  addTestPage(new TestAddingSameItemToGridPage());
	  addTestPage(new TestMD5Page());
  }
  
  
}
