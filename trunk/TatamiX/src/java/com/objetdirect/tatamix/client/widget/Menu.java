package com.objetdirect.tatamix.client.widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Widget;

/**
 * <p>
 * The <code>Menu</code> aims to create an "accessible" menu. The menu uses
 * hyperlink and also "ul" list.
 * </p>
 * <p>
 * Foreach item a command is associated. The text for the item can be HTML code.
 * To change the CSS Style, use your owner class style or rewrite the style
 * declared for <code>ul, li, a</code> elements.
 * </p>
 * 
 * @author Vianney
 * 
 */
public class Menu extends Composite {

   private List<ListSelectionListener> listeners;

   /**
    * The menu to add the item
    */
   private HTMLList menu;

   /**
    * The last widget that is set active
    */
   private Widget lastActive;

   /**
    * The listener of the click event used by each item
    */
   private ClickHandler itemListener;

   /**
    * Map to associate item and Command
    */
   private Map<Hyperlink, Command> actionMap;

   /**
    * Map to determinate if an item is selectable.
    * An item selectable fire the ListSelectioneListener
    */
   private Map<Hyperlink, Boolean> selectMap;
   
   private Map<Hyperlink,HandlerRegistration> itemRegistration;

   /**
    * Creates an empty menu
    * 
    */
   public Menu() {
      menu = new HTMLList();
      actionMap = new HashMap<Hyperlink, Command>();
      selectMap = new HashMap<Hyperlink, Boolean>();
      itemRegistration = new HashMap<Hyperlink, HandlerRegistration>();

      initWidget(menu);
      setStylePrimaryName("tatamix-Menu");
      //creates the clickListener for each item
      itemListener = new ClickHandler() {

         public void onClick(ClickEvent event) {
        	 doClick((Widget)event.getSource());
           
         }

      };

   }
   
   
   
   
   private void doClick(Widget item) {
	   Command cmd = (Command) actionMap.get(item);
       boolean enabled = true;
       // si la command est une action
       // on verifie si elle est enabled pour executer l'action
       if (cmd instanceof AbstractAction) {
          enabled = ((AbstractAction) cmd).isEnabled();
       }

       if (enabled) {
          int index = menu.indexOf(item);

          if (index != -1 && isSelectable(item)) {
             activeItem(lastActive, false);
             lastActive = item;
             activeItem(lastActive, true);
             fireListSelectionListeners(index);
          }
          //event the item is not selectable we execute the command. 
          if (cmd != null) {

             cmd.execute();
          }

       }
   }

   /**
    * Returns if the given sender is selectable or not.
    * @param sender the widget that receive a click event.
    * @return <code>true</code> if the widget is selectable, <code>false</code> otherwise.
    */
   private boolean isSelectable(Widget sender) {
      boolean result = false;
      if (selectMap.containsKey(sender)) {
         result = selectMap.get(sender);
      }
      return result;
   }

   /**
    * Fires the SelectionListener at the giv index
    * @param index the index of the widget that received the click event.
    */
   protected void fireListSelectionListeners(int index) {

      if (listeners != null) {
         ListSelectionEvent event = new ListSelectionEvent(this, index);
         for (ListSelectionListener listener : listeners) {
            listener.valueChanged(event);
         }
      }
   }

   /**
    * Adds a <code>ListSelectionListener</code> to this menu.
    * @param listener the listener to add.
    */
   public void addListSelectionListener(ListSelectionListener listener) {
      if (listeners == null) {
         listeners = new ArrayList<ListSelectionListener>();
      }
      listeners.add(listener);

   }

   /**
    * Removes the <code>ListSelectionListener</code> from this menu.
    * @param listener the listener to remove.
    */
   public void removeListSelectionListener(ListSelectionListener listener) {
      if (listeners != null) {
         listeners.remove(listener);
      }

   }

   /**
    * Unselects the items of this menu. 
    */
   public void unSelectAllItem() {
      lastActive = null;
      Iterator<Hyperlink> ite = actionMap.keySet().iterator();
      while (ite.hasNext()) {
         Widget w = ite.next();
         activeItem(w, false);
      }
   }

   /**
    * Active or not the item corresponding to the given widget.
    * @param sender the widget of the item to activate or not.
    * @param active <code>true</code> to set the item active, the CSS class will
    *               be modified adding or removing the active CSS class "active".
    */
   private void activeItem(Widget sender, boolean active) {
      if (sender != null) {
         Element el = sender.getElement();
         Element li = el.getParentElement();
         if (li != null) {
            String currentClass = li.getClassName();
            if (currentClass == null) {
               currentClass = "";
            }
            if (active) {
               if (!currentClass.contains("active")) {
                  li.setClassName(currentClass + " active");
               }

            }
            else {
               li.setClassName(currentClass.replace("active", ""));
            }
         }
      }

   }

   /**
    * Selects the specified item.
    * @param index the index of the item to select.
    */
   public void selectItem(int index) {
      Widget sender = menu.getWidget(index);
      if (sender != null) {
          doClick(sender);
      }
   }

   /**
    * Adds a new item for the menu
    * 
    * @param text
    *           the text of the item, should not be <code>null</code>
    * @param tokenHistory
    *           the history token used when the item is selected, should not be
    *           <code>null</code>
    * @param cmd
    *           the <code>Command</code> executed when the item is selected, can
    *           be <code>null</code>
    */
   public void add(String text, String tokenHistory, Command cmd) {
      this.add(text, tokenHistory, cmd, true);

   }

   /**
    * Adds a new item for the menu
    * 
    * @param text
    *           the text of the item, should not be <code>null</code>
    * @param tokenHistory
    *           the histoy token used when the item is selected, should not be
    *           <code>null</code>
    * @param cmd
    *           the <code>Command</code> executed when the item is selected, can
    *           be <code>null</code>
    * @param selectable
    *           boolean to determinate if the item will be "selectable". If the
    *           <code>false</code> no <code>ListSelectionEvent</code> will be
    *           fired when the item will execute the command
    */
   public void add(String text, String tokenHistory, Command cmd, boolean selectable) {
      Hyperlink item = new Hyperlink();
      item.setHTML(text);
      item.setTargetHistoryToken(tokenHistory);

      actionMap.put(item, cmd);
      selectMap.put(item, selectable);
      itemRegistration.put(item,item.addClickHandler(itemListener));
      menu.add(item);

   }

   /**
    * Removes an item at the specified index
    * 
    * @param index
    *           the index of the item to remove
    */
   public void remove(int index) {
      Hyperlink item = getLinkAt(index);
      HandlerRegistration hr = itemRegistration.get(item);
      if (hr != null) {
    	  hr.removeHandler();
      }
      if ( item != null) {
    	  menu.remove(index);
      }
   }

   /**
    * Returns the number of items in this <code>Menu</code>
    * 
    * @return the number of items in this <code>Menu</code>
    */
   public int countItems() {
      return menu.countItems();
   }

   /**
    * Removes all items in this <code>Menu</code>
    * 
    * 
    */
   public void clear() {
      Iterator<Widget> ite = menu.iterator();
      while (ite.hasNext()) {
         Hyperlink item = (Hyperlink) ite.next();
         HandlerRegistration hr = itemRegistration.get(item);
         if ( hr != null) {
        	 hr.removeHandler();
         }
         
         ite.remove();
      }

   }

   /**
    * Returns the <code>Command</code> at the specified index
    * 
    * @param index
    *           the index of the <code>Command</code>
    * @return the <code>Command</code> at the specified index, <code>null</code>
    *         if no <code>Command</code> was associated
    * @throws ArrayOutOfBoundException
    *            if index is greater than the size of the menu or negative
    */
   public Command getCommandAt(int index) {
      Hyperlink item = getLinkAt(index);
      return (Command) this.actionMap.get(item);
   }

   /**
    * Sets the <code>Command</code> at the specified index
    * 
    * @param cmd
    *           the new <code>Command</code> for the item
    * @param index
    *           the index of the item to update
    */
   public void setCommandAt(Command cmd, int index) {
      Hyperlink item = getLinkAt(index);
      actionMap.put(item, cmd);

   }

   /**
    * Sets the history token of the specified item
    * 
    * @param token
    *           the new token of the item to update, should not be
    *           <code>null</code>
    * @param index
    *           the index of the item
    */
   public void setTokenHistoryAt(String token, int index) {
      Hyperlink item = getLinkAt(index);
      item.setTargetHistoryToken(token);
   }

   /**
    * Sets the text of the specified item
    * 
    * @param text
    *           the new text of the item, should not be <code>null</code>
    * @param index
    *           the index of the item to update
    */
   public void setTextAt(String text, int index) {
      Hyperlink item = getLinkAt(index);
      item.setText(text);
   }

   /**
    * Return the text of the item at the specified index
    * 
    * @param index
    *           the index of the item
    * @return the text of the item at the specified index
    */
   public String getTextAt(int index) {
      Hyperlink item = getLinkAt(index);
      return item.getHTML();
   }

   /**
    * Returns the history token of the item at the specified index
    * 
    * @param index
    *           the index of the item
    * @return the history token of the item at the specified index
    */
   public String getTokenHistoryAt(int index) {
      Hyperlink item = getLinkAt(index);
      return item.getTargetHistoryToken();
   }

   /**
    * Returns the item at the specified index
    * 
    * @param index
    *           the index of the item to return
    * @return the <code>HyperLink</code> widget which represent the item.
    * @throws ArrayOutOfBoundException
    *            if index is greater than the size of the menu or negative
    */
   private Hyperlink getLinkAt(int index) {
      return (Hyperlink) menu.getWidget(index);
   }

   /**
    * sets the CSS style at the specified index of the menu.
    * @param index the index in the menu.
    * @param styleName the CSS class name.
    */
   public void setStyleAt(int index, String styleName) {
      menu.setStyleAt(index, styleName);

   }

}// end of class
