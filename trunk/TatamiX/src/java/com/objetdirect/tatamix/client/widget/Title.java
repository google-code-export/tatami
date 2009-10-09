package com.objetdirect.tatamix.client.widget;

import com.google.gwt.event.dom.client.HasMouseOutHandlers;
import com.google.gwt.event.dom.client.HasMouseOverHandlers;
import com.google.gwt.event.dom.client.HasMouseWheelHandlers;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HasHTML;

/**
 * This class permits to generate some title in your application. It means that
 * we have a <code>h1, h2,h3...</code> HTML elements instead of a simple
 * <code>div</code> element.
 *
 * @author Vianney Grassaud
 *
 */
public class Title extends FocusWidget implements HasHTML  {

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
    

    /**
     * Create an empty title with the level to 1 (h1)
     *
     */
    public Title(int level) {
        super();
        initTitle(level);


    }

    /**
     * Create an empty title with the level to 1 (h1)
     *
     */
    public Title() {
        this(H1);
    }

    public Title(String text) {
        this(H1,text);
    }
    
    
    /**
     * Creates a <code>Title</code> with level set to 1.
     *
     * @param text
     *            the text according to the <code>Title</code>. The text will
     *            be not recognized as HTML code
     */
    public Title(int level, String text) {
        this(level, text, false);

    }

    /**
     * Create <code>Title</code> with level set to 1. with some content
     * @param text the content
     * @param asHTML if true, the content will be read as HTML code
     */
    public Title(int level, String text, boolean asHTML) {
        this(level);
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
        int levelTemp = level;
        if (levelTemp < 1) {
            levelTemp = 1;
        }
        if (levelTemp > 6) {
            levelTemp = 6;
        }
        this.level = levelTemp;
        setElement(DOM.createElement("H" + level));
        sinkEvents(Event.MOUSEEVENTS | Event.ONMOUSEWHEEL);

        if (asHTML) {
            setHTML(text);
        } else {
            setText(text);
        }

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

  

    
}//end of class
