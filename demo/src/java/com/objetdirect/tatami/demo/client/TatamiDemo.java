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
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.RootPanel;
import com.objetdirect.tatami.client.Tatami;
import com.objetdirect.tatami.demo.client.resources.TatamiMessages;
import com.objetdirect.tatamix.client.hmvc.MVCImpl;

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


	private final boolean DEBUG_ON = false;
	
	
	private static TatamiMessages messages = null;

	/**
	 *
	 * @return
	 */
	public static TatamiMessages getMessages() {
		if ( messages == null) {
			messages =GWT.create(TatamiMessages.class);
		}
		return messages;
	}


	public static String getIconURL(String iconName) {
		return GWT.getModuleBaseURL() + "images/" + iconName;
	}



	public void onModuleLoad() {
        
		Tatami.applyDefaultTheme();
		Tatami.applyTheme(Tatami.NIHILO_THEME,false);
		MainController controller = new MainController();
		MainView view = new MainView();
		MVCImpl.createTriad(null,view,controller);
	   
		RootPanel.get().add(view);
	
		controller.init(DEBUG_ON);
		
		
		
	   	hideLoading();
	}



	 /**
     * Hides the loading message in the hosted page
     *
     */
	private void hideLoading() {
		final Element loading = DOM.getElementById("loading-shadow");
		if (loading != null) {
			DOM.removeChild(RootPanel.getBodyElement(),loading);

		}
	}


}//end of class
