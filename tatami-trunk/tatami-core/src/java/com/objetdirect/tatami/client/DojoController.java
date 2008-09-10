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
 * Authors: Vianney Grassaud
 * Initial developer(s):
 * Contributor(s):
 */
package com.objetdirect.tatami.client;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;

/**
 * This class is a sungleton and permits to control some Dojo features. It controls the loading of
 * the scripts JavaScript those are necessaries for the Dojo widgets, ie avoids
 * to load several times the same scripts. It permits also to link or unlink the dojo widget with the GWT widget
 * and finally permits to scedule a creation of the Dojo widget to avoit some memory leak.
 *
 *
 * @author Vianney
 */
public class DojoController {

	/** singleton DojoController*/
	private static DojoController controller;

	/**

	 * The map of the declared Dojo widgets. This map  permits to know
	 * what are the script JavaScript which are loaded in the browser.

	 */
	private Map mapWidget;

	/**
	 * Creates the controller.
	 **/
	private DojoController() {
		mapWidget = new HashMap();
	}

	/**
	 * Returns the instance of the singleton <code>DojoController</code>
	 * @return the uniq instance
	 */
	public static DojoController getInstance() {
		if (controller == null) {
			controller = new DojoController();
		}
		return controller;
	}

	/**
	 * Loads the scripts Javascripts for the DOJO widget.
	 * @param widgetDojo the package to import in the browser for the DOJO widget.
     * @return true if the loading of the script was necessary, false if the script was already loaded.
     * @throws IllegalArgumentException if the param widgetDojo is not a package for the version of Dojo.
	 */
	public boolean require(String widgetDojo) {
		boolean loaded = mapWidget.containsKey(widgetDojo);
		if (!loaded) {
			boolean ok = requireDojo(widgetDojo);
			if (!ok) {
				throw new IllegalArgumentException("bad widget : " + widgetDojo);
			}
			mapWidget.put(widgetDojo, Boolean.TRUE);
		}
		return !loaded;
	}

	/**
	 * Destroy the Dojo widget.
	 * @param dojoWidget the DOJO widget to destroy.
	 *
	 */
	public native void destroy(JavaScriptObject dojoWidget)
	/*-{
	   // try {
	     dojoWidget.destroyRecursive();
       // } catch (e) {
	     // $wnd.alert("error " + e.toString());
	    // }
	 }-*/;

	/**
	 *
	 * Loads the scritps JavaScript for the Dojo widget.
	 * @param widget the widget to import example : dojo.widget.Clock
	 *
	 */
	private native boolean requireDojo(String widget)
	/*-{
	 var ok = true;
	 try {

	    $wnd.dojo.require(widget);
     } catch (e) {
	   ok =false;
	 }

     return ok;




	 }-*/;

	/**
	 *
	 * Loads if it is necessary the scripts JavaScript DOJO for a component implementing the interface
	 * <code>Hasdojo</code>. This method calls also <code>HasDojo#onDojoLoad()</code> if the scripts was loaded.
     * @param widget the widget implementing HasDojo interface.
	 *
	 */
	public void loadDojoWidget(HasDojo widget) {
		try {
			if (require(widget.getDojoName())) {
				widget.onDojoLoad();
			}
		} catch (IllegalArgumentException e) {
			GWT.log("ERROR", e);
			throw e;
		}
	}

	/**
	 * Creates a dojo widget from an object implementing the  interface <code>HasDojo</code>.
	 * Links also the dojo Widget with GTW widget.
	 * @param widget the widget implementing the HasDojo interface.
	 * @param gwtWidget the GWT widget to link with the Dojo widget.
	 * @see #link(JavaScriptObject, Widget)
	 */
	public void constructDojoWidget(HasDojo widget, Widget gwtWidget) {
		if (widget.getDojoWidget() == null) {
			try {
				widget.createDojoWidget();
				link(widget.getDojoWidget(), gwtWidget);
				widget.doAfterCreation();
			} catch (Exception e) {
				GWT.log("ERROR", e);
			}
		}
	}

	/**
	 * Removes a link between a GWT widget and a DOJO widget. The DOJO widget
	 * will be also removed from the GWT widget.
	 * @param widget the widget containing the DOJO widget.
	 * @param gwtWidget the gwt widget linked with the DOJO widget.
	 */
	public void destroyDojoWidget(HasDojo widget, Widget gwtWidget) {
		JavaScriptObject dojoWidget = widget.getDojoWidget();
		widget.doBeforeDestruction();
 		unLink(dojoWidget, gwtWidget);
		destroy(dojoWidget);
		widget.free();
	}

	/**

	 * Creates the link between a DOJO widget et the GWT widget. Adds to DOM element of the GWT widget, the DOM element
	 * of the DOJO widget.
	 * @param dojoWidget the DOJO widget to link
	 * @param gwtWidget the GWT widget to link
	 */
	public void link(JavaScriptObject dojoWidget, Widget gwtWidget) {
		if (dojoWidget != null) {
			DOM.appendChild(gwtWidget.getElement(), getDomNodeDojo(dojoWidget));
			setGWTWidget(dojoWidget, gwtWidget);
		}
	}

	/**

	 * Removes a link between a GWT widget and a DOJO widget. The DOJO widget
	 * will be also removed from the GWT widget.
	 * @param dojoWidget the DOJO widget.
	 * @param gwtWidget the gwt widget linked with the DOJO widget.
	 */
	public void unLink(JavaScriptObject dojoWidget, Widget gwtWidget) {
		setGWTWidget(dojoWidget, null);
        Element domNode = getDomNodeDojo(dojoWidget);
        if ( domNode != null) {
		 DOM.removeChild(gwtWidget.getElement(), domNode);
        }
	}

	/**

	 * Returns the DOM element that correponding to the DOJO widget.
	 * @param dojoWidget the DOJO widget.
	 * @return the DOM element of the given DOJO widget
	 *
	 */
	private native Element getDomNodeDojo(JavaScriptObject dojoWidget)
	/*-{
	    var element = null;
   	    if ( dojoWidget.domNode != "undefined" && dojoWidget.domNode != null ){
   	       element= dojoWidget.domNode;
   	    }
   	    return element;
	 }-*/;


	/**
	 * see {@link #startup(HasDojo)}
	 * @param dojoWidget the DOJO widget
	 */
	static private native void startup(JavaScriptObject dojoWidget) /*-{
		dojoWidget.startup();
	}-*/;

	/**
	 * Calls the startup method on a widget which contains a DOJO widget
	 * @param widget a widget wrapping a DOJO widget
	 */
	static public void startup(HasDojo widget) {
		startup(widget.getDojoWidget());
	}


	/**

	 * Returns the DOM element that correponding to the DOJO widget.
	 * @param widget a widget containing a DOJO widget.
	 * @return the DOM element fulfiling the given widget
	 *
	 */
	public Element getDomNode(HasDojo widget) {
		return getDomNodeDojo(widget.getDojoWidget());
	}


	/**

	 * Creates or deletes the link between the DOJO widget and the GWT widget.
     * @param dojoWidget the DOJO widget.
     * @para  gwtWidget the GWT widget.
	 *
	 * @see #link(JavaScriptObject, Widget)
	 */
	public native void setGWTWidget(JavaScriptObject dojoWidget,Widget gwtWidget)
	/*-{
        try {
        dojoWidget.gwtWidget = gwtWidget;
        } catch(e) {
        }
	 }-*/;

	/**

	 * Creates a JavaScript alert
	 * @param object the message to display in the alert.
	 * NB: if you want to debug, use the <code>GWT.log(String, Throwable)</code> method instead

	 */
	static public native void alert(String object)
	/*-{
	     $wnd.alert(object);
	 }-*/;

	/**
	 * Creates a JavaScrtip Array of String object from a Java String array.
	 * @param array the Java array
	 * @return the JavaScript array corresponding to the given Java array
	 */
     static public  JavaScriptObject createArray(String[] array) {
    	 JavaScriptObject jsArray = JavaScriptObject.createArray();
    	 for ( int i=0; i < array.length;i++) {
    		 jsArray = put(jsArray,i,array[i]);
    	 }
    	 return jsArray;
     }

     /**
      * Puts a string at the specified index in a JavaScript array
      * @param array the JavaScript array
      * @param index the index to put the value
      * @param value the string value to put at the specified index
      * @return the JavaScript array
      */
     static private native JavaScriptObject put(JavaScriptObject array,int index, String value)/*-{
         array[index] = value;
         return array;
     }-*/;

     /**
      * Removes a <code>JavaScriptObject</code> from the DOJO widget
      * @param widget the GWT widget containing the DOJO widget
      * @param child the <code>JavaScriptObject</code> to remove
      */
     public void removeChild(HasDojo widget,JavaScriptObject child) {
    	 removeChild(widget.getDojoWidget(),child);
     }

     /**
      * Adds a <code>JavaScriptObject</code> as a child for the DOJO widget
      * @param widget the GWT widget containing the DOJO widget
      * @param child the <code>JavaScriptObject</code> to add
      */
     public void addChild(HasDojo widget,JavaScriptObject child) {
    	 addChild(widget.getDojoWidget(),child);
     }

     /*
      * @see #removeChild(HasDojo,JavaScriptObject)
      */
     private native void removeChild(JavaScriptObject dojoWidget, JavaScriptObject child)/*-{
        dojoWidget.removeChild(child);
     }-*/;

     /*
      * @see #addChild(HasDojo,JavaScriptObject)
      */
      private native void addChild(JavaScriptObject dojoWidget, JavaScriptObject child) /*-{
        dojoWidget.addChild(child);
	    dojoWidget.startup();
      }-*/;


}//end of class
