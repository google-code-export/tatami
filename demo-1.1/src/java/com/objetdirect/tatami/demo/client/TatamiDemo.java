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

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.Clock;
import com.objetdirect.tatami.client.FishEye;
import com.objetdirect.tatami.client.Toaster;

/**
 * Entry point classes define <code>onModuleLoad()</code>.  	
 * Demo of Tatami, the demo consists in showing some new widget GWT inserting wigdet Dojo. 
 * For it a particular menu that is with icons cliquables and putting on weight near the mouse, 
 * this menu will be the widget FishEye. An element which made the glory of Dojo a bit and "very " impressive.
 * Every item of the menu is therefore going to serve for introducing categories of widgets GWT as selectors 
 * of dates, clock etc.. or then widgets simply as Toggler makes windows, to slider... 
 * In every new chosen item a notification will be made, another new object GWT which offers Tatami!!
 * 
 * @author Henri Darmet, Vianney Grassaud
 */

public class TatamiDemo implements EntryPoint {

	/* the Tatami components  */
	private FishEye fishEye;
	private Toaster toaster;
    
	/*-- widget GWT --*/
	private DockPanel mainPanel;
	private DockPanel root; 

	/* for the menu */
	private VerticalPanel menuPanel;
	
	private DockPanel titlePanel;
	
	private HTML labelMenu;
	

	/*-- other --*/
	private int page = 0;

	//the different page 
	private final int GFX  = 1;
	private final int SLIDERS = 3;
	private final int DATE_TIME = 2;
    private final int DRAG_N_DROP = 6;
	private final int COLOR = 7;

	
	

	/**
	 * Loads the demo module
	 * The module aims to present the components of Tatami. 
	 * each class or component (it depends) are demonstrated by clicking on the FishEye menu a component of Tatami.
	 * The panel of TatamiDemo is split like this : 
	 * <pre> 
	 *  ----------------
	 *  | Title        |
	 *  |--------------
	 *  |menu |        |
	 *  |     | main   |
	 *  |     |        |
	 *  |     |        |
	 *  ----------------
	 * </pre>
	 */
	public void onModuleLoad() {
		DockPanel body = new DockPanel();
		
		root = new DockPanel();
	    
	    mainPanel = new DockPanel();
	    mainPanel.setStyleName("TatamiDemo-mainPanel");
	    toaster = new Toaster("message",Toaster.BOTTOM_LEFT_UP);
	    	    
	    body.add(root,DockPanel.CENTER);
	       
	    //create and init the menuPanel
	    initMenuPanel();
	    //creates the title panel
	    initTitlePanel();
	    
	 
	    root.add(mainPanel,DockPanel.CENTER);
	    root.setCellWidth(mainPanel,"100%");
	    root.add(menuPanel,DockPanel.WEST);
	    root.setSpacing(20);
	  
	    body.add(titlePanel,DockPanel.NORTH);
	    body.add(root,DockPanel.CENTER);
			   
	    RootPanel.get().add(body);
	    RootPanel.get().add(toaster);
	    //the first page
	    setPage(0);
	   	    
	}

	/**
	 * Inits the title panel for the Demo. 
	 * Shows two logos ObjetDirect and France Telecom. 
	 * The title is between these 2 logos. 
	 */
	private void initTitlePanel() {
		titlePanel = new DockPanel();
		Image logoOD = new Image("od-logo.gif");
		logoOD.setStyleName("TatamiDemo-logoOD");
		logoOD.setPixelSize(126, 64);
		titlePanel.add(logoOD,DockPanel.WEST);
		HTML title = new HTML("Tatami version 1.1 (DOJO 1.0 wrapped)");
		title.setStyleName("TatamiDemo-title");
		Image logoFT = new Image("logo_ft.gif");
		logoFT.setStyleName("TatamiDemo-logoFT");
		titlePanel.add(title,DockPanel.CENTER);
		titlePanel.setCellWidth(title, "100%");
		titlePanel.add(logoFT,DockPanel.EAST);
	}
	
	
	/**
	 * Creates a menu with a FishEye widget.
	 * Each item of the menu present a demo of a component or a category of components, 
	 * like Date- time, gfx, slider, color tools 
	 * 
	 */
	private void initMenuPanel() {
	   menuPanel = new VerticalPanel();
	   fishEye = new FishEye(50, 50, 200, 200, FishEye.VERTICAL, 2, 10, FishEye.CENTER, FishEye.RIGHT, false);
	    

	   labelMenu = new HTML("Menu");
	   labelMenu.setTitle("Go Home");
	   labelMenu.setStyleName("TatamiDemo-labelMenu");
	   DOM.setStyleAttribute(labelMenu.getElement(),"cursor","pointer");
	   labelMenu.addClickListener(new ClickListener() {
		  public void onClick(Widget sender) {
			  setPage(0);
		  }
	   });
	   
	   Clock clock = new Clock(null,77);
	   
	   menuPanel.setStyleName("TatamiDemo-menu");
	   menuPanel.setSpacing(20);
  
	   menuPanel.add(clock);
	   menuPanel.add(labelMenu);
	   menuPanel.add(fishEye);
 	   
	   addItem("browser.png", SLIDERS, "sliders");
	   addItem("kalarm.png", DATE_TIME, "date-time");
   	   addItem("icoColorPic.gif", COLOR, "color tools");
	   addItem("amor.png", DRAG_N_DROP, "drap'n'drop");
	   addItem("blackboard.png",GFX, "draw with GFX");
	  
	}
	
	
	
	/**
	 * Creates the message for the Toggler component
	 * @param icon icon to insert in the message
	 * @return The HTML code corresponding to a message
	 */
	private String getMessage(String icon) {
		return "<table><tr><td>You have selected : </td><td><img src=\""
				+ icon + "\"></td></tr></table>";
	}

	/**
	 * Adds a item to the FishEye menu.
	 * The command for each item is the demo of a component or a class of components from Tatami
	 * @param icon icon to represent for the item.
	 * @param page the number of the demo page to present when the command is invoked
	 * @param title title for the notification (displayed by the Toggler component)
	 */
	private void addItem(String icon, int page, String title) {
		this.fishEye.add(icon, title, new DemoCommand(icon, page));
	}

	/**
	 * Unloads the content of page 
	 */
	private void unLoadPage() {
		mainPanel.clear();
		
	}

	
	/**
	 * Returns the number of the curent demo page
     * @return the number of the current page
	 */
	private int getPage() {
		return this.page;
	}

	/**
	 * Loads and displays on the screen the current demo page
	 * 
	 */
	private void loadPage() {
		
		Widget widgetDemo = null;
		switch (getPage()) {
		default: {
			widgetDemo = new HTML();
			widgetDemo.setStyleName("TatamiDemo-welcome");
			((HTML)widgetDemo).setHTML("<p>The project aims to integrate the Google Web Toolkit (GWT) and the DOJO framework. Indeed the DOJO framework is very rich in term of widgets and utilities (fisheye, slider, drag and drop functionality) and the main interest is to take benefits of the huge work which has been already done by the DOJO community. In other words, it means: <b>the DOJO widgets become GWT widgets, the DOJO utilities become GWT helper.</b></p><br><p> The project is on the Google code community : <a href=\"http://code.google.com/p/tatami\">Tatami</a></p><p> Click on an item of the menu at the left to see the widgets that Tatami proposes.</p>");
			break;
		}
		case SLIDERS: {
			widgetDemo = new SliderDemo();
			break;
		}
		case DATE_TIME: {
			widgetDemo = new DateTimeDemo();
			break;
		}
		case DRAG_N_DROP: {
			widgetDemo = new DragAndDropDemo();
			break;
		}
		case COLOR: {
			widgetDemo = new ColorDemo();
			break;
		}
		
		
		case GFX: {
			widgetDemo = new GfxDemo();
			break;
		}
		
		
		}
		if ( widgetDemo != null) {
		   mainPanel.add(widgetDemo,DockPanel.CENTER);
		}
	
	}

	
	
	/**
	 * Sets a demo page to load and display 
	 * @param page the number of the page to display
	 */
	private void setPage(int page) {
		this.unLoadPage();
		this.page = page;
		this.loadPage();

	}


	
	/**
	 * Class to represent a command for each item of the menu FishEye
	 * @author Vianney
	 * 
	 */
	private class DemoCommand implements Command {
		private String icon = "";

		private int page = 0;

		/**
		 * Creates a command for an item of the menu FishEye (the main component of the demo).
		 * the command aims to change a "page" to disaplay on the screencf <code>setPage(int)</code>
		 * @param icon icon for a notification that the command is selected.
		 * @param page number of the page to display.
		 */
		public DemoCommand(String icon, int page) {
			this.icon = icon;
			this.page = page;
		}

		/**
		 * Executes the command
		 */
		public void execute() {
        	Toaster.publishMessage("message", getMessage(icon));
			setPage(page);
			
		}
	}
  


}//end of class
