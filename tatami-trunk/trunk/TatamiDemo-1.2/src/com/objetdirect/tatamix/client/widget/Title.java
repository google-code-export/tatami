package com.objetdirect.tatamix.client.widget;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.MouseListenerCollection;
import com.google.gwt.user.client.ui.MouseWheelListener;
import com.google.gwt.user.client.ui.MouseWheelListenerCollection;
import com.google.gwt.user.client.ui.SourcesMouseEvents;
import com.google.gwt.user.client.ui.SourcesMouseWheelEvents;

/**
 * This class permits to generate some title in your application. It means that
 * we have a <code>h1, h2,h3...</code> HTML elements instead of a simple
 * <code>div</code> element.
 *
 * @author Vianney Grassaud
 *
 */
public class Title extends FocusWidget implements HasHTML, 	SourcesMouseEvents, SourcesMouseWheelEvents {

	/** level to create H1 element */
	public static final int H1 = 1;

	/** level to create H2 element */
	public static final int H2 = 2;

	/** level to create H3 element */
	public static final int H3 = 3;

	/** level to create H4 element */
	public static final int H4 = 4;

	/** level to create H5 element */
	public static final int H5 = 5;

	/** level to create H6 element */
	public static final int H6 = 6;

	private String text;

	/**
	 * The level of this <code>Title</code>
	 */
	private int level = 1;

	private boolean asHTML = false;


	/** listens mouse event as mouseout, mouseover ect... */
	private MouseListenerCollection mouseListeners;

	private MouseWheelListenerCollection mouseWheelListeners;



	/**
	 * Create an empty title with the level to 1 (h1)
	 *
	 */
	public Title() {
		super();
		initTitle(level);


	}

	/**
	 * Creates a <code>Title</code> with level set to 1.
	 *
	 * @param text
	 *            the text according to the <code>Title</code>. The text will
	 *            be not recognized as HTML code
	 */
	public Title(String text) {
		this(text,false);

	}

	/**
	 * Create <code>Title</code> with level set to 1. with some content
	 * @param text the content
	 * @param asHTML if true, the content will be read as HTML code
	 */
	public Title(String text, boolean asHTML) {
		this();
		this.asHTML = asHTML;
		if (asHTML) {
			setHTML(text);
		} else {
			setText(text);
		}
	}

	/**
	 * Inits this <code>Title</code> with the specified level
	 *
	 * @param level
	 *            the level to apply
	 */
	private void initTitle(int level) {


		setElement(DOM.createElement("H" + level));
		sinkEvents( Event.MOUSEEVENTS | Event.ONMOUSEWHEEL);

        if ( asHTML ) {
        	setHTML(text);
        } else {
		    setText(text);
        }

	}

	/**
	 * Sets the level for this <code>Title</code>.
	 * @param level the new level to apply. If level is greater than 6 so the level will be set to 6. And
	 * if the level is lesser than 1, the level will be set to 1.
	 */
	public void setLevel(final int level) {
		int levelTemp  = level;
		if ( levelTemp < 1 ) {
			levelTemp = 1;
		}
		if ( levelTemp > 6) {
			levelTemp = 6;
		}
		if (this.level != levelTemp) {
			initTitle(levelTemp);
		}
		this.level = levelTemp;

	}

	/**
	 * Returns the level of this <code>Title</code>
	 *
	 * @return a level between 1 and 6 inclusive
	 */
	public int getLevel() {
		return this.level;
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

	/**
	 * Returns the text specified for this <code>Title</code>
	 * @return the text
	 */
	public String getText() {
		return this.text;

	}

	/**
	 * Sets the text of this <code>Title</code>. To put HTML code in your
	 * text use the {@link #setHTML(String)} instead.
	 *
	 * @param text
	 *            some text
	 */
	public void setText(String text) {
		this.asHTML = false;
		this.text = text;
		if (text != null) {
			DOM.setInnerText(getElement(), text);
		}

	}

	/**
	 * Returns the HTML code.
	 * @return the HTML text of this <code>Title</code>
	 */
	public String getHTML() {
		return DOM.getInnerHTML(getElement());
	}

	/**
	 * Sets the HTML text of this <code>Title</code>
	 * @param html
	 */
	public void setHTML(String html) {
		this.asHTML = true;
		this.text = html;
		DOM.setInnerHTML(getElement(), text);

	}

	/**
	 * Manage events
	 */
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

}//end of class
