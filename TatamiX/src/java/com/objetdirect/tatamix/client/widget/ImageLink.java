package com.objetdirect.tatamix.client.widget;

import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.Image;

public class ImageLink extends FocusWidget implements Enablable {

    private String imageSrc;
    private String alt;
    private String href = "#";
    private ImageElement image;
    private AnchorElement anchorElem;
    private String targetHistoryToken;
    private Command command;
    

    /**
     * Creates an empty <code>ImageLink</code>.
     *
     *
     */
    public ImageLink() {
        super();
        anchorElem = Document.get().createAnchorElement();
        anchorElem.setHref(href);
        setElement(anchorElem);
        image = Document.get().createImageElement();
        //add the image to the anchor element
        anchorElem.appendChild(image);

        addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent event) {
                if (command != null && isEnabled()) {
                    command.execute();
                }
            }
        });

        
    }

    /**
     * Creates an <code>ImageLink</code>
     * @param src the URL of the image to display
     * @param alt the value of the "alt" attribute of the image if the URL is not found by the browser
     */
    public ImageLink(String src, String alt) {
        this();
        setImageSrc(src);
        setAlt(alt);


    }

    /**
     * Creates an <code>ImageLink</code> from the URL of the given <code>Image</code>
     * @param img the <code>Image</code> use to get an URL
     * @param alt the value of the "alt" attribute of the image if the URL is not found by the browser
     */
    public ImageLink(Image img, String alt) {
        this(img.getUrl(), alt);
    }

   
    /**
     * Returns the URL of the image.
     * @return the URL of the image
     */
    public String getImageSrc() {
        return this.imageSrc;
    }

    /**
     * Returns the "alt" attribut value of the image.
     * @return  the "alt" attribut value of the image.
     */
    public String getAlt() {
        return this.alt;
    }

    /**
     * Sets the URL of the image to display
     * @param src the URL of the image to display
     */
    public final void setImageSrc(String src) {
        this.imageSrc = src;
        image.setSrc(imageSrc);
        
    }

    /**
     * Sets the "Alt" attribute of the image. This attribute is used when the URL of the image is not found.
     * This parameter should not be <code>null</code>
     * @param alt the value of the attribute
     */
    public final void setAlt(String alt) {
        this.alt = alt;
        image.setAlt(alt);
        
    }

    /**
     * Gets the history token referenced by this hyperlink.
     *
     * @return the target history token
     * @see #setTargetHistoryToken
     */
    public String getTargetHistoryToken() {
        return targetHistoryToken;
    }

    public void onBrowserEvent(Event event) {
        if (DOM.eventGetType(event) == Event.ONCLICK) {

            History.newItem(targetHistoryToken);
            if (href.startsWith("#")) {
                DOM.eventPreventDefault(event);
            }
        }

      
        super.onBrowserEvent(event);
    }

    /**
     * Sets the history token referenced by this hyperlink. This is the history
     * token that will be passed to {@link History#newItem} when this link is
     * clicked.
     *
     * @param targetHistoryToken the new target history token
     */
    public void setTargetHistoryToken(String targetHistoryToken) {
        href = "#" + targetHistoryToken;
        this.targetHistoryToken = targetHistoryToken;
        anchorElem.setHref(href);
        
    }

    public void setHref(String href) {
        this.href = href;
        targetHistoryToken = null;
        anchorElem.setHref(href);
        
    }

    public String getHref() {
        return this.href;
    }

    /**
     * Sets the command associated to this <code>ImageLink</code>
     * You can set an <code>AbstractAction</code> that it permits to directly set
     * the icon, the alt attribute and the title if the properties were correctly set.
     * @param cmd the <code>Command</code> to associate
     */
    public void setCommand(Command cmd) {

        if (command instanceof AbstractAction) {
            AbstractAction action = (AbstractAction) command;
            action.removeEnablable(this);

        }
        this.command = cmd;
        if (cmd instanceof AbstractAction) {
            AbstractAction action = (AbstractAction) cmd;
            action.addEnablable(this);
            setAlt((String) action.getValue(AbstractAction.NAME));
            setTitle((String) action.getValue(AbstractAction.LONG_DESCRIPTION));
            String src = (String) action.getValue(AbstractAction.ICON_SRC);
            if (src != null) {
                setImageSrc(src);
            }
        }
    }
}//end of class
