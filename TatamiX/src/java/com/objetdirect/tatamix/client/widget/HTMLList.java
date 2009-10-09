package com.objetdirect.tatamix.client.widget;

import java.util.Iterator;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasMouseOutHandlers;
import com.google.gwt.event.dom.client.HasMouseOverHandlers;
import com.google.gwt.event.dom.client.HasMouseWheelHandlers;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.WidgetCollection;

/**
 * <p>This widget aims to create a list of widget representing by the "UL" element.</p>
 * <p>There is no style declared for this widget.
 *    To change the style of this <code>Widget</code> you can use for example : <br>
 *    <code>
 *    ul { <br>
 *    <b>list-style:</b> square;<br>
 *    } <br>
 *    </code>
 * </p>
 * @author Vianney Grassaud
 *
 */
public class HTMLList extends Panel implements HasClickHandlers, HasMouseOutHandlers,HasMouseOverHandlers, HasMouseWheelHandlers {

	/** the widgets contained by the list*/
	private WidgetCollection items;

	
	/**
	 * Creates an empty list.
	 *
	 *
	 */
	public HTMLList() {
		items = new WidgetCollection(this);


	}


	


	/*
	 * (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.HasWidgets#iterator()
	 */
	public Iterator<Widget> iterator() {
		return items.iterator();
	}

	/**
	 * Adds a new <code>Widget</code> to the list
	 * @param w the widget to add
	 */
	public void add(Widget w) {
		Element li = Document.get().createLIElement();
		li.appendChild(w.getElement());
		getElement().appendChild(li);
		
		items.add(w);
		adopt(w);
	}

	/**
	 * Returns the number of items in the list
	 * @return the number of items in the list
	 */
	public int countItems() {
		return items.size();
	}

	/**
	 * Returns the <code>Widget</code> at the specified index
	 * @param index the index of the widget to get
	 * @return the <code>Widget</code> at the specified index
	 * @throws IndexOutOfBoundsException if the index is out of range
	 */
	public Widget getWidget(int index) {
		return (Widget)items.get(index);
	}

	/**
	 * Removes an item at the specified index
	 * @param index the index of the widget to remove from the list
	 * @return <code>true</code> if the widget was found, <code>false</code> otherwise
	 * @see #remove(Widget)
	 */
	public boolean remove(int index) {
		boolean result = false;
		if ( index < items.size() && index >= 0) {
			Widget w = (Widget)items.get(index);
			orphan(w);
			Node li = getElement().getChildNodes().getItem(index);
    		getElement().removeChild(li);
			items.remove(index);
			result = true;
		}
		return result;
	}

	/**
	 * Removes the given widget from the list
	 * @param w the widget to remove
	 * @return <code>true</code> if the widget was found, <code>false</code> otherwise
	 * @see #remove(int)
	 */
	public boolean remove(Widget w) {

		return remove(items.indexOf(w));
	}

	
	/**
	 * Returns the index of the given widget in the list.
	 * @param w the widget to retrieve
	 * @return the index of the given widget. <code>-1</code> if the widget is not found. 
	 */
	public int indexOf(Widget w) {
		return items.indexOf(w);
	}
	
	
	/**
	 * Sets the style for the LI element at the specified index
	 * @param index
	 * @param style
	 */
	public void setStyleAt(int index,String style) {
        Widget w = this.getWidget(index);
        if ( w != null) {
        	Element el = w.getElement();
            Element li = el.getParentElement();
            li.getStyle().setProperty("className",style);
            
        }

	}

	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return this.addDomHandler(handler, ClickEvent.getType());
	}


	
	public HandlerRegistration addMouseWheelHandler(MouseWheelHandler handler) {
		return this.addDomHandler(handler, MouseWheelEvent.getType());
	}


	@Override
	public HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
	 
		return addDomHandler(handler, MouseOutEvent.getType());
	}


	@Override
	public HandlerRegistration addMouseOverHandler(MouseOverHandler handler) {
		return addDomHandler(handler, MouseOverEvent.getType());
	}
	

}//end of class
