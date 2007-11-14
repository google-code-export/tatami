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
 * Authors: Henri Darmet, Vianney Grassaud
 * Initial developer(s):
 * Contributor(s):
 */
package com.objetdirect.tatami.client;

import java.util.ArrayList;

import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;

/**
 * Menu similar to the fish eye menu on the Mac OS.
 * 
 * Each item of the fish eye menu is clickable. 
 * A command (GWT) can be associated to each icons and will be invoked
 * each times that an item will be selected
 * 
 *  <p>
 * This component is an encapsulation GWT of Dojo components. Warning the widget
 * exist under 3 faces :
 * <ul>
 * <li>a GWT widget (this!)</li>
 * <li>a Dojo widget (which play the role of 'this' for GWT)</li>
 * <li>a structur of DOM elements, the only exploitable presentation by the
 * navigator.</li>
 * </ul>
 * </p>
 * <p>
 * 
 * @author Henry, Vianney
 * 
 * @version 1.1  the method resetLocation was added. 
 */
public class FishEye extends AbstractDojo {

	/**
	 * Horizontal orientation for the menu
	 */
	static public final String HORIZONTAL = "horizontal";
	
	/**
	 * Vertical orientation for the menu
	 */
	static  public final String VERTICAL = "vertical";
	
	/**
	 * Center position for tooltip, attachment 
	 */
	static  public final String CENTER ="center";
	
	/**
	 * Bottom position for tooltip, attachment
	 */
	static  public final String BOTTOM = "bottom";
	
	/**
	 * Top position for tooltip, attachment
	 */
	static  public final String TOP = "top";

	/**
	 * Left position for tooltip, attachment
	 */
	
	static  public final String LEFT = "left";
	/**
	 * Right position for tooltip, attachment
	 */
	static  public final String RIGHT = "right";
	
	
	/** width of menu item (in pixels) in it's dormant state (when the mouse is far away) */
	private int itemWidth = 50;

	/** height of menu item (in pixels) in it's dormant state (when the mouse is far away) */
	private int itemHeight = 50;

	/**
	 * width of menu item (in pixels) in it's fully enlarged state (when the mouse is directly over it)
	 */
	private int itemMaxWidth = 200;

	/**
	 * height of menu item (in pixels) in it's fully enlarged state (when the mouse is directly over it)
	 */
	private int itemMaxHeight = 200;

	/** orientation de la barre d'icone (horizontale où verticale). */
	private String orientation = HORIZONTAL;

	/** controls how much reaction the menu makes, relative to the distance of the mouse from the menu */
	private int effectUnits = 2;

	/** padding (in pixels) betweeen each menu item*/
	private int itemPadding = 10;

	/** controls the border that the menu items don't expand past;
		for example, if set to "top", then the menu items will drop downwards as they expand. 
	*/
	private String attachEdge = CENTER;

	/** controls were the labels show up in relation to the menu item icons */
	private String labelEdge = BOTTOM;

	/**
	 *  for browsers that support svg, use the svg image (specified in FisheyeListIem.svgSrc)
		rather than the iconSrc image attribute
	 */
	private final boolean enableCrappySvgSupport = false;

	/** if true, don't start enlarging menu items until mouse is over an image;
	    if false, start enlarging menu items as the mouse moves near them. 
	 */
	private boolean conservativeTrigger = true;

	/**
	 * Horizontal position of the widget when the last update of the DOJO widget 
	 * had been made to catch the mouse events 
	 */
	private int x = 0;

	/**
	 * Vertical position of the widget when the last update of the DOJO widget 
	 * had been made to catch the mouse events 
	 */
	private int y = 0;

	/**
	 * List of the items <code>Item</code> of the FishEye menu
	 */
	private List items = new ArrayList();

	/**
	 * Creates a FishEye menu with no icons
	 * 
	 * @param itemWidth           width of menu item (in pixels) in it's dormant state (when the mouse is far away)
	 * @param itemHeight          height of menu item (in pixels) in it's dormant state (when the mouse is far away)
	 * @param itemMaxWidth        width of menu item (in pixels) in it's fully enlarged state (when the mouse is directly over it)
	 * @param itemMaxHeight       height of menu item (in pixels) in it's fully enlarged state (when the mouse is directly over it)
	 * @param orientation         orientation of the menu, either HORIZONTAL or VERTICAL
	 * @param effectUnits         controls how much reaction the menu makes, relative to the distance of the mouse from the menu 
	 * @param itemPadding         padding (in pixels) betweeen each menu item
	 * @param attachEdge          controls the border that the menu items don't expand past;
		                          for example, if set to "top", then the menu items will drop downwards as they expand. 
		                          Possible values are : CENTER, TOP, BOTTOM, LEFT, RIGHT
	 * @param labelEdge           controls were the labels show up in relation to the menu item icons.  Possible values are : CENTER, TOP, BOTTOM, LEFT, RIGHT
	 * @param conservativeTrigger if true, don't start enlarging menu items until mouse is over an image;
	                              if false, start enlarging menu items as the mouse moves near them. 
	 */
	public FishEye(int itemWidth, int itemHeight, int itemMaxWidth,
			int itemMaxHeight, String orientation, int effectUnits,
			int itemPadding, String attachEdge, String labelEdge,
			boolean conservativeTrigger) {
		super();
		this.itemWidth = itemWidth;
		this.itemHeight = itemHeight;
		this.itemMaxWidth = itemMaxWidth;
		this.itemMaxHeight = itemMaxHeight;
		this.orientation = orientation;
		this.effectUnits = effectUnits;
		this.itemPadding = itemPadding;
		this.attachEdge = attachEdge;
		this.labelEdge = labelEdge;
		// this.enableCrappySvgSupport = enableCrappySvgSupport;
		this.conservativeTrigger = conservativeTrigger;

		DOM.sinkEvents(getElement(), Event.ONMOUSEOVER);
		DOM.setEventListener(getElement(), this);

	}

	/**
	 * Creates a FishEye menu with some specific default values.
	 * @param itemWidth           width of menu item (in pixels) in it's dormant state (when the mouse is far away)
	 * @param itemHeight          height of menu item (in pixels) in it's dormant state (when the mouse is far away)
	 * @param itemMaxWidth        width of menu item (in pixels) in it's fully enlarged state (when the mouse is directly over it)
	 * @param itemMaxHeight       height of menu item (in pixels) in it's fully enlarged state (when the mouse is directly over it)
	 * @param orientation         orientation of the menu, either HORIZONTAL or VERTICAL
	 */
	public FishEye(int itemWidth, int itemHeight, int itemMaxWidth,
			int itemMaxHeight, String orientation) {
		this(itemWidth, itemHeight, itemMaxWidth, itemMaxHeight, orientation,
				2, 10, CENTER, BOTTOM, false);
	}

	/**
	 *  Creates a FishEye with a horizontal orientation and with default values.
	 */
	public FishEye() {
		this(50, 50, 200, 200, HORIZONTAL, 2, 10, CENTER, BOTTOM, false);
	}

	/**
	 * Overrides the method in order to catch if the mouse cross over the widget.
	 * In this case, the event mouse structure of the DOJO widget is updated if the widget was moved.
	 * afin de réagir si la souris passe au dessus de la widget : on mêt à jour
	 * @param event event from the Browser
	 */
	public void onBrowserEvent(Event event) {
		if (DOM.eventGetType(event) == Event.ONMOUSEOVER) {
			if (getAbsoluteLeft() != x || getAbsoluteTop() != y) {
				x = getAbsoluteLeft();
				y = getAbsoluteTop();
				resetLocation(getDojoWidget());
			}
		}
		// by default the method onBrowserEvent does nothing
		super.onBrowserEvent(event);
	}

	/**
	 * Creates the DOJO widget FishEyeList
	 * @throws Exception
	 */
	public void createDojoWidget() throws Exception {
		this.dojoWidget=  createFishEye(itemWidth, itemHeight, itemMaxWidth,
				itemMaxHeight, orientation, effectUnits, itemPadding,
				attachEdge, labelEdge, enableCrappySvgSupport,
				conservativeTrigger);
	}

	/**
	 * Adds an item to the FishEye menu
	 * @param icon  URL of the icon display on the screen. The icon has to be unique for the menu
	 * @param caption the title for the item.
	 * @param command the command will be invoke when the icon is selected.  (can be <code>null</code>)
	 */
	public void add(String icon, String caption, Command command) {
		Item item = new Item(icon, caption, command);
		items.add(item);
		if (isAttached()) { // if the widget is attached to the browser 
			// we construct the new item
			buildItem(item);
		}
	}

	/**
	 * Removes an item from the FishEye menu
	 * @param icon URL of the icon to get the item. If the icon was not found, the method does nothing.
	 */
	public void remove(String icon) {
		for (int i = 0; i < items.size(); i++) {
			Item item = (Item) items.get(i);
			if (item.icon.equals(icon)) {
				items.remove(item);
				if (isAttached()) {
					removeItem(item);
				}
			}
		}
	}

	/**
	 * Creates all the items of the FishEye menu
	 * @see #buildItem(com.objetdirect.tatami.client.FishEye.Item)
	 */
	private void buildItems() {
		for (int i = 0; i < items.size(); i++) {
			Item item = (Item) items.get(i);
			buildItem(item);
		}
	}

	/**
	 * Creates an Item for the FishEye menu
	 * @param item Item to build
	 */
	private void buildItem(Item item) {
		JavaScriptObject child = createFishEyeItem(getDojoWidget(), item.icon,item.caption);
		addChildWidget(getDojoWidget(), child);
		item.child = child;
	}

	/**
	 * Removes all the items from the FishEye menu
	 * @see #removeItem(com.objetdirect.tatami.client.FishEye.Item)
	 */
	private void removeItems() {
		for (int i = 0; i < items.size(); i++) {
			Item item = (Item) items.get(i);
			removeItem(item);
		}
	}

	/**
	 * Removes an item from the FishEye menu (the item of the DOJO widget et destroy this item)
	 * @param item item to destroy.
	 */
	private void removeItem(Item item) {
		removeChildWidget(getDojoWidget(), item.child);
		DojoController.getInstance().destroy(item.child);
		item.child = null;
	}

	/**
	 * Deletes all DOJO items added to the FishEye menu.
	 * Don't use this methode directly, it is called by the <code>DojoController</code> when  
	 * the GWT widget is detached from the browser.
	 */
    public void doBeforeDestruction() {
    	removeItems(); 
	}
	
    
    /**
     * Returns the command at the index position in the FishEye menu.
     * @param i index position for the command to find
     * @return the command at the index position or null if the command is not found. 
     */
    public Command getCommand(int i) {
    	Command theCommand = null;
    	if ( i < items.size() && i >= 0) {
    		Item item = (Item)items.get(i);
    		theCommand = item.command;
    	}
        return theCommand;
    }
	
    /**
     * Returns the URL of the icon at the index position 
     * @param i index position for the URL to find
     * @return the URL at the index position or null if the URL is not found. 
     */
    public String getIcon(int i) {
    	String theIcone = null;
    	if ( i < items.size() && i >= 0) {
    		Item item = (Item)items.get(i);
    		theIcone = item.icon;
    	}
        return theIcone;
    }
    
    
    /**
     * Returns the Command of the icon from the URL of the associated icon. 
     * @param icon the URL of the icon associated to the command to find
     * @return the command associated with the given URL or null if the URL is not found. 
     */
    public Command getCommand(String icon) {
    	Command theCommand = null;
    	boolean stop = false;
    	for (int i = 0; i < items.size() && !stop; i++) {
			Item item = (Item) items.get(i);
		    if ( item.icon.equals(icon)) {
		    	stop = true;
		    	theCommand = item.command;
		    }
		}
    	return theCommand;
    }
    
    /**
     * Returns all the icons from the FishEye menu. 
     * @return an array containing the URL (in String) of the icons. If the menu doesn't have icons, 
     *         so an  empty array is returned.
     */
    public String[] getIcons() {
    	String[] icons = new String[items.size()];
    	for ( int i=0; i < icons.length; i++) {
    		Item item = (Item)items.get(i);
    		icons[i] = item.icon;
    	}
    	return icons;
    }
    
    
	/**
	 * Creates the DOJO widget FishEyeList.
	/**
	 * Creates a FishEye menu with no icons
	 * 
	 * @param itemWidth               width of menu item (in pixels) in it's dormant state (when the mouse is far away)
	 * @param itemHeight              height of menu item (in pixels) in it's dormant state (when the mouse is far away)
	 * @param itemMaxWidth            width of menu item (in pixels) in it's fully enlarged state (when the mouse is directly over it)
	 * @param itemMaxHeight           height of menu item (in pixels) in it's fully enlarged state (when the mouse is directly over it)
	 * @param orientation             orientation of the menu, either HORIZONTAL or VERTICAL
	 * @param effectUnits             controls how much reaction the menu makes, relative to the distance of the mouse from the menu 
	 * @param itemPadding             padding (in pixels) betweeen each menu item
	 * @param attachEdge              controls the border that the menu items don't expand past;
		                              for example, if set to "top", then the menu items will drop downwards as they expand. 
		                              Possible values are : CENTER, TOP, BOTTOM, LEFT, RIGHT
	 * @param labelEdge               controls were the labels show up in relation to the menu item icons.  Possible values are : CENTER, TOP, BOTTOM, LEFT, RIGHT
	 * @param enableCrappySvgSupport  for browsers that support svg, use the svg image (specified in FisheyeListIem.svgSrc)
		                              rather than the iconSrc image attribute
	 * @param conservativeTrigger     if true, don't start enlarging menu items until mouse is over an image;
	                                  if false, start enlarging menu items as the mouse moves near them. 
	 *
	 * @return la widget DOJO construite par cette méthode
	 */
	private native JavaScriptObject createFishEye(int itemWidth,
			int itemHeight, int itemMaxWidth, int itemMaxHeight,
			String orientation, int effectUnits, int itemPadding,
			String attachEdge, String labelEdge,
			boolean enableCrappySvgSupport, boolean conservativeTrigger)
	/*-{
	 var fisheye = $wnd.dojo.widget.createWidget(
	 "FisheyeList",
	 {
	 itemWidth: itemWidth,
	 itemHeight: itemHeight,
	 itemMaxWidth: itemMaxWidth,
	 itemMaxHeight:itemMaxHeight,
	 orientation: orientation,
	 effectUnits: effectUnits,
	 itemPadding: itemPadding,
	 attachEdge: attachEdge,
	 labelEdge: labelEdge,
	 enableCrappySvgSupport: enableCrappySvgSupport,
	 conservativeTrigger: conservativeTrigger
	 }
	 );
	 return fisheye;
	 }-*/;

	/**
	 * Adds an item to the DOJO widget FishEye menu.
	 * @param dojoWidget the DOJO widget FishEye menu
	 * @param child the item to add
	 */
	private native void addChildWidget(JavaScriptObject dojoWidget,JavaScriptObject child)
	/*-{
	 dojoWidget.addChild(child);
	 }-*/;

	/**
	 * Removes an item from the DOJO widget FishEye menu.
	 * Re-initializes also the position of the FishEye menu.
	 * @param dojoWidget the DOJO widget 
	 * @param child the item to remove
	 */
	private native void removeChildWidget(JavaScriptObject dojoWidget,JavaScriptObject child)
	/*-{
	 dojoWidget.removeChild(child);
	 dojoWidget._initializePositioning();
	 }-*/;

	/**
	 * Creates the DOJO widget which fulfilling an <code>Item</code>.
	 * Warning this method doesn't add the item to the FishEye menu.
	 * @param dojoWidget the DOJO widget FishEye 
	 * @param icon URL of the icon 
	 * @param caption title for the item
	 * @return the item created
	 */
	private native JavaScriptObject createFishEyeItem(
			JavaScriptObject dojoWidget, String icon, String caption)
	/*-{
	  var child = $wnd.dojo.widget.createWidget("TatamiFisheyeItem",
	           {
	            parent:  dojoWidget,
	            iconSrc: icon,
	            caption: caption
	           }
	 );
	 return child;
	 }-*/;

	
	/**
	 * Creates the DOJO items for the FishEye menu when GWT widget is attached on the browser.
	 * Don't use this method directly the <code>DojoController</code> call the method.
	 */
	public void doAfterCreation() {
		buildItems();
	}
	
		
	
	/**
	 * Returns the name of the DOJO widget.
	 * @return "FishEyeList".
	 */
	public String getDojoName() {
		return "FisheyeList";
	}

	/**
	 * Counts the number of item in the FishEye menu.
	 * @return the number of items in the FishEye menu.
	 */
	public int countItems() {
		return this.items.size();
	}

	/**
	 * Returns the index position in the FishEye menu of an icon. 
	 * @param icon the icon to find.
	 * @return the index of the first icon found in the FishEye menu. 
	 */
	public int indexOf(String icon) {
		int index = -1;
		boolean stop = false;
		for ( int i =0; i< items.size() && !stop;i++) {
			Item item = (Item)items.get(i);
			if ( item.icon.equals(icon)) {
				stop = true;
				index = i;
			}
		}
		return index;
	}
	
	
	/**
	 * Defines the DOJO widget Item (not create one). The name of the widget is TatamiFisheyeItem.
	 * Arms also the callback to catch the mouse event or these items. 
	 */
	public void onDojoLoad() {
		defineTatamiItem();
	}

	/**
	 * Defines the DOJO widget Item (not create one). The name of the widget is TatamiFisheyeItem.
	 * Arms also the callback to catch the mouse event or these items. 
	 */
	private native void defineTatamiItem()
	/*-{
	 $wnd.dojo.widget.defineWidget("dojo.widget.TatamiFisheyeItem", $wnd.dojo.widget.FisheyeListItem, {
	 onClick:function (e) {
	 this.parent.gwtWidget.@com.objetdirect.tatami.client.FishEye::dispatchClick(Ljava/lang/String;)(this.iconSrc);
	 }});
	 
	 }-*/;

	/**
	 * Method invoked by the DOJO widget when a item is selected.
	 * This method execute the command associated to the item.
	 * @param icon the URL of the icon corresponding to the item.
	 */
	protected void dispatchClick(String icon) {
		for (int i = 0; i < items.size(); i++) {
			Item item = (Item) items.get(i);
			if (item.icon.equals(icon)) {
				if (item.command != null) {
					item.command.execute();
				}
			}
		}
	}

	/**
	 * Asks to the DOJO widget to reset its location 
	 * @param dojoWidget DOJO widget to reset the location
	 */
	private native void resetLocation(JavaScriptObject dojoWidget)
	/*-{
	 dojoWidget._initializePositioning();
	 }-*/;

	
	/**
	 * Resets the location of the fishEye menu
	 *
	 */
	public void resetLocation() {
		resetLocation(getDojoWidget());
	}
	
	/**
	 * Inner class for the FishEye. a JAVA Item corresponding to the DOJO Item.
	 * An item  has an icon, a title, and a command (being able to be null).and a child
	 * @author Henri
	 * 
	 */
	private class Item {
		String icon;
		String caption;
		Command command;
		JavaScriptObject child;

		/**
		 * Creates a Item, that will be added to the FishEye
		 * @param icon icon corresponding to the item
		 * @param caption title for the item (ToolTip)
		 * @param command the command to execute when a click is caught on the icon
		 */
		public Item(String icon, String caption, Command command) {
			this.icon = icon;
			this.caption = caption;
			this.command = command;
		}

	}

}
