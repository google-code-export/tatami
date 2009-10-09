package com.objetdirect.tatamix.client.widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.objetdirect.tatamix.client.widget.renderer.ListRenderer;
/**<p>
 * This widget aims to list some elements.
 * Each element can be selectable with a <code>ChechBox</code> button.
 * </p>
 * <p>
 * The CSS class of this widget is <code>.listChecker</code>
 * The CSS class used when an item is selected is <code>.gwt-CheckBox-selected</code>
 * </p>
 * <p>
 * For example if you want that the checkboxes are displayed at the right of the label,  writes
 * the CSS rules belows :
 * <code>
 *  <pre>
    .gwt-CheckBox input {
      float:right;
      width:auto;
   }

   .gwt-CheckBox {
      display:block;
   }
 * </pre>
 * </code>
 * </p>
 *
 * @author Vianney.
 *
 * TODO move this widget to the Gwtools project
 *
 */
public class ListChecker<T> extends Composite   {

	private HTMLList list;
	private ListRenderer renderer;
	private Map<CheckBox,T> model;
    private List<T> selected;
    private ClickHandler listener;

    /**
     * Creates  widget with an empty list.
     *
     */
	public ListChecker() {
		super();
		list = new HTMLList();
		initWidget(list);
		setStylePrimaryName("listChecker");
		selected = new ArrayList<T>();
		model = new HashMap<CheckBox,T>();
		listener = new ClickHandler() {
			public void onClick(ClickEvent event) {
				CheckBox cb = (CheckBox)event.getSource();
				T item = model.get(cb);
				if (cb.getValue()) {
					selected.add(item);
					cb.addStyleDependentName("selected");
				} else {
					selected.remove(item);
					cb.removeStyleDependentName("selected");
				}
			}
		};

	}

    /**
     * Sets the list of items.
     * @param items a list of <code>Object</code>
     */
	public void setItems(List<T> items) {
		selected.clear();
		this.list.clear();
		model.clear();
		Iterator<T> ite = items.iterator();
		while (ite.hasNext()) {
			addItem(ite.next());
		}
	}


	/**
	 * Sets the rendere to use to display an item.If no renderer was set, the method
	 * <code>toString</code> is used
	 * @param renderer the renderer to use
	 */
	public void setRenderer(ListRenderer renderer) {
		this.renderer = renderer;
	}

	/**
	 * Adds a new object in the list
	 * @param item the object to add
	 */
    public void addItem(T item) {
    	int index = list.countItems();
    	CheckBox cb = new CheckBox();
    	cb.addClickHandler(listener);
    	String text = item.toString();
    	if ( renderer != null) {
    	  text = renderer.getCellRendererValue(item,index);
    	}
    	cb.setText(text);
    	model.put(cb,item);
    	list.add(cb);
    }

    /**
     * Removes an item at the specified index in the list
     * @param index the index of the item
     */
    public void removeItemAt(int index) {
    	CheckBox cb = (CheckBox)list.getWidget(index);
    	list.remove(index);
    	Object item = model.remove(cb);
    	selected.remove(item);

    }

    /**
     * Returns the renderer used to display the items
     * @return the renderer used to display the items, <code>null</code> if no render is used
     */
    public ListRenderer getRenderer() {
    	return this.renderer;
    }

    /**
     * Removes the given item from the list.
     * @param item the item to remove
     */
    public void removeItem(T item) {
    	selected.remove(item);
    	Iterator<CheckBox> ite = model.keySet().iterator();
    	boolean stop = false;
    	while ( ite.hasNext() && !stop) {
    		CheckBox key = ite.next();
    		T itemToRemove = model.get(key);
    		if ( item.equals(itemToRemove)) {
    			stop = true;
    			list.remove(key);
    			model.remove(key);
    		}
    	}

    }

    /**
     * Returns all the items of the list
     * @return all the items of the list
     */
   public List<T> getItems() {
	   List<T> result = null;
	   if ( !model.isEmpty()) {
	   result =  new ArrayList<T>(model.values());
	   }
	   return result;
   }

   /**
    * Returns the items which were selected in the list.
    * @return the items which were selected in the list, an empty array is returned if there are
    *         item selected.
    *         
    */
   @SuppressWarnings("unchecked")
   public T[] getItemsSelected() {
        T[] selection = (T[])new Object[selected.size()];
	    Iterator<T> ite = selected.iterator();
	    for (int i=0;ite.hasNext();i++) {
	    	selection[i] = ite.next();
	    }
        return selection;
   }

   /**
    * Return the last item which was selected in the list
    * @return the last item which was selected in the list. Retuns <code>null</code>
    *         if there are no item selected.
    */
   public T getItemSelected() {
	   T result = null;
	   if ( !selected.isEmpty()) {
		   result = selected.get(selected.size()-1);
	   }
	   return result;
   }



}//end of class
