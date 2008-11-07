package com.objetdirect.tatamix.client.widget;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class StackPanel extends Composite  {
 
	private FlowPanel layout;
	public static final String CSS_ITEM = "item";
	public static final String CSS = "MyStackPanel";
	private Map<Title, Widget> map;
	private Title selectedItem;
	private ClickListener titleListener;
	
	/**
	 *Creates an empty <code>MyStackPanel</code>
	 */
	public StackPanel() {
		super();
		layout  = new FlowPanel();
		map = new HashMap<Title, Widget>();
		titleListener = new ClickListener() {
			public void onClick(Widget sender) {
			    
				selectTitle((Title)sender);
			}
			
		};
		initWidget(layout);
		setStylePrimaryName(CSS);
		
    }
	
	/**
	 * Selects the given <code>Title</code> in the stack. If the given
	 * <code>Title</code> is found, then the associated <code>Widget</code> is show, and the ohter widgets are hidden.
	 * The selected <code>Title</code> has the dependent style CSS "selected". 
	 * 
	 * @param title the <code>Title</code> to select
	 */
	private void selectTitle(Title title) {
		Iterator<Title> ite = map.keySet().iterator();
		while ( ite.hasNext()) {
			Title key = (Title)ite.next();
			Widget w = (Widget)map.get(key);
			boolean selected =  key.equals(title);
			if ( w != null) {
				w.setVisible(selected);
		    }
      		if ( selected) {
				key.addStyleDependentName("selected");
				selectedItem = key;
			
			} else {
				key.removeStyleDependentName("selected");
				
			}
			  
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public int countItems() {
		return map.size();
		
	}
	
	
	
	/**
	 * Creates the item it means the couple (<code>Title,Widget</code>) for the stack. 
	 * <p>The <code>Title</code> of an item has the CSS style  <code>CSS_ITEM</code>. Also, a <code>ClickListener</code> is adding to the <code>Title</code>
	 * in order to show the associated <code>Widget</code> when it receive a clik event.
	 * The item is  representing in a <code>FlowPanel</code>
	 * </p>
	 * @param title the <code>Title</code> for the item 
     * @param widget the <code>Widget</code> associated to the <code>Title</code>
	 * @return the <code>Title</code> and the <code>Widget</code> in a <code>FlowPanel</code>
	 */
	private FlowPanel prepareItem(Title title,Widget widget) {
		if ( map.containsKey(title)) {
    		throw new IllegalArgumentException();
    	}
    	map.put(title,widget);
    	title.setStylePrimaryName(CSS_ITEM);
    	title.addClickListener(titleListener);
    	selectTitle(title);
    	FlowPanel item = new FlowPanel();
    	item.add(title);
    	item.add(widget);
    	return item;
	}
	
	
	/**
	 * Adds an item to the stack, it means a couple (<code>Title,Widget</code>)
	 * @param title the <code>Title</code> for the item 
     * @param widget the <code>Widget</code> associated to the <code>Title</code>
	 */
    public void add(Title title,Widget widget) {
    	layout.add(prepareItem(title,widget));
    	
    }
    
    /**
     * Inserts a new item to the stack at the specified index
     * @param title the <code>Title</code> for the item 
     * @param widget the <code>Widget</code> associated to the <code>Title</code>
     * @param index index to insert in the stack
     */
    public void insert(Title title,Widget widget ,int index) {
    	layout.insert(prepareItem(title,widget),index);
    	
    }
    
    /**
     * Removes the given item from the stack
     * @param item the item to remove
     */
    private void removeItem(FlowPanel item) {
    	Title t = (Title)item.getWidget(0);
        t.removeClickListener(titleListener);
    	map.remove(t);
    	layout.remove(item);
    }
    
    /**
     * Removes the <code>Title</code> and so the <code>Widget</code> at the specified index in the stack
     * @param index index of the item (the couple (<code>Title,Widget</code>) in the stack
     * @throws ArrayIndexOutOfBoundsException 
     */
    public void remove(int index) {
    	FlowPanel item = (FlowPanel)layout.getWidget(index);
    	removeItem(item);
    	
    	
    }
    
    /**
     * Removes the given <code>Widget</code> in the stack
     * @param widget the <code>Widget to remove
     * @return <code>true</code> if the widget was found, <code>false</code> otherwise
     */
    public boolean remove(Widget widget) {
    	boolean stop = false;
    	for ( int i=0; i < layout.getWidgetCount() && !stop; i++) {
    		FlowPanel item = (FlowPanel)layout.getWidget(i);
    		int index = item.getWidgetIndex(widget);
    		if ( index != -1 ) {
    			stop = true;
    			removeItem(item);
    			
    		}
    	}
    	return stop;
    	
    }
    
    /**
     * Select the <code>Widget</code> to show in the stack
     * @param index index of the <code>Widget</code> to show
     */
     public void selectItem(int index) {
    	 this.selectTitle(getTitleAt(index));
     }

     /**
      * Return tthe current <code>Widget</code> shown in the stack
      * @return the current <code>Widget</code> shown in the stack
      */
     public Widget getSelectedItem() {
    	 Widget res = null;
    	 if ( selectedItem != null) {
    		res = (Widget) map.get(selectedItem); 
    	 }
    	 return res;
    	 
     }
	
     /**
      * Returns the <code>Title</code> widget at the specified index in the stack
      * @param index the index of the <code>Title</code>
      * @return the <code>Title</code> widget at the specified index
      * @throws ArrayIndexOutOfBoundsException 
      */
    public Title getTitleAt(int index) {
    	FlowPanel item = (FlowPanel)layout.getWidget(index);
    	return  (Title)item.getWidget(0);
    	
    	
    }
   
    /**
     * Sets the text of the <code>Title</code> at the specified index in the stack
     * @param text the new text for the <code>Title</code>
     * @param index index of the <code>Title</code> in the stack
     * @throws ArrayIndexOutOfBoundsException 
     */
    public void setTitleTextAt(String text,int index) {
    	  	Title t = getTitleAt(index);
    	    t.setHTML(text);
    }
    
    /**
     * Sets the level of the <code>Title</code> at the specified index in the stack
     * @param level the new level to apply
     * @param index the index of the <code>Title</code> in the stack
     * @throws ArrayIndexOutOfBoundsException 
     */
    public void setTitleLevelAt(int level,int index) {
	  	Title t = getTitleAt(index);
	    t.setLevel(level);
   }
    
    
    

}//end of class
