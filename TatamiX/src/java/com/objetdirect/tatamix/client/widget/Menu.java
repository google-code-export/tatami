package com.objetdirect.tatamix.client.widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Widget;


/**
 * <p>The <code>Menu</code> aims to create an "accessible" menu. The menu uses hyperlink and also "ul" list.</p>
 * <p>
 * Foreach item a command is associated. The text for the item can be HTML code.
 * To change the CSS Style, use your owner class style or rewrite the style declared for <code>ul, li, a</code> elements.
 * </p>
 * @author Vianney
 *
 */
public class Menu extends Composite implements ListSelectionListener{

    private List<ListSelectionListener> listeners;
	private HTMLList menu;
	private Widget lastActive;
	private ClickListener itemListener;
	private Map<Hyperlink,Command> actionMap;
	private Map<Hyperlink,Boolean> selectMap;

	/**
	 * Creates an empty menu
	 *
	 */
	public Menu() {
		menu = new HTMLList();
		actionMap = new HashMap<Hyperlink,Command>();
		selectMap = new HashMap<Hyperlink,Boolean>();
		
	    initWidget(menu);
        setStylePrimaryName("tatamix-Menu");
	    itemListener = new ClickListener() {
	    	public void onClick(Widget sender) {
	    		Command cmd = (Command)actionMap.get(sender);
	    		boolean enabled = true;
	    		//si la command est une action
	    		//on verifie si elle est enabled pour executer l'action
	    		if ( cmd instanceof AbstractAction ) {
                       enabled = ((AbstractAction)cmd).isEnabled();
	    		}

	    		if ( enabled) {
	    		
	    		  int index = menu.indexOf(sender);
	    		  
	    		  if ( selectMap.get(sender)) {
	    		    fireListSelectionListeners(index);
	    		  }
   	    		  if ( cmd != null) {
	    			cmd.execute();
  	    		  }
	    		}
	    	}

	    };
	    this.addListSelectionListener(this);

	}

	
	private void fireListSelectionListeners(int index) {
		if ( listeners != null) {
		ListSelectionEvent event = new ListSelectionEvent(this,index);
		for ( ListSelectionListener listener : listeners) {
			listener.valueChanged(event);
		}
		}
	}
	
	
	
	public void addListSelectionListener(ListSelectionListener listener) {
		if ( listeners == null) {
			listeners = new ArrayList<ListSelectionListener>();
		}
		listeners.add(listener);
		
	}
	
	public void removeListSelectionListener(ListSelectionListener listener) {
		if ( listeners != null) {
			listeners.remove(listener);
		}
		
		
	}
	

	public void unSelectAllItem() {
		 lastActive = null;
		 Iterator<Hyperlink> ite = actionMap.keySet().iterator();
		 while (ite.hasNext()) {
			 Widget w = ite.next();
			 activeItem(w,false);
		 }
	}


	private void activeItem(Widget sender,boolean active) {
	    if ( sender != null) {
		Element el = sender.getElement();
        Element li = el.getParentElement();

        String currentClass = li.getClassName();
        if  (currentClass == null) {
        	currentClass = "";
        }
           if (active) {
               if ( !currentClass.contains("active")) {
            	   li.setClassName(currentClass + " active");
               }
        	  
           } else {
        	  li.setClassName(currentClass.replace("active",""));
           }

		}


	}

	
	
	
	
	public void selectItem(int index) {
		Widget sender = menu.getWidget(index);
		if ( sender != null) {
			this.itemListener.onClick(sender);
		}
	}
	

	/**
	 * Adds a new  item for the menu
	 * @param text the text of the item, should not be <code>null</code>
	 * @param tokenHistory the histoy token used when the item is selected, should not be <code>null</code>
	 * @param cmd the <code>Command</code> executed when the item is selected, can be <code>null</code>
	 */
    public void add(String text,String tokenHistory,Command cmd) {
    	this.add(text, tokenHistory, cmd,true);


    }

    
    
    /**
	 * Adds a new  item for the menu
	 * @param text the text of the item, should not be <code>null</code>
	 * @param tokenHistory the histoy token used when the item is selected, should not be <code>null</code>
	 * @param cmd the <code>Command</code> executed when the item is selected, can be <code>null</code>
	 * @param selectable boolean to determinate if the item will be "selectable". If the <code>false</code> no <code>ListSelectionEvent</code> will be fired 
	 * when the item will execute the command 
	 */
    public void add(String text,String tokenHistory,Command cmd,boolean selectable) {
    	Hyperlink item = new Hyperlink();
    	item.setHTML(text);
    	item.setTargetHistoryToken(tokenHistory);

    	actionMap.put(item,cmd);
    	selectMap.put(item,selectable);
    	item.addClickListener(itemListener);
    	menu.add(item);


    }

    
    

    /**
     * Removes an item at the specified index
     * @param index the index of the item to remove
     */
    public void remove(int index) {
    	Hyperlink item =getLinkAt(index);
    	if ( item != null) {
    	  item.removeClickListener(itemListener);
    	  menu.remove(index);
    	}
    }

    /**
     * Returns the number of items in this <code>Menu</code>
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
    	Iterator ite = menu.iterator();
    	while ( ite.hasNext()) {
    		Hyperlink item =(Hyperlink)ite.next();
    		item.removeClickListener(itemListener);
    		ite.remove();
    	}

    }

    /**
     * Returns the <code>Command</code> at the specified index
     * @param index the index of the <code>Command</code>
     * @return  the <code>Command</code> at the specified index, <code>null</code> if no <code>Command</code> was associated
     * @throws ArrayOutOfBoundException if index is greater than the size of the menu or negative
     */
    public Command getCommandAt(int index) {
    	Hyperlink item = getLinkAt(index);
    	return (Command)this.actionMap.get(item);
    }


    /**
     * Sets the <code>Command</code> at the specified index
     * @param cmd the new <code>Command</code> for the item
     * @param index the index of the item to update
     */
    public void setCommandAt(Command cmd,int index) {
    	Hyperlink item = getLinkAt(index);
    	actionMap.put(item, cmd);

    }

    /**
     * Sets the history token of the specified item
     * @param token the new token of the item to update, should not be <code>null</code>
     * @param index the index of the item
     */
    public void setTokenHistoryAt(String token,int index) {
    	Hyperlink item = getLinkAt(index);
    	item.setTargetHistoryToken(token);
    }

    /**
     * Sets the text of the specified item
     * @param text the new text of the item, should not be <code>null</code>
     * @param index the index of the item to update
     */
    public void setTextAt(String text,int index) {
    	Hyperlink item = getLinkAt(index);
    	item.setText(text);
    }

    /**
     * Return the text of the item at the specified index
     * @param index the index of the  item
     * @return the text of the item at the specified index
     */
    public String getTextAt(int index) {
    	Hyperlink item = getLinkAt(index);
    	return item.getHTML();
    }

    /**
     * Returns the history token of the item at the specified index
     * @param index the index of the item
     * @return the history token of the item at the specified index
     */
    public String getTokenHistoryAt(int index) {
    	Hyperlink item = getLinkAt(index);
    	return item.getTargetHistoryToken();
    }

    /**
     * Returns the item at the specified index
     * @param index the index of the item to return
     * @return the <code>HyperLink</code> widget which represent the item.
     * @throws ArrayOutOfBoundException if index is greater than the size of the menu or negative
     */
    private Hyperlink getLinkAt(int index) {
    	return (Hyperlink)menu.getWidget(index);
    }

    public void setStyleAt(int index,String styleName) {
    	menu.setStyleAt(index, styleName);
    	
    }


	
	public void valueChanged(ListSelectionEvent event) {
		  activeItem(lastActive,false);
	      lastActive = menu.getWidget(event.getIndex());
	      activeItem(lastActive,true);
		
	}
    
   
    

}//end of class
