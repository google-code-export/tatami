/*
 *
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
 * Authors:
 * Initial developer(s):
 * Contributor(s):
 */
package com.objetdirect.tatami.theme.client;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Helper class for tatami. 
 * With this class you can define the theme to use. 
 * Note that you need to specify in your module 
 * descriptor file the themes which will be inherited in order to load the css files. 
 * 
 * @author vgrassaud
 *
 */
public class Theme {

   /**
    * Use this constant for the theme soria
    */
	static public final String SORIA_THEME = "soria";
	
	/**
	 * Use this constant for the theme tundra
	 */
	static public final String TUNDRA_THEME = "tundra";
  
   /**
	* Use this constant for the theme nihilo
	*/
	static public final String NIHILO_THEME = "nihilo";
	
	/**
	 * Applies a Tatami theme to the module. 
	 * @param theme the name of the theme to apply. use <code>SORIA_THEME | TUNDRA_THEME | NIHILO_THEME </code>
	 * @param erase <code>true</code> to only apply the given theme, if a theme is already use it will be lost
	 */
	public static void applyTheme(String theme, boolean erase) {
		Element body = RootPanel.getBodyElement();
		String classValue = body.getClassName();
		if ( (classValue == null) || !classValue.contains(theme)) {
			
			if ( erase) {
				body.setClassName(theme);
			} else {
				body.setClassName(classValue + " " + theme);
			}
			
		}
	}
	
	/**
	 * Applies the default theme of Tatami which is the Tundra theme. 
	 */
	public static void applyDefaultTheme() {
		applyTheme(TUNDRA_THEME,false);
	}
	

	
}//end of class
