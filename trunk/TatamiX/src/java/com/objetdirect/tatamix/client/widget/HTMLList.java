package com.objetdirect.tatamix.client.widget;

import java.util.Iterator;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.ClickListenerCollection;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.MouseListenerCollection;
import com.google.gwt.user.client.ui.MouseWheelListener;
import com.google.gwt.user.client.ui.MouseWheelListenerCollection;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SourcesClickEvents;
import com.google.gwt.user.client.ui.SourcesMouseEvents;
import com.google.gwt.user.client.ui.SourcesMouseWheelEvents;
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
public class HTMLList extends Panel implements SourcesClickEvents, SourcesMouseEvents, SourcesMouseWheelEvents {

	/** the widgets contained by the list*/
	private WidgetCollection items;

	private ClickListenerCollection clickListeners;

	private MouseListenerCollection mouseListeners;

	private MouseWheelListenerCollection mouseWheelListeners;

	/**
	 * Creates an empty list.
	 *
	 *
	 */
	public HTMLList() {
		items = new WidgetCollection(this);
		setElement(DOM.createElement("ul"));
		sinkEvents(Event.ONCLICK);
		sinkEvents(Event.ONMOUSEWHEEL | Event.MOUSEEVENTS);

	}


	/**
	 * Manage events
	 */
	public void onBrowserEvent(Event event) {
		super.onBrowserEvent(event);
		switch (DOM.eventGetType(event)) {

		case Event.ONCLICK: {
			if ( clickListeners != null) {
				clickListeners.fireClick(this);
			}
			break;
		}
		case Event.ONMOUSEDOWN:
		case Event.ONMOUSEUP:
		case Event.ONMOUSEMOVE:
		case Event.ONMOUSEOVER:
		case Event.ONMOUSEOUT: {
			if (mouseListeners != null) {
				mouseListeners.fireMouseEvent(this, event);
			}
			break;
		}
		case Event.ONMOUSEWHEEL: {
			if (mouseWheelListeners != null) {
				mouseWheelListeners.fireMouseWheelEvent(this, event);
			}
			break;
		}

		}
	}


	/*
	 * (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.HasWidgets#iterator()
	 */
	public Iterator iterator() {
		return items.iterator();
	}

	/**
	 * Adds a new <code>Widget</code> to the list
	 * @param w the widget to add
	 */
	public void add(Widget w) {
		Element li = DOM.createElement("li");
		DOM.appendChild(li,w.getElement());
		DOM.appendChild(getElement(),li);
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

			Element li = DOM.getChild(getElement(), index);
			DOM.removeChild(getElement(),li);
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
            Element li = DOM.getParent(el);
            DOM.setElementProperty(li,"className",style);
        }

	}

	public void addClickListener(ClickListener listener) {
		if ( clickListeners == null) {
			clickListeners = new ClickListenerCollection();
		}
		clickListeners.add(listener);
	}


	public void removeClickListener(ClickListener listener) {
		if ( clickListeners != null) {
			clickListeners.remove(listener);
		}
	}

	public void addMouseListener(MouseListener listener) {
		if ( mouseListeners == null) {
			mouseListeners = new MouseListenerCollection();
		}
		mouseListeners.add(listener);
	}

	public void removeMouseListener(MouseListener listener) {
		if ( mouseListeners != null) {
			mouseListeners.remove(listener);
		}
	}

	public void addMouseWheelListener(MouseWheelListener listener) {
		if ( mouseWheelListeners == null) {
			mouseWheelListeners = new MouseWheelListenerCollection();
		}
		mouseWheelListeners.add(listener);
	}


	public void removeMouseWheelListener(MouseWheelListener listener) {
		if (mouseWheelListeners != null ) {
			mouseWheelListeners.remove(listener);
		}
	}

}//end of class
