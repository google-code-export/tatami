package com.objetdirect.tatamix.client.widget;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.MouseListenerCollection;
import com.google.gwt.user.client.ui.Widget;

public class ImageLink extends FocusWidget implements Enablable {

	private String imageSrc;
	private String alt;
    private Element image;
    private Element anchorElem;
    private String targetHistoryToken;
    private Command command;

    private MouseListenerCollection listenerCollection;

    /**
     * Creates an empty <code>ImageLink</code>.
     *
     *
     */
    public ImageLink() {
    	super();
    	anchorElem = DOM.createAnchor();
		DOM.setElementProperty(anchorElem, "href", "#");
		setElement(anchorElem);
		image = DOM.createImg();
        //add the image to the anchor element
		DOM.appendChild(anchorElem, image);

		addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
			   if ( command != null && isEnabled()) {
				   command.execute();
			   }
			}
		});

		sinkEvents(Event.MOUSEEVENTS);
    }


    /**
     * Creates an <code>ImageLink</code>
     * @param src the URL of the image to display
     * @param alt the value of the "alt" attribute of the image if the URL is not found by the browser
     */
	public ImageLink(String src,String alt) {
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
		this(img.getUrl(),alt);
	}


	public void addMouseListener(MouseListener listener) {
		if ( listenerCollection ==null) {
			listenerCollection = new MouseListenerCollection();
		}
		listenerCollection.add(listener);
	}


	public void removeMouseListener(MouseListener listener) {
		if ( listenerCollection !=null) {
			listenerCollection.remove(listener);
		}
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
		DOM.setImgSrc(image,imageSrc);
	}

	/**
	 * Sets the "Alt" attribute of the image. This attribute is used when the URL of the image is not found.
	 * This parameter should not be <code>null</code>
	 * @param alt the value of the attribute
	 */
	public final void setAlt(String alt) {
        this.alt = alt;
		DOM.setElementProperty(image, "alt", alt);
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
	      DOM.eventPreventDefault(event);
	    }

	    if ( listenerCollection != null) {
	    	switch(DOM.eventGetType(event) ) {
	    	   case Event.ONMOUSEDOWN:{
	    		listenerCollection.fireMouseDown(this,DOM.eventGetClientX(event),DOM.eventGetClientY(event));
	    		break;
    	    	}
	        	case Event.ONMOUSEUP:{
	    		  listenerCollection.fireMouseUp(this,DOM.eventGetClientX(event),DOM.eventGetClientY(event));
	    		  break;
	    	    }
	        	case Event.ONMOUSEOVER:{
	        		listenerCollection.fireMouseEnter(this);
	        		break;
	        	}
	        	case Event.ONMOUSEOUT:{
	        		listenerCollection.fireMouseLeave(this);
	        		break;
	        	}
	        	case Event.MOUSEEVENTS:{
	        		listenerCollection.fireMouseEvent(this,event);
	        		break;
	        	}
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
	    this.targetHistoryToken = targetHistoryToken;
	    DOM.setElementProperty(anchorElem, "href", "#" + targetHistoryToken);
	  }


	  /**
	   * Sets the command associated to this <code>ImageLink</code>
	   * You can set an <code>AbstractAction</code> that it permits to directly set
	   * the icon, the alt attribute and the title if the properties were correctly set.
	   * @param cmd the <code>Command</code> to associate
	   */
	  public void setCommand(Command cmd ) {

		  if ( command instanceof AbstractAction) {
				AbstractAction action = (AbstractAction)command;
				action.removeEnablable(this);

			}
			this.command = cmd;
			if ( cmd instanceof AbstractAction) {
				AbstractAction action = (AbstractAction)cmd;
				action.addEnablable(this);
				setAlt((String)action.getValue(AbstractAction.NAME));
				setTitle((String)action.getValue(AbstractAction.LONG_DESCRIPTION));
				String src = (String)action.getValue(AbstractAction.ICON_SRC);
				if ( src != null) {
					setImageSrc(src);
				}
			}
	  }




}//end of class
