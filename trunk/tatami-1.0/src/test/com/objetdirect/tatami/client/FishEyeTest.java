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

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Timer;
import com.objetdirect.tatami.client.test.Task;
import com.objetdirect.tatami.client.test.TestUtil;

public class FishEyeTest extends DefaultTatamiTest {

	
   /**
    * Tests the adding and removing of items on the 
    * FishEye component.
    *
    */
	public void testAddItem() {
		FishEyeModule module = new FishEyeModule();
		module.onModuleLoad();

		FishEye fishEye = module.getFishEye();

		
		String newIcon = "background.png";
		Command command = new Command() {
			public void execute() {
				; // do nothing;
			}
		};
	
		int preCountItem = fishEye.countItems();
		fishEye.add(newIcon, "caption", command);

		assertTrue(preCountItem + 1 == fishEye.countItems());

		assertEquals(newIcon, fishEye.getIcon(fishEye.countItems() - 1));

		assertEquals(command, fishEye.getCommand(newIcon));

		fishEye.remove(newIcon);

		assertTrue(preCountItem == fishEye.countItems());
	}

	
	
	/**
	 * Tests if we can click on the item and the associated command with 
	 * the item is executed.
	 *
	 */
	public void testClickOnItem() {
		final FishEyeModule module = new FishEyeModule();
	
		module.onModuleLoad();
		
		final Task task = new Task() {
			public void run() {
                
				assertTrue(module.isClicked());
			}
		};

		Timer timer = new Timer() {
			public void run(){
 			    TestUtil.fireEvent("click", 35,35,TestUtil.MOUSE_BUTTON_1,1,task);
				finishTest();
			}
		};
		
		this.delayTestFinish(150);
		timer.schedule(100);
		}
	
	
	
	/**
	 * Tests the <code>getIcon()</code> and <code>getCommande()</code> of the FishEye.
	 * 
	 */
	public void testGetIconAndCommand() {
		FishEyeModule module = new FishEyeModule();
		module.onModuleLoad();

		FishEye fishEye = module.getFishEye();
		String newIcon = "background.png";

		for (int i = 0; i < 10; i++) {
			fishEye.add(newIcon, "caption", null);
		}
		int size = fishEye.countItems();
		String[] icons = fishEye.getIcons();
		assertTrue(size == icons.length);
		assertEquals(FishEyeModule.defaultIcon, fishEye.getIcon(0));
		assertNull(fishEye.getCommand(newIcon));
		int index = fishEye.indexOf(newIcon);
		assertNull(fishEye.getCommand(index));
		for (int i = 0; i < 10; i++) {
			fishEye.remove(newIcon);
		}
	}

	
	

}//
