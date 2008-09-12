package com.objetdirect.tatamix.client.widget;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.MouseListenerCollection;
import com.google.gwt.user.client.ui.MouseWheelListener;
import com.google.gwt.user.client.ui.MouseWheelListenerCollection;

public class Paragraph extends FocusWidget implements HasHTML {

	private String text;


	/** listens mouse event as mouseout, mouseover ect... */
	private MouseListenerCollection mouseListeners;

	private MouseWheelListenerCollection mouseWheelListeners;



	public Paragraph() {
		super();
		setElement(DOM.createElement("P"));
		sinkEvents( Event.MOUSEEVENTS | Event.ONMOUSEWHEEL);
		sinkEvents(Event.ONCLICK);
	}



	public String getHTML() {
		return DOM.getInnerHTML(getElement());
	}

	public void setHTML(String html) {
		this.text = html;
		DOM.setInnerHTML(getElement(), text);

	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
		if (text != null) {
			DOM.setInnerText(getElement(), text);
		}

	}


	/**
	 * Adds a mouse listener to this <code>Title</code>
	 *
	 * @param listener
	 */
	public void addMouseListener(MouseListener listener) {
		if (mouseListeners == null) {
			mouseListeners = new MouseListenerCollection();
		}
		mouseListeners.add(listener);
	}

	/**
	 * Adds a mouse wheel listener to this <code>Title</code>
	 *
	 * @param listener
	 */
	public void addMouseWheelListener(MouseWheelListener listener) {
		if (mouseWheelListeners == null) {
			mouseWheelListeners = new MouseWheelListenerCollection();
		}
		mouseWheelListeners.add(listener);
	}




	public void onBrowserEvent(Event event) {
		super.onBrowserEvent(event);
		switch (DOM.eventGetType(event)) {


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



	/**
	 *  Removes a <code>MouseListener</code>
	 */
	public void removeMouseListener(MouseListener listener) {
		if (mouseListeners != null) {
			mouseListeners.remove(listener);
		}
	}

	/**
	 * Removes a <code>MouseWheelListener</code>
	 */
	public void removeMouseWheelListener(MouseWheelListener listener) {
		if (mouseWheelListeners != null) {
			mouseWheelListeners.remove(listener);
		}
	}



}
