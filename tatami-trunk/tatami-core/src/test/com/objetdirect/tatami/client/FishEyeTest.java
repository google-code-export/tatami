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

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootPanel;
import com.objetdirect.tatami.client.test.Task;
import com.objetdirect.tatami.client.test.TestUtil;

public class FishEyeTest extends DefaultTatamiTest {

	private FishEye fisheye;

	private boolean clicked = false;

	public static final String defaultIcon = "browser.png";
	public static final String backgroundIcon = "background.png";
	
   /**
    * Tests the adding and removing of items on the 
    * FishEye component.
    *
    */
	public void testAddItem() {
		fisheye = getFishEye();
	
		final Command command = new Command() {
			public void execute() {
				assertTrue("",1+1==2); 
			}
		};
	
		final int preCountItem = fisheye.countItems();
		fisheye.add(backgroundIcon, "caption", command);
		assertTrue("The count is not good",preCountItem + 1 == fisheye.countItems());
		assertEquals("This icon is not expected",backgroundIcon, fisheye.getIcon(fisheye.countItems() - 1));
		assertEquals("This command is not expected",command, fisheye.getCommand(backgroundIcon));
		fisheye.remove(backgroundIcon);
		assertTrue("The count is not good",preCountItem == fisheye.countItems());
	}

	
	
	/**
	 * Tests if we can click on the item and the associated command with 
	 * the item is executed.
	 *
	 */
	public void testClickOnItem() {
		fisheye = getFishEye();
		
		final Task task = new Task() {
			public void run() {
                
				assertTrue("No click event",isClicked());
			}
		};

		final Timer timer = new Timer() {
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
		fisheye = getFishEye();
		
		
	

		for (int i = 0; i < 10; i++) {
			fisheye.add(backgroundIcon, "caption", null);
		}
		
		final String[] icons = fisheye.getIcons();
		assertEquals("The size is not good",fisheye.countItems(),icons.length);
		assertEquals("This is not the expected icon",defaultIcon, fisheye.getIcon(0));
		assertNull("The command should not be null",fisheye.getCommand(backgroundIcon));
		final int index = fisheye.indexOf(backgroundIcon);
		assertNull("The command should not be null",fisheye.getCommand(index));
		for (int i = 0; i < 10; i++) {
			fisheye.remove(backgroundIcon);
		}
	}

	
	/**
	 * 
	 * @return
	 */
	private FishEye getFishEye() {
		if (fisheye == null) {
			fisheye = new FishEye();
			
		}
		fisheye.add(defaultIcon, "titre", new Command() {
			public void execute() {
			  //  System.out.println("Command well executed");
				clicked = true;
			}
		});
		RootPanel.get().add(fisheye, 10, 10);
		return fisheye;
	}
	
		
	public void gwtTearDown() {
		this.fisheye = null;
		this.clicked = false;
	}
	
	
	 /**
	 * Permits to test is a mouse click was thrown.
	 * @return true if a click was done false otherwise.
	 */
	private boolean isClicked() {
		return this.clicked;
	}
	

}//
